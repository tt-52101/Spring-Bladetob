package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.GoodsSku;
import cn.rzedu.sc.goods.utils.SkuUtil;
import cn.rzedu.sc.goods.vo.GoodsSkuVO;
import cn.rzedu.sc.goods.mapper.GoodsSkuMapper;
import cn.rzedu.sc.goods.service.IGoodsSkuService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 商品库存表，以一个SKU为记录单位 服务实现类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Service
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements IGoodsSkuService {

    @Override
    public IPage<GoodsSkuVO> selectGoodsSkuPage(IPage<GoodsSkuVO> page, GoodsSkuVO goodsSku) {
        return page.setRecords(baseMapper.selectGoodsSkuPage(page, goodsSku));
    }

    @Override
    public List<GoodsSkuVO> findByGoodsId(Integer goodsId) {
        List<GoodsSkuVO> list = baseMapper.findByGoodsId(goodsId);
        generateShortName(list);
        return list;
    }

    private void generateShortName(List<GoodsSkuVO> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        String shortName = "";
        for (GoodsSkuVO vo : list) {
            shortName = SkuUtil.extractSkuName(vo.getName());
            vo.setShortName(shortName);
        }
    }

    @Override
    public List<GoodsSkuVO> findByIds(List<Integer> ids) {
        return baseMapper.findByIds(ids);
    }
}
