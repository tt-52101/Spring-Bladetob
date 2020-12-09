package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.CourseStarConfig;
import cn.rzedu.sf.resource.vo.CourseStarConfigVO;

/**
 * 通用课程星星数配置表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-09
 */
public class CourseStarConfigWrapper extends BaseEntityWrapper<CourseStarConfig, CourseStarConfigVO> {

    public static CourseStarConfigWrapper build() {
        return new CourseStarConfigWrapper();
    }

    @Override
    public CourseStarConfigVO entityVO(CourseStarConfig courseStarConfig) {
        CourseStarConfigVO courseStarConfigVO = BeanUtil.copy(courseStarConfig, CourseStarConfigVO.class);

        return courseStarConfigVO;
    }

}
