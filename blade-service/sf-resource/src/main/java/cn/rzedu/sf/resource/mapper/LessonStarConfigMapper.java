package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.LessonStarConfig;
import cn.rzedu.sf.resource.vo.LessonStarConfigVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 课程星星数配置表 Mapper 接口
 *
 * @author Blade
 * @since 2020-08-11
 */
public interface LessonStarConfigMapper extends BaseMapper<LessonStarConfig> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param lessonStarConfig
	 * @return
	 */
	List<LessonStarConfigVO> selectLessonStarConfigPage(IPage page, LessonStarConfigVO lessonStarConfig);

	/**
	 * 根据lessonId获取星数配置
	 * @param lessonId
	 * @return
	 */
	LessonStarConfig findByLessonId(Integer lessonId);
}
