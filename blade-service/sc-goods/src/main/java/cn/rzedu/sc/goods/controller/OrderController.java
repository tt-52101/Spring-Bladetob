package cn.rzedu.sc.goods.controller;

import cn.rzedu.sc.goods.entity.Goods;
import cn.rzedu.sc.goods.entity.GoodsSku;
import cn.rzedu.sc.goods.entity.OrderGoods;
import cn.rzedu.sc.goods.service.IGoodsService;
import cn.rzedu.sc.goods.service.IGoodsSkuService;
import cn.rzedu.sc.goods.service.IOrderGoodsService;
import cn.rzedu.sc.goods.vo.UserOrderSalesVO;
import cn.rzedu.sf.user.feign.IUserLessonClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.rzedu.sc.goods.entity.Order;
import cn.rzedu.sc.goods.vo.OrderVO;
import cn.rzedu.sc.goods.wrapper.OrderWrapper;
import cn.rzedu.sc.goods.service.IOrderService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 订单 控制器
 *
 * @author Blade
 * @since 2020-10-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/order")
@Api(value = "订单业务", tags = "订单业务")
public class OrderController extends BladeController {

    private IOrderService orderService;

    private IOrderGoodsService orderGoodsService;

    private IGoodsService goodsService;

    private IGoodsSkuService goodsSkuService;

    private IUserLessonClient userLessonClient;


    /**
     *  订单列表
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "订单列表", notes = "订单列表")
    public R<IPage<OrderVO>> list(
            @ApiParam(value = "课程名称") @RequestParam(value = "goodsNames", required = false) String goodsNames,
            @ApiParam(value = "订单号") @RequestParam(value = "code", required = false) String code,
            Query query) {
        OrderVO orderVO = new OrderVO();
        orderVO.setCode(code);
        orderVO.setGoodsNames(goodsNames);
        IPage<OrderVO> pages = orderService.findByOrderVO(Condition.getPage(query), orderVO);
        return R.data(pages);
    }

    /**
     *  用户消费总额列表
     */
    @GetMapping("/sales/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "用户消费列表", notes = "用户消费总额列表")
    public R<IPage<UserOrderSalesVO>> list(Query query) {
        IPage<UserOrderSalesVO> pages = orderService.findUserOrderSales(Condition.getPage(query));
        return R.data(pages);
    }


    /**
     * 订单支付成功回调
     */
    @PostMapping("/pay/notify")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "订单支付成功回调", notes = "订单支付成功回调")
    public R payNotify(@ApiParam(value = "订单号", required = true) @RequestParam String orderCode) {
        OrderVO order = orderService.findByCode(orderCode);
        if (order == null) {
            return R.fail("订单不存在");
        }
        if (2 == order.getStatus()) {
            return R.success("订单已支付成功");
        }
        boolean success = orderService.paySuccess(orderCode);
        if (!success) {
            return R.fail("订单状态修改失败");
        }

        List<OrderGoods> list = orderGoodsService.findByOrderId(order.getId());
        if (list == null || list.isEmpty()) {
            return R.status(true);
        }
        Integer userId = order.getUserId();
        Goods goods = null;
        Integer ownerType = null;
        GoodsSku goodsSku = null;
        for (OrderGoods orderGoods : list) {
            goods = goodsService.getById(orderGoods.getGoodsId());
            if (goods == null) {
                continue;
            }
            ownerType = goods.getOwnerType();
            if (1 == ownerType) {
                //添加通用课程
                userLessonClient.createCourse(userId, goods.getOwnerId());
            } else if (2 == ownerType && orderGoods.getGoodsSkuId() != null) {
                //添加软硬笔
                goodsSku = goodsSkuService.getById(orderGoods.getGoodsSkuId());
                if (goodsSku != null) {
                    userLessonClient.createTextbook(userId, goodsSku.getOwnerId());
                }
            }
        }
        return R.status(true);
    }


    /**
     * 订单支付成功回调
     */
    @PostMapping("/test/pay")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "订单支付成功回调，测试用", notes = "订单支付成功回调")
    public R testPayNotify(@ApiParam(value = "订单号", required = true) @RequestParam String orderCode,
                       @ApiParam(value = "总金额，单价：分", required = true) @RequestParam Integer totalFee) {
        return orderService.payNotify(orderCode, totalFee);
    }
}
