package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.PropValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 属性值。说明属性项有多少个可以使用的值视图实体类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PropValueVO对象", description = "属性值。说明属性项有多少个可以使用的值")
public class PropValueVO extends PropValue {
    private static final long serialVersionUID = 1L;

}
