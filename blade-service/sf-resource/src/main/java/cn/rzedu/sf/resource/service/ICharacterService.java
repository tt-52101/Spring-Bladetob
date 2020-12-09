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

import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.vo.CharacterVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 汉字表
对应字典里面的汉字
一个汉字对应唯一一个记录，同时包含该汉字的软笔和硬笔的资源总属性
 服务类
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface ICharacterService extends IService<Character> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param character
	 * @return
	 */
	IPage<CharacterVO> selectCharacterPage(IPage<CharacterVO> page, CharacterVO character);

	/**
	 * 新增
	 * 重写，默认创建时间
	 * @param character
	 * @return
	 */
	@Override
	boolean save(Character character);

	/**
	 * 根据字模糊查询
	 *
	 * @param page
	 * @param name
	 * @return
	 */
	IPage<CharacterVO> findCharacterByChar(IPage<CharacterVO> page, String name);


	/**
	 * 根据简体字判断是否存在
	 * @param charS
	 * @param id
	 * @return true=存在
	 */
	boolean judgeRepeatByCharS(String charS, Integer id);


	/**
	 * 获取书法资源列表，带访问量
	 * @param page
	 * @param character hasResource:是否有相关资源; subject:资源的学科
	 * @return
	 */
	IPage<CharacterVO> findCharacterVoWithVisitedCount(IPage<CharacterVO> page, CharacterVO character);

	/**
	 * 书法资源，带访问量，根据汉字id获取
	 * @param ids 汉字id，多个id逗号隔开
	 * @param character hasResource:是否有相关资源; subject:资源的学科
	 * @return
	 */
	List<CharacterVO> findWithVisitedCountByIds(String ids, CharacterVO character);

	/**
	 * 更新汉字的资源数
	 * @param id 必填
	 * @return
	 */
	boolean updateResCount(Integer id);


	/**
	 * 获取多个汉字
	 * @param chars 字列表，顿号隔开。e.g. 大、中、小
	 * @return
	 */
	List<Character> findByChars(String chars);

	/**
	 * 更新软笔访问量，自动+1
	 * @param id
	 * @return
	 */
	boolean updateSoftVisitedCount(Integer id);

	/**
	 * 更新硬笔笔访问量，自动+1
	 * @param id
	 * @return
	 */
	boolean updateHardVisitedCount(Integer id);

	/**
	 * 根据软硬笔二维码编号和类型获取 汉字
	 * @param code 软|硬笔二维码编号
	 * @param type 类型 1=软笔  2=硬笔
	 * @return
	 */
	Character findByCodeAndType(String code, Integer type);

	/**
	 * 从教程课程中获取二维码 -- 用于更新汉字二维码
	 * @param textbookId  可为空，查全部教材
	 * @return
	 */
	List<Character> findErCodeFromLesson(Integer textbookId);
}
