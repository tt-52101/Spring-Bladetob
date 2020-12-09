package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.OrderGoods;
import cn.rzedu.sc.goods.vo.OrderGoodsVO;

/**
 * 订单商品表。一张订单的商品明细，是订单表的子表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-12
 */
public class OrderGoodsWrapper extends BaseEntityWrapper<OrderGoods, OrderGoodsVO> {

    public static OrderGoodsWrapper build() {
        return new OrderGoodsWrapper();
    }

    @Override
    public OrderGoodsVO entityVO(OrderGoods orderGoods) {
        OrderGoodsVO orderGoodsVO = BeanUtil.copy(orderGoods, OrderGoodsVO.class);

        return orderGoodsVO;
    }

}
