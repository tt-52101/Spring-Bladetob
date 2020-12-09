package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.StatisLessonUser;
import cn.rzedu.sf.user.vo.StatisLessonUserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 课程链接点击用户 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-25
 */
public interface StatisLessonUserMapper extends BaseMapper<StatisLessonUser> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param statisLessonUser
	 * @return
	 */
	List<StatisLessonUserVO> selectStatisLessonUserPage(IPage page, StatisLessonUserVO statisLessonUser);

	/**
	 * 唯一
	 * @param lessonId
	 * @param userId
	 * @return
	 */
	StatisLessonUser findByLessonIdAndUserId(Integer lessonId, Integer userId);
}
