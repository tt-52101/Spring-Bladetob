package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.UserCharacter;
import cn.rzedu.sf.user.vo.UserCharacterVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 学生汉字学习的情况
 * Mapper 接口
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface UserCharacterMapper extends BaseMapper<UserCharacter> {

    /**
     * 自定义分页
     *
     * @param page
     * @param userCharacter
     * @return
     */
    List<UserCharacterVO> selectUserCharacterPage(IPage page, UserCharacterVO userCharacter);

    /**
     * 课程所有汉字 和 用户完成情况
     * @param lessonId
     * @param userId
     * @return
     */
    List<UserCharacterVO> findAllCharsByLessonId(Integer lessonId, Integer userId);

    /**
     * 唯一记录
     * @param lessonId
     * @param characterId
     * @param userId
     * @return
     */
    UserCharacter findUnionByCharacterId(Integer lessonId, Integer characterId, Integer userId);

    /**
     * 用户某课程 完成的汉字数
     * @param lessonId
     * @param userId
     * @return
     */
    Integer findFinishedCharCountOfLesson(Integer lessonId, Integer userId);

    /**
     * 用户某教材 完成的汉字数
     * @param textbookId
     * @param userId
     * @return
     */
    Integer findFinishedCharCountOfTextbook(Integer textbookId, Integer userId);
}
