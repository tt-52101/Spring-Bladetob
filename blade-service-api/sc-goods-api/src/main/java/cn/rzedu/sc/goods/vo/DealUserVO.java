package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.DealUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 用户-商品交易记录表。以用户和商品为单位的成交流水明细
 * 视图实体类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DealUserVO对象", description = " 用户-商品交易记录表。以用户和商品为单位的成交流水明细 ")
public class DealUserVO extends DealUser {
    private static final long serialVersionUID = 1L;

}
