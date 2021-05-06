package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.Homework;
import cn.rzedu.sf.user.vo.HomeworkVO;

/**
 * 作业包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-21
 */
public class HomeworkWrapper extends BaseEntityWrapper<Homework, HomeworkVO>  {

    public static HomeworkWrapper build() {
        return new HomeworkWrapper();
    }

	@Override
	public HomeworkVO entityVO(Homework homework) {
		HomeworkVO homeworkVO = BeanUtil.copy(homework, HomeworkVO.class);

		return homeworkVO;
	}

}
