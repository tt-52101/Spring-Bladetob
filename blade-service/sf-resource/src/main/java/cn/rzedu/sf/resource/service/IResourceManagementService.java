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
import cn.rzedu.sf.resource.vo.ProgramaManagementVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  服务类
 *
 * @author Blade
 * @since 2021-03-15
 */
public interface IResourceManagementService extends IService<MediaResource> {

	/**
	 * 栏目列表
	 * @param subject
	 * @param mediaType
	 * @return
	 */
	List<ProgramaManagementVO> selectProgramList(Integer subject, Integer mediaType);

	/**
	 * 更新栏目
	 * @param sortName
	 * @param subject
	 * @param mediaType
	 * @return
	 */
	boolean updateSort(String sortName,Integer subject,Integer mediaType);

	/**
	 * 新增栏目
	 * @param sortName
	 * @param mediaType
	 * @param subject
	 * @return
	 */
	boolean addSort(String sortName,Integer mediaType,Integer subject);

	/**
	 * 查询
	 * @param page
	 * @param subject
	 * @param title
	 * @param mediaType
	 * @return
	 */
	IPage<MediaResourceVO> selectResourceList(IPage<MediaResourceVO> page,Integer subject, Integer mediaType,String title, String sortName);

	/**
	 * 查看
	 * @param resourceId
	 * @return
	 */
	MediaResourceVO selectResourceDetail(Integer resourceId);

	/**
	 * 编辑
	 * @param title
	 * @param sortId
	 * @param uuid
	 * @param coverImgUrl
	 * @param resourceId
	 * @return
	 */
	boolean updateResource(String title,Integer sortId,String uuid, String coverImgUrl,Integer resourceId);

	/**
	 * 删除
	 * @param resourceIds
	 * @return
	 */
	boolean deleteResource(List<Integer> resourceIds);

	/**
	 * 上传资源
	 * @param title
	 * @param subject
	 * @param sortId
	 * @param objectType
	 * @param suffix
	 * @param uuid
	 * @param coverImgUrl
	 * @param mediaType
	 * @return
	 */
	boolean addResource(String title, Integer subject, Integer sortId, String objectType, String suffix, String uuid, String coverImgUrl, Integer mediaType);

}
