package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.GoodsSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 商品库存表，以一个SKU为记录单位视图实体类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GoodsSkuVO对象", description = "商品库存表，以一个SKU为记录单位")
public class GoodsSkuVO extends GoodsSku {
    private static final long serialVersionUID = 1L;

    /**
     * 简称，key-value对值合并
     */
    @ApiModelProperty(value = "简称，key-value对值合并")
    private String shortName;
}
