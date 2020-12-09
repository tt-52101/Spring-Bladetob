package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.CourseResource;
import cn.rzedu.sf.resource.vo.CourseResourceVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 课程资源
 * 资源文件一般与课程单元挂钩，一个单元可能有多个文件
 * 服务类
 *
 * @author Blade
 * @since 2020-09-08
 */
public interface ICourseResourceService extends IService<CourseResource> {

    /**
     * 自定义分页
     *
     * @param page
     * @param courseResource
     * @return
     */
    IPage<CourseResourceVO> selectCourseResourcePage(IPage<CourseResourceVO> page, CourseResourceVO courseResource);

}
