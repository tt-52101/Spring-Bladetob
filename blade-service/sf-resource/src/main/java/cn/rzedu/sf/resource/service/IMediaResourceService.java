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
import cn.rzedu.sf.resource.vo.MediaResourceVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *  服务类
 *
 * @author Blade
 * @since 2021-03-15
 */
public interface IMediaResourceService extends IService<MediaResource> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param mediaResource
	 * @return
	 */
	IPage<MediaResourceVO> selectMediaResourceSKJAPage(IPage<MediaResourceVO> page, MediaResourceVO mediaResource);
	IPage<MediaResourceVO> selectMediaResourceCZWAPage(IPage<MediaResourceVO> page, MediaResourceVO mediaResource);
	IPage<MediaResourceVO> selectMediaResourceGXSPPage(IPage<MediaResourceVO> page, MediaResourceVO mediaResource);
	IPage<MediaResourceVO> selectMediaResourceZSBKPage(IPage<MediaResourceVO> page, MediaResourceVO mediaResource);
	IPage<MediaResourceVO> selectMediaResourceMSWKPage(IPage<MediaResourceVO> page, MediaResourceVO mediaResource);

	/**
	 * 一级分类
	 *
	 * @param sort
	 * @return
	 */
	IPage<MediaResourceVO> selectMediaResourceSKJAList(IPage<MediaResourceVO> page, String sort);
	IPage<MediaResourceVO> selectMediaResourceCZWAList(IPage<MediaResourceVO> page, String sort);
	IPage<MediaResourceVO> selectMediaResourceGXSPList(IPage<MediaResourceVO> page, String sort);
	IPage<MediaResourceVO> selectMediaResourceZSBKList(IPage<MediaResourceVO> page, String sort);
	IPage<MediaResourceVO> selectMediaResourceMSWKList(IPage<MediaResourceVO> page, String sort);
	/**
	 * getUuid
	 *
	 * @param id
	 * @return
	 */
	String selectMediaResourceSKJADetail(Integer id);
	String selectMediaResourceCZWADetail(Integer id);
	String selectMediaResourceGXSPDetail(Integer id);
	String selectMediaResourceZSBKDetail(Integer id);
	String selectMediaResourceMSWKDetail(Integer id);
}
