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

import cn.rzedu.sf.user.entity.FrontUser;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 视图实体类
 *
 * @author Blade
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FrontUserVO对象", description = "FrontUserVO对象")
public class FrontUserVO extends FrontUser {
	private static final long serialVersionUID = 1L;

	/**
	 * 新密码
	 */
	@ApiModelProperty(value = "新密码")
	private String newPassword;

	/**
	 * 新用户名
	 */
	@ApiModelProperty(value = "新用户名")
	private String newUserName;
	/**
	 * 生成帐号的数量
	 */
	@ApiModelProperty(value = "生成数量")
	private Integer batchSize;

	@ApiModelProperty(value = "批量删除")
	private List<String> userNameList;

	@ApiModelProperty(value = "functionId")
	private List<String>  functionIds;

	@ApiModelProperty(value = "functionName")
	private List<String> functionNames;

	@ApiModelProperty(value = "publisherId")
	private List<String> publisherIds;

	@ApiModelProperty(value = "publisherName")
	private List<String> publisherNames;

	@ApiModelProperty(value = "gradeId")
	private List<String> gradeIds;

	@ApiModelProperty(value = "批量删除")
	private List<String> gradeNames;


}
