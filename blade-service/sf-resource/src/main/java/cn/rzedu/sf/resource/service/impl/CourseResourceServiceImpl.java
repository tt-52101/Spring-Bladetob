package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.CourseResource;
import cn.rzedu.sf.resource.vo.CourseResourceVO;
import cn.rzedu.sf.resource.mapper.CourseResourceMapper;
import cn.rzedu.sf.resource.service.ICourseResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 课程资源
 * 资源文件一般与课程单元挂钩，一个单元可能有多个文件
 * 服务实现类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Service
public class CourseResourceServiceImpl extends ServiceImpl<CourseResourceMapper, CourseResource> implements ICourseResourceService {

    @Override
    public IPage<CourseResourceVO> selectCourseResourcePage(IPage<CourseResourceVO> page,
															CourseResourceVO courseResource) {
        return page.setRecords(baseMapper.selectCourseResourcePage(page, courseResource));
    }

}
