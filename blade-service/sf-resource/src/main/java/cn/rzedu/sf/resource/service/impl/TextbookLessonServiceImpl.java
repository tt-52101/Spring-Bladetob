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

import cn.rzedu.sf.resource.entity.TextbookLesson;
import cn.rzedu.sf.resource.mapper.TextbookLessonMapper;
import cn.rzedu.sf.resource.service.ITextbookLessonService;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 教材课程
1.教材textbook表包含的课程
2.部分课程（硬笔）包含二维码
 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Service
public class TextbookLessonServiceImpl extends ServiceImpl<TextbookLessonMapper, TextbookLesson> implements ITextbookLessonService {

	@Override
	public IPage<TextbookLessonVO> selectTextbookLessonPage(IPage<TextbookLessonVO> page, TextbookLessonVO textbookLesson) {
		return page.setRecords(baseMapper.selectTextbookLessonPage(page, textbookLesson));
	}

	@Override
	public List<TextbookLessonVO> findLessonByTextbookId(Integer textbookId) {
		return baseMapper.findLessonByTextbookId(textbookId);
	}

	@Override
	public boolean updateCharCount(Integer id) {
		return SqlHelper.retBool(baseMapper.updateCharCount(id));
	}

	@Override
	public TextbookLesson findLessonByCode(String code) {
		return baseMapper.findLessonByCode(code);
	}

	@Override
	public Map<String, Object> getLessonCharacterInfo(String code) {
		return baseMapper.getLessonCharacterInfo(code);
	}

	@Override
	public boolean removeByTextbookIds(List<Integer> textbookIds) {
		return SqlHelper.retBool(baseMapper.removeByTextbookIds(textbookIds));
	}
}
