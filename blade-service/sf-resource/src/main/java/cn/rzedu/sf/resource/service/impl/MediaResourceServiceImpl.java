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
package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.MediaResource;
import cn.rzedu.sf.resource.mapper.MediaResourceMapper;
import cn.rzedu.sf.resource.service.IMediaResourceService;
import cn.rzedu.sf.resource.vo.MediaResourceVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2021-03-15
 */
@Service
public class MediaResourceServiceImpl extends ServiceImpl<MediaResourceMapper, MediaResource> implements IMediaResourceService {

	@Override
	public IPage<MediaResourceVO> selectMediaResourceSKJAPage(IPage<MediaResourceVO> page, MediaResourceVO mediaResource) {
		return page.setRecords(baseMapper.selectMediaResourceSKJAPage(page, mediaResource));
	}
	@Override
	public IPage<MediaResourceVO> selectMediaResourceCZWAPage(IPage<MediaResourceVO> page, MediaResourceVO mediaResource) {
		return page.setRecords(baseMapper.selectMediaResourceCZWAPage(page, mediaResource));
	}
	@Override
	public IPage<MediaResourceVO> selectMediaResourceGXSPPage(IPage<MediaResourceVO> page, MediaResourceVO mediaResource) {
		return page.setRecords(baseMapper.selectMediaResourceGXSPPage(page, mediaResource));
	}
	@Override
	public IPage<MediaResourceVO> selectMediaResourceZSBKPage(IPage<MediaResourceVO> page, MediaResourceVO mediaResource) {
		return page.setRecords(baseMapper.selectMediaResourceZSBKPage(page, mediaResource));
	}
	@Override
	public IPage<MediaResourceVO> selectMediaResourceMSWKPage(IPage<MediaResourceVO> page, MediaResourceVO mediaResource) {
		return page.setRecords(baseMapper.selectMediaResourceMSWKPage(page, mediaResource));
	}


	@Override
	public IPage<MediaResourceVO> selectMediaResourceSKJAList(IPage<MediaResourceVO> page,String sort) {
		return page.setRecords(baseMapper.selectMediaResourceSKJAList(page,sort));
	}
	@Override
	public IPage<MediaResourceVO> selectMediaResourceCZWAList(IPage<MediaResourceVO> page,String sort) {
		return page.setRecords(baseMapper.selectMediaResourceCZWAList(page,sort));
	}
	@Override
	public IPage<MediaResourceVO> selectMediaResourceGXSPList(IPage<MediaResourceVO> page,String sort) {
		return page.setRecords(baseMapper.selectMediaResourceGXSPList(page,sort));
	}
	@Override
	public IPage<MediaResourceVO> selectMediaResourceZSBKList(IPage<MediaResourceVO> page,String sort) {
		return page.setRecords(baseMapper.selectMediaResourceZSBKList(page,sort));
	}
	@Override
	public IPage<MediaResourceVO> selectMediaResourceMSWKList(IPage<MediaResourceVO> page,String sort) {
		return page.setRecords(baseMapper.selectMediaResourceMSWKList(page,sort));
	}

	@Override
	public String selectMediaResourceSKJADetail(Integer id) {
		return baseMapper.selectMediaResourceSKJADetail(id);
	}
	@Override
	public String selectMediaResourceCZWADetail(Integer id) {
		return baseMapper.selectMediaResourceCZWADetail(id);
	}
	@Override
	public String selectMediaResourceGXSPDetail(Integer id) {
		return baseMapper.selectMediaResourceGXSPDetail(id);
	}
	@Override
	public String selectMediaResourceZSBKDetail(Integer id) {
		return baseMapper.selectMediaResourceZSBKDetail(id);
	}
	@Override
	public String selectMediaResourceMSWKDetail(Integer id) {
		return baseMapper.selectMediaResourceMSWKDetail(id);
	}
}
