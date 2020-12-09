package cn.rzedu.sc.goods.vo;

import cn.rzedu.sc.goods.entity.BuriedPointUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 商城埋点-操作人员视图实体类
 *
 * @author Blade
 * @since 2020-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "BuriedPointUserVO对象", description = "商城埋点-操作人员")
public class BuriedPointUserVO extends BuriedPointUser {
    private static final long serialVersionUID = 1L;

}
