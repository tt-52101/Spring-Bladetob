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
package org.springblade.resource.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.resource.entity.EntityFile;
import org.springblade.resource.vo.EntityFileVO;

/**
 * 书法上传文件记录表 服务类
 *
 * @author Blade
 * @since 2020-08-10
 */
public interface IEntityFileService extends IService<EntityFile> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param entityFile
	 * @return
	 */
	IPage<EntityFileVO> selectEntityFilePage(IPage<EntityFileVO> page, EntityFileVO entityFile);

	/**
     * 根据文件md5查询文件
	 * @param md5
     * @return
     */
	EntityFile findFileByMD5(String md5);

	/**
	 * 根据uuid查询文件
	 * @param md5
	 * @return
	 */
	EntityFile findFileByUuid(String md5);
}
