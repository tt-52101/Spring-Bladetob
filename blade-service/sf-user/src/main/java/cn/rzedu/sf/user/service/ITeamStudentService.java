package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.TeamStudent;
import cn.rzedu.sf.user.vo.TeamStudentVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 班级学生 服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface ITeamStudentService extends IService<TeamStudent> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param teamStudent
	 * @return
	 */
	IPage<TeamStudentVO> selectTeamStudentPage(IPage<TeamStudentVO> page, TeamStudentVO teamStudent);

}
