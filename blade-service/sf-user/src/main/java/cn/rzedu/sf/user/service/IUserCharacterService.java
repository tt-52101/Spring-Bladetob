package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.UserCharacter;
import cn.rzedu.sf.user.vo.UserCharacterVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 学生汉字学习的情况
 * 服务类
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface IUserCharacterService extends IService<UserCharacter> {

    /**
     * 自定义分页
     *
     * @param page
     * @param userCharacter
     * @return
     */
    IPage<UserCharacterVO> selectUserCharacterPage(IPage<UserCharacterVO> page, UserCharacterVO userCharacter);

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
    int findFinishedCharCountOfLesson(Integer lessonId, Integer userId);

    /**
     * 用户某教材 完成的汉字数
     * @param textbookId
     * @param userId
     * @return
     */
    int findFinishedCharCountOfTextbook(Integer textbookId, Integer userId);
}
