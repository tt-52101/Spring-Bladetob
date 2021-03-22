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
	 * @param mediaResource
	 * @return
	 */
	List<MediaResourceVO> selectMediaResourceSKJAPage(IPage page, MediaResourceVO mediaResource);
	List<MediaResourceVO> selectMediaResourceCZWAPage(IPage page, MediaResourceVO mediaResource);
	List<MediaResourceVO> selectMediaResourceGXSPPage(IPage page, MediaResourceVO mediaResource);
	List<MediaResourceVO> selectMediaResourceZSBKPage(IPage page, MediaResourceVO mediaResource);
	List<MediaResourceVO> selectMediaResourceMSWKPage(IPage page, MediaResourceVO mediaResource);
	/**
	 * 分类
	 *
	 * @param sort
	 * @return
	 */
	List<MediaResourceVO> selectMediaResourceSKJAList(IPage page, String sort);
	List<MediaResourceVO> selectMediaResourceCZWAList(IPage page, String sort);
	List<MediaResourceVO> selectMediaResourceGXSPList(IPage page, String sort);
	List<MediaResourceVO> selectMediaResourceZSBKList(IPage page, String sort);
	List<MediaResourceVO> selectMediaResourceMSWKList(IPage page, String sort);

	String selectMediaResourceSKJADetail(Integer id);
	String selectMediaResourceCZWADetail(Integer id);
	String selectMediaResourceGXSPDetail(Integer id);
	String selectMediaResourceZSBKDetail(Integer id);
	String selectMediaResourceMSWKDetail(Integer id);

}
