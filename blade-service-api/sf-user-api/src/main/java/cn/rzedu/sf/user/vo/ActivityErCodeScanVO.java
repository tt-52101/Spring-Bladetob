package cn.rzedu.sf.user.vo;

import cn.rzedu.sf.user.entity.ActivityErCodeScan;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 活动二维码扫码用户视图实体类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ActivityErCodeScanVO对象", description = "活动二维码扫码用户")
public class ActivityErCodeScanVO extends ActivityErCodeScan {
    private static final long serialVersionUID = 1L;

}
