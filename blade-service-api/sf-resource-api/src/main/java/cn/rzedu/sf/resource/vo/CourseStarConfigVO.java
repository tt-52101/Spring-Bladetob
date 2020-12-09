package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.CourseStarConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 通用课程星星数配置表视图实体类
 *
 * @author Blade
 * @since 2020-09-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CourseStarConfigVO对象", description = "通用课程星星数配置表")
public class CourseStarConfigVO extends CourseStarConfig {
    private static final long serialVersionUID = 1L;

}
