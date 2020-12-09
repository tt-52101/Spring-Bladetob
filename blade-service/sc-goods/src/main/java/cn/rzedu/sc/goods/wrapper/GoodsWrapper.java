package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.Goods;
import cn.rzedu.sc.goods.vo.GoodsVO;

/**
 * 商品表，即SPU：Standard Product Unit （标准产品单位），是商品信息聚合的最小单位包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-12
 */
public class GoodsWrapper extends BaseEntityWrapper<Goods, GoodsVO>  {

    public static GoodsWrapper build() {
        return new GoodsWrapper();
    }

	@Override
	public GoodsVO entityVO(Goods goods) {
		GoodsVO goodsVO = BeanUtil.copy(goods, GoodsVO.class);

		return goodsVO;
	}

}
