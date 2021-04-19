package cn.rzedu.sf.resource.feign;

import cn.rzedu.sf.resource.entity.*;
import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.service.*;
import cn.rzedu.sf.resource.vo.CharLinkVO;
import cn.rzedu.sf.resource.vo.CourseLessonVO;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 远程调用服务 控制器
 * @author
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class TextbookClient implements ITextbookClient {

    private ITextbookService textbookService;

    private ITextbookLessonService textbookLessonService;

    private ITextbookLessonCharacterService textbookLessonCharacterService;

    private ILessonStarConfigService lessonStarConfigService;

    private ICharacterService characterService;

    private ICharacterResourceService characterResourceService;

    private ICourseService courseService;

    private ICourseLessonService courseLessonService;

    private ICourseStarConfigService courseStarConfigService;

    @Override
    @GetMapping(API_PREFIX + "/detail")
    public R<Textbook> detail(Integer id) {
        Textbook textbook = textbookService.getById(id);
        return R.data(textbook);
    }

    @Override
    public R<List<TextbookLessonVO>> allLessons(Integer textbookId) {
        List<TextbookLessonVO> list = textbookLessonService.findLessonByTextbookId(textbookId);
        return R.data(list);
    }

    @Override
    public R<TextbookLesson> lessonDetail(Integer lessonId) {
        TextbookLesson textbookLesson = textbookLessonService.getById(lessonId);
        return R.data(textbookLesson);
    }

    @Override
    public R<List<TextbookLessonCharacter>> allTextbookCharacters(Integer textbookId) {
        List<TextbookLessonCharacter> list = textbookLessonCharacterService.findByTextbookId(textbookId);
        return R.data(list);
    }

    @Override
    public R<List<TextbookLessonCharacter>> allLessonCharacters(Integer lessonId) {
        List<TextbookLessonCharacter> list = textbookLessonCharacterService.findByLessonId(lessonId);
        return R.data(list);
    }

    @Override
    public R<TextbookLessonCharacter> lessonCharacterDetail(Integer lessonId, Integer characterId) {
        TextbookLessonCharacter character =
                textbookLessonCharacterService.findByLessonIdAndCharacterId(lessonId, characterId);
        return R.data(character);
    }

    @Override
    public R<LessonStarConfig> lessonStar(Integer lessonId) {
        LessonStarConfig config = lessonStarConfigService.findByLessonId(lessonId);
        return R.data(config);
    }

    @Override
    public R updateCharVisitedCount(Integer textbookId, Integer lessonId, Integer characterId) {
        Textbook textbook = textbookService.getById(textbookId);
        if (textbook == null) {
            R.fail("教材不存在，无法更新访问量");
        }
        Integer subject = textbook.getSubject();
        textbookService.updateVisitedCount(textbookId);
        textbookLessonCharacterService.updateVisitedCount(lessonId, characterId);
        if (subject == 71) {
            characterService.updateSoftVisitedCount(characterId);
        } else if (subject == 72) {
            characterService.updateHardVisitedCount(characterId);
        }
        characterResourceService.updateVisitedCount(characterId, subject, 1);
        return R.success("更新数据成功");
    }

	@Override
	public R<List<CharLinkVO>> findByCodeAndType(String type, String code) {
        List<CharLinkVO> list = textbookService.findLessonByCodeAndType(type, code);
        return R.data(list);
	}

    @Override
    public R<LessonStarConfig> createLessonStarConfig(Integer lessonId) {
        LessonStarConfig config = lessonStarConfigService.findByLessonId(lessonId);
        if (config != null) {
            return R.data(config);
        }
        config = lessonStarConfigService.saveOrUpdateConfig(lessonId, false);
        return R.data(config);
    }

    @Override
    public R<TextbookLesson> firstLesson(Integer textbookId) {
        TextbookLesson lesson = new TextbookLesson();
        lesson.setIsDeleted(0);
        lesson.setTextbookId(textbookId);
        lesson.setListOrder(1);
        TextbookLesson textbookLesson = textbookLessonService.getOne(Condition.getQueryWrapper(lesson));
        return R.data(textbookLesson);
    }

	@Override
	public R<Map<String, Object>> getLessonCharacterInfo(String code) {
		Map<String, Object> map = this.textbookLessonService.getLessonCharacterInfo(code);
		return R.data(map);
	}

    @Override
    public R<List<CourseLessonVO>> findKnowledgeExtensionLesson() {
        String name = "传统蒙学动画千字文";
        List<CourseLessonVO> list = new ArrayList<>();
        List<Course> courseList = courseService.findByName(name);
        if (courseList != null && !courseList.isEmpty()) {
            Integer courseId = courseList.get(0).getId();
            list = courseLessonService.findByCourseId(courseId);
        } else {
            Course course = courseService.getById(1);
            if (course != null) {
                list = courseLessonService.findByCourseId(course.getId());
            }
        }
        return R.data(list);
    }

    @Override
    public R<List<CourseLessonVO>> findCourseLessons(Integer courseId) {
        List<CourseLessonVO> list = courseLessonService.findByCourseId(courseId);
        return R.data(list);
    }

    @Override
    public R<Course> findCourseById(Integer courseId) {
        Course course = courseService.getById(courseId);
        return R.data(course);
    }

    @Override
    public R<CourseStarConfig> findCourseStar(Integer courseId) {
        CourseStarConfig config = courseStarConfigService.findByCourseId(courseId);
        return R.data(config);
    }

    @Override
    public R<CourseLesson> findCourseLessonById(Integer lessonId) {
        CourseLesson courseLesson = courseLessonService.getById(lessonId);
        return R.data(courseLesson);
    }
}
