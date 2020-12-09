package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.Activity;
import cn.rzedu.sf.user.vo.ActivityVO;

/**
 * 活动包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-18
 */
public class ActivityWrapper extends BaseEntityWrapper<Activity, ActivityVO> {

    public static ActivityWrapper build() {
        return new ActivityWrapper();
    }

    @Override
    public ActivityVO entityVO(Activity activity) {
        ActivityVO activityVO = BeanUtil.copy(activity, ActivityVO.class);

        return activityVO;
    }

}
