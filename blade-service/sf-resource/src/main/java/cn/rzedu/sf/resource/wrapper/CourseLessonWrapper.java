package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.CourseLesson;
import cn.rzedu.sf.resource.vo.CourseLessonVO;

/**
 * 课程单元（课时），课程course的子单元（课时、节、集）
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-08
 */
public class CourseLessonWrapper extends BaseEntityWrapper<CourseLesson, CourseLessonVO> {

    public static CourseLessonWrapper build() {
        return new CourseLessonWrapper();
    }

    @Override
    public CourseLessonVO entityVO(CourseLesson courseLesson) {
        CourseLessonVO courseLessonVO = BeanUtil.copy(courseLesson, CourseLessonVO.class);

        return courseLessonVO;
    }

}
