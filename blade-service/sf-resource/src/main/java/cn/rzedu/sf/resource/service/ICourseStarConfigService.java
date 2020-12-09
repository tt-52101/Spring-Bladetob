package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.CourseStarConfig;
import cn.rzedu.sf.resource.vo.CourseStarConfigVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 通用课程星星数配置表 服务类
 *
 * @author Blade
 * @since 2020-09-09
 */
public interface ICourseStarConfigService extends IService<CourseStarConfig> {

    /**
     * 自定义分页
     *
     * @param page
     * @param courseStarConfig
     * @return
     */
    IPage<CourseStarConfigVO> selectCourseStarConfigPage(IPage<CourseStarConfigVO> page,
                                                         CourseStarConfigVO courseStarConfig);

    /**
     * 根据课程id获取星数配置
     * 若没有配置，则创建
     * @param courseId
     * @return
     */
    CourseStarConfig findByCourseId(Integer courseId);
}
