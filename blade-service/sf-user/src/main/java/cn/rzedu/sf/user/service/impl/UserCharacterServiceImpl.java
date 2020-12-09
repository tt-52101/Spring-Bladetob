package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.UserCharacter;
import cn.rzedu.sf.user.vo.UserCharacterVO;
import cn.rzedu.sf.user.mapper.UserCharacterMapper;
import cn.rzedu.sf.user.service.IUserCharacterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 学生汉字学习的情况
 * 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Service
public class UserCharacterServiceImpl extends ServiceImpl<UserCharacterMapper, UserCharacter> implements IUserCharacterService {

    @Override
    public IPage<UserCharacterVO> selectUserCharacterPage(IPage<UserCharacterVO> page, UserCharacterVO userCharacter) {
        return page.setRecords(baseMapper.selectUserCharacterPage(page, userCharacter));
    }

    @Override
    public List<UserCharacterVO> findAllCharsByLessonId(Integer lessonId, Integer userId) {
        return baseMapper.findAllCharsByLessonId(lessonId, userId);
    }

    @Override
    public UserCharacter findUnionByCharacterId(Integer lessonId, Integer characterId, Integer userId) {
        return baseMapper.findUnionByCharacterId(lessonId, characterId, userId);
    }

    @Override
    public int findFinishedCharCountOfLesson(Integer lessonId, Integer userId) {
        return baseMapper.findFinishedCharCountOfLesson(lessonId, userId);
    }

    @Override
    public int findFinishedCharCountOfTextbook(Integer textbookId, Integer userId) {
        return baseMapper.findFinishedCharCountOfTextbook(textbookId, userId);
    }
}
