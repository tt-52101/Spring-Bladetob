package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.CourseLesson;
import cn.rzedu.sf.resource.vo.CourseLessonVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 课程单元（课时），课程course的子单元（课时、节、集）
 * 服务类
 *
 * @author Blade
 * @since 2020-09-08
 */
public interface ICourseLessonService extends IService<CourseLesson> {

    /**
     * 自定义分页
     *
     * @param page
     * @param courseLesson
     * @return
     */
    IPage<CourseLessonVO> selectCourseLessonPage(IPage<CourseLessonVO> page, CourseLessonVO courseLesson);

    /**
     * 获取所有课程单元
     * 仅含启用，包含课程单位里的所有文件uuid
     * @param courseId
     * @return
     */
    List<CourseLessonVO> findByCourseId(Integer courseId);

}
