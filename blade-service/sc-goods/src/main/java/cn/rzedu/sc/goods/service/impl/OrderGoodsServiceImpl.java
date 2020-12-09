package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.OrderGoods;
import cn.rzedu.sc.goods.vo.OrderGoodsVO;
import cn.rzedu.sc.goods.mapper.OrderGoodsMapper;
import cn.rzedu.sc.goods.service.IOrderGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 订单商品表。一张订单的商品明细，是订单表的子表 服务实现类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements IOrderGoodsService {

    @Override
    public IPage<OrderGoodsVO> selectOrderGoodsPage(IPage<OrderGoodsVO> page, OrderGoodsVO orderGoods) {
        return page.setRecords(baseMapper.selectOrderGoodsPage(page, orderGoods));
    }

    @Override
    public List<OrderGoods> findByOrderId(Integer orderId) {
        return baseMapper.findByOrderId(orderId);
    }
}
