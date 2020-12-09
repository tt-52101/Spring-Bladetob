package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.Brand;
import cn.rzedu.sc.goods.vo.BrandVO;

/**
 * 商品品牌包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-12
 */
public class BrandWrapper extends BaseEntityWrapper<Brand, BrandVO>  {

    public static BrandWrapper build() {
        return new BrandWrapper();
    }

	@Override
	public BrandVO entityVO(Brand brand) {
		BrandVO brandVO = BeanUtil.copy(brand, BrandVO.class);

		return brandVO;
	}

}
