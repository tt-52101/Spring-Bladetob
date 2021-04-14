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
import org.springblade.core.mp.base.BaseEntity;

/**
 * 用户表 书法系统的用户 实体类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Data
@TableName("sf_user")
@ApiModel(value = "User对象", description = "用户表 书法系统的用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 微信unionId
     */
    @ApiModelProperty(value = "微信unionId")
    private String unionId;
    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String username;
    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String mobile;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /**
     * 性别  0/n=未知 1=男 2=女
     */
    @ApiModelProperty(value = "性别  0/n=未知 1=男 2=女")
    private Integer sex;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private LocalDateTime birthday;

    /**
     * 头像  uuid
     */
    @ApiModelProperty(value = "头像  uuid")
    private String icon;
    /**
     * 所在省
     */
    @ApiModelProperty(value = "所在省")
    private String province;
    /**
     * 所在市
     */
    @ApiModelProperty(value = "所在市")
    private String city;
    /**
     * 所在区
     */
    @ApiModelProperty(value = "所在区")
    private String district;
    /**
     * 微信openId
     */
    @ApiModelProperty(value = "微信openId")
    private String openId;
    /**
     * 系统用户的唯一识别码
     */
    @ApiModelProperty(value = "系统用户的唯一识别码")
    private String uuid;
    /**
     * 用户来源  0=正常关注；1=海报助力关注；2=推文关注；3=课本扫码关注
     */
    @ApiModelProperty(value = "用户来源  0=正常关注；1=海报助力关注；2=推文关注；3=课本扫码关注")
    private Integer sourceType;
    /**
     * 关注状态  1=关注 0=取关
     */
    @ApiModelProperty(value = "关注状态  1=关注 0=取关")
    private Integer focusStatus;
    /**
     * 用户维度  1=新用户  2=老用户  3=其他用户
     */
    @ApiModelProperty(value = "用户维度  1=新用户  2=老用户  3=其他用户")
    private Integer dimension;
    /**
     * 是否机器人（虚拟用户）
     */
    @ApiModelProperty(value = "是否机器人（虚拟用户）")
    private Integer isRobot;

    /**
     * 是否机器人（虚拟用户）
     */
    @ApiModelProperty(value = "是否获取了微信用户信息）")
    private Boolean isGetInfo;
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
