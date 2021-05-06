package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.Teacher;
import cn.rzedu.sf.user.vo.TeacherVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 教师 Mapper 接口
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param teacher
	 * @return
	 */
	List<TeacherVO> selectTeacherPage(IPage page, TeacherVO teacher);

	/**
	 * 学校教师
	 * @param schoolId
	 * @return
	 */
	List<TeacherVO> findBySchoolId(Integer schoolId);
}
