package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.Course;
import cn.rzedu.sf.resource.vo.CourseVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 通用课程
 * 服务类
 *
 * @author Blade
 * @since 2020-09-08
 */
public interface ICourseService extends IService<Course> {

    /**
     * 自定义分页
     *
     * @param page
     * @param course
     * @return
     */
    IPage<CourseVO> selectCoursePage(IPage<CourseVO> page, CourseVO course);

    /**
     * 根据课程名获取课程
     * @param name          课程名称
     * @param subject       相关学科
     * @param courseType    课程类型  1=点播课 2=直播课  3=慕课 4=课程专辑
     * @return
     */
    List<Course> findByName(String name, Integer subject, Integer courseType);

    /**
     * 根据课程名获取课程
     * 默认subject=70，courseType=1
     * @param name          课程名称
     * @return
     */
    List<Course> findByName(String name);


}
