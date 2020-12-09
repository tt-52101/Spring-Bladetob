package cn.rzedu.sf.user.feign;

import cn.rzedu.sf.resource.entity.TextbookLesson;
import cn.rzedu.sf.resource.entity.TextbookLessonCharacter;
import cn.rzedu.sf.resource.feign.ITextbookClient;
import cn.rzedu.sf.resource.vo.CourseLessonVO;
import cn.rzedu.sf.user.entity.*;
import cn.rzedu.sf.user.service.*;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 书法用户课程 远程调用服务 控制器
 * @author
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class UserLessonClient implements IUserLessonClient {

    private IUserLessonService userLessonService;

    private IUserTextbookService userTextbookService;

    private IUserCharacterService userCharacterService;

    private ITextbookClient textbookClient;

    private ICourseUserService courseUserService;

    private ICourseUserLessonService courseUserLessonService;

    @Override
    public R<Boolean> createTextbook(Integer userId, Integer textbookId) {
        LocalDateTime now = LocalDateTime.now();
        //教材
        UserTextbook textbook = userTextbookService.findUnionByTextbookIdAndUserId(textbookId, userId);
        if (textbook == null) {
            textbook = new UserTextbook();
            textbook.setUserId(userId);
            textbook.setTextbookId(textbookId);
            textbook.setOwnedTime(now);
            textbook.setActiveTime(now);
            textbook.setStartTime(now);
            textbook.setCreateDate(now);
            userTextbookService.save(textbook);
        }
        //激活第一课
        R<TextbookLesson> result = textbookClient.firstLesson(textbookId);
        TextbookLesson data = result.getData();
        if (data == null || data.getId() == null) {
            return R.data(false);
        }
        Integer lessonId = data.getId();
        UserLesson lesson = userLessonService.findUnionByLessonIdAndUserId(lessonId, userId);
        if (lesson == null) {

            Integer charCount = 0;
            R<List<TextbookLessonCharacter>> result_1 = textbookClient.allLessonCharacters(lessonId);
            List<TextbookLessonCharacter> lessonCharacterList = result_1.getData();
            if (lessonCharacterList != null && !lessonCharacterList.isEmpty()) {
                charCount = lessonCharacterList.size();
            }

            UserLesson userLesson = new UserLesson();
            userLesson.setUserId(userId);
            userLesson.setTextbookId(textbookId);
            userLesson.setLessonId(lessonId);
            userLesson.setCharCount(charCount);
            userLesson.setFinishedCharCount(0);
            userLesson.setLocked(false);
            userLesson.setStarCount(0);
            userLesson.setCreateDate(now);
            userLesson.setModifyDate(now);
            userLessonService.save(userLesson);

            if (charCount != 0) {
                List<UserCharacter> ucList = new ArrayList<>(charCount);
                UserCharacter uc = null;
                for (TextbookLessonCharacter lessonCharacter : lessonCharacterList) {
                    uc = new UserCharacter();
                    uc.setUserId(userId);
                    uc.setLessonId(lessonId);
                    uc.setCharacterLessonId(lessonCharacter.getId());
                    uc.setCharacterId(lessonCharacter.getCharacterId());
                    uc.setFinishedPercent(0);
                    uc.setWatchProgress(0);
                    uc.setLastVisitedTime(now);
                    uc.setCreateDate(now);
                    ucList.add(uc);
                }
                userCharacterService.saveBatch(ucList);
            }
        }
        return R.data(true);
    }

    @Override
    public R<Boolean> createCourse(Integer userId, Integer courseId) {
        CourseUser courseUser = courseUserService.findUnion(userId, courseId);
        if (courseUser == null) {

            LocalDateTime now = LocalDateTime.now();
            courseUser = new CourseUser();
            courseUser.setCourseId(courseId);
            courseUser.setUserId(userId);
            courseUser.setOwnedTime(now);
            courseUser.setOwnedType(1);
            courseUser.setCreateDate(now);
            courseUserService.save(courseUser);

            Integer courseUserId = courseUser.getId();
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
        }
        return R.data(true);
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
}
