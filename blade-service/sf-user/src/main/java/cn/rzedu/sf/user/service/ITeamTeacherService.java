package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.TeamTeacher;
import cn.rzedu.sf.user.vo.TeamTeacherVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 班级学生 服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface ITeamTeacherService extends IService<TeamTeacher> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param teamTeacher
	 * @return
	 */
	IPage<TeamTeacherVO> selectTeamTeacherPage(IPage<TeamTeacherVO> page, TeamTeacherVO teamTeacher);

}
