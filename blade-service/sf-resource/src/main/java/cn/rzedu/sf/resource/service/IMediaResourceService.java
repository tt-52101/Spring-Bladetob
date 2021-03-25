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

import cn.rzedu.sf.resource.entity.MediaResource;
import cn.rzedu.sf.resource.vo.MediaResourceSortVO;
import cn.rzedu.sf.resource.vo.MediaResourceVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  服务类
 *
 * @author Blade
 * @since 2021-03-15
 */
public interface IMediaResourceService extends IService<MediaResource> {

	/**
	 * 首页-全部资源
	 *
	 * @param page
	 * @param mediaType
	 * @return
	 */
	IPage<MediaResourceVO> selectMediaResourcePage(IPage<MediaResourceVO> page, Integer mediaType,Integer subject);

	/**
	 * 一级分类列表
	 *
	 * @param mediaType
	 * @return
	 */
	List<MediaResourceSortVO> selectMediaResourceSortList(Integer mediaType,Integer subject);

	/**
	 * 一级分类资源列表
	 * @param page
	 * @param mediaType
	 * @param sortId
	 * @return
	 */

	IPage<MediaResourceVO> selectMediaResourceList(IPage<MediaResourceVO> page,Integer mediaType,Integer sortId,Integer subject);

	/**
	 * 分类搜索资源列表
	 * @param page
	 * @param mediaType
	 * @param sortId
	 * @param subject
	 * @param title
	 * @return
	 */
	IPage<MediaResourceVO> selectMediaResourceSortSearch(IPage<MediaResourceVO> page,Integer mediaType,Integer sortId,Integer subject,String title);



}
