package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.Category;
import cn.rzedu.sc.goods.vo.CategoryVO;

/**
 * 商品分类。商城所有商品分类，树状结构，从一级节点开始包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-12
 */
public class CategoryWrapper extends BaseEntityWrapper<Category, CategoryVO>  {

    public static CategoryWrapper build() {
        return new CategoryWrapper();
    }

	@Override
	public CategoryVO entityVO(Category category) {
		CategoryVO categoryVO = BeanUtil.copy(category, CategoryVO.class);

		return categoryVO;
	}

}
