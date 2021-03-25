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
import cn.rzedu.sf.resource.vo.MediaResourceSortVO;
import cn.rzedu.sf.resource.vo.MediaResourceVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2021-03-15
 */
@Service
public class MediaResourceServiceImpl extends ServiceImpl<MediaResourceMapper, MediaResource> implements IMediaResourceService {

	@Override
	public IPage<MediaResourceVO> selectMediaResourcePage(IPage<MediaResourceVO> page,Integer mediaType,Integer subject) {
		return page.setRecords(baseMapper.selectMediaResourcePage(page,mediaType,subject));
	}

	@Override
	public List<MediaResourceSortVO> selectMediaResourceSortList(Integer mediaType,Integer subject) {

		return baseMapper.selectMediaResourceSortList(mediaType,subject);
	}

	@Override
	public IPage<MediaResourceVO> selectMediaResourceList(IPage<MediaResourceVO> page,Integer mediaType,Integer sortId,Integer subject) {
		return page.setRecords(baseMapper.selectMediaResourceList(page,mediaType,sortId,subject));
	}

	@Override
	public IPage<MediaResourceVO> selectMediaResourceSortSearch(IPage<MediaResourceVO> page, Integer mediaType, Integer sortId, Integer subject, String title) {
		return page.setRecords(baseMapper.selectMediaResourceSortSearch(page,mediaType,sortId,subject,title));
	}



}
