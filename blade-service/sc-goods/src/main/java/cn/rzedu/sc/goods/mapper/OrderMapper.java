package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.Order;
import cn.rzedu.sc.goods.vo.OrderVO;
import cn.rzedu.sc.goods.vo.UserOrderSalesVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 订单表。一个用户一次下单购买商品的订单，一个订单包括一个或多个商品 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface OrderMapper extends BaseMapper<Order> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param order
	 * @return
	 */
	List<OrderVO> selectOrderPage(IPage page, OrderVO order);

	/**
	 * 订单分页，带用户名、商品名
	 * @param page
	 * @param order
	 * @return
	 */
	List<OrderVO> findByOrderVO(IPage page, OrderVO order);

	/**
	 * 用户消费总额
	 * @param page
	 * @return
	 */
	List<UserOrderSalesVO> findUserOrderSales(IPage page);

	/**
	 * 根据订单号修改状态
	 * @param code
	 * @param status
	 */
	int updateStatusByCode(String code, Integer status);

	/**
	 * 根据订单号查询
	 * @param code
	 * @return
	 */
	OrderVO findByCode(String code);

}
