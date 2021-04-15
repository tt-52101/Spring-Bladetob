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
package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.FrontUser;
import cn.rzedu.sf.user.vo.FrontUserVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-12
 */
public class FrontUserWrapper extends BaseEntityWrapper<FrontUser, FrontUserVO>  {

    public static FrontUserWrapper build() {
        return new FrontUserWrapper();
    }

	@Override
	public FrontUserVO entityVO(FrontUser frontUser) {
		FrontUserVO frontUserVO = BeanUtil.copy(frontUser, FrontUserVO.class);

		return frontUserVO;
	}

}
