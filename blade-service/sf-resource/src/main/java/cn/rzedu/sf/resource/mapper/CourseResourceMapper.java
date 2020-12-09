package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.CourseResource;
import cn.rzedu.sf.resource.vo.CourseResourceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 课程资源
 * 资源文件一般与课程单元挂钩，一个单元可能有多个文件
 * Mapper 接口
 *
 * @author Blade
 * @since 2020-09-08
 */
public interface CourseResourceMapper extends BaseMapper<CourseResource> {

    /**
     * 自定义分页
     *
     * @param page
     * @param courseResource
     * @return
     */
    List<CourseResourceVO> selectCourseResourcePage(IPage page, CourseResourceVO courseResource);

}
