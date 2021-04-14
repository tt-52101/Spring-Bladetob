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
package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.Font;
import cn.rzedu.sf.resource.vo.FontVO;

/**
 * 书法字体表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-14
 */
public class FontWrapper extends BaseEntityWrapper<Font, FontVO>  {

    public static FontWrapper build() {
        return new FontWrapper();
    }

	@Override
	public FontVO entityVO(Font font) {
		FontVO fontVO = BeanUtil.copy(font, FontVO.class);

		return fontVO;
	}

}
