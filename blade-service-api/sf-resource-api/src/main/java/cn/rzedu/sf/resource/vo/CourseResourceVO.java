package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.CourseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 课程资源
 * 资源文件一般与课程单元挂钩，一个单元可能有多个文件
 * 视图实体类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CourseResourceVO对象", description = "课程资源，资源文件一般与课程单元挂钩，一个单元可能有多个文件")
public class CourseResourceVO extends CourseResource {
    private static final long serialVersionUID = 1L;

}
