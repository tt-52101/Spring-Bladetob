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
 * 活动奖品领取用户实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@TableName("sf_activity_award_user")
@ApiModel(value = "ActivityAwardUser对象", description = "活动奖品领取用户")
public class ActivityAwardUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 奖品类型  1=千字文课程 2=诗词课程  3=礼包
     */
    @ApiModelProperty(value = "奖品类型  1=千字文课程 2=诗词课程  3=礼包")
    private Integer type;
    /**
     * 奖品礼包类型  1=礼包A  2=礼包B
     */
    @ApiModelProperty(value = "奖品礼包类型  1=礼包A  2=礼包B")
    private Integer awardType;
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
     * 收货人
     */
    @ApiModelProperty(value = "收货人")
    private String name;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /**
     * 收货地址
     */
    @ApiModelProperty(value = "收货地址")
    private String location;
    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String address;
    /**
     * 状态  1=发放  2=领取（填写收货地址） 3=发货
     */
    @ApiModelProperty(value = "状态  1=发放  2=领取（填写收货地址） 3=发货")
    private Integer status;
    /**
     * 用户点击状态 1=已点击
     */
    @ApiModelProperty(value = "用户点击状态 1=已点击")
    private Integer clickStatus;
    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNumber;
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
