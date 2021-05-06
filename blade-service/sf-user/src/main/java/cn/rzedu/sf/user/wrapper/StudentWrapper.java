package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.Student;
import cn.rzedu.sf.user.vo.StudentVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-21
 */
public class StudentWrapper extends BaseEntityWrapper<Student, StudentVO>  {

    public static StudentWrapper build() {
        return new StudentWrapper();
    }

	@Override
	public StudentVO entityVO(Student student) {
		StudentVO studentVO = BeanUtil.copy(student, StudentVO.class);

		return studentVO;
	}

}
