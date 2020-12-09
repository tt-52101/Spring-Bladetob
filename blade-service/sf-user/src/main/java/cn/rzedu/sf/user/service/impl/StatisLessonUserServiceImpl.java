package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.StatisLessonUser;
import cn.rzedu.sf.user.vo.StatisLessonUserVO;
import cn.rzedu.sf.user.mapper.StatisLessonUserMapper;
import cn.rzedu.sf.user.service.IStatisLessonUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 课程链接点击用户 服务实现类
 *
 * @author Blade
 * @since 2020-09-25
 */
@Service
public class StatisLessonUserServiceImpl extends ServiceImpl<StatisLessonUserMapper, StatisLessonUser> implements IStatisLessonUserService {

	@Override
	public IPage<StatisLessonUserVO> selectStatisLessonUserPage(IPage<StatisLessonUserVO> page, StatisLessonUserVO statisLessonUser) {
		return page.setRecords(baseMapper.selectStatisLessonUserPage(page, statisLessonUser));
	}

	@Override
	public StatisLessonUser findByLessonIdAndUserId(Integer lessonId, Integer userId) {
		return baseMapper.findByLessonIdAndUserId(lessonId, userId);
	}
}
