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

import cn.rzedu.sf.resource.entity.Textbook;
import cn.rzedu.sf.resource.vo.CharLinkVO;
import cn.rzedu.sf.resource.vo.TextbookVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 书法教材表
1.系统使用的书法教材
2.属性与普教资源平台的教材类似
 服务类
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface ITextbookService extends IService<Textbook> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param textbook
	 * @return
	 */
	IPage<TextbookVO> selectTextbookPage(IPage<TextbookVO> page, TextbookVO textbook);

	/**
	 * 获取教材详情，带课程数据
	 * @param id
	 * @return
	 */
	TextbookVO findTextBookById(Integer id);


	/**
	 * 根据教材名判断是否存在
	 * @param name
	 * @param publisher
	 * @param subject
	 * @param id
	 * @return true=存在
	 */
	boolean judgeRepeatByName(String name, String publisher, Integer subject, Integer id);


	/**
	 * 更新课程数和汉字数
	 * @param id 必填
	 * @return
	 */
	boolean updateLessonAndCharCount(Integer id);

	/**
	 * 更新教材访问量，自动+1
	 * @param id
	 * @return
	 */
	boolean updateVisitedCount(Integer id);

	/**
	 * 教材，根据ids获取
	 * @param ids
	 * @param textbook
	 * @return
	 */
	List<TextbookVO> findByIds(String ids, TextbookVO textbook);

	/**
	 * 获取课程汉字，根据二维码编号和软硬笔类型
	 * @param type	1=软笔 2=硬笔
	 * @param code	二维码编号，硬笔为课程编号，软笔为汉字编号
	 * @return  硬笔只有1条，软笔可能有多条：textbookId, lessonId, characterId, textbookName, lessonName, characterName
	 */
	List<CharLinkVO> findLessonByCodeAndType(String type, String code);

	/**
	 * 出版社列表
	 * @param subject
	 * @return
	 */
	List<String> findPublisherList(Integer subject);

	/**
	 * 获取教材详情，带课程数据，按单元分组
	 * @param id
	 * @return
	 */
	TextbookVO findTextBookByIdWithUnit(Integer id);
}
