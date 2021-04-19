package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.resource.entity.TextbookLesson;
import cn.rzedu.sf.resource.entity.TextbookLessonCharacter;
import cn.rzedu.sf.resource.feign.ITextbookClient;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import cn.rzedu.sf.user.entity.UserCharacter;
import cn.rzedu.sf.user.entity.UserLesson;
import cn.rzedu.sf.user.entity.UserTextbook;
import cn.rzedu.sf.user.service.*;
import cn.rzedu.sf.user.vo.UserTextbookVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生课程相关
 * 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Service
@AllArgsConstructor
public class UserTextbookBizServiceImpl implements IUserTextbookBizService {

    private static final int SUCCESS_CODE = 200;

    private IUserTextbookService userTextbookService;

    private final IUserLessonService userLessonService;

    private final IUserCharacterService userCharacterService;

    private final IUserService userService;

    private final ITextbookClient textbookClient;

    @Override
    public boolean setupLearningTextbook(Integer userId, Integer textbookId, Integer lessonId) {
        UserTextbook userTextbook = userTextbookService.findUnionByTextbookIdAndUserId(textbookId, userId);
        if (userTextbook != null) {
            return true;
        }
        if (lessonId == null) {
            R<TextbookLesson> lessonR = textbookClient.firstLesson(textbookId);
            if (lessonR.getData() != null) {
                lessonId = lessonR.getData().getId();
            }
        }
        LocalDateTime now = LocalDateTime.now();
        userTextbook = new UserTextbook();
        userTextbook.setUserId(userId);
        userTextbook.setTextbookId(textbookId);
        userTextbook.setOwnedTime(now);
        userTextbook.setActiveTime(now);
        userTextbook.setStartTime(now);
        userTextbook.setActiveLessonId(lessonId);
        userTextbook.setCreateDate(now);
        userTextbook.setModifyDate(now);
        userTextbookService.save(userTextbook);

        createUserLessonAndCharacter(userId, textbookId);
        return true;
    }

    /**
     * 新增教材下所有课程，所有汉字
     */
    private void createUserLessonAndCharacter(Integer userId, Integer textbookId) {
        LocalDateTime now = LocalDateTime.now();
        Integer charCount = 0;
        //获取课程所有汉字
        List<TextbookLessonCharacter> lessonCharacterList = null;
        R<List<TextbookLessonVO>> allLessons = textbookClient.allLessons(textbookId);
        List<TextbookLessonVO> lessonList = allLessons.getData();
        if (lessonList == null) {
            return;
        }

        for (TextbookLessonVO lessonVO : lessonList) {
            Integer lessonId = lessonVO.getId();
            UserLesson union = userLessonService.findUnionByLessonIdAndUserId(lessonId, userId);
            if (union != null) {
                continue;
            }

            R<List<TextbookLessonCharacter>> allLessonCharacters = textbookClient.allLessonCharacters(lessonId);
            lessonCharacterList = allLessonCharacters.getData();
            if (lessonCharacterList == null) {
                charCount = 0;
            } else {
                charCount = lessonCharacterList.size();
            }

            UserLesson userLesson = new UserLesson();
            userLesson.setUserId(userId);
            userLesson.setTextbookId(textbookId);
            userLesson.setLessonId(lessonId);
            userLesson.setCharCount(charCount);
            userLesson.setFinishedCharCount(0);
            userLesson.setLocked(false);
            userLesson.setStarCount(0);
            userLesson.setCreateDate(now);
            userLesson.setModifyDate(now);
            userLessonService.save(userLesson);

            if (charCount != 0) {
                List<UserCharacter> ucList = new ArrayList<>(charCount);
                UserCharacter uc = null;
                for (TextbookLessonCharacter lessonCharacter : lessonCharacterList) {
                    uc = new UserCharacter();
                    uc.setUserId(userId);
                    uc.setLessonId(lessonId);
                    uc.setCharacterLessonId(lessonCharacter.getId());
                    uc.setCharacterId(lessonCharacter.getCharacterId());
                    uc.setFinishedPercent(0);
                    uc.setWatchProgress(0);
                    uc.setLastVisitedTime(now);
                    uc.setCreateDate(now);
                    uc.setModifyDate(now);
                    ucList.add(uc);
                }
                userCharacterService.saveBatch(ucList);
            }
        }
    }

    @Override
    public List findRecentlyTextbook(Integer userId) {
        Query query = new Query();
        query.setSize(5);
        IPage<UserTextbookVO> pages = userTextbookService.findBoughtTextBookList(Condition.getPage(query), userId);
        List<UserTextbookVO> userTextbookList = pages.getRecords();
        if (userTextbookList == null || userTextbookList.isEmpty()) {
            return null;
        }
        List<String> icons = userService.getRandomIcons();
        List list = new ArrayList();
        Map<String, Object> map = null;
        for (UserTextbookVO vo : userTextbookList) {
            map = new HashMap();
            map.put("textbookId", vo.getTextbookId());
            map.put("textbookName", vo.getTextbookName());
            map.put("coverImage", vo.getCoverImage());
            map.put("subject", vo.getSubject());
            map.put("lessonId", vo.getActiveLessonId());
            map.put("lessonName", vo.getActiveLessonName());
            map.put("learnCount", vo.getBoughtCount());
            map.put("font", vo.getFont());
            map.put("icons", getRandomIcon(icons));
            list.add(map);
        }
        return list;
    }

    private List<String> getRandomIcon(List<String> icons) {
        List<String> list = new ArrayList<>();
        int size = icons.size();
        if (size < 4) {
            list = icons;
        } else {
            for (int i = 1; i < 4; i++) {
                list.add(icons.get((int) (Math.random() * size)));
            }
        }
        return list;
    }

    @Override
    public Map getLearningLesson(Integer userId) {
        Query query = new Query();
        query.setSize(1);
        IPage<UserTextbookVO> pages = userTextbookService.findBoughtTextBookList(Condition.getPage(query), userId);
        List<UserTextbookVO> userTextbookList = pages.getRecords();
        if (userTextbookList == null || userTextbookList.isEmpty()) {
            return null;
        }
        UserTextbookVO vo = userTextbookList.get(0);
        Integer textbookId = vo.getTextbookId();
        Integer activeLessonId = vo.getActiveLessonId();

        Map<String, Object> map = new HashMap<>();
        map.put("textbookId", textbookId);
        map.put("textbookName", vo.getTextbookName());
        map.put("lessonId", activeLessonId);
        map.put("lessonName", vo.getActiveLessonName());
        map.put("subject", vo.getSubject());
        map.put("font", vo.getFont());

        int listOrder = 1;
        R<List<TextbookLessonVO>> allLessons = textbookClient.allLessons(textbookId);
        List<TextbookLessonVO> lessonVOList = allLessons.getData();
        List lessonList = new ArrayList();
        Map<String, Object> lessonMap = null;
        if (lessonVOList != null) {
            for (TextbookLessonVO lessonVO : lessonVOList) {
                lessonMap = new HashMap<>();
                int isActive = 0;
                if (lessonVO.getId().equals(activeLessonId)) {
                    isActive = 1;
                    listOrder = lessonVO.getListOrder();
                }
                lessonMap.put("lessonId", lessonVO.getId());
                lessonMap.put("lessonName", lessonVO.getName());
                lessonMap.put("listOrder", lessonVO.getListOrder());
                lessonMap.put("isActive", isActive);
                lessonList.add(lessonMap);
            }
        }
        map.put("lessonList", lessonList);
        map.put("listOrder", listOrder);
        return map;
    }
}
