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
 * 学生教材学习情况表 实体类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@TableName("sf_user_textbook")
@ApiModel(value = "UserTextbook对象", description = "学生教材学习情况表。记录用户使用教材和学习教材的情况总表，每个学生每门教材产生一个记录")
public class UserTextbook implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户  user.id
     */
    @ApiModelProperty(value = "用户  user.id")
    private Integer userId;
    /**
     * 教材  textbook.id
     */
    @ApiModelProperty(value = "教材  textbook.id")
    private Integer textbookId;
    /**
     * 购买成功或获得的时间
     */
    @ApiModelProperty(value = "购买成功或获得的时间")
    private LocalDateTime ownedTime;
    /**
     * 初次激活时间
     */
    @ApiModelProperty(value = "初次激活时间")
    private LocalDateTime activeTime;
    /**
     * 最开始的学习时间
     * （第一次看进入课程，看视频）
     */
    @ApiModelProperty(value = "最开始的学习时间（第一次看进入课程，看视频）")
    private LocalDateTime startTime;
    /**
     * 完成所有课程内容的时间
     * （每个课程完成度都是100%）
     */
    @ApiModelProperty(value = "完成所有课程内容的时间 每个课程完成度都是100%）")
    private LocalDateTime finishTime;
    /**
     * 当前正在学习的课程  lesson_id
     */
    @ApiModelProperty(value = "当前正在学习的课程  lesson_id")
    private Integer activeLessonId;
    /**
     * 完成课程的总数
     */
    @ApiModelProperty(value = "完成课程的总数")
    private Integer finishedLessonCount;
    /**
     * 完成汉字学习的总数
     */
    @ApiModelProperty(value = "完成汉字学习的总数")
    private Integer finishedCharCount;
    /**
     * 完成本教材学习进度的百分数
     */
    @ApiModelProperty(value = "完成本教材学习进度的百分数")
    private Integer finishedPercent;
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
