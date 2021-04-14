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

import cn.rzedu.sf.resource.entity.Font;
import cn.rzedu.sf.resource.vo.FontVO;
import cn.rzedu.sf.resource.mapper.FontMapper;
import cn.rzedu.sf.resource.service.IFontService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 书法字体表 服务实现类
 *
 * @author Blade
 * @since 2021-04-14
 */
@Service
public class FontServiceImpl extends ServiceImpl<FontMapper, Font> implements IFontService {

	@Override
	public IPage<FontVO> selectFontPage(IPage<FontVO> page, FontVO font) {
		return page.setRecords(baseMapper.selectFontPage(page, font));
	}

}
