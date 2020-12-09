package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.OrderGoods;
import cn.rzedu.sc.goods.vo.OrderGoodsVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 订单商品表。一张订单的商品明细，是订单表的子表 服务类
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface IOrderGoodsService extends IService<OrderGoods> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param orderGoods
	 * @return
	 */
	IPage<OrderGoodsVO> selectOrderGoodsPage(IPage<OrderGoodsVO> page, OrderGoodsVO orderGoods);

	/**
	 * 根据订单id获取
	 * @param orderId
	 * @return
	 */
	List<OrderGoods> findByOrderId(Integer orderId);

}
