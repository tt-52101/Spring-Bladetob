package cn.rzedu.sf.user.service;

import java.util.List;
import java.util.Map;

/**
 * 学生课程相关
 * 服务类
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface IUserTextbookBizService {

    /**
     * 设置在学课程
     * @param userId
     * @param textbookId
     * @param lessonId
     * @return
     */
    boolean setupLearningTextbook(Integer userId, Integer textbookId, Integer lessonId);

    /**
     * 最近学习的课程
     * @param userId
     * @return
     */
    List findRecentlyTextbook(Integer userId);

    /**
     * 当前学习课程
     * @param userId
     * @return
     */
    Map getLearningLesson(Integer userId);
}
