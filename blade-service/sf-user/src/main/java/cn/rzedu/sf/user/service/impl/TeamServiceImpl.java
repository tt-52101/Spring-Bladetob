package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.Team;
import cn.rzedu.sf.user.vo.TeamVO;
import cn.rzedu.sf.user.mapper.TeamMapper;
import cn.rzedu.sf.user.service.ITeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 班级 服务实现类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements ITeamService {

	@Override
	public IPage<TeamVO> selectTeamPage(IPage<TeamVO> page, TeamVO team) {
		return page.setRecords(baseMapper.selectTeamPage(page, team));
	}

	@Override
	public List<TeamVO> findTeamVOWithTextbook(Integer groupType, Integer schoolId, Integer teamType) {
		return baseMapper.findTeamVOWithTextbook(groupType, schoolId, teamType);
	}

	@Override
	public boolean saveOrUpdateTeam(Team team) {
		if (team == null) {
			return false;
		}
		if (team.getId() != null) {
			baseMapper.updateById(team);
		} else{
			team.setCreateDate(LocalDateTime.now());
			baseMapper.insert(team);
		}
		return true;
	}
}
