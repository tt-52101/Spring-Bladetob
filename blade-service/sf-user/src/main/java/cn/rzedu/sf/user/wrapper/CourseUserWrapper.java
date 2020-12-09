package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.CourseUser;
import cn.rzedu.sf.user.vo.CourseUserVO;

/**
 * 课程用户，参加到某个课程course的用户
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-08
 */
public class CourseUserWrapper extends BaseEntityWrapper<CourseUser, CourseUserVO> {

    public static CourseUserWrapper build() {
        return new CourseUserWrapper();
    }

    @Override
    public CourseUserVO entityVO(CourseUser courseUser) {
        CourseUserVO courseUserVO = BeanUtil.copy(courseUser, CourseUserVO.class);

        return courseUserVO;
    }

}
