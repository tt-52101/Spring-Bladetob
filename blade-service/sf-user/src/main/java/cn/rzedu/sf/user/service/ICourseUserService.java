package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.CourseUser;
import cn.rzedu.sf.user.vo.CourseUserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 课程用户，参加到某个课程course的用户
 * 服务类
 *
 * @author Blade
 * @since 2020-09-08
 */
public interface ICourseUserService extends IService<CourseUser> {

    /**
     * 自定义分页
     *
     * @param page
     * @param courseUser
     * @return
     */
    IPage<CourseUserVO> selectCourseUserPage(IPage<CourseUserVO> page, CourseUserVO courseUser);

    /**
     * 获取唯一课程
     * @param userId
     * @param courseId
     * @return
     */
    CourseUser findUnion(Integer userId, Integer courseId);

    /**
     * 获取用户所有课程
     * @param userId
     * @return
     */
    List<CourseUserVO> findByUserId(Integer userId);

    /**
     * 获取课程星数配置 和 用户获得星数
     *
     * @param userId
     * @param courseId
     * @return
     */
    Map<String, Object> getCourseStars(Integer userId, Integer courseId);

    /**
     * 获取用户购买的课程，含课程学习人数
     * @param userId
     * @return
     */
    List<CourseUserVO> findBoughtCourseList(Integer userId);

    /**
     * 获取用户已完成的课程，含课程学习人数
     * @param userId
     * @return
     */
    List<CourseUserVO> findFinishedCourseList(Integer userId);
}
