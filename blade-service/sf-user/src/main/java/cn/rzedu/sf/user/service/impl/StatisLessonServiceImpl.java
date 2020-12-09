package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.StatisLesson;
import cn.rzedu.sf.user.entity.StatisLessonUser;
import cn.rzedu.sf.user.service.IStatisLessonUserService;
import cn.rzedu.sf.user.vo.StatisLessonVO;
import cn.rzedu.sf.user.mapper.StatisLessonMapper;
import cn.rzedu.sf.user.service.IStatisLessonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;

/**
 * 课程链接统计数据 服务实现类
 *
 * @author Blade
 * @since 2020-09-25
 */
@AllArgsConstructor
@Service
public class StatisLessonServiceImpl extends ServiceImpl<StatisLessonMapper, StatisLesson> implements IStatisLessonService {

    private IStatisLessonUserService statisLessonUserService;

    @Override
    public IPage<StatisLessonVO> selectStatisLessonPage(IPage<StatisLessonVO> page, StatisLessonVO statisLesson) {
        return page.setRecords(baseMapper.selectStatisLessonPage(page, statisLesson));
    }

    @Override
    public StatisLesson findByLessonId(Integer lessonId) {
        return findByLessonId(lessonId, null);
    }

    @Override
    public StatisLesson findByLessonId(Integer lessonId, String lessonName) {
        StatisLesson statisLesson = baseMapper.findByLessonId(lessonId);
        if (statisLesson != null) {
            return statisLesson;
        }
        statisLesson = new StatisLesson();
        statisLesson.setLessonId(lessonId);
        statisLesson.setLessonName(lessonName);
        statisLesson.setSendCount(0);
        statisLesson.setClickCount(0);
        statisLesson.setClickPeopleCount(0);
        statisLesson.setCreateDate(LocalDateTime.now());
        statisLesson.setModifyDate(LocalDateTime.now());
        statisLesson.setIsDeleted(0);
        baseMapper.insert(statisLesson);
        return statisLesson;
    }

    @Override
    public boolean updateSendCount(Integer lessonId) {
        boolean status = SqlHelper.retBool(baseMapper.updateSendCount(lessonId));
        //更新失败，新建记录再次更新
        if (!status) {
            findByLessonId(lessonId);
            status = SqlHelper.retBool(baseMapper.updateSendCount(lessonId));
        }
        return status;
    }

    @Override
    public boolean updateClickCount(Integer lessonId, Integer userId) {
        boolean status = SqlHelper.retBool(baseMapper.updateClickCount(lessonId));
        if (!status) {
            findByLessonId(lessonId);
            status = SqlHelper.retBool(baseMapper.updateClickCount(lessonId));
        }
        //点击用户，已有的不再添加
        StatisLessonUser statisLessonUser = statisLessonUserService.findByLessonIdAndUserId(lessonId, userId);
        if (statisLessonUser != null) {
            return status;
        }
        LocalDateTime now = LocalDateTime.now();
        statisLessonUser = new StatisLessonUser();
        statisLessonUser.setLessonId(lessonId);
        statisLessonUser.setUserId(userId);
        statisLessonUser.setCreateDate(now);
        statisLessonUser.setModifyDate(now);
        statisLessonUser.setIsDeleted(0);
        boolean success = statisLessonUserService.save(statisLessonUser);
        if (success) {
            status = SqlHelper.retBool(baseMapper.updateClickPeopleCount(lessonId));
        }
        return status;
    }
}
