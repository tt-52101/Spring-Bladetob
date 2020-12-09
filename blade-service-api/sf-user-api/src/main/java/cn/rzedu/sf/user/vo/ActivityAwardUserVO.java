package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.ActivityAwardUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 活动奖品领取用户视图实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ActivityAwardUserVO对象", description = "活动奖品领取用户")
public class ActivityAwardUserVO extends ActivityAwardUser {
    private static final long serialVersionUID = 1L;

}
