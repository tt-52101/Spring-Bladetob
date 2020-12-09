package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.Category;
import cn.rzedu.sc.goods.vo.CategoryVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 商品分类。商城所有商品分类，树状结构，从一级节点开始 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface CategoryMapper extends BaseMapper<Category> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param category
	 * @return
	 */
	List<CategoryVO> selectCategoryPage(IPage page, CategoryVO category);

}
