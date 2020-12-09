package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.GoodsSku;
import cn.rzedu.sc.goods.entity.GrouponRule;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 团购规则。一个团购规则记录是针对一个商品进行一次团购销售而设计的
 * 视图实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GrouponRuleVO对象", description = "团购规则。一个团购规则记录是针对一个商品进行一次团购销售而设计的 ")
public class GrouponRuleVO extends GrouponRule {
    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    /**
     * 商品主图，uuid
     */
    @ApiModelProperty(value = "商品主图，uuid")
    private String goodsImage;
    /**
     * 商品在原实体表的类型   1=通用课程
     */
    @ApiModelProperty(value = "商品在原实体表的类型   1=通用课程")
    private Integer ownerType;
    /**
     * 商品在原实体数据表的id（如有）
     */
    @ApiModelProperty(value = "商品在原实体数据表的id（如有）")
    private Integer ownerId;
    /**
     * 上传素材数量
     */
    @ApiModelProperty(value = "上传素材数量")
    private Integer materialNumber;
    /**
     * 购买人数
     */
    @ApiModelProperty(value = "购买人数")
    private Integer boughtCount;
    /**
     * 是否包含sku
     */
    @ApiModelProperty(value = "是否包含sku")
    private Boolean hasSku;
    /**
     * 商品包含的sku
     */
    @ApiModelProperty(value = "商品包含的sku")
    private List<GoodsSkuVO> skuList;

}
