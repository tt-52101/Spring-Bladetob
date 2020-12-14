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

import cn.rzedu.sf.resource.entity.Textbook;
import cn.rzedu.sf.resource.vo.TextbookVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface TextbookMapper extends BaseMapper<Textbook> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param textbook
	 * @return
	 */
	List<TextbookVO> selectTextbookPage(IPage page, TextbookVO textbook);


	/**
	 * 更新课程数和汉字数
	 * @param id 必填
	 * @return
	 */
	int updateLessonAndCharCount(Integer id);


	/**
	 * 更新教材访问量，自动+1
	 * @param id
	 * @return
	 */
	int updateVisitedCount(Integer id);

	/**
	 * 教材，根据ids获取
	 * @param ids
	 * @param textbook
	 * @return
	 */
	List<TextbookVO> findByIds(String ids, TextbookVO textbook);

	/**
	 * 出版社列表
	 * @param subject
	 * @return
	 */
	List<String> findPublisherList(Integer subject);
}
