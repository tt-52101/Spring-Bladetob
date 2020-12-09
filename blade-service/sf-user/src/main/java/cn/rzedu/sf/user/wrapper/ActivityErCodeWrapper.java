package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.ActivityErCode;
import cn.rzedu.sf.user.vo.ActivityErCodeVO;

/**
 * 活动二维码包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-15
 */
public class ActivityErCodeWrapper extends BaseEntityWrapper<ActivityErCode, ActivityErCodeVO> {

    public static ActivityErCodeWrapper build() {
        return new ActivityErCodeWrapper();
    }

    @Override
    public ActivityErCodeVO entityVO(ActivityErCode activityErCode) {
        ActivityErCodeVO activityErCodeVO = BeanUtil.copy(activityErCode, ActivityErCodeVO.class);

        return activityErCodeVO;
    }

}
