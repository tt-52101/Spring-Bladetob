package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.TeamType;
import cn.rzedu.sf.user.mapper.TeamTypeMapper;
import cn.rzedu.sf.user.service.ITeamTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 班级类型 服务实现类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Service
public class TeamTypeServiceImpl extends ServiceImpl<TeamTypeMapper, TeamType> implements ITeamTypeService {

    @Override
    public List<TeamType> findByType(Integer groupType) {
        return baseMapper.findByType(groupType);
    }

    @Override
    public boolean saveOrUpdateTeamType(Integer groupType, String name, Integer listOrder, Integer id) {
        TeamType teamType = new TeamType();
        teamType.setGroupType(groupType);
        teamType.setName(name);
        teamType.setListOrder(listOrder);
        if (id != null) {
            teamType.setId(id);
            baseMapper.updateById(teamType);
        } else {
            teamType.setCreateDate(LocalDateTime.now());
            baseMapper.insert(teamType);
        }
        return true;
    }
}
