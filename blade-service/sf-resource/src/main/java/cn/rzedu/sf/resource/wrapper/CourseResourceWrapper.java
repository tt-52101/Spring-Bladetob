package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.CourseResource;
import cn.rzedu.sf.resource.vo.CourseResourceVO;

/**
 * 课程资源
 * 资源文件一般与课程单元挂钩，一个单元可能有多个文件
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-08
 */
public class CourseResourceWrapper extends BaseEntityWrapper<CourseResource, CourseResourceVO> {

    public static CourseResourceWrapper build() {
        return new CourseResourceWrapper();
    }

    @Override
    public CourseResourceVO entityVO(CourseResource courseResource) {
        CourseResourceVO courseResourceVO = BeanUtil.copy(courseResource, CourseResourceVO.class);

        return courseResourceVO;
    }

}
