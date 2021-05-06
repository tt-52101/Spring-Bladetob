package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.TeamStudent;
import cn.rzedu.sf.user.vo.TeamStudentVO;
import cn.rzedu.sf.user.mapper.TeamStudentMapper;
import cn.rzedu.sf.user.service.ITeamStudentService;
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
public class TeamStudentServiceImpl extends ServiceImpl<TeamStudentMapper, TeamStudent> implements ITeamStudentService {

	@Override
	public IPage<TeamStudentVO> selectTeamStudentPage(IPage<TeamStudentVO> page, TeamStudentVO teamStudent) {
		return page.setRecords(baseMapper.selectTeamStudentPage(page, teamStudent));
	}

}
