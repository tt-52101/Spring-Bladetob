package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.Course;
import cn.rzedu.sf.resource.vo.CourseVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 线上课程
 * Mapper 接口
 *
 * @author Blade
 * @since 2020-09-08
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 自定义分页
     *
     * @param page
     * @param course
     * @return
     */
    List<CourseVO> selectCoursePage(IPage page, CourseVO course);

    /**
     * 根据课程名获取课程
     * @param name          课程名称
     * @param subject       相关学科
     * @param courseType    课程类型  1=点播课 2=直播课  3=慕课 4=课程专辑
     * @return
     */
    List<Course> findByName(String name, Integer subject, Integer courseType);

}
