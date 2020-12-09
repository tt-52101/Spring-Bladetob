package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.Category;
import cn.rzedu.sc.goods.vo.CategoryVO;
import cn.rzedu.sc.goods.mapper.CategoryMapper;
import cn.rzedu.sc.goods.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 商品分类。商城所有商品分类，树状结构，从一级节点开始 服务实现类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public IPage<CategoryVO> selectCategoryPage(IPage<CategoryVO> page, CategoryVO category) {
        return page.setRecords(baseMapper.selectCategoryPage(page, category));
    }

}
