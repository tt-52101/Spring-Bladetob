package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.TeamTeacher;
import cn.rzedu.sf.user.vo.TeamTeacherVO;
import cn.rzedu.sf.user.mapper.TeamTeacherMapper;
import cn.rzedu.sf.user.service.ITeamTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 班级学生 服务实现类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Service
public class TeamTeacherServiceImpl extends ServiceImpl<TeamTeacherMapper, TeamTeacher> implements ITeamTeacherService {

	@Override
	public IPage<TeamTeacherVO> selectTeamTeacherPage(IPage<TeamTeacherVO> page, TeamTeacherVO teamTeacher) {
		return page.setRecords(baseMapper.selectTeamTeacherPage(page, teamTeacher));
	}

}
