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

import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.vo.CharacterVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface CharacterMapper extends BaseMapper<Character> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param character
	 * @return
	 */
	List<CharacterVO> selectCharacterPage(IPage page, CharacterVO character);

	/**
	 * 根据字模糊查询
	 * @param page
	 * @param name
	 * @return
	 */
	List<CharacterVO> findCharacterByChar(IPage page, String name);

	/**
	 * 书法资源，带访问量
	 * @param page
	 * @param character
	 * @return
	 */
	List<CharacterVO> findCharacterVoWithVisitedCount(IPage page, CharacterVO character);

	/**
	 * 书法资源，带访问量，根据汉字id获取
	 * @param ids 汉字id，多个id逗号隔开
	 * @return
	 */
	List<CharacterVO> findWithVisitedCountByIds(String ids, CharacterVO character);

	/**
	 * 更新汉字的资源数
	 * @param id
	 * @return
	 */
	int updateResCount(Integer id);


	/**
	 * 获取多个汉字
	 * @param list 字列表，顿号隔开。e.g. 大、中、小
	 * @return
	 */
	List<Character> findByChars(List<String> list);

	/**
	 * 更新软笔访问量，自动+1
	 * @param id
	 * @return
	 */
	int updateSoftVisitedCount(Integer id);

	/**
	 * 更新硬笔笔访问量，自动+1
	 * @param id
	 * @return
	 */
	int updateHardVisitedCount(Integer id);

	/**
	 * 根据软硬笔二维码编号和类型获取 汉字
	 * @param code 软|硬笔二维码编号
	 * @param type 类型 1=软笔  2=硬笔
	 * @return
	 */
	Character findByCodeAndType(String code, Integer type);

	/**
	 * 从教程课程中获取二维码 -- 用于更新汉字二维码
	 * @param textbookId
	 * @return
	 */
	List<Character> findErCodeFromLesson(Integer textbookId);
}
