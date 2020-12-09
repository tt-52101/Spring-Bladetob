package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.PropKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 属性项。描述商品的属性，从描述特征有SPU属性和SKU属性，从描述范围有类目属性和商品属性，本表是类目属性视图实体类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PropKeyVO对象", description = "属性项。描述商品的属性，从描述特征有SPU属性和SKU属性，从描述范围有类目属性和商品属性，本表是类目属性")
public class PropKeyVO extends PropKey {
    private static final long serialVersionUID = 1L;

}
