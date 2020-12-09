package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.StatisLessonUser;
import cn.rzedu.sf.user.vo.StatisLessonUserVO;

/**
 * 课程链接点击用户包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-25
 */
public class StatisLessonUserWrapper extends BaseEntityWrapper<StatisLessonUser, StatisLessonUserVO>  {

    public static StatisLessonUserWrapper build() {
        return new StatisLessonUserWrapper();
    }

	@Override
	public StatisLessonUserVO entityVO(StatisLessonUser statisLessonUser) {
		StatisLessonUserVO statisLessonUserVO = BeanUtil.copy(statisLessonUser, StatisLessonUserVO.class);

		return statisLessonUserVO;
	}

}
