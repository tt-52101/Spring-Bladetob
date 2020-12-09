package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.GrouponGroupGoods;
import cn.rzedu.sc.goods.vo.GrouponGroupGoodsVO;
import cn.rzedu.sc.goods.mapper.GrouponGroupGoodsMapper;
import cn.rzedu.sc.goods.service.IGrouponGroupGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 团购的商品。某团购团购买的商品项目 服务实现类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Service
public class GrouponGroupGoodsServiceImpl extends ServiceImpl<GrouponGroupGoodsMapper, GrouponGroupGoods> implements IGrouponGroupGoodsService {

    @Override
    public IPage<GrouponGroupGoodsVO> selectGrouponGroupGoodsPage(IPage<GrouponGroupGoodsVO> page,
																  GrouponGroupGoodsVO grouponGroupGoods) {
        return page.setRecords(baseMapper.selectGrouponGroupGoodsPage(page, grouponGroupGoods));
    }

    @Override
    public List<GrouponGroupGoodsVO> findByGrouponGroupId(Integer grouponGroupId) {
        return baseMapper.findByGrouponGroupId(grouponGroupId);
    }
}
