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
 * 学生汉字学习的情况 实体类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@TableName("sf_user_character")
@ApiModel(value = "UserCharacter对象", description = "学生汉字学习的情况。记录用户针对每个汉字的学习情况，每个学习每学习一个汉字产生一个记录")
public class UserCharacter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户  user.id
     */
    @ApiModelProperty(value = "用户  user.id")
    private Integer userId;
    /**
     * 课程  textbook_lesson.id
     */
    @ApiModelProperty(value = "课程  textbook_lesson.id")
    private Integer lessonId;
    /**
     * 课程单元  textbook_lesson_character.id
     */
    @ApiModelProperty(value = "课程单元  textbook_lesson_character.id")
    private Integer characterLessonId;
    /**
     * 学习的汉字  character.id
     */
    @ApiModelProperty(value = "学习的汉字  character.id")
    private Integer characterId;
    /**
     * 已经完成学习的百分比
     */
    @ApiModelProperty(value = "已经完成学习的百分比")
    private Integer finishedPercent;
    /**
     * 视频观看进度，单位：秒。目前1000为最大值，表示已看完
     */
    @ApiModelProperty(value = "视频观看进度，单位：秒。目前1000为最大值，表示已看完")
    private Integer watchProgress;
    /**
     * 最后访问（学习）时间。当完成百分比已经是100%时不再更新此字段
     */
    @ApiModelProperty(value = "最后访问（学习）时间。当完成百分比已经是100%时不再更新此字段 ")
    private LocalDateTime lastVisitedTime;
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
