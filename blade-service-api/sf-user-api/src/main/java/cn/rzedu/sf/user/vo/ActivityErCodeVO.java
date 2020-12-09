package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.ActivityErCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 活动二维码视图实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ActivityErCodeVO对象", description = "活动二维码")
public class ActivityErCodeVO extends ActivityErCode {
    private static final long serialVersionUID = 1L;

}
