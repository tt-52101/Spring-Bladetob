package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.Team;
import cn.rzedu.sf.user.vo.TeamVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 班级 服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface ITeamService extends IService<Team> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param team
	 * @return
	 */
	IPage<TeamVO> selectTeamPage(IPage<TeamVO> page, TeamVO team);

	/**
	 * 班级列表，含课程
	 * @param groupType
	 * @param schoolId
	 * @param teamType
	 * @return
	 */
	List<TeamVO> findTeamVOWithTextbook(Integer groupType, Integer schoolId, Integer teamType);

	/**
	 * 增/改
	 * @param team
	 * @return
	 */
	boolean saveOrUpdateTeam(Team team);
}
