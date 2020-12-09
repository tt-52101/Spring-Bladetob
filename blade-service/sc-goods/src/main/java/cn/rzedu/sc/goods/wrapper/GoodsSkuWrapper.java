package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.GoodsSku;
import cn.rzedu.sc.goods.vo.GoodsSkuVO;

/**
 * 商品库存表，以一个SKU为记录单位包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-16
 */
public class GoodsSkuWrapper extends BaseEntityWrapper<GoodsSku, GoodsSkuVO>  {

    public static GoodsSkuWrapper build() {
        return new GoodsSkuWrapper();
    }

	@Override
	public GoodsSkuVO entityVO(GoodsSku goodsSku) {
		GoodsSkuVO goodsSkuVO = BeanUtil.copy(goodsSku, GoodsSkuVO.class);

		return goodsSkuVO;
	}

}
