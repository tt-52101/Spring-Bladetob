package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.CourseUserLesson;
import cn.rzedu.sf.user.vo.CourseUserLessonVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 课程学习情况表，记录用户在教材里面的课程lesson的学习情况，每个学生学习一个课程产生一个记录 服务类
 *
 * @author Blade
 * @since 2020-09-08
 */
public interface ICourseUserLessonService extends IService<CourseUserLesson> {

    /**
     * 自定义分页
     *
     * @param page
     * @param courseUserLesson
     * @return
     */
    IPage<CourseUserLessonVO> selectCourseUserLessonPage(IPage<CourseUserLessonVO> page,
                                                         CourseUserLessonVO courseUserLesson);

    /**
     * 用户唯一课程单元
     * @param userId
     * @param lessonId
     * @return
     */
    CourseUserLesson findUnion(Integer userId, Integer lessonId);

    /**
     * 用户某课程内 所有单元
     * 多表联查以课程单元为主表，兼用户完成情况（无论是否完成都有数据）
     * @param userId
     * @param courseId
     * @return
     */
    List<CourseUserLessonVO> findByUserIdAndCourseId(Integer userId, Integer courseId);

    /**
     * 获取用户已完成单元数
     * @param userId
     * @param courseId
     * @return
     */
    int getFinishedLessonCount(Integer userId, Integer courseId);

    /**
     * 用户已有课程单元
     * @param userId
     * @param courseId
     * @return
     */
    List<CourseUserLesson> findByUserAndCourse(Integer userId, Integer courseId);
}
