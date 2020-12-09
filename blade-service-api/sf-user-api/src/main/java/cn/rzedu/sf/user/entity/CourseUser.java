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
 * 课程用户，参加到某个课程course的用户
 * 实体类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Data
@TableName("sf_course_user")
@ApiModel(value = "CourseUser对象", description = "课程用户，参加到某个课程course的用户 ")
public class CourseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对应的课程  course.id
     */
    @ApiModelProperty(value = "对应的课程  course.id")
    private Integer courseId;
    /**
     * 对应的用户  user.id
     */
    @ApiModelProperty(value = "对应的用户  user.id")
    private Integer userId;
    /**
     * 加入时间（购买成功、获赠等的时间）
     */
    @ApiModelProperty(value = "加入时间（购买成功、获赠等的时间）")
    private LocalDateTime ownedTime;
    /**
     * 加入课程方式  1=单购  2=团购  3=免费试用
     */
    @ApiModelProperty(value = "加入课程方式  1=单购  2=团购  3=免费试用")
    private Integer ownedType;
    /**
     * 拥有课程的有效期（最后有效时间）如无为空
     */
    @ApiModelProperty(value = "拥有课程的有效期（最后有效时间）如无为空")
    private LocalDateTime ownedDeadline;
    /**
     * 最开始的学习时间
     */
    @ApiModelProperty(value = "最开始的学习时间")
    private LocalDateTime startedTime;
    /**
     * 完成所有课程内容的时间
     */
    @ApiModelProperty(value = "完成所有课程内容的时间")
    private LocalDateTime finishedTime;
    /**
     * 当前正在学习的课程  course_lesson.id
     */
    @ApiModelProperty(value = "当前正在学习的课程  course_lesson.id")
    private Integer activeLessonId;
    /**
     * 完成课程的总数
     */
    @ApiModelProperty(value = "完成课程的总数")
    private Integer finishedLessonCount;
    /**
     * 完成本教材学习进度的百分数
     */
    @ApiModelProperty(value = "完成本教材学习进度的百分数")
    private Integer finishedPercent;
    /**
     * 获得星星的数目
     */
    @ApiModelProperty(value = "获得星星的数目")
    private Integer starCount;
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
