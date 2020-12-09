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
 * 课程链接统计数据实体类
 *
 * @author Blade
 * @since 2020-09-25
 */
@Data
@TableName("sf_statis_lesson")
@ApiModel(value = "StatisLesson对象", description = "课程链接统计数据")
public class StatisLesson implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 课程id  sf_textbook_lesson.id
     */
    @ApiModelProperty(value = "课程id  sf_textbook_lesson.id")
    private Integer lessonId;
    /**
     * 课程名
     */
    @ApiModelProperty(value = "课程名")
    private String lessonName;
    /**
     * 发送次数
     */
    @ApiModelProperty(value = "发送次数")
    private Integer sendCount;
    /**
     * 点击次数
     */
    @ApiModelProperty(value = "点击次数")
    private Integer clickCount;
    /**
     * 点击人数
     */
    @ApiModelProperty(value = "点击人数")
    private Integer clickPeopleCount;
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
