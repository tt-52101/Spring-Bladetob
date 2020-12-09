package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 用户表
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserVO对象", description = "书法系统的用户")
public class UserVO extends User {
	private static final long serialVersionUID = 1L;

	/**
	 * 获得星星的数目
	 */
	@ApiModelProperty(value = "获得星星的数目")
	private Integer starCount;


	private Integer number;

}
