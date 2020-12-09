package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.StatisLesson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 课程链接统计数据视图实体类
 *
 * @author Blade
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StatisLessonVO对象", description = "课程链接统计数据")
public class StatisLessonVO extends StatisLesson {
	private static final long serialVersionUID = 1L;

}
