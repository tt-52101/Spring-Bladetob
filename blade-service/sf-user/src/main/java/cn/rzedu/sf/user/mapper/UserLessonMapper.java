package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.UserLesson;
import cn.rzedu.sf.user.vo.UserLessonVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 学生课程学习情况表
 * Mapper 接口
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface UserLessonMapper extends BaseMapper<UserLesson> {

    /**
     * 自定义分页
     *
     * @param page
     * @param userLesson
     * @return
     */
    List<UserLessonVO> selectUserLessonPage(IPage page, UserLessonVO userLesson);

    /**
     * 获取教材所有课程，和用户解锁、完成情况
     *
     * @param textbookId
     * @param userId
     * @return
     */
    List<UserLessonVO> findAllLessonWithUser(Integer textbookId, Integer userId);

    /**
     * 获取课程信息
     * 教材类型，教材id，教材名，课程总数，课程id，课程名，第几单元
     * @param lessonId
     * @return
     */
    Map<String, Object> getLessonMessage(Integer lessonId);

    /**
     * 获取用户课程星数
     * @param lessonId
     * @param userId
     * @return
     */
    UserLessonVO getLessonStars(Integer lessonId, Integer userId);

    /**
     * 唯一记录
     * @param lessonId
     * @param userId
     * @return
     */
    UserLesson findUnionByLessonIdAndUserId(Integer lessonId, Integer userId);

    /**
     * 根据userId 和 lessonId 修改数据
     * @param userLesson
     * @return
     */
    int updateByLessonIdAndUserId(UserLesson userLesson);

    /**
     * 用户某教材 完成的课程数
     * @param textbookId
     * @param userId
     * @return
     */
    Integer findFinishedLessonCountOfTextbook(Integer textbookId, Integer userId);
}
