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

import cn.rzedu.sf.resource.entity.TextbookLessonCharacter;
import cn.rzedu.sf.resource.vo.TextbookLessonCharacterVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 课程相关字体（资源）
1.课程lesson里面包含的字（可关联到资源）
2.相关字关联到汉字表里面的具体某个字，但要通过科目类型区分以确定是软硬哪种资源
 服务类
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface ITextbookLessonCharacterService extends IService<TextbookLessonCharacter> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param textbookLessonCharacter
	 * @return
	 */
	IPage<TextbookLessonCharacterVO> selectTextbookLessonCharacterPage(IPage<TextbookLessonCharacterVO> page,
                                                                       TextbookLessonCharacterVO textbookLessonCharacter);
	/**
	 * 批量删除课程下的资源
	 * @param lessonIds
	 * @return
	 */
	boolean removeByLessonIds(List<Integer> lessonIds);

	/**
	 * 获取课程相关汉字
	 * @param lessonId
	 * @return
	 */
	List<TextbookLessonCharacter> findByLessonId(Integer lessonId);

	/**
	 * 唯一
	 * @param lessonId
	 * @param characterId
	 * @return
	 */
	TextbookLessonCharacter findByLessonIdAndCharacterId(Integer lessonId, Integer characterId);

	/**
	 * 更新访问量，自动+1
	 * @param lessonId
	 * @param characterId
	 * @return
	 */
	boolean updateVisitedCount(Integer lessonId, Integer characterId);

	/**
	 * 根据教材批量删除
	 * @param textbookIds
	 * @return
	 */
	boolean removeByTextbookIds(List<Integer> textbookIds);
}
