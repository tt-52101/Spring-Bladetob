package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.CourseUserLesson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 课程学习情况表，记录用户在教材里面的课程lesson的学习情况，每个学生学习一个课程产生一个记录视图实体类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CourseUserLessonVO对象", description = "课程学习情况表，记录用户在教材里面的课程lesson的学习情况，每个学生学习一个课程产生一个记录")
public class CourseUserLessonVO extends CourseUserLesson {
    private static final long serialVersionUID = 1L;

    /**
     * 本节单元名称
     */
    @ApiModelProperty(value = "本节单元名称")
    private String lessonName;
    /**
     * 排序次序
     */
    @ApiModelProperty(value = "排序次序")
    private Integer lessonIndex;
    /**
     * 资源类型  1=视频课  2=音频课  9=其他
     */
    @ApiModelProperty(value = "资源类型  1=视频课  2=音频课  9=其他 ")
    private Integer resourceType;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型，逗号分隔  1=视频  2=音频  3=课件  4=文档  5=打包  99=其他 ")
    private Integer formatType;
    /**
     * 文件uuid
     */
    @ApiModelProperty(value = "文件uuid")
    private String uuid;
    /**
     * 学习人数
     */
    @ApiModelProperty(value = "学习人数")
    private Integer learnCount;

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
