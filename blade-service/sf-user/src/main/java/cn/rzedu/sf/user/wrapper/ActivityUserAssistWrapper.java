package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.ActivityUserAssist;
import cn.rzedu.sf.user.vo.ActivityUserAssistVO;

/**
 * 用户助力表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-15
 */
public class ActivityUserAssistWrapper extends BaseEntityWrapper<ActivityUserAssist, ActivityUserAssistVO> {

    public static ActivityUserAssistWrapper build() {
        return new ActivityUserAssistWrapper();
    }

    @Override
    public ActivityUserAssistVO entityVO(ActivityUserAssist activityUserAssist) {
        ActivityUserAssistVO activityUserAssistVO = BeanUtil.copy(activityUserAssist, ActivityUserAssistVO.class);

        return activityUserAssistVO;
    }

}
