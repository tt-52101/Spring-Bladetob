package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.GoodsSku;
import cn.rzedu.sc.goods.vo.GoodsSkuVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 商品库存表，以一个SKU为记录单位 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-16
 */
public interface GoodsSkuMapper extends BaseMapper<GoodsSku> {

    /**
     * 自定义分页
     *
     * @param page
     * @param goodsSku
     * @return
     */
    List<GoodsSkuVO> selectGoodsSkuPage(IPage page, GoodsSkuVO goodsSku);

    /**
     * 某商品下的所欲sku
     * @param goodsId
     * @return
     */
    List<GoodsSkuVO> findByGoodsId(Integer goodsId);

    /**
     * 根据skuIds获取
     * @param list
     * @return
     */
    List<GoodsSkuVO> findByIds(List<Integer> list);

}
