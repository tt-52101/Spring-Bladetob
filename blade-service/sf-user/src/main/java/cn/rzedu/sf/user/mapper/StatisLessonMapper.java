package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.StatisLesson;
import cn.rzedu.sf.user.vo.StatisLessonVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 课程链接统计数据 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-25
 */
public interface StatisLessonMapper extends BaseMapper<StatisLesson> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param statisLesson
	 * @return
	 */
	List<StatisLessonVO> selectStatisLessonPage(IPage page, StatisLessonVO statisLesson);

	/**
	 * 单个课程统计记录
	 * @param lessonId
	 * @return
	 */
	StatisLesson findByLessonId(Integer lessonId);

	/**
	 * 更新发送次数
	 * @param lessonId
	 * @return
	 */
	int updateSendCount(Integer lessonId);

	/**
	 * 更新点击次数
	 * @param lessonId
	 * @return
	 */
	int updateClickCount(Integer lessonId);

	/**
	 * 更新点击人数
	 * @param lessonId
	 * @return
	 */
	int updateClickPeopleCount(Integer lessonId);
}
