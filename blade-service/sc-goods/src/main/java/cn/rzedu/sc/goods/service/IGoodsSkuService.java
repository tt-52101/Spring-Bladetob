package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.GoodsSku;
import cn.rzedu.sc.goods.vo.GoodsSkuVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 商品库存表，以一个SKU为记录单位 服务类
 *
 * @author Blade
 * @since 2020-10-16
 */
public interface IGoodsSkuService extends IService<GoodsSku> {

    /**
     * 自定义分页
     *
     * @param page
     * @param goodsSku
     * @return
     */
    IPage<GoodsSkuVO> selectGoodsSkuPage(IPage<GoodsSkuVO> page, GoodsSkuVO goodsSku);

    /**
     * 某商品下的所欲sku
     * @param goodsId
     * @return
     */
    List<GoodsSkuVO> findByGoodsId(Integer goodsId);

    /**
     * 根据skuIds获取
     * @param ids
     * @return
     */
    List<GoodsSkuVO> findByIds(List<Integer> ids);
}
