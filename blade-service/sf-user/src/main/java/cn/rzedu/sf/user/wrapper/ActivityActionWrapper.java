package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.ActivityAction;
import cn.rzedu.sf.user.vo.ActivityActionVO;

/**
 * 活动行为表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-15
 */
public class ActivityActionWrapper extends BaseEntityWrapper<ActivityAction, ActivityActionVO> {

    public static ActivityActionWrapper build() {
        return new ActivityActionWrapper();
    }

    @Override
    public ActivityActionVO entityVO(ActivityAction activityAction) {
        ActivityActionVO activityActionVO = BeanUtil.copy(activityAction, ActivityActionVO.class);

        return activityActionVO;
    }

}
