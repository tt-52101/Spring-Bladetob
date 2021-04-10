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

import cn.rzedu.sf.resource.entity.MediaResource;
import cn.rzedu.sf.resource.vo.MediaResourceSortVO;
import cn.rzedu.sf.resource.vo.MediaResourceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Blade
 * @since 2021-03-15
 */
public interface MediaResourceMapper extends BaseMapper<MediaResource> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param mediaType
	 * @return
	 */
	List<MediaResourceVO> selectMediaResourcePage(IPage page,Integer mediaType,Integer subject);

	/**
	 * 分类列表
	 *
	 * @param mediaType
	 * @return
	 */
	List<MediaResourceSortVO> selectMediaResourceSortList(Integer mediaType,Integer subject);


	/**
	 * 资源列表
	 * @param page
	 * @param mediaType
	 * @param sortId
	 * @return
	 */
	List<MediaResourceVO> selectMediaResourceList(IPage page,Integer mediaType,Integer sortId,Integer subject);


	/**
	 * 资源搜索
	 * @param page
	 * @param mediaType
	 * @param sortId
	 * @param subject
	 * @param title
	 * @return
	 */
	List<MediaResourceVO> selectMediaResourceSortSearch(IPage page,Integer mediaType,Integer sortId,Integer subject,String title);

	/**
	 * 资源查询
	 * @param page
	 * @param subject
	 * @param title
	 * @return
	 */
	List<MediaResourceVO> selectMediaResourceQuery(IPage page,Integer subject,String title);

}
