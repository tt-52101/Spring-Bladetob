package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.Course;
import cn.rzedu.sf.resource.vo.CourseVO;

/**
 * 线上课程
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-08
 */
public class CourseWrapper extends BaseEntityWrapper<Course, CourseVO> {

    public static CourseWrapper build() {
        return new CourseWrapper();
    }

    @Override
    public CourseVO entityVO(Course course) {
        CourseVO courseVO = BeanUtil.copy(course, CourseVO.class);

        return courseVO;
    }

}
