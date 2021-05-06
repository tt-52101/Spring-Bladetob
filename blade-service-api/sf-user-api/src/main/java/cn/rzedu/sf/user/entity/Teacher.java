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
 * 教师实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@TableName("sf_teacher")
@ApiModel(value = "Teacher对象", description = "教师")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id")
    private Integer schoolId;
    /**
     * 名字
     */
    @ApiModelProperty(value = "名字")
    private String name;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    private String icon;
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String code;
    /**
     * 微信unionId
     */
    @ApiModelProperty(value = "微信unionId")
    private String unionId;
    /**
     * 状态   1=启用，0=禁用
     */
    @ApiModelProperty(value = "状态   1=启用，0=禁用")
    private Integer status;
    /**
     * 任职状态   1=正常，0=离职
     */
    @ApiModelProperty(value = "任职状态   1=正常，0=离职")
    private Integer enrollStatus;
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
