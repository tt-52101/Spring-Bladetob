/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.Handwriting;

import cn.rzedu.sf.resource.mapper.HandwritingMapper;
import cn.rzedu.sf.resource.service.IHandwritingService;
import cn.rzedu.sf.resource.vo.HandwritingWordVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2021-04-26
 */
@Service
public class HandwritingServiceImpl extends ServiceImpl<HandwritingMapper, Handwriting> implements IHandwritingService {


	@Override
	public List<HandwritingWordVO> generateHandwriting(List<String> sentences,String standards, String banner, String font, List<String> signs, int userId, String userName) {
		List<HandwritingWordVO> handwritingWordVOS = new ArrayList<>();
		LocalDateTime createDate = LocalDateTime.now();
		String sentence = sentences.toString();
		String sign = signs.toString();
		String sourceAuthor = "颜真卿";
		String sourceInscriptions = "多宝塔碑";
		baseMapper.generateHandwriting(sentence,standards,banner,font,sign,userId,userName,createDate);
		for (String word : sentences){
			HandwritingWordVO handwritingWordVO = baseMapper.selectHandwritingWord(word,font,sourceAuthor,sourceInscriptions);
			handwritingWordVO.setWordType("sentence");
			handwritingWordVOS.add(handwritingWordVO);
		}
		for (String word : signs){
			HandwritingWordVO handwritingWordVO = baseMapper.selectHandwritingWord(word,font,sourceAuthor,sourceInscriptions);
			handwritingWordVO.setWordType("sign");
			handwritingWordVOS.add(handwritingWordVO);
		}
		return handwritingWordVOS;
	}

	@Override
	public HandwritingWordVO selectHandwritingWord(String word, String font, String sourceAuthor, String sourceInscriptions) {

		if(sourceAuthor == null || sourceAuthor.isEmpty()){
			sourceAuthor = "颜真卿";
		}
		if (sourceInscriptions == null || sourceInscriptions.isEmpty()){
			sourceInscriptions = "多宝塔碑";
		}
		return baseMapper.selectHandwritingWord(word,font,sourceAuthor,sourceInscriptions);
	}

	@Override
	public List<String> selectSourceAuthor() {
		return baseMapper.selectSourceAuthor();
	}

	@Override
	public List<HandwritingWordVO> selectSourceInscriptions(String word, String font, String sourceAuthor) {
		return baseMapper.selectSourceInscriptions(word,font,sourceAuthor);
	}

	@Override
	public boolean saveHandwritingWord(String word, String uuid, String font, String sourceAuthor, String sourceInscriptions) {
		LocalDateTime createDate = LocalDateTime.now();
		return SqlHelper.retBool(baseMapper.saveHandwritingWord(word,uuid,font,sourceAuthor,sourceInscriptions,createDate));
	}
}
