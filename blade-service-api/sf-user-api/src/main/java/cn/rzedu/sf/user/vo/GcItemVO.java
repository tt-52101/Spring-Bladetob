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
package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.GcItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 基础代码视图实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@ApiModel(value = "GcItemVO对象", description = "基础代码")
public class GcItemVO {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Integer id;
	/**
	 * 代码项名称
	 */
	@ApiModelProperty(value = "代码项名称")
	private String name;
	/**
	 * 代码项值
	 */
	@ApiModelProperty(value = "代码项值")
	private String value;
	/**
	 * 排列次序
	 */
	@ApiModelProperty(value = "排列次序")
	private Integer sortOrder;
	/**
	 * 层级关系
	 */
	@ApiModelProperty(value = "层级关系")
	private Integer level;
}
