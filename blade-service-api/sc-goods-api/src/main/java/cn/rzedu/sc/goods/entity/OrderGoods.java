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
 * 订单商品表。一张订单的商品明细，是订单表的子表
 * 实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@TableName("sc_order_goods")
@ApiModel(value = "OrderGoods对象", description = "订单商品表。一张订单的商品明细，是订单表的子表 ")
public class OrderGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单记录ID   order.id
     */
    @ApiModelProperty(value = "订单记录ID   order.id")
    private Integer orderId;
    /**
     * 商品记录ID   goods.id
     */
    @ApiModelProperty(value = "商品记录ID   goods.id")
    private Integer goodsId;
    /**
     * 商品规格（暂无）
     */
    @ApiModelProperty(value = "商品规格（暂无）")
    private Integer goodsSkuId;
    /**
     * 商品名称（冗余）
     */
    @ApiModelProperty(value = "商品名称（冗余）")
    private String goodsName;
    /**
     * 原单价
     */
    @ApiModelProperty(value = "原单价")
    private BigDecimal primePrice;
    /**
     * 折扣后单价
     */
    @ApiModelProperty(value = "折扣后单价")
    private BigDecimal actualPrice;
    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量")
    private Integer count;
    /**
     * 支付总价
     */
    @ApiModelProperty(value = "支付总价")
    private BigDecimal dealPrice;
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
