package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.Goods;
import cn.rzedu.sc.goods.vo.GoodsVO;
import cn.rzedu.sc.goods.mapper.GoodsMapper;
import cn.rzedu.sc.goods.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.mp.support.Condition;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 商品表，即SPU：Standard Product Unit （标准产品单位），是商品信息聚合的最小单位 服务实现类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Override
    public IPage<GoodsVO> selectGoodsPage(IPage<GoodsVO> page, GoodsVO goods) {
        return page.setRecords(baseMapper.selectGoodsPage(page, goods));
    }

    @Override
    public boolean judgeRepeatByCode(String code, Integer id) {
        boolean isExist = false;
        List<Goods> list = baseMapper.findByCode(code);
        if (list != null && !list.isEmpty()) {
            if (id != null) {
                isExist = list.stream().anyMatch(goods -> !goods.getId().equals(id));
            } else {
                isExist = true;
            }
        }
        return isExist;
    }

    @Override
    public List<Goods> findCourseGoods() {
        Goods goods = new Goods();
        goods.setIsDeleted(0);
        goods.setOwnerType(1);
        List<Goods> list = baseMapper.selectList(Condition.getQueryWrapper(goods));
        return list;
    }
}
