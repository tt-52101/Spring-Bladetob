package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.Teacher;
import cn.rzedu.sf.user.vo.TeacherVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 教师 服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface ITeacherService extends IService<Teacher> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param teacher
	 * @return
	 */
	IPage<TeacherVO> selectTeacherPage(IPage<TeacherVO> page, TeacherVO teacher);

	/**
	 * 学校教师
	 * @param schoolId
	 * @return
	 */
	List<TeacherVO> findBySchoolId(Integer schoolId);

	/**
	 * 增/改
	 * @param teacher
	 * @return
	 */
	boolean saveOrUpdateTeacher(Teacher teacher);
}
