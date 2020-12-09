package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.CourseStarConfig;
import cn.rzedu.sf.resource.vo.CourseStarConfigVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 通用课程星星数配置表 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-09
 */
public interface CourseStarConfigMapper extends BaseMapper<CourseStarConfig> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param courseStarConfig
	 * @return
	 */
	List<CourseStarConfigVO> selectCourseStarConfigPage(IPage page, CourseStarConfigVO courseStarConfig);

	/**
	 * 根据课程id获取星数配置
	 * @param courseId
	 * @return
	 */
	CourseStarConfig findByCourseId(Integer courseId);

}
