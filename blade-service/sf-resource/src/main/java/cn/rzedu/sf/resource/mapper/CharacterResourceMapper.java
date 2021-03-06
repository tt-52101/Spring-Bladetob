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

import cn.rzedu.sf.resource.entity.CharacterResource;
import cn.rzedu.sf.resource.vo.CharacterResourceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface CharacterResourceMapper extends BaseMapper<CharacterResource> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param characterResource
	 * @return
	 */
	List<CharacterResourceVO> selectCharacterResourcePage(IPage page, CharacterResourceVO characterResource);


	/**
	 * 汉字资源改为未启用，enable=false
	 * @param characterId
	 * @param subject
	 * @param resourceType
	 * @return
	 */
	int updateDisable(Integer characterId, Integer subject, Integer resourceType);

	/**
	 * 修改资源和汉字简繁体
	 * @param characterId
	 * @param charS
	 * @param charT
	 * @return
	 */
	int updateCharST(Integer characterId, String charS, String charT);

	/**
	 * 更新访问量，自动+1
	 * @param characterId
	 * @param subject
	 * @param resourceType
	 * @return
	 */
	int updateVisitedCount(Integer characterId, Integer subject, Integer resourceType);

	/**
	 * 获取唯一启用的资源
	 * @param characterId
	 * @param subject
	 * @param resourceType
	 * @return
	 */
	CharacterResource findUnion(Integer characterId, Integer subject, Integer resourceType);


	/**
	 * 单字视频资源
	 * @param characterId
	 * @param font
	 * @param subject
	 * @return
	 */
	Map<String, Object> findCharVideoResource(Integer characterId, String font, Integer subject);
}
