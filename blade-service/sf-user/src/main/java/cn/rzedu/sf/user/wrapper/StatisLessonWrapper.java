package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.StatisLesson;
import cn.rzedu.sf.user.vo.StatisLessonVO;

/**
 * 课程链接统计数据包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-25
 */
public class StatisLessonWrapper extends BaseEntityWrapper<StatisLesson, StatisLessonVO> {

    public static StatisLessonWrapper build() {
        return new StatisLessonWrapper();
    }

    @Override
    public StatisLessonVO entityVO(StatisLesson statisLesson) {
        StatisLessonVO statisLessonVO = BeanUtil.copy(statisLesson, StatisLessonVO.class);

        return statisLessonVO;
    }

}
