package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.Course;
import cn.rzedu.sf.resource.vo.CourseVO;
import cn.rzedu.sf.resource.mapper.CourseMapper;
import cn.rzedu.sf.resource.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 通用课程
 * 服务实现类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Override
    public IPage<CourseVO> selectCoursePage(IPage<CourseVO> page, CourseVO course) {
        return page.setRecords(baseMapper.selectCoursePage(page, course));
    }

    @Override
    public List<Course> findByName(String name, Integer subject, Integer courseType) {
        return baseMapper.findByName(name, subject, courseType);
    }

    @Override
    public List<Course> findByName(String name) {
        return baseMapper.findByName(name, 70, 1);
    }
}
