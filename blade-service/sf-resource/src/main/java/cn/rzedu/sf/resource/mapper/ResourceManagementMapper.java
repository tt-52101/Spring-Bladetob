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
import cn.rzedu.sf.resource.vo.ProgramaManagementVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Blade
 * @since 2021-03-15
 */
public interface ResourceManagementMapper extends BaseMapper<MediaResource> {

    List<ProgramaManagementVO> selectPrograma(IPage page,Integer subject,Integer mediaType);

    int updateSort(String sortName,Integer subject,Integer mediaType,Integer sortId);

    int removeSort(Integer sortId);

    int addSort(String sortName,Integer mediaType,Integer subject);

    List<MediaResourceVO> selectResourceList(IPage page,Integer subject,Integer mediaType, String title,Integer sortId);

    MediaResourceVO selectResourceDetail(Integer resourceId);

    int updateResource(String title,Integer sortId,String uuid, String coverImgUrl,String objectType,String suffix, Integer resourceId);

    int deleteResource(Integer resourceId);

    int addResource(String title, Integer subject, Integer sortId, String objectType, String suffix, String uuid, String coverImgUrl, LocalDateTime createDate,Integer mediaType);
}
