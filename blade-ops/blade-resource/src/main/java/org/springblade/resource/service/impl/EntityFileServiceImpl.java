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
package org.springblade.resource.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.resource.entity.EntityFile;
import org.springblade.resource.mapper.EntityFileMapper;
import org.springblade.resource.service.IEntityFileService;
import org.springblade.resource.vo.EntityFileVO;
import org.springframework.stereotype.Service;

/**
 * 书法上传文件记录表 服务实现类
 *
 * @author Blade
 * @since 2020-08-10
 */
@Service
public class EntityFileServiceImpl extends ServiceImpl<EntityFileMapper, EntityFile> implements IEntityFileService {

	@Override
	public IPage<EntityFileVO> selectEntityFilePage(IPage<EntityFileVO> page, EntityFileVO entityFile) {
		return page.setRecords(baseMapper.selectEntityFilePage(page, entityFile));
	}

	@Override
	public EntityFile findFileByMD5(String md5) {
		return this.baseMapper.findByMD5(md5);
	}

	@Override
	public EntityFile findFileByUuid(String uuid) {
		return this.baseMapper.findFileByUuid(uuid);
	}

}
