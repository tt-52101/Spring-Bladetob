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
package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.Video;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 视图实体类
 *
 * @author Blade
 * @since 2021-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VideoVO对象", description = "VideoVO对象")
public class VideoVO extends Video {
	private static final long serialVersionUID = 1L;

	private String charaterName;

	public String getCharaterName() {
		return charaterName;
	}

	public void setCharaterName(String charaterName) {
		this.charaterName = charaterName;
	}
}
