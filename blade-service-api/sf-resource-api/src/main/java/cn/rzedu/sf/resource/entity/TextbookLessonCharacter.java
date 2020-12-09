package cn.rzedu.sf.resource.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 课程相关字体（资源）
 * 1.课程lesson里面包含的字（可关联到资源）
 * 2.相关字关联到汉字表里面的具体某个字，但要通过科目类型区分以确定是软硬哪种资源
 * 实体类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@TableName("sf_textbook_lesson_character")
@ApiModel(value = "TextbookLessonCharacter对象", description = "课程相关字体（资源），1.课程lesson里面包含的字（可关联到资源）2" +
        ".相关字关联到汉字表里面的具体某个字，但要通过科目类型区分以确定是软硬哪种资源")
public class TextbookLessonCharacter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 对应的课程  lesson.id
     */
    @ApiModelProperty(value = "对应的课程  lesson.id")
    private Integer lessonId;
    /**
     * 对应的汉字  character.id
     */
    @ApiModelProperty(value = "对应的汉字  character.id")
    private Integer characterId;
    /**
     * 教材的学科  71=软笔书法 72=硬笔书法
     */
    @ApiModelProperty(value = "教材的学科  71=软笔书法 72=硬笔书法")
    private Integer subject;
    /**
     * 访问总次数
     */
    @ApiModelProperty(value = "访问总次数")
    private Integer visitedCount;
    /**
     * 完成总次数（视频看完100%）
     */
    @ApiModelProperty(value = "完成总次数（视频看完100%）")
    private Integer finishedCount;
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
