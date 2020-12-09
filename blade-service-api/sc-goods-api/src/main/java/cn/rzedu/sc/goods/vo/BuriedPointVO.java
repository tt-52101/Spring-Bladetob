package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.BuriedPoint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 商城埋点视图实体类
 *
 * @author Blade
 * @since 2020-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "BuriedPointVO对象", description = "商城埋点")
public class BuriedPointVO extends BuriedPoint {
    private static final long serialVersionUID = 1L;

}
