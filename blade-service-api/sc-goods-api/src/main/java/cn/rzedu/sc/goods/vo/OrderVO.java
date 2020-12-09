package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.Order;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 订单表。一个用户一次下单购买商品的订单，一个订单包括一个或多个商品
 * 视图实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OrderVO对象", description = "订单表。一个用户一次下单购买商品的订单，一个订单包括一个或多个商品 ")
public class OrderVO extends Order {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 商品名称（多个汇总）
     */
    @ApiModelProperty(value = "商品名称（多个汇总）")
    private String goodsNames;
}
