package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.StatisLessonUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 课程链接点击用户视图实体类
 *
 * @author Blade
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StatisLessonUserVO对象", description = "课程链接点击用户")
public class StatisLessonUserVO extends StatisLessonUser {
	private static final long serialVersionUID = 1L;

}
