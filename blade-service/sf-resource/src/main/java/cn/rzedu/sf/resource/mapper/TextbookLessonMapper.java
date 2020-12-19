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
package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.TextbookLesson;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface TextbookLessonMapper extends BaseMapper<TextbookLesson> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param textbookLesson
	 * @return
	 */
	List<TextbookLessonVO> selectTextbookLessonPage(IPage page, TextbookLessonVO textbookLesson);

	/**
	 * 根据教材id获取课程
	 * @param textbookId
	 * @return
	 */
	List<TextbookLessonVO> findLessonByTextbookId(Integer textbookId);

	/**
	 * 单个课程
	 * @param lessonId
	 * @return
	 */
	TextbookLessonVO findLessonById(Integer lessonId);

	/**
	 * 更新课程汉字和汉字数
	 * @param id
	 * @return
	 */
	int updateCharCount(Integer id);

	/**
	 * 根据课程编号获取课程
	 * @param code
	 * @return
	 */
	TextbookLesson findLessonByCode(String code);

	/**
	 * 根据课程编号获取教材id，课程id，汉字id，教材名，课程名，汉字名
	 * @param code
	 * @return textbookId, lessonId, characterId, textbookName, lessonName, characterName
	 */
	Map<String, Object> getLessonCharacterInfo(String code);

	/**
	 * 根据教材批量删除
	 * @param list  教材ids
	 * @return
	 */
	int removeByTextbookIds(List<Integer> list);
}
