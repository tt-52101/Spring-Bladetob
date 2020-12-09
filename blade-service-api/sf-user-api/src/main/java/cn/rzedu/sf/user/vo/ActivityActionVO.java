package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.ActivityAction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 活动行为表视图实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ActivityActionVO对象", description = "活动行为表")
public class ActivityActionVO extends ActivityAction {
    private static final long serialVersionUID = 1L;

}
