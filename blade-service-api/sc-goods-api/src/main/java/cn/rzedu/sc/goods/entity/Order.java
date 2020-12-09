package cn.rzedu.sc.goods.entity;

import java.math.BigDecimal;

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
 * 订单表。一个用户一次下单购买商品的订单，一个订单包括一个或多个商品
 * 实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@TableName("sc_order")
@ApiModel(value = "Order对象", description = "订单表。一个用户一次下单购买商品的订单，一个订单包括一个或多个商品 ")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String code;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**
     * 订单原价
     */
    @ApiModelProperty(value = "订单原价")
    private BigDecimal primePrice;
    /**
     * 订单实际支付价
     */
    @ApiModelProperty(value = "订单实际支付价")
    private BigDecimal dealPrice;
    /**
     * 状态  1=下单待支付  2=支付成功  4=已发货  5=交易成功  6=交易关闭  0=订单取消
     */
    @ApiModelProperty(value = "状态  1=下单待支付  2=支付成功  4=已发货  5=交易成功  6=交易关闭  0=订单取消")
    private Integer status;
    /**
     * 购买方式  1=单购 2=团购
     */
    @ApiModelProperty(value = "购买方式  1=单购 2=团购 ")
    private Integer purchaseType;
    /**
     * 团购参团ID   groupon_group.id
     */
    @ApiModelProperty(value = "团购参团ID   groupon_group.id")
    private Integer grouponGroupId;
    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private Integer payType;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;
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
