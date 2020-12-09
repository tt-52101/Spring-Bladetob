package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.OrderGoods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 订单商品表。一张订单的商品明细，是订单表的子表
 * 视图实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OrderGoodsVO对象", description = "订单商品表。一张订单的商品明细，是订单表的子表 ")
public class OrderGoodsVO extends OrderGoods {
    private static final long serialVersionUID = 1L;

}
