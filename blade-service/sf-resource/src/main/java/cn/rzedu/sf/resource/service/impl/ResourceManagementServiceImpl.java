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
import cn.rzedu.sf.resource.mapper.ResourceManagementMapper;
import cn.rzedu.sf.resource.service.IMediaResourceService;
import cn.rzedu.sf.resource.service.IResourceManagementService;
import cn.rzedu.sf.resource.vo.MediaResourceSortVO;
import cn.rzedu.sf.resource.vo.MediaResourceVO;
import cn.rzedu.sf.resource.vo.ProgramaManagementVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
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
public class ResourceManagementServiceImpl extends ServiceImpl<ResourceManagementMapper, MediaResource> implements IResourceManagementService {


	@Override
	public List<ProgramaManagementVO> selectProgramList(Integer subject, Integer mediaType) {
		return baseMapper.selectPrograma(subject,mediaType);
	}

	@Override
	public boolean updateSort(String sortName, Integer subject, Integer mediaType) {
		return SqlHelper.retBool(baseMapper.updateSort(sortName,subject,mediaType));
	}

	@Override
	public boolean addSort(String sortName, Integer mediaType, Integer subject) {
		return SqlHelper.retBool(baseMapper.addSort(sortName,mediaType,subject));
	}

	@Override
	public IPage<MediaResourceVO> selectResourceList(IPage<MediaResourceVO> page, Integer subject, Integer mediaType,String title, String sortName) {
		return  page.setRecords(baseMapper.selectResourceList(page,subject,mediaType,title,sortName));
	}
}
