package cn.rzedu.sf.user.feign;

import cn.rzedu.sf.user.constant.ActivityConstant;
import cn.rzedu.sf.user.entity.Activity;
import cn.rzedu.sf.user.entity.ActivityErCode;
import cn.rzedu.sf.user.entity.User;
import cn.rzedu.sf.user.service.*;
import cn.rzedu.sf.user.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 活动埋点 远程调用服务 控制器
 * @author
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class ActivityClient implements IActivityClient {

    private IUserService userService;

    private IActivityErCodeService activityErCodeService;

    private IActivityActionService activityActionService;

    private IActivityService activityService;

    private IStatisLessonService statisLessonService;


    @Override
    public R userFocus(Integer userId, Integer focusStatus) {
        User u = userService.getById(userId);
        if (u == null) {
            return R.status(false);
        }
        User user = new User();
        user.setId(userId);
        user.setFocusStatus(focusStatus);
        boolean status = userService.updateById(user);
        //取消关注，且是由活动海报进入的，更新取关人数
        if (status && focusStatus == 0 && u.getSourceType() != null && 1 == u.getSourceType()) {
            activityActionService.updateClickCount(ActivityConstant.ACTION_UNFOLLOW);
        }
        return R.status(status);
    }

    @Override
    public R<Integer> userAwardType(Integer userId) {
        ActivityErCode erCode = activityErCodeService.findByTypeAndUserId(ActivityConstant.ER_CODE_POSTER, userId);
        return R.data(erCode.getAwardType());
    }

    @Override
    public R<Integer> assistantProcess(Integer initiatorId, Integer assistantId, Integer type) {
        int number = activityErCodeService.assistantProcess(initiatorId, assistantId, type);
        return R.data(number);
    }

    @Override
    public R<ActivityErCode> findOrAddUserErCode(Integer userId, Integer type) {
        ActivityErCode erCode = activityErCodeService.findByTypeAndUserId(type, userId);
        return R.data(erCode);
    }

    @Override
    public R modifyErCodeCount(Integer type, Integer userId, Integer scanUserId) {
        activityErCodeService.updateScanCount(type, userId);
        activityErCodeService.updateScanPeopleCount(type, userId, scanUserId);
        return R.status(true);
    }

    @Override
    public R updateSendCount(Integer type, Integer count) {
        return R.status(activityActionService.updateSendCount(type, count));
    }

    @Override
    public R updateClickCount(Integer type, Integer count) {
        return R.status(activityActionService.updateClickCount(type, count));
    }

    @Override
    public R<Activity> findZuLiActivity() {
        return R.data(activityService.findZuLiActivity());
    }

    @Override
    public R updateLessonSendCount(Integer lessonId) {
        return R.status(statisLessonService.updateSendCount(lessonId));
    }

    @Override
    public R<List<UserVO>> findInitiatorUser(Integer greaterNumber, Integer lessNumber) {
        return R.data(userService.findInitiatorByAssistNumber(greaterNumber, lessNumber));
    }
}
