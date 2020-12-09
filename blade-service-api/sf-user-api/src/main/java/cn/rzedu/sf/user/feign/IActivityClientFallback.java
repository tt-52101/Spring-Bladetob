package cn.rzedu.sf.user.feign;

import cn.rzedu.sf.user.entity.Activity;
import cn.rzedu.sf.user.vo.UserVO;
import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 活动埋点 远程调用服务失败处理
 * @author
 */
@Component
public class IActivityClientFallback implements IActivityClient {
    @Override
    public R userFocus(Integer userId, Integer focusStatus) {
        return R.fail("调用失败");
    }

    @Override
    public R userAwardType(Integer userId) {
        return R.fail("调用失败");
    }

    @Override
    public R assistantProcess(Integer initiatorId, Integer assistantId, Integer type) {
        return R.fail("调用失败");
    }

    @Override
    public R findOrAddUserErCode(Integer userId, Integer type) {
        return R.fail("调用失败");
    }

    @Override
    public R modifyErCodeCount(Integer type, Integer userId, Integer scanUserId) {
        return R.fail("调用失败");
    }

    @Override
    public R updateSendCount(Integer type, Integer count) {
        return R.fail("调用失败");
    }

    @Override
    public R updateClickCount(Integer type, Integer count) {
        return R.fail("调用失败");
    }

    @Override
    public R<Activity> findZuLiActivity() {
        return R.fail("调用失败");
    }

    @Override
    public R updateLessonSendCount(Integer lessonId) {
        return R.fail("调用失败");
    }

    @Override
    public R<List<UserVO>> findInitiatorUser(Integer greaterNumber, Integer lessNumber) {
        return R.fail("调用失败");
    }
}
