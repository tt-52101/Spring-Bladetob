package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.Student;
import cn.rzedu.sf.user.vo.StudentVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface IStudentService extends IService<Student> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param student
	 * @return
	 */
	IPage<StudentVO> selectStudentPage(IPage<StudentVO> page, StudentVO student);

}
