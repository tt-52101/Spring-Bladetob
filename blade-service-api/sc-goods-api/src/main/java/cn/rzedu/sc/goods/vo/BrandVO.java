package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.Brand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 商品品牌视图实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "BrandVO对象", description = "商品品牌")
public class BrandVO extends Brand {
    private static final long serialVersionUID = 1L;

}
