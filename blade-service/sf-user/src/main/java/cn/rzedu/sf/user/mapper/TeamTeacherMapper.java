package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.TeamTeacher;
import cn.rzedu.sf.user.vo.TeamTeacherVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 班级学生 Mapper 接口
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface TeamTeacherMapper extends BaseMapper<TeamTeacher> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param teamTeacher
	 * @return
	 */
	List<TeamTeacherVO> selectTeamTeacherPage(IPage page, TeamTeacherVO teamTeacher);

}
