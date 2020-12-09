package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.StatisLessonUser;
import cn.rzedu.sf.user.vo.StatisLessonUserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 课程链接点击用户 服务类
 *
 * @author Blade
 * @since 2020-09-25
 */
public interface IStatisLessonUserService extends IService<StatisLessonUser> {

    /**
     * 自定义分页
     *
     * @param page
     * @param statisLessonUser
     * @return
     */
    IPage<StatisLessonUserVO> selectStatisLessonUserPage(IPage<StatisLessonUserVO> page,
                                                         StatisLessonUserVO statisLessonUser);

    /**
     * 唯一
     * @param lessonId
     * @param userId
     * @return
     */
    StatisLessonUser findByLessonIdAndUserId(Integer lessonId, Integer userId);
}
