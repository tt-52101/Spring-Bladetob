package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.StatisLesson;
import cn.rzedu.sf.user.vo.StatisLessonVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 课程链接统计数据 服务类
 *
 * @author Blade
 * @since 2020-09-25
 */
public interface IStatisLessonService extends IService<StatisLesson> {

    /**
     * 自定义分页
     *
     * @param page
     * @param statisLesson
     * @return
     */
    IPage<StatisLessonVO> selectStatisLessonPage(IPage<StatisLessonVO> page, StatisLessonVO statisLesson);

    /**
     * 单个课程统计记录
     * @param lessonId
     * @return
     */
    StatisLesson findByLessonId(Integer lessonId);

    /**
     * 单个课程统计记录
     * @param lessonId
     * @param lessonName
     * @return
     */
    StatisLesson findByLessonId(Integer lessonId, String lessonName);

    /**
     * 更新发送次数
     * @param lessonId
     * @return
     */
    boolean updateSendCount(Integer lessonId);

    /**
     * 更新点击次数
     * @param lessonId
     * @param userId
     * @return
     */
    boolean updateClickCount(Integer lessonId, Integer userId);
}
