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

import cn.rzedu.sf.resource.entity.TextbookLessonCharacter;
import cn.rzedu.sf.resource.vo.TextbookLessonCharacterVO;
import cn.rzedu.sf.resource.mapper.TextbookLessonCharacterMapper;
import cn.rzedu.sf.resource.service.ITextbookLessonCharacterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 课程相关字体（资源）
1.课程lesson里面包含的字（可关联到资源）
2.相关字关联到汉字表里面的具体某个字，但要通过科目类型区分以确定是软硬哪种资源
 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Service
public class TextbookLessonCharacterServiceImpl extends ServiceImpl<TextbookLessonCharacterMapper, TextbookLessonCharacter> implements ITextbookLessonCharacterService {

	@Override
	public IPage<TextbookLessonCharacterVO> selectTextbookLessonCharacterPage(IPage<TextbookLessonCharacterVO> page, TextbookLessonCharacterVO textbookLessonCharacter) {
		return page.setRecords(baseMapper.selectTextbookLessonCharacterPage(page, textbookLessonCharacter));
	}

	@Override
	public boolean removeByLessonIds(List<Integer> lessonIds) {
		return SqlHelper.retBool(baseMapper.removeByLessonIds(lessonIds));
	}

	@Override
	public List<TextbookLessonCharacter> findByLessonId(Integer lessonId) {
		return baseMapper.findByLessonId(lessonId);
	}

	@Override
	public TextbookLessonCharacter findByLessonIdAndCharacterId(Integer lessonId, Integer characterId) {
		return baseMapper.findByLessonIdAndCharacterId(lessonId, characterId);
	}

	@Override
	public boolean updateVisitedCount(Integer lessonId, Integer characterId) {
		return SqlHelper.retBool(baseMapper.updateVisitedCount(lessonId, characterId));
	}

	@Override
	public boolean removeByTextbookIds(List<Integer> textbookIds) {
		return SqlHelper.retBool(baseMapper.removeByTextbookIds(textbookIds));
	}

	@Override
	public List<TextbookLessonCharacter> findByTextbookId(Integer textbookId) {
		return baseMapper.findByTextbookId(textbookId);
	}
}
