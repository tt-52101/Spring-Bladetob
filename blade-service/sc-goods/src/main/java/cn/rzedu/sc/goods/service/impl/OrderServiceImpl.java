package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.*;
import cn.rzedu.sc.goods.service.*;
import cn.rzedu.sc.goods.utils.OrderCodeUtil;
import cn.rzedu.sc.goods.utils.PushMessageUtil;
import cn.rzedu.sc.goods.vo.GoodsSkuVO;
import cn.rzedu.sc.goods.vo.GrouponRuleVO;
import cn.rzedu.sc.goods.vo.OrderVO;
import cn.rzedu.sc.goods.mapper.OrderMapper;
import cn.rzedu.sc.goods.vo.UserOrderSalesVO;
import cn.rzedu.sf.user.feign.ISFUserClient;
import cn.rzedu.sf.user.feign.IUserLessonClient;
import cn.rzedu.sf.user.vo.UserVO;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单表。一个用户一次下单购买商品的订单，一个订单包括一个或多个商品 服务实现类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private IOrderGoodsService orderGoodsService;

    private IDealUserService dealUserService;

    private IGoodsSkuService goodsSkuService;

    private IGoodsService goodsService;

    private IUserLessonClient userLessonClient;

    private ISFUserClient userClient;

    private IGrouponGroupService grouponGroupService;

    private IGrouponRuleService grouponRuleService;

    private final static String GROUPON_JOIN_TEMPLATE = "GvqntvNC53pDYDT_lJQasiOeiTybD-ITFn3HuWLs5Hg";
    private final static String GROUPON_SUCCESS_TEMPLATE = "7tao3SerMDOTY1_7CYewIOtnGyIpZAOU5PcXGmwkojk";
    private final static String GROUPON_MEMBER_LACK_TEMPLATE = "nqGKLcQoGtJOZtK_fdFWsLPtqAryHOdrxX9gmFJBxWs";

    @Override
    public IPage<OrderVO> selectOrderPage(IPage<OrderVO> page, OrderVO order) {
        return page.setRecords(baseMapper.selectOrderPage(page, order));
    }

    @Override
    public IPage<OrderVO> findByOrderVO(IPage page, OrderVO order) {
        return page.setRecords(baseMapper.findByOrderVO(page, order));
    }

    @Override
    public IPage<UserOrderSalesVO> findUserOrderSales(IPage page) {
        return page.setRecords(baseMapper.findUserOrderSales(page));
    }

    @Override
    public Order createOrderByGroupon(Integer userId, GrouponRuleVO grouponRuleVO, Integer purchaseType,
                                      Integer payType, String skuIds, Integer grouponGroupId, Integer status) {

        //生成订单号
        String orderCode = OrderCodeUtil.getOrderCode(userId);
        //生成订单
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order();
        order.setCode(orderCode);
        order.setUserId(userId);
        order.setPrimePrice(grouponRuleVO.getPrimePrice());
        order.setDealPrice(grouponRuleVO.getGroupPrice());
        //单购支付价格为原价
        if (1 == purchaseType) {
            order.setDealPrice(grouponRuleVO.getPrimePrice());
        }
        order.setStatus(status);
        order.setPurchaseType(purchaseType);
        order.setGrouponGroupId(grouponGroupId);
        order.setPayType(payType);
        order.setPayTime(now);
        order.setCreateDate(now);
        baseMapper.insert(order);

        Integer orderId = order.getId();
        Integer goodsId = grouponRuleVO.getGoodsId();

        //订单商品
        if (StringUtils.isBlank(skuIds)) {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderId(orderId);
            orderGoods.setGoodsId(goodsId);
            orderGoods.setGoodsName(grouponRuleVO.getGoodsName());
            orderGoods.setPrimePrice(grouponRuleVO.getPrimePrice());
            orderGoods.setActualPrice(grouponRuleVO.getGroupPrice());
            orderGoods.setCount(1);
            orderGoods.setDealPrice(grouponRuleVO.getGroupPrice());
            orderGoods.setCreateDate(now);
            orderGoodsService.save(orderGoods);

        } else {
            List<GoodsSkuVO> goodsSkuVOList = goodsSkuService.findByIds(Func.toIntList(skuIds));
            for (GoodsSkuVO vo : goodsSkuVOList) {
                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setOrderId(orderId);
                orderGoods.setGoodsId(goodsId);
                orderGoods.setGoodsSkuId(vo.getId());
                orderGoods.setGoodsName(grouponRuleVO.getGoodsName());
                orderGoods.setPrimePrice(vo.getPrice());
                orderGoods.setActualPrice(vo.getPrice());
                orderGoods.setCount(1);
                orderGoods.setDealPrice(vo.getPrice());
                orderGoods.setCreateDate(now);
                orderGoodsService.save(orderGoods);
            }
        }

        //订单流水
        DealUser dealUser = new DealUser();
        dealUser.setUserId(userId);
        dealUser.setOrderId(orderId);
        dealUser.setGoodsId(goodsId);
        dealUser.setDealPrice(grouponRuleVO.getGroupPrice());
        dealUser.setCreateDate(now);
        dealUserService.save(dealUser);

        return order;
    }

    @Override
    public boolean paySuccess(String orderCode) {
        return SqlHelper.retBool(baseMapper.updateStatusByCode(orderCode, 2));
    }

    @Override
    public OrderVO findByCode(String orderCode) {
        return baseMapper.findByCode(orderCode);
    }

    @Override
    public R payNotify(String orderCode, Integer totalFee) {
        OrderVO order = baseMapper.findByCode(orderCode);
        if (order == null) {
            return R.fail("订单不存在");
        }
        if (2 == order.getStatus()) {
            return R.success("订单已支付成功");
        }
        if (!totalFee.equals((order.getDealPrice().multiply(new BigDecimal(100)).intValue()))) {
            return R.fail("订单金额不一致，支付金额：" + totalFee +"，订单金额：" + (order.getDealPrice().multiply(new BigDecimal(100)).intValue()) );
        }
        boolean success = paySuccess(orderCode);
        if (!success) {
            return R.fail("订单状态修改失败");
        }

        List<OrderGoods> list = orderGoodsService.findByOrderId(order.getId());
        if (list == null || list.isEmpty()) {
            return R.status(true);
        }

        //团购商品，需成团人数已满后才能发放课程，且参团人同时发放
        Integer purchaseType = order.getPurchaseType();
        Integer grouponGroupId = order.getGrouponGroupId();
        if (1 == purchaseType) {
            createUserCourse(list, order.getUserId());
        } else if (grouponGroupId != null){
            //只获取能成团的非机器人用户
            List<Integer> userIds = grouponGroupService.findNotRobotFinishGroupUser(grouponGroupId);
            if (userIds != null && !userIds.isEmpty()) {
                for (Integer userId : userIds) {
                    createUserCourse(list, userId);
                }
            }
            //发送通知消息
            sendMessage(grouponGroupId, order.getUserId(), userIds);
        }
        return R.status(true);
    }

    private void createUserCourse(List<OrderGoods> list, Integer userId) {
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
    }

    private void sendMessage(Integer grouponGroupId, Integer userId, List<Integer> userIds) {
        GrouponGroup grouponGroup = grouponGroupService.getById(grouponGroupId);
        if (grouponGroup != null) {
            Integer grouponId = grouponGroup.getGrouponId();
            Integer status = grouponGroup.getStatus();
            Integer launchUserId = grouponGroup.getLaunchUserId();

            GrouponRuleVO grouponRule = grouponRuleService.findDetailById(grouponGroup.getGrouponId());
            //拼团成功，给所有人发送成功消息
            if (1 == status) {
                String name = userClient.detail(userId).getData().getName();
                for (Integer id : userIds) {
                    PushMessageUtil.sendFinishGrouponMessage(GROUPON_SUCCESS_TEMPLATE,
                            getUserOpenId(id), name, grouponRule.getGoodsName(),
                            grouponRule.getGroupPrice().toString());
                }
            } else if (0 == status) {
                if (!launchUserId.equals(userId)) {
                    //当前人!=发起人，给发起人消息
                    String name = userClient.detail(userId).getData().getName();
                    PushMessageUtil.sendJoinGrouponMessage(GROUPON_JOIN_TEMPLATE,
                            getUserOpenId(launchUserId), name, grouponRule.getGoodsName(), grouponId, grouponGroupId);
                }
            }
        }
    }

    private String getUserOpenId(Integer userId) {
        R<UserVO> result = userClient.detail(userId);
        return result.getData().getOpenId();
    }
}
