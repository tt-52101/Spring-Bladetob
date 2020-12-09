package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.GrouponGroupGoods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 团购的商品。某团购团购买的商品项目视图实体类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GrouponGroupGoodsVO对象", description = "团购的商品。某团购团购买的商品项目")
public class GrouponGroupGoodsVO extends GrouponGroupGoods {
    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    /**
     * 商品在原实体表的类型
     */
    @ApiModelProperty(value = "商品在原实体表的类型")
    private Integer ownerType;
    /**
     * 商品在原实体数据表的id
     */
    @ApiModelProperty(value = "商品在原实体数据表的id")
    private Integer ownerId;
    /**
     * 商品sku名称
     */
    @ApiModelProperty(value = "商品sku名称")
    private String goodsSkuName;

}
