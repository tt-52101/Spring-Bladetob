package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 商品表，即SPU：Standard Product Unit （标准产品单位），是商品信息聚合的最小单位
 * 视图实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GoodsVO对象", description = "商品表，即SPU：Standard Product Unit （标准产品单位），是商品信息聚合的最小单位 ")
public class GoodsVO extends Goods {
    private static final long serialVersionUID = 1L;

}
