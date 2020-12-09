package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.LessonStarConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 课程星星数配置表视图实体类
 *
 * @author Blade
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "LessonStarConfigVO对象", description = "课程星星数配置表")
public class LessonStarConfigVO extends LessonStarConfig {
    private static final long serialVersionUID = 1L;

}
