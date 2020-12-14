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

import cn.rzedu.sf.resource.entity.CharacterResourceFile;
import cn.rzedu.sf.resource.vo.CharacterResourceFileVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 汉字资源文件 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-05
 */
public interface CharacterResourceFileMapper extends BaseMapper<CharacterResourceFile> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param characterResourceFile
	 * @return
	 */
	List<CharacterResourceFileVO> selectCharacterResourceFilePage(IPage page,
                                                                  CharacterResourceFileVO characterResourceFile);

	/**
	 * 根据资源类型和objectId获取模板下的唯一对象
	 * @param resourceId
	 * @param objectId
	 * @return
	 */
	CharacterResourceFile findUnionByResourceIdAndObjectId(Integer resourceId, String objectId, String font);

	/**
	 * 获取某汉字资源模板下 的所有object对象
	 * @param resourceId
	 * @param font
	 * @return
	 */
	List<CharacterResourceFile> findByResourceId(Integer resourceId, String font);

	/**
	 * 获取某汉字资源模板(唯一启用)下的所有object对象
	 * characterId，subject，resourceType确定唯一启用资源
	 * @param characterId
	 * @param subject
	 * @param resourceType
	 * @return
	 */
	List<CharacterResourceFile> findByCharacterId(Integer characterId, Integer subject, Integer resourceType);

}
