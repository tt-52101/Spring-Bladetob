package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.CourseUser;
import cn.rzedu.sf.user.vo.CourseUserVO;
import cn.rzedu.sf.user.mapper.CourseUserMapper;
import cn.rzedu.sf.user.service.ICourseUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 课程用户，参加到某个课程course的用户
 * 服务实现类
 *
 * @author Blade
 * @since 2020-09-08
 */
@Service
public class CourseUserServiceImpl extends ServiceImpl<CourseUserMapper, CourseUser> implements ICourseUserService {

    @Override
    public IPage<CourseUserVO> selectCourseUserPage(IPage<CourseUserVO> page, CourseUserVO courseUser) {
        return page.setRecords(baseMapper.selectCourseUserPage(page, courseUser));
    }

    @Override
    public CourseUser findUnion(Integer userId, Integer courseId) {
        return baseMapper.findUnion(userId, courseId);
    }

    @Override
    public List<CourseUserVO> findByUserId(Integer userId) {
        return baseMapper.findByUserId(userId);
    }

    @Override
    public Map<String, Object> getCourseStars(Integer userId, Integer courseId) {
        return baseMapper.getCourseStars(userId, courseId);
    }

    @Override
    public List<CourseUserVO> findBoughtCourseList(Integer userId) {
        return baseMapper.findBoughtCourseList(userId);
    }

    @Override
    public List<CourseUserVO> findFinishedCourseList(Integer userId) {
        return baseMapper.findFinishedCourseList(userId);
    }
}
