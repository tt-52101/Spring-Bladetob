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
import cn.rzedu.sf.resource.vo.BrowsingHistoryVO;
import cn.rzedu.sf.resource.vo.MediaResourceSortVO;
import cn.rzedu.sf.resource.vo.MediaResourceVO;
import cn.rzedu.sf.resource.vo.SearchHistoryVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	public List<MediaResourceVO> selectMediaResourceByID(List<Integer> resourceIds) {
		List<MediaResourceVO> MediaResourceVOs = new ArrayList<>();
		if (!resourceIds.isEmpty()){
			for(Integer resourceId : resourceIds){
				MediaResourceVOs.add(baseMapper.selectMediaResourceByID(resourceId));
			}
			return MediaResourceVOs;
		}else
		return null;
	}

	@Override
	public IPage<MediaResourceVO> selectMediaResourceList(IPage<MediaResourceVO> page,Integer mediaType,Integer sortId,Integer subject) {
		return page.setRecords(baseMapper.selectMediaResourceList(page,mediaType,sortId,subject));
	}

	@Override
	public IPage<MediaResourceVO> selectMediaResourceSortSearch(IPage<MediaResourceVO> page, Integer mediaType, Integer sortId, Integer subject, String title) {
		return page.setRecords(baseMapper.selectMediaResourceSortSearch(page,mediaType,sortId,subject,title));
	}

	@Override
	public IPage<MediaResourceVO> selectMediaResourceQuery(IPage<MediaResourceVO> page,Integer subject, String title) {
		return page.setRecords(baseMapper.selectMediaResourceQuery(page,subject,title));
	}

	@Override
	public boolean saveBrowsingHistory(Integer userId, String userName, Integer resourceId, Integer subject, Integer mediaType) {
		LocalDateTime localDateTime=LocalDateTime.now();
		int r = baseMapper.saveBrowsingHistory(userId,userName,resourceId,subject,mediaType,localDateTime,localDateTime);
		if (r>0){
			return true;
		}else
			return false;
	}

	@Override
	public List<Integer> selectResourceId(String userName, Integer subject, Integer mediaType) {
		return baseMapper.selectResourceId(userName,subject,mediaType);
	}

	@Override
	public boolean saveSearchHistory(String keyword, Integer userId, String userName, Integer subject) {
		LocalDateTime localDateTime = LocalDateTime.now();
		int r = baseMapper.saveSearchHistory(keyword,userId,userName,subject,localDateTime,localDateTime);
		if (r>0){
			return true;
		}else
			return false;
	}

	@Override
	public List<String> selectKeyword(String userName, Integer subject) {
		return baseMapper.selectSearchHistory(userName,subject);
	}
}
