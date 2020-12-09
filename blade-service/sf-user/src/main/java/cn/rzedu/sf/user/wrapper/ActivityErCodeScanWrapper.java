package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.ActivityErCodeScan;
import cn.rzedu.sf.user.vo.ActivityErCodeScanVO;

/**
 * 活动二维码扫码用户包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-15
 */
public class ActivityErCodeScanWrapper extends BaseEntityWrapper<ActivityErCodeScan, ActivityErCodeScanVO> {

    public static ActivityErCodeScanWrapper build() {
        return new ActivityErCodeScanWrapper();
    }

    @Override
    public ActivityErCodeScanVO entityVO(ActivityErCodeScan activityErCodeScan) {
        ActivityErCodeScanVO activityErCodeScanVO = BeanUtil.copy(activityErCodeScan, ActivityErCodeScanVO.class);

        return activityErCodeScanVO;
    }

}
