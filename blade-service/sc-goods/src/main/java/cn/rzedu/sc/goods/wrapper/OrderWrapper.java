package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.Order;
import cn.rzedu.sc.goods.vo.OrderVO;

/**
 * 订单表。一个用户一次下单购买商品的订单，一个订单包括一个或多个商品包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-12
 */
public class OrderWrapper extends BaseEntityWrapper<Order, OrderVO> {

    public static OrderWrapper build() {
        return new OrderWrapper();
    }

    @Override
    public OrderVO entityVO(Order order) {
        OrderVO orderVO = BeanUtil.copy(order, OrderVO.class);

        return orderVO;
    }

}
