package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.TeamStudent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 班级学生视图实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TeamStudentVO对象", description = "班级学生")
public class TeamStudentVO extends TeamStudent {
	private static final long serialVersionUID = 1L;

}
