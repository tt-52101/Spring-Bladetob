package cn.rzedu.sf.user.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@TableName("sf_student")
@ApiModel(value = "Student对象", description = "Student对象")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 用户id  sf_user.id
     */
    @ApiModelProperty(value = "用户id  sf_user.id")
    private Integer userId;
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
     * 班级名称
     */
    @ApiModelProperty(value = "班级名称")
    private String teamName;
    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名")
    private String name;
    /**
     * 头像，默认微信头像
     */
    @ApiModelProperty(value = "头像，默认微信头像")
    private String icon;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Integer sex;
    /**
     * 学号
     */
    @ApiModelProperty(value = "学号")
    private Integer number;
    /**
     * 微信unionId
     */
    @ApiModelProperty(value = "微信unionId")
    private String unionId;
    /**
     * 绑定关系来源  1=本人 2=爸爸  3=妈妈
     */
    @ApiModelProperty(value = "绑定关系来源  1=本人 2=爸爸  3=妈妈")
    private Integer bindingSource;
    /**
     * 在读状态  1=正常  2=离校
     */
    @ApiModelProperty(value = "在读状态  1=正常  2=离校")
    private Integer studyStatus;
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
