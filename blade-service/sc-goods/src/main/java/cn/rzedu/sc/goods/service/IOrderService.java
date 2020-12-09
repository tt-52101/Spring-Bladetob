package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.Order;
import cn.rzedu.sc.goods.vo.GrouponRuleVO;
import cn.rzedu.sc.goods.vo.OrderVO;
import cn.rzedu.sc.goods.vo.UserOrderSalesVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.tool.api.R;

import java.util.List;

/**
 * 订单表。一个用户一次下单购买商品的订单，一个订单包括一个或多个商品 服务类
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface IOrderService extends IService<Order> {

    /**
     * 自定义分页
     *
     * @param page
     * @param order
     * @return
     */
    IPage<OrderVO> selectOrderPage(IPage<OrderVO> page, OrderVO order);

    /**
     * 订单分页，带用户名、商品名
     * @param page
     * @param order
     * @return
     */
    IPage<OrderVO> findByOrderVO(IPage page, OrderVO order);

    /**
     * 用户消费总额
     * @param page
     * @return
     */
    IPage<UserOrderSalesVO> findUserOrderSales(IPage page);

    /**
     * 团购生成用户订单
     * @param userId        用户id
     * @param grouponRuleVO 团购课程
     * @param purchaseType  购买方式  1=单购 2=团购
     * @param payType       支付类型，1=微信支付
     * @param skuIds        团购课程skuId，可无
     * @param grouponGroupId    拼团id，可无
     * @param status        状态  1=下单待支付  2=支付成功  4=已发货  5=交易成功  6=交易关闭  0=订单取消
     * @return
     */
    Order createOrderByGroupon(Integer userId, GrouponRuleVO grouponRuleVO, Integer purchaseType,
                               Integer payType, String skuIds, Integer grouponGroupId, Integer status);

    /**
     * 订单支付成功
     * @param orderCode 订单号
     * @return
     */
    boolean paySuccess(String orderCode);

    /**
     * 根据订单号查询
     * @param orderCode
     * @return
     */
    OrderVO findByCode(String orderCode);

    /**
     * 微信支付后修改订单状态
     * @param orderCode     订单号
     * @param totalFee      支付金额
     * @return
     */
    R payNotify(String orderCode, Integer totalFee);
}
