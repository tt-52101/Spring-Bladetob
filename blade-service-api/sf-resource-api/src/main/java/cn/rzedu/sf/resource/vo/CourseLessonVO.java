package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.CourseLesson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 课程单元（课时），课程course的子单元（课时、节、集）
 * 视图实体类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CourseLessonVO对象", description = "课程单元（课时），课程course的子单元（课时、节、集） ")
public class CourseLessonVO extends CourseLesson {
    private static final long serialVersionUID = 1L;

    /**
     * 课程单元下所有文件类型，逗号分隔
     */
    @ApiModelProperty(value = "课程单元下所有文件类型，逗号分隔  1=视频  2=音频  3=课件  4=文档  5=打包  99=其他 ")
    private String formatTypes;
    /**
     * 课程单元下所有文件uuid，逗号分隔
     */
    @ApiModelProperty(value = "课程单元下所有文件uuid逗号分隔")
    private String uuids;
}
