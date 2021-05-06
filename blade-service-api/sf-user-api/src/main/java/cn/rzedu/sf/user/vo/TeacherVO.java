package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.Teacher;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 教师视图实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TeacherVO对象", description = "教师")
public class TeacherVO extends Teacher {
	private static final long serialVersionUID = 1L;

	/**
	 * 学校名
	 */
	@ApiModelProperty(value = "学校名")
	private String schoolName;
}
