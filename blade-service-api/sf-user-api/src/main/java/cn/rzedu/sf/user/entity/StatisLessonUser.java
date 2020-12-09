package cn.rzedu.sf.user.entity;

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
 * 课程链接点击用户实体类
 *
 * @author Blade
 * @since 2020-09-25
 */
@Data
@TableName("sf_statis_lesson_user")
@ApiModel(value = "StatisLessonUser对象", description = "课程链接点击用户")
public class StatisLessonUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 课程id  sf_textbook_lesson.id
     */
    @ApiModelProperty(value = "课程id  sf_textbook_lesson.id")
    private Integer lessonId;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;
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
    @ApiModelProperty(value = "删除标识")
    private Integer isDeleted;


}
