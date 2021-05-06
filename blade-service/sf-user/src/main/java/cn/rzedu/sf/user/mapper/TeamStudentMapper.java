package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.TeamStudent;
import cn.rzedu.sf.user.vo.TeamStudentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 班级学生 Mapper 接口
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface TeamStudentMapper extends BaseMapper<TeamStudent> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param teamStudent
	 * @return
	 */
	List<TeamStudentVO> selectTeamStudentPage(IPage page, TeamStudentVO teamStudent);

}
