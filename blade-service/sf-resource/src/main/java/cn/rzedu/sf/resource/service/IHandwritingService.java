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
package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.Handwriting;
import cn.rzedu.sf.resource.entity.HandwritingWord;
import cn.rzedu.sf.resource.vo.HandwritingVO;
import cn.rzedu.sf.resource.vo.HandwritingWordVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  服务类
 *
 * @author Blade
 * @since 2021-04-26
 */
public interface IHandwritingService extends IService<Handwriting> {
	/**
	 * 保存集字
	 * @param sentence
	 * @param standards
	 * @param banner
	 * @param font
	 * @param sign
	 * @param userId
	 * @param userName
	 * @return
	 */
	List<HandwritingWordVO> generateHandwriting(List<String> sentence,String standards, String banner, String font, List<String> sign, int userId, String userName);

	/**
	 * 查询字
	 * @param word
	 * @param font
	 * @param sourceAuthor
	 * @param sourceInscriptions
	 * @return
	 */
	HandwritingWordVO selectHandwritingWord(String word,String font,String sourceAuthor,String sourceInscriptions);

	/**
	 * 所属书法作者列表
	 */
	List<String> selectSourceAuthor(String word);

	/**
	 * 所属碑帖分类
	 * @param word
	 * @param font
	 * @param sourceAuthor
	 * @return
	 */
	List<HandwritingWordVO> selectSourceInscriptions(String word,String font,String sourceAuthor);

	boolean saveHandwritingWord(String word,String uuid,String font,String sourceAuthor,String sourceInscriptions);


}

