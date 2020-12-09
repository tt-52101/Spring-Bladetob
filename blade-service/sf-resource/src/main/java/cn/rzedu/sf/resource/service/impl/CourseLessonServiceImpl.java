package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.CourseLesson;
import cn.rzedu.sf.resource.vo.CourseLessonVO;
import cn.rzedu.sf.resource.mapper.CourseLessonMapper;
import cn.rzedu.sf.resource.service.ICourseLessonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 课程单元（课时），课程course的子单元（课时、节、集）
 * 服务实现类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Service
public class CourseLessonServiceImpl extends ServiceImpl<CourseLessonMapper, CourseLesson> implements ICourseLessonService {

    @Override
    public IPage<CourseLessonVO> selectCourseLessonPage(IPage<CourseLessonVO> page, CourseLessonVO courseLesson) {
        return page.setRecords(baseMapper.selectCourseLessonPage(page, courseLesson));
    }

    @Override
    public List<CourseLessonVO> findByCourseId(Integer courseId) {
        return baseMapper.findByCourseId(courseId);
    }
}
