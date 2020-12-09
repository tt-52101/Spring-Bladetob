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
 * 用户每日登陆记录实体类
 *
 * @author Blade
 * @since 2020-09-24
 */
@Data
@TableName("sf_user_login_log")
@ApiModel(value = "UserLoginLog对象", description = "用户每日登陆记录")
public class UserLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    /**
     * 用户uuid
     */
    @ApiModelProperty(value = "用户uuid")
    private String userUuid;
    /**
     * 登录时间
     */
    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginTime;
    /**
     * 登录来源
     */
    @ApiModelProperty(value = "登录来源")
    private Integer loginSource;
    /**
     * 登录的ip地址
     */
    @ApiModelProperty(value = "登录的ip地址")
    private String loginIp;
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


}
