package cn.rzedu.sf.user.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 课程学习情况表，记录用户在教材里面的课程lesson的学习情况，每个学生学习一个课程产生一个记录实体类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Data
@TableName("sf_course_user_lesson")
@ApiModel(value = "CourseUserLesson对象", description = "课程学习情况表，记录用户在教材里面的课程lesson的学习情况，每个学生学习一个课程产生一个记录")
public class CourseUserLesson implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对应的用户  user.id
     */
    @ApiModelProperty(value = "对应的用户  user.id")
    private Integer userId;
    /**
     * 课程用户记录  course_user.id
     */
    @ApiModelProperty(value = "课程用户记录  course_user.id")
    private Integer courseUserId;
    /**
     * 课程  course_lesson.id
     */
    @ApiModelProperty(value = "课程  course_lesson.id")
    private Integer lessonId;
    /**
     * 是否完成本单元
     */
    @ApiModelProperty(value = "是否完成本单元")
    private Boolean hasFinished;
    /**
     * 是否解锁本单元
     */
    @ApiModelProperty(value = "是否解锁本单元")
    private Boolean unlocked;
    /**
     * 已看的视频或音频时长，单位：秒
     */
    @ApiModelProperty(value = "已看的视频或音频时长，单位：秒")
    private Integer finishedDuration;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyDate;
    /**
     * 删除标识
     */
    @TableLogic
    @ApiModelProperty(value = "删除标识")
    private Integer isDeleted;


}
