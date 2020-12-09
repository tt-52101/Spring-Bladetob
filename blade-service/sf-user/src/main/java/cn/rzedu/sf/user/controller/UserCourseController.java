package cn.rzedu.sf.user.controller;

import cn.rzedu.sf.resource.entity.Course;
import cn.rzedu.sf.resource.entity.CourseLesson;
import cn.rzedu.sf.resource.entity.CourseStarConfig;
import cn.rzedu.sf.resource.feign.ITextbookClient;
import cn.rzedu.sf.resource.vo.CourseLessonVO;
import cn.rzedu.sf.user.entity.CourseUserLesson;
import cn.rzedu.sf.user.service.ICourseUserLessonService;
import cn.rzedu.sf.user.utils.RandomNumberUtil;
import cn.rzedu.sf.user.vo.CourseUserLessonVO;
import cn.rzedu.sf.user.vo.UserLessonVO;
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
import cn.rzedu.sf.user.entity.CourseUser;
import cn.rzedu.sf.user.vo.CourseUserVO;
import cn.rzedu.sf.user.wrapper.CourseUserWrapper;
import cn.rzedu.sf.user.service.ICourseUserService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户通用课程
 * 控制器
 *
 * @author Blade
 * @since 2020-09-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user/course")
@Api(value = "用户通用课程", tags = "用户通用课程接口")
public class UserCourseController extends BladeController {

    private ICourseUserService courseUserService;

    private ICourseUserLessonService courseUserLessonService;

    private ITextbookClient textbookClient;

    /**
     * 添加用户通用课程
     * @param userId
     * @param courseId
     * @return
     */
    @GetMapping("/save")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "添加用户通用课程", notes = "添加用户通用课程")
    public R saveCourse(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer courseId,
            @ApiParam(value = "加入课程方式 1=单购 2=团购 3=免费试用") @RequestParam(required = false, defaultValue = "1") Integer ownedType,
            @ApiParam(value = "课程有效期，长期有效则不填") @RequestParam(required = false) LocalDateTime deadline
    ) {
        CourseUser union = courseUserService.findUnion(userId, courseId);
        if (union != null) {
            return R.status(true);
        }
        createUserCourse(userId, courseId, ownedType, deadline);
        return R.status(true);
    }

    /**
     * 新建用户课程
     * @param userId
     * @param courseId
     * @param ownedType
     * @param deadline
     */
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

    /**
     * 未添加的课程单元ids
     * @param userId
     * @param courseId
     * @param lessonVOList
     * @return
     */
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
     * 已购通用课程
     */
    @GetMapping("/list/buy")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "已购通用课程", notes = "获取用户已购通用课程，按最后修改时间降序")
    public R<List<CourseUserVO>> buy(@ApiParam(value = "用户id", required = true) @RequestParam Integer userId) {
        List<CourseUserVO> list = courseUserService.findBoughtCourseList(userId);
        addRandomBoughtCount(list);
        return R.data(list);
    }

    private void addRandomBoughtCount(List<CourseUserVO> list) {
        if (list != null && !list.isEmpty()) {
            for (CourseUserVO vo : list) {
                vo.setBoughtCount(RandomNumberUtil.getRandomCount(vo.getBoughtCount()));
            }
        }
    }

    /**
     * 已完成通用课程
     */
    @GetMapping("/list/finish")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "已完成通用课程", notes = "获取用户已完成通用课程")
    public R<List<CourseUserVO>> finish(@ApiParam(value = "用户id", required = true) @RequestParam Integer userId) {
        List<CourseUserVO> list = courseUserService.findFinishedCourseList(userId);
        addRandomBoughtCount(list);
        return R.data(list);
    }

    /**
     * 随机课程单元视频uuid
     */
    @ApiIgnore
    @GetMapping("/lesson/random")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "随机课程单元视频uuid", notes = "适用于硬笔课-知识扩展")
    public R randomLessonUUid() {
        R<List<CourseLessonVO>> result = textbookClient.findKnowledgeExtensionLesson();
        List<CourseLessonVO> list = result.getData();
        String uuid = "";
        if (list != null && !list.isEmpty()) {
            int i = (int) (Math.random() * list.size());
            uuid = list.get(i).getUuids();
        }
        return R.data(uuid);
    }

    /**
     * 课程单元资源列表，仅含启用
     * 学习人数，用户学习情况（是否已完成）
     */
    @GetMapping("/lessons")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "课程单元资源列表", notes = "课程单元资源列表，含学习人数，用户学习情况（是否已完成）")
    public R<List<CourseUserLessonVO>> characters(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer courseId
    ) {
        List<CourseUserLessonVO> list = courseUserLessonService.findByUserIdAndCourseId(userId, courseId);
        //添加随机人数
        addRandomLearnCount(list);
        return R.data(list);
    }

    private void addRandomLearnCount(List<CourseUserLessonVO> list) {
        if (list != null && !list.isEmpty()) {
            for (CourseUserLessonVO vo : list) {
                vo.setLearnCount(RandomNumberUtil.getRandomCount(vo.getLearnCount()));
            }
        }
    }


    /**
     * 课程标题
     * 课程标题，总课时
     */
    @GetMapping("/title")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "课程标题", notes = "课程标题，总课时")
    public R<Map<String, Object>> title(
            @ApiParam(value = "课程id", required = true) @RequestParam Integer courseId
    ) {
        Map<String, Object> result = new HashMap<>(4);
        R<Course> r = textbookClient.findCourseById(courseId);
        Course course = r.getData();
        if (course != null) {
            result.put("courseId", course.getId());
            result.put("courseName", course.getName());
            result.put("subject", course.getSubject());
            result.put("lessonCount", course.getLessonCount());
        }
        return R.data(result);
    }


    /**
     * 用户课程星数
     * 课程的总单元数，星数配置（6颗所在汉字位置），用户已完成单元数，用户已获得星星数，用户待练字（总数-已完成）
     */
    @GetMapping("/star")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "用户课程星数", notes = "课程的总单元数，星数配置（6颗所在汉字位置），用户已完成单元数，用户已获得星星数，用户待练字（总数-已完成）")
    public R<Map<String, Object>> star(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer courseId
    ) {
        Map<String, Object> result = courseUserService.getCourseStars(userId, courseId);
        return R.data(result);
    }


    /**
     * 完整看完视频后保存
     * @param userId
     * @param courseId
     * @param lessonId
     * @return
     */
    @PostMapping("/lesson/update")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "完整看完视频后保存", notes = "完整看完视频后保存，修改完成单元数，修改获得星星数")
    public R lessonUpdate(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer courseId,
            @ApiParam(value = "单元id", required = true) @RequestParam Integer lessonId,
            @ApiParam(value = "是否完成观看", required = true) @RequestParam(defaultValue = "false") Boolean hasFinished,
            @ApiParam(value = "已看的视频或音频时长，单位：秒", required = true) @RequestParam Integer finishedDuration
    ) {
        //课程和单元是否一致
        R<CourseLesson> clResult = textbookClient.findCourseLessonById(lessonId);
        CourseLesson data = clResult.getData();
        if (data == null) {
            return R.fail("课程不存在");
        }
        if (data.getCourseId() == null || data.getCourseId().intValue() != courseId) {
            return R.fail("课程和单元不匹配");
        }
        //用户课程和单元是否存在
        LocalDateTime now = LocalDateTime.now();
        CourseUser courseUser = courseUserService.findUnion(userId, courseId);
        if (courseUser == null) {
            courseUser = createUserCourse(userId, courseId, 1, null);
        }
        Integer courseUserId = courseUser.getId();
        CourseUserLesson userLesson = courseUserLessonService.findUnion(userId, lessonId);
        if (userLesson == null) {
            userLesson = new CourseUserLesson();
            userLesson.setUserId(userId);
            userLesson.setCourseUserId(courseUserId);
            userLesson.setLessonId(lessonId);
            userLesson.setHasFinished(false);
            userLesson.setUnlocked(true);
            userLesson.setCreateDate(now);
            courseUserLessonService.save(userLesson);
        }
        //修改用户单元：已完成，如果已经完成了，则不修改
        if (userLesson.getHasFinished() == null || !userLesson.getHasFinished()) {
            CourseUserLesson cul = new CourseUserLesson();
            cul.setId(userLesson.getId());
            cul.setHasFinished(hasFinished);
            cul.setFinishedDuration(finishedDuration);
            courseUserLessonService.updateById(cul);
        }

        if (hasFinished) {
            //获取已完成课程数
            int finishedLessonCount = courseUserLessonService.getFinishedLessonCount(userId, courseId);
            //获得课程星数配置
            int lessonCount = getCourseLessonCount(courseId);
            int finishedStarCount = getFinishedStarCount(courseId, finishedLessonCount);
            int finishedPercent = finishedLessonCount * 100 / lessonCount;
            //修改用户课程：完成单元数，获得星星数
            CourseUser cu = new CourseUser();
            cu.setId(courseUserId);
            cu.setFinishedLessonCount(finishedLessonCount);
            cu.setFinishedPercent(finishedPercent);
            cu.setStarCount(finishedStarCount);
            if (courseUser.getStartedTime() == null) {
                cu.setStartedTime(now);
            }
            if (finishedLessonCount == lessonCount && finishedLessonCount != 0) {
                cu.setFinishedTime(now);
            }
            courseUserService.updateById(cu);
        }

        return R.data("success");
    }



    private int getCourseLessonCount(Integer courseId) {
        int lessonCount = 0;
        R<Course> result = textbookClient.findCourseById(courseId);
        Course course = result.getData();
        if (course != null && course.getLessonCount() != null) {
            lessonCount = course.getLessonCount();
        }
        return lessonCount;
    }

    /**
     * 用户课程能获得几颗星
     */
    private int getFinishedStarCount(Integer courseId, Integer finishedLessonCount) {
        int finishedStarCount = 0;
        R<CourseStarConfig> result = textbookClient.findCourseStar(courseId);
        if (result.isSuccess() && 200 == result.getCode()) {
            CourseStarConfig config = result.getData();
            if (config.getId() != null) {
                Integer n1 = config.getStarsN1();
                Integer n2 = config.getStarsN2();
                Integer n3 = config.getStarsN3();
                Integer n4 = config.getStarsN4();
                Integer n5 = config.getStarsN5();
                Integer n6 = config.getStarsN6();
                if (finishedLessonCount >= n6) {
                    finishedStarCount = 6;
                } else if (finishedLessonCount >= n5) {
                    finishedStarCount = 5;
                } else if (finishedLessonCount >= n4) {
                    finishedStarCount = 4;
                } else if (finishedLessonCount >= n3) {
                    finishedStarCount = 3;
                } else if (finishedLessonCount >= n2) {
                    finishedStarCount = 2;
                } else if (finishedLessonCount >= n1) {
                    finishedStarCount = 1;
                } else {
                    finishedStarCount = 0;
                }
            }
        }
        return finishedStarCount;
    }

}
