package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.ActivityAwardUser;
import cn.rzedu.sf.user.vo.ActivityAwardUserVO;

/**
 * 活动奖品领取用户包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-15
 */
public class ActivityAwardUserWrapper extends BaseEntityWrapper<ActivityAwardUser, ActivityAwardUserVO> {

    public static ActivityAwardUserWrapper build() {
        return new ActivityAwardUserWrapper();
    }

    @Override
    public ActivityAwardUserVO entityVO(ActivityAwardUser activityAwardUser) {
        ActivityAwardUserVO activityAwardUserVO = BeanUtil.copy(activityAwardUser, ActivityAwardUserVO.class);

        return activityAwardUserVO;
    }

}
