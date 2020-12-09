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
	 * 获取软笔相关资源：观察、分析、笔法
	 * @param characterId
	 * @return
	 */
	Map<String, Object> findSoftResource(Integer characterId);
}
