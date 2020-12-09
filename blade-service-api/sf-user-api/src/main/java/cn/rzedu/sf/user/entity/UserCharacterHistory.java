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
 * 学生学习汉字的历史记录 实体类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@TableName("sf_user_character_history")
@ApiModel(value = "UserCharacterHistory对象", description = "学生学习汉字的历史记录。这个表是user_character的历史记录")
public class UserCharacterHistory implements Serializable {

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
     * 最后访问（学习）时间
     */
    @ApiModelProperty(value = "最后访问（学习）时间")
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
