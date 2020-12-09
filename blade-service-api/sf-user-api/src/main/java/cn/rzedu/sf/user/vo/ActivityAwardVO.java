package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.ActivityAward;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 活动奖品视图实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ActivityAwardVO对象", description = "活动奖品")
public class ActivityAwardVO extends ActivityAward {
    private static final long serialVersionUID = 1L;

}
