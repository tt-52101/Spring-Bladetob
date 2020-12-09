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
 * 学生课程学习情况表 实体类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@TableName("sf_user_lesson")
@ApiModel(value = "UserLesson对象", description = " 学生课程学习情况表。记录用户在教材里面的课程lesson的学习情况，每个学生学习一个课程产生一个记录")
public class UserLesson implements Serializable {

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
     * 课程  textbook_lesson.id
     */
    @ApiModelProperty(value = "课程  textbook_lesson.id")
    private Integer lessonId;
    /**
     * 本课包含的待学习汉字总数。与主表值一样，冗余
     */
    @ApiModelProperty(value = "本课包含的待学习汉字总数。与主表值一样，冗余 ")
    private Integer charCount;
    /**
     * 学生已经完成本课程学习的汉字数
     */
    @ApiModelProperty(value = "学生已经完成本课程学习的汉字数")
    private Integer finishedCharCount;
    /**
     * 否解锁本课程
     */
    @ApiModelProperty(value = "否解锁本课程")
    private Boolean locked;
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
