package cn.rzedu.sf.resource.feign;

import cn.rzedu.sf.resource.entity.*;
import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.vo.CourseLessonVO;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 远程调用服务失败处理
 * @author
 */
@Component
public class ITextbookClientFallback implements ITextbookClient {

    @Override
    public R<Textbook> detail(Integer id) {
        return R.fail("调用失败");
    }

    @Override
    public R<List<TextbookLessonVO>> allLessons(Integer textbookId) {
        return R.fail("调用失败");
    }

    @Override
    public R<TextbookLesson> lessonDetail(Integer lessonId) {
        return R.fail("调用失败");
    }

    @Override
    public R<List<TextbookLessonCharacter>> allLessonCharacters(Integer lessonId) {
        return R.fail("调用失败");
    }

    @Override
    public R<TextbookLessonCharacter> lessonCharacterDetail(Integer lessonId, Integer characterId) {
        return R.fail("调用失败");
    }

    @Override
    public R<LessonStarConfig> lessonStar(Integer lessonId) {
        return R.fail("调用失败");
    }

    @Override
    public R updateCharVisitedCount(Integer textbookId, Integer lessonId, Integer characterId) {
        return R.fail("调用失败");
    }

	@Override
	public R findByCodeAndType(String type, String code) {
		return R.fail("调用失败");
	}

    @Override
    public R<LessonStarConfig> createLessonStarConfig(Integer lessonId) {
        return R.fail("调用失败");
    }

    @Override
    public R<TextbookLesson> firstLesson(Integer textbookId) {
        return R.fail("调用失败");
    }

	@Override
	public R<Map<String, Object>> getLessonCharacterInfo(String code) {
		return R.fail("调用失败");
	}

    @Override
    public R<List<CourseLessonVO>> findKnowledgeExtensionLesson() {
        return R.fail("调用失败");
    }

    @Override
    public R<List<CourseLessonVO>> findCourseLessons(Integer courseId) {
        return R.fail("调用失败");
    }

    @Override
    public R<Course> findCourseById(Integer courseId) {
        return R.fail("调用失败");
    }

    @Override
    public R<CourseStarConfig> findCourseStar(Integer courseId) {
        return R.fail("调用失败");
    }

    @Override
    public R<CourseLesson> findCourseLessonById(Integer lessonId) {
        return R.fail("调用失败");
    }
}
