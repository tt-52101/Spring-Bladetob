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

import cn.rzedu.sf.resource.entity.CharacterResource;
import cn.rzedu.sf.resource.vo.CharacterResourceVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

/**
 * 汉字资源
汉字的（视频）资源
一个汉字会有多个资源记录，软笔和硬笔资源都放在这个表，软硬笔作为不同科目来区分
 服务类
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface ICharacterResourceService extends IService<CharacterResource> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param characterResource
	 * @return
	 */
	IPage<CharacterResourceVO> selectCharacterResourcePage(IPage<CharacterResourceVO> page,
                                                           CharacterResourceVO characterResource);

	/**
	 * 新增
	 * 重写，默认创建时间
	 * @param characterResource
	 * @return
	 */
	@Override
	boolean save(CharacterResource characterResource);

	/**
	 * 判断是否重复，字典ID和资源标题
	 * @param characterId
	 * @param nameTr
	 * @param subject
	 * @param id
	 * @return true=存在
	 */
	boolean judgeRepeatByName(Integer characterId, String nameTr, Integer subject, Integer id);

	/**
	 * 汉字资源改为未启用，enable=false
	 * @param characterId
	 * @param subject
	 * @param resourceType
	 * @return
	 */
	boolean updateDisable(Integer characterId, Integer subject, Integer resourceType);

	/**
	 * 修改资源和汉字简繁体
	 * @param characterId
	 * @return
	 */
	boolean updateCharST(Integer characterId, String charS, String charT);

	/**
	 * 更新访问量，自动+1
	 * @param characterId
	 * @param subject
	 * @param resourceType
	 * @return
	 */
	boolean updateVisitedCount(Integer characterId, Integer subject, Integer resourceType);

	/**
	 * 获取唯一启用的资源
	 * @param characterId
	 * @param subject
	 * @param resourceType
	 * @return
	 */
	CharacterResource findUnion(Integer characterId, Integer subject, Integer resourceType);

	/**
	 * 获取软笔相关资源：观察、分析、笔法、认读
	 * @param characterId
	 * @param font
	 * @return
	 */
	Map<String, Object> findSoftResource(Integer characterId, String font);

	/**
	 * 获取硬笔相关资源：认读、视频
	 * @param characterId
	 * @param font
	 * @return
	 */
	Map<String, Object> findHardResource(Integer characterId, String font);

	/**
	 * 获取资源包数据
	 * @param characterId
	 * @param subject
	 * @param font
	 * @return
	 */
	Map<String, Object> findResources(Integer characterId, Integer subject, String font);

	/**
	 * 获取资源包数据 返回json 格式数据
	 * @param characterId
	 * @param subject
	 * @param font
	 * @return
	 */
	Map<String, Object> findResourcesByJson(Integer characterId, Integer subject, String font);

	/**
	 * 创建软硬笔资源
	 * 先判断汉字是否存在，再判断资源模板是否存在，最后新增资源文件
	 * @param charS			汉字
	 * @param subject		科目，71=软笔，72=硬笔
	 * @param resourceType	资源类型
	 * @param font			字体
	 * @param objectId		模板中的文件对象
	 * @param objectType	文件类型
	 * @param value			文件值，uuid或text
	 * @return
	 */
	boolean createResourceFile(String charS, Integer subject, Integer resourceType,
							   String font, String objectId, String objectType, String value);

	/**
	 * 创建硬笔资源
	 * @return
	 */
	boolean createHardResourceFile(String charS, Integer resourceType, String font, String objectCode, String value);

	/**
	 * 创建软笔资源
	 * @return
	 */
	boolean createSoftResourceFile(String charS, Integer resourceType, String font, String objectCode, String value);

	/**
	 * 创建硬笔资源 by characterId
	 * @return
	 */
	boolean createHardResourceFile(Integer characterId, Integer resourceType, String font, String objectCode, String value);

	/**
	 * 创建软笔资源 by characterId
	 * @return
	 */
	boolean createSoftResourceFile(Integer characterId, Integer resourceType, String font, String objectCode, String value);

	/**
	 * 单字视频资源
	 * @param characterId
	 * @param font
	 * @param subject
	 * @return
	 */
	Map<String, Object> findCharVideoResource(Integer characterId, String font, Integer subject);
}
