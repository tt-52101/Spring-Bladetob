package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.Course;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 通用课程
 * 视图实体类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CourseVO对象", description = "线上课程 ")
public class CourseVO extends Course {
    private static final long serialVersionUID = 1L;

}
