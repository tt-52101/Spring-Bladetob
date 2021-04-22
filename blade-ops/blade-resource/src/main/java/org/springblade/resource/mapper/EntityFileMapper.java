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
package org.springblade.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.resource.entity.EntityFile;
import org.springblade.resource.vo.EntityFileVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 书法上传文件记录表 Mapper 接口
 *
 * @author Blade
 * @since 2020-08-10
 */
public interface EntityFileMapper extends BaseMapper<EntityFile> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param entityFile
	 * @return
	 */
	List<EntityFileVO> selectEntityFilePage(IPage page, EntityFileVO entityFile);

	EntityFile findByMD5(String md5);

	EntityFile findFileByUuid(String uuid);

	/**
	 * 保存浏览记录
	 * @param userId
	 * @param userName
	 * @param resourceId
	 * @param subject
	 * @param mediaType
	 * @param createDate
	 * @param modifyDate
	 * @return
	 */
	int saveBrowsingHistory(Integer userId, String userName, Integer resourceId, Integer subject, Integer mediaType, LocalDateTime createDate, LocalDateTime modifyDate);


}
