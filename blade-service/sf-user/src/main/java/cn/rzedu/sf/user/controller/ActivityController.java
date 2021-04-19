package cn.rzedu.sf.user.controller;

import cn.rzedu.sf.resource.feign.ITextbookClient;
import cn.rzedu.sf.resource.vo.CourseLessonVO;
import cn.rzedu.sf.user.constant.ActivityConstant;
import cn.rzedu.sf.user.entity.*;
import cn.rzedu.sf.user.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.rzedu.sf.user.vo.ActivityAwardUserVO;
import cn.rzedu.sf.user.wrapper.ActivityAwardUserWrapper;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动 控制器
 *
 * @author Blade
 * @since 2020-09-15
 */
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping("/activity")
@Api(value = "活动接口", tags = "活动接口")
public class ActivityController extends BladeController {

    private IActivityAwardUserService activityAwardUserService;

    private IActivityErCodeService activityErCodeService;

    private IActivityActionService activityActionService;

    private IActivityUserAssistService activityUserAssistService;

    private ICourseUserService courseUserService;

    private ICourseUserLessonService courseUserLessonService;

    private ITextbookClient textbookClient;

    private IStatisLessonService statisLessonService;

    /**
     * 收货地址详情
     *
     * @param userId
     * @return
     */
    @PostMapping("/award/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "收货地址详情", notes = "收货地址详情")
    public R<ActivityAwardUser> awardDetail(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "奖品类型，原6人助力type=3，新助力活动11~14", required = true) @RequestParam(defaultValue = "3") Integer type
    ) {

        Integer awardType = null;
        ActivityErCode erCode = null;
        if (ActivityConstant.AWARD_TYPE_GIFT == type) {
            erCode = activityErCodeService.findByTypeAndUserId(ActivityConstant.ER_CODE_POSTER, userId);
        } else {
            erCode = activityErCodeService.findByTypeAndUserId(type, userId);
        }
        if (erCode != null) {
            awardType = erCode.getAwardType();
        }
        ActivityAwardUser awardUser = activityAwardUserService.findByTypeAndUserId(type, userId, awardType);
        //未领取的更新领取礼包次数
        if (awardUser != null && awardUser.getClickStatus() != null && awardUser.getClickStatus() == 0) {
            if (ActivityConstant.AWARD_TYPE_GIFT == type) {
                activityActionService.updateClickCount(ActivityConstant.ACTION_MESSAGE_GIFT);
            }
            ActivityAwardUser au = new ActivityAwardUser();
            au.setId(awardUser.getId());
            au.setClickStatus(1);
            activityAwardUserService.updateById(au);
        }
        return R.data(awardUser);
    }

    /**
     * 填写收货地址
     */
    @PostMapping("/award/submit")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "填写收货地址", notes = "填写收货地址")
    public R submitAddress(@Valid @RequestBody ActivityAwardUser activityAwardUser) {
        boolean status = false;
        Integer id = activityAwardUser.getId();
        if (id != null) {
            //设置状态为领取
            activityAwardUser.setStatus(2);
            status = activityAwardUserService.updateById(activityAwardUser);
        } else {
            ActivityAwardUser awardUser = activityAwardUserService.findByTypeAndUserId(activityAwardUser.getType(),
                    activityAwardUser.getUserId(), activityAwardUser.getAwardType());
            awardUser.setName(activityAwardUser.getName());
            awardUser.setMobile(activityAwardUser.getMobile());
            awardUser.setLocation(activityAwardUser.getLocation());
            awardUser.setAddress(activityAwardUser.getAddress());
            awardUser.setStatus(2);
            status = activityAwardUserService.updateById(awardUser);
            id = awardUser.getId();
        }
        //更新提交奖品地址次数
        if (status) {
            activityAwardUser = activityAwardUserService.getById(id);
            if (activityAwardUser.getType().equals(ActivityConstant.AWARD_TYPE_GIFT)) {
                activityActionService.updateClickCount(ActivityConstant.ACTION_SUBMIT_ADDRESS);
            } else {
                activityActionService.updateClickCount(activityAwardUser.getType());
            }
        }
        return R.status(status);
    }


    /**
     * 助力流程
     * @param initiatorId
     * @param assistantId
     * @return
     */
    @ApiIgnore
    @PostMapping("/assistant/process")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "助力流程", notes = "助力流程")
    public R assistantProcess(
            @ApiParam(value = "发起人userId", required = true) @RequestParam Integer initiatorId,
            @ApiParam(value = "助力人userId", required = true) @RequestParam Integer assistantId,
            @ApiParam(value = "活动类型", required = true) @RequestParam(defaultValue = "1") Integer type
    ) {
        int number = activityErCodeService.assistantProcess(initiatorId, assistantId, type);
        return R.data(number);
    }


    /**
     * 更新二维码扫码次数
     * @param type
     * @param userId
     * @param scanUserId
     * @return
     */
    @PostMapping("/scan/modify")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "更新二维码扫码次数/人数", notes = "更新二维码扫码次数")
    public R modifyErCodeCount(
            @ApiParam(value = "二维码类型 1=活动海报二维码 2=推文二维码 3=课本二维码", required = true) @RequestParam Integer type,
            @ApiParam(value = "用户id，type=2/3时，传0", required = true) @RequestParam Integer userId,
            @ApiParam(value = "扫码用户id", required = true) @RequestParam Integer scanUserId
    ) {
        activityErCodeService.updateScanCount(type, userId);
        activityErCodeService.updateScanPeopleCount(type, userId, scanUserId);
        return R.status(true);
    }

    /**
     * 更新发送次数
     * @param type
     * @return
     */
    @ApiIgnore
    @PostMapping("/action/modify/send")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "更新发送次数", notes = "更新发送次数")
    public R updateSendCount(
            @ApiParam(value = "行为类型  1=千字文课程消息模板  2=诗词课程消息模板  3=领取礼包消息模板  4=公众号发出活动海报  5=用户提交奖品地址  6=用户取消关注", required = true)
            @RequestParam Integer type,
            @ApiParam(value = "发送次数", required = true) @RequestParam(defaultValue = "1") Integer count
    ) {
        return R.status(activityActionService.updateSendCount(type, count));
    }

    /**
     * 更新点击次数
     * @param type
     * @return
     */
    @ApiIgnore
    @PostMapping("/action/modify/click")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "更新点击次数", notes = "更新点击次数")
    public R updateClickCount(
            @ApiParam(value = "行为类型  1=千字文课程消息模板  2=诗词课程消息模板  3=领取礼包消息模板  4=公众号发出活动海报  5=用户提交奖品地址  6=用户取消关注", required = true)
            @RequestParam Integer type,
            @ApiParam(value = "发送次数", required = true) @RequestParam(defaultValue = "1") Integer count
    ) {
        return R.status(activityActionService.updateClickCount(type, count));
    }


    /**
     * 点击链接领取课程
     * @param course
     * @return
     */
    @PostMapping("/receive/course")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "点击链接领取课程", notes = "点击链接领取课程")
    public R receiveCourse(
            @ApiParam(value = "课程  1=千字文课程  2=诗词课程", required = true) @RequestParam Integer course,
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId
    ) {
        Integer type = null;
        Integer courseId = null;
        if (course == 1) {
            type = ActivityConstant.ACTION_MESSAGE_QZW;
            courseId = 1;
        } else if (course == 2) {
            type = ActivityConstant.ACTION_MESSAGE_SC;
            courseId = 2;
        } else {
            return R.fail("课程类型错误");
        }
        CourseUser union = courseUserService.findUnion(userId, courseId);
        if (union != null) {
            return R.status(true);
        }
        activityActionService.updateClickCount(type);
        createUserCourse(userId, courseId, 1, null);
        return R.status(true);
    }

    private CourseUser createUserCourse(Integer userId, Integer courseId, Integer ownedType, LocalDateTime deadline) {
        LocalDateTime now = LocalDateTime.now();
        CourseUser union = new CourseUser();
        union.setCourseId(courseId);
        union.setUserId(userId);
        union.setOwnedTime(now);
        union.setOwnedType(ownedType);
        union.setOwnedDeadline(deadline);
        union.setCreateDate(now);
        courseUserService.save(union);

        Integer courseUserId = union.getId();
        //课程所有单元
        R<List<CourseLessonVO>> result = textbookClient.findCourseLessons(courseId);
        List<CourseLessonVO> lessonVOList = result.getData();
        //新建用户课程单元
        //已有的用户单元不再添加
        if (lessonVOList != null && !lessonVOList.isEmpty()) {
            List<CourseUserLesson> userLessonList = new ArrayList<>();
            CourseUserLesson cul = null;
            List<Integer> idList = getLessonIdList(userId, courseId, lessonVOList);
            for (Integer id : idList) {
                cul = new CourseUserLesson();
                cul.setUserId(userId);
                cul.setCourseUserId(courseUserId);
                cul.setLessonId(id);
                cul.setHasFinished(false);
                cul.setUnlocked(true);
                cul.setCreateDate(now);
                userLessonList.add(cul);
            }
            courseUserLessonService.saveBatch(userLessonList);
        }
        return union;
    }

    private List<Integer> getLessonIdList(Integer userId, Integer courseId, List<CourseLessonVO> lessonVOList) {
        List<Integer> result = new ArrayList<>();
        List<CourseUserLesson> existList = courseUserLessonService.findByUserAndCourse(userId, courseId);
        if (existList != null && !existList.isEmpty()) {
            for (CourseLessonVO vo : lessonVOList) {
                if (vo.getId() != null) {
                    //不再列表中的才需添加
                    boolean match = existList.stream().anyMatch(lesson -> lesson.getLessonId().equals(vo.getId()));
                    if (!match) {
                        result.add(vo.getId());
                    }
                }
            }
        } else {
            for (CourseLessonVO vo : lessonVOList) {
                if (vo.getId() != null) {
                    result.add(vo.getId());
                }
            }
        }
        return result;
    }


    /**
     * 查找或生成用户海报二维码，生成助力数据
     * @param userId
     * @return
     */
    @ApiIgnore
    @PostMapping("/ercode/detail")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "查找/生成用户海报二维码", notes = "查找/生成用户二维码")
    public R modifyErCodeCount(
            @ApiParam(value = "用户id，type=2/3时，传0", required = true) @RequestParam Integer userId
    ) {
        ActivityErCode erCode = activityErCodeService.findByTypeAndUserId(ActivityConstant.ER_CODE_POSTER, userId);
        activityUserAssistService.createUserAssist(0, userId);
        return R.data(erCode);
    }



    /**
     * 批量生成用户海报二维码数据
     * @return
     */
    @ApiIgnore
    @PostMapping("/ercode/batch/add")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "批量生成用户海报二维码数据", notes = "")
    public R batchCreateErCode() {
        activityErCodeService.batchCreateErCode();
        return R.status(true);
    }

    /**
     * 批量生成用户助力数据
     * @return
     */
    @ApiIgnore
    @PostMapping("/assist/batch/add")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "批量生成用户助力数据", notes = "")
    public R batchCreateUserAssist() {
        activityUserAssistService.batchCreateUserAssist();
        return R.status(true);
    }


    /**
     * 课程链接更新点击次数
     * @param lessonId
     * @param userId
     * @return
     */
    @PostMapping("/lesson/modify/click")
    @ApiOperationSupport(order = 21)
    @ApiOperation(value = "课程链接更新点击次数", notes = "课程链接更新点击次数")
    public R updateLessonClickCount(
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId,
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId
    ) {
        boolean status = statisLessonService.updateClickCount(lessonId, userId);
        return R.status(status);
    }


    /**
     * 课程链接更新发送次数
     * @param lessonId
     * @return
     */
    @ApiIgnore
    @PostMapping("/lesson/modify/send")
    @ApiOperationSupport(order = 22)
    @ApiOperation(value = "课程链接更新发送次数", notes = "课程链接更新发送次数")
    public R updateLessonSendCount(
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId
    ) {
        boolean status = statisLessonService.updateSendCount(lessonId);
        return R.status(status);
    }


    /**
     * 课程链接
     * @param lessonId
     * @return
     */
    @ApiIgnore
    @PostMapping("/lesson/find")
    @ApiOperationSupport(order = 23)
    @ApiOperation(value = "课程链接", notes = "课程链接")
    public R findStatisLesson(
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId
    ) {
        StatisLesson statisLesson = statisLessonService.findByLessonId(lessonId);
        return R.data(statisLesson);
    }
}
