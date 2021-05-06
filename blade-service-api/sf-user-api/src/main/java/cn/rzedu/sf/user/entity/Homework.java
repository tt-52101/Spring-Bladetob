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
 * 作业实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@TableName("sf_homework")
@ApiModel(value = "Homework对象", description = "作业")
public class Homework implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id")
    private Integer schoolId;
    /**
     * 班级id
     */
    @ApiModelProperty(value = "班级id")
    private Integer teamId;
    /**
     * 教师id
     */
    @ApiModelProperty(value = "教师id")
    private Integer teacherId;
    /**
     * 作业名字
     */
    @ApiModelProperty(value = "作业名字")
    private String name;
    /**
     * 作业内容
     */
    @ApiModelProperty(value = "作业内容")
    private String content;
    /**
     * 作业图片
     */
    @ApiModelProperty(value = "作业图片")
    private String photos;
    /**
     * 作业音频
     */
    @ApiModelProperty(value = "作业音频")
    private String audios;
    /**
     * 作业所在课程
     */
    @ApiModelProperty(value = "作业所在课程")
    private Integer lessonId;
    /**
     * 课程生字id
     */
    @ApiModelProperty(value = "课程生字id")
    private String charIds;
    /**
     * 课程生字
     */
    @ApiModelProperty(value = "课程生字")
    private String chars;
    /**
     * 截止时间
     */
    @ApiModelProperty(value = "截止时间")
    private LocalDateTime deadline;
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
