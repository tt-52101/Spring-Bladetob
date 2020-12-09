package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.Category;
import cn.rzedu.sc.goods.vo.CategoryVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 商品分类。商城所有商品分类，树状结构，从一级节点开始 服务类
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface ICategoryService extends IService<Category> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param category
	 * @return
	 */
	IPage<CategoryVO> selectCategoryPage(IPage<CategoryVO> page, CategoryVO category);

}
