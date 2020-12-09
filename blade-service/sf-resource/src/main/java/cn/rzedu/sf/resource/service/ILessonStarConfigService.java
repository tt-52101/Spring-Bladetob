package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.LessonStarConfig;
import cn.rzedu.sf.resource.vo.LessonStarConfigVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 课程星星数配置表 服务类
 *
 * @author Blade
 * @since 2020-08-11
 */
public interface ILessonStarConfigService extends IService<LessonStarConfig> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param lessonStarConfig
	 * @return
	 */
	IPage<LessonStarConfigVO> selectLessonStarConfigPage(IPage<LessonStarConfigVO> page,
                                                         LessonStarConfigVO lessonStarConfig);

	/**
	 * 根据lessonId获取星数配置
	 * @param lessonId
	 * @return
	 */
	LessonStarConfig findByLessonId(Integer lessonId);

	/**
	 * 新增或修改课程配置
	 * @param lessonId
	 * @param isChange
	 * @return
	 */
	LessonStarConfig saveOrUpdateConfig(Integer lessonId, boolean isChange);
}
