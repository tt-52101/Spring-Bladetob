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
 * 班级实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@TableName("sf_team")
@ApiModel(value = "Team对象", description = "班级")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id")
    private Integer schoolId;
    /**
     * 年级code
     */
    @ApiModelProperty(value = "年级code")
    private String gradeCode;
    /**
     * 班级类型
     */
    @ApiModelProperty(value = "班级类型")
    private Integer teamType;
    /**
     * 班级名
     */
    @ApiModelProperty(value = "班级名")
    private String name;
    /**
     * 预设人数
     */
    @ApiModelProperty(value = "预设人数")
    private Integer preCount;
    /**
     * 现有学生人数
     */
    @ApiModelProperty(value = "现有学生人数")
    private Integer memberCount;
    /**
     * 教材
     */
    @ApiModelProperty(value = "教材")
    private Integer textbookId;
    /**
     * 现学习课程
     */
    @ApiModelProperty(value = "现学习课程")
    private Integer lessonId;
    /**
     * 班级口令
     */
    @ApiModelProperty(value = "班级口令")
    private String password;
    /**
     * 班主任userid
     */
    @ApiModelProperty(value = "班主任userid")
    private Integer classMasterId;
    /**
     * 班主任名
     */
    @ApiModelProperty(value = "班主任名")
    private String classMaster;
    /**
     * 教师数量
     */
    @ApiModelProperty(value = "教师数量")
    private Integer teacherCount;
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
