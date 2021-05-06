package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.Teacher;
import cn.rzedu.sf.user.vo.TeacherVO;

/**
 * 教师包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-21
 */
public class TeacherWrapper extends BaseEntityWrapper<Teacher, TeacherVO>  {

    public static TeacherWrapper build() {
        return new TeacherWrapper();
    }

	@Override
	public TeacherVO entityVO(Teacher teacher) {
		TeacherVO teacherVO = BeanUtil.copy(teacher, TeacherVO.class);

		return teacherVO;
	}

}
