package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.CourseUserLesson;
import cn.rzedu.sf.user.vo.CourseUserLessonVO;

/**
 * 课程学习情况表，记录用户在教材里面的课程lesson的学习情况，每个学生学习一个课程产生一个记录包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-08
 */
public class CourseUserLessonWrapper extends BaseEntityWrapper<CourseUserLesson, CourseUserLessonVO> {

    public static CourseUserLessonWrapper build() {
        return new CourseUserLessonWrapper();
    }

    @Override
    public CourseUserLessonVO entityVO(CourseUserLesson courseUserLesson) {
        CourseUserLessonVO courseUserLessonVO = BeanUtil.copy(courseUserLesson, CourseUserLessonVO.class);

        return courseUserLessonVO;
    }

}
