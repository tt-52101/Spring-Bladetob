package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.Student;
import cn.rzedu.sf.user.vo.StudentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface StudentMapper extends BaseMapper<Student> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param student
	 * @return
	 */
	List<StudentVO> selectStudentPage(IPage page, StudentVO student);

}
