package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.CourseUserLesson;
import cn.rzedu.sf.user.vo.CourseUserLessonVO;
import cn.rzedu.sf.user.mapper.CourseUserLessonMapper;
import cn.rzedu.sf.user.service.ICourseUserLessonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 课程学习情况表，记录用户在教材里面的课程lesson的学习情况，每个学生学习一个课程产生一个记录 服务实现类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Service
public class CourseUserLessonServiceImpl extends ServiceImpl<CourseUserLessonMapper, CourseUserLesson> implements ICourseUserLessonService {

    @Override
    public IPage<CourseUserLessonVO> selectCourseUserLessonPage(IPage<CourseUserLessonVO> page,
																CourseUserLessonVO courseUserLesson) {
        return page.setRecords(baseMapper.selectCourseUserLessonPage(page, courseUserLesson));
    }

    @Override
    public CourseUserLesson findUnion(Integer userId, Integer lessonId) {
        return baseMapper.findUnion(userId, lessonId);
    }

    @Override
    public List<CourseUserLessonVO> findByUserIdAndCourseId(Integer userId, Integer courseId) {
        return baseMapper.findByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public int getFinishedLessonCount(Integer userId, Integer courseId) {
        Integer count = baseMapper.getFinishedLessonCount(userId, courseId);
        if (count == null) {
            count = 0;
        }
        return count.intValue();
    }

    @Override
    public List<CourseUserLesson> findByUserAndCourse(Integer userId, Integer courseId) {
        return baseMapper.findByUserAndCourse(userId, courseId);
    }
}
