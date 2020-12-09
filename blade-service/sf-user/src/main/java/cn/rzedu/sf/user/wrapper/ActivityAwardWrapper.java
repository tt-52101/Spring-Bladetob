package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.ActivityAward;
import cn.rzedu.sf.user.vo.ActivityAwardVO;

/**
 * 活动奖品包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-15
 */
public class ActivityAwardWrapper extends BaseEntityWrapper<ActivityAward, ActivityAwardVO> {

    public static ActivityAwardWrapper build() {
        return new ActivityAwardWrapper();
    }

    @Override
    public ActivityAwardVO entityVO(ActivityAward activityAward) {
        ActivityAwardVO activityAwardVO = BeanUtil.copy(activityAward, ActivityAwardVO.class);

        return activityAwardVO;
    }

}
