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

import cn.rzedu.sf.resource.entity.HardPenQuery;
import cn.rzedu.sf.resource.mapper.HardPenQueryMapper;
import cn.rzedu.sf.resource.service.IHardPenQueryService;
import cn.rzedu.sf.resource.vo.HardPenQueryVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2021-04-07
 */
@Service
public class HardPenQueryServiceImpl extends ServiceImpl<HardPenQueryMapper, HardPenQuery> implements IHardPenQueryService {

	@Override
	public IPage<HardPenQueryVO> selectHardPenQuery(IPage<HardPenQueryVO> page, String name) {
		return page.setRecords(baseMapper.selectHardPenQuery(page,name));
	}

}
