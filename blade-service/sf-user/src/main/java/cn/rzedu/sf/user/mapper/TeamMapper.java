package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.Team;
import cn.rzedu.sf.user.vo.TeamVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 班级 Mapper 接口
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface TeamMapper extends BaseMapper<Team> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param team
	 * @return
	 */
	List<TeamVO> selectTeamPage(IPage page, TeamVO team);

	/**
	 * 班级列表，含课程
	 * @param groupType
	 * @param schoolId
	 * @param teamType
	 * @return
	 */
	List<TeamVO> findTeamVOWithTextbook(Integer groupType, Integer schoolId, Integer teamType);
}
