package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.UserLesson;
import cn.rzedu.sf.user.vo.UserLessonVO;
import cn.rzedu.sf.user.mapper.UserLessonMapper;
import cn.rzedu.sf.user.service.IUserLessonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 学生课程学习情况表
 * 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Service
public class UserLessonServiceImpl extends ServiceImpl<UserLessonMapper, UserLesson> implements IUserLessonService {

    @Override
    public IPage<UserLessonVO> selectUserLessonPage(IPage<UserLessonVO> page, UserLessonVO userLesson) {
        return page.setRecords(baseMapper.selectUserLessonPage(page, userLesson));
    }

    @Override
    public List<UserLessonVO> findAllLessonWithUser(Integer textbookId, Integer userId) {
        return baseMapper.findAllLessonWithUser(textbookId, userId);
    }

    @Override
    public Map<String, Object> getLessonMessage(Integer lessonId) {
        return baseMapper.getLessonMessage(lessonId);
    }

    @Override
    public UserLessonVO getLessonStars(Integer lessonId, Integer userId) {
        return baseMapper.getLessonStars(lessonId, userId);
    }

    @Override
    public UserLesson findUnionByLessonIdAndUserId(Integer lessonId, Integer userId) {
        return baseMapper.findUnionByLessonIdAndUserId(lessonId, userId);
    }

    @Override
    public boolean updateByLessonIdAndUserId(UserLesson userLesson) {
        return SqlHelper.retBool(baseMapper.updateByLessonIdAndUserId(userLesson));
    }

    @Override
    public int findFinishedLessonCountOfTextbook(Integer textbookId, Integer userId) {
        return baseMapper.findFinishedLessonCountOfTextbook(textbookId, userId);
    }
}
