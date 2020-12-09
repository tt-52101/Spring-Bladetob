package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 商品分类。商城所有商品分类，树状结构，从一级节点开始视图实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CategoryVO对象", description = "商品分类。商城所有商品分类，树状结构，从一级节点开始")
public class CategoryVO extends Category {
    private static final long serialVersionUID = 1L;

}
