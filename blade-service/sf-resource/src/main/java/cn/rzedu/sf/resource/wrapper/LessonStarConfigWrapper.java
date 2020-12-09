package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.LessonStarConfig;
import cn.rzedu.sf.resource.vo.LessonStarConfigVO;

/**
 * 课程星星数配置表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-08-11
 */
public class LessonStarConfigWrapper extends BaseEntityWrapper<LessonStarConfig, LessonStarConfigVO> {

    public static LessonStarConfigWrapper build() {
        return new LessonStarConfigWrapper();
    }

    @Override
    public LessonStarConfigVO entityVO(LessonStarConfig lessonStarConfig) {
        LessonStarConfigVO lessonStarConfigVO = BeanUtil.copy(lessonStarConfig, LessonStarConfigVO.class);

        return lessonStarConfigVO;
    }

}
