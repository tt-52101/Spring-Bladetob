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
 * 班级学生实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@TableName("sf_team_teacher")
@ApiModel(value = "TeamTeacher对象", description = "班级学生")
public class TeamTeacher implements Serializable {

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
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    /**
     * 老师姓名
     */
    @ApiModelProperty(value = "老师姓名")
    private String name;
    /**
     * 任职类型  1=班主任  2=普通老师
     */
    @ApiModelProperty(value = "任职类型  1=班主任  2=普通老师")
    private Integer type;
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
