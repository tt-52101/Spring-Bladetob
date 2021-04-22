package cn.rzedu.sf.user.controller;

import cn.rzedu.sf.resource.entity.LessonStarConfig;
import cn.rzedu.sf.resource.entity.Textbook;
import cn.rzedu.sf.resource.entity.TextbookLesson;
import cn.rzedu.sf.resource.entity.TextbookLessonCharacter;
import cn.rzedu.sf.resource.feign.ITextbookClient;
import cn.rzedu.sf.resource.vo.CourseLessonVO;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import cn.rzedu.sf.user.entity.*;
import cn.rzedu.sf.user.service.*;
import cn.rzedu.sf.user.utils.RandomNumberUtil;
import cn.rzedu.sf.user.vo.UserCharacterVO;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;


import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.map.HashedMap;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import cn.rzedu.sf.user.vo.UserLessonVO;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生课程学习情况
 * 控制器
 *
 * @author Blade
 * @since 2020-08-05
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/lesson")
@Api(value = " 学生课程学习情况 ", tags = " 课程学习情况")
public class UserLessonController extends BladeController {

    /**
     *  默认激活的关卡数
     */
    @Value("${lesson.activateSection}")
    private int ACTIVATE_LESSON_NUMBER;

    private static final int SUCCESS_CODE = 200;

    private final IUserLessonService userLessonService;

    private final IUserTextbookService userTextbookService;

    private final IUserCharacterService userCharacterService;

    private final IUserCharacterHistoryService userCharacterHistoryService;

    private final IUserService userService;

    private final ITextbookClient textbookClient;
    

    /**
     * 课程标题-详情
     * 课程标题，教材名，第几课，总课时
     */
    @GetMapping("/title")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "课程标题-详情", notes = "课程标题:lessonCount; 教材名:textbookName; 第几课:listOrder; 总课时:lessonCount" +
            "; 软硬笔:subject,71=软笔,72=硬笔")
    public R<Map<String, Object>> detail(
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId
    ) {
        Map<String, Object> result = userLessonService.getLessonMessage(lessonId);
        return R.data(result);
    }


    /**
     * 视频正在看的人数
     */
    @GetMapping("/audience")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "视频正在看的人数", notes = "视频正在看的人数(随机人数)、用户头像")
    public R audience() {
        List<String> iconList = userService.getRandomIcons();
        String[] icons = new String[]{};
        if (iconList != null && !iconList.isEmpty()) {
            if (iconList.size() <= 5) {
                icons = iconList.toArray(icons);
            } else {
                icons = new String[5];
                for (int i = 0; i < 5; i++) {
                    icons[i] = iconList.get(i);
                }
            }
        }
        Map<String, Object> result = new HashMap<>(2);
        result.put("icons", icons);
        result.put("count", (int) (Math.random() * 200 + 1) + 200);
        return R.data(result);
    }


    /**
     * 课程汉字列表
     * 学习人数，用户学习情况（是否已完成）
     */
    @GetMapping("/characters")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "课程汉字列表", notes = "用户学习情况（是否已完成）")
    public R<List<UserCharacterVO>> characters(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId
    ) {
        List<UserCharacterVO> list = userCharacterService.findAllCharsByLessonId(lessonId, userId);
        List result = transferList(list);
        return R.data(result);
    }

    private List transferList(List<UserCharacterVO> list) {
        List result = new ArrayList();
        if (list == null || list.isEmpty()) {
            return result;
        }
        Map<String, Object> map = null;
        for (UserCharacterVO vo : list) {
            map = new HashedMap();
            map.put("lessonId", vo.getLessonId());
            map.put("characterId", vo.getCharacterId());
            map.put("charS", vo.getCharS());
            map.put("videoId", vo.getVideoId());
            int isFinished = 0;
            if(vo.getFinishedPercent() != 0) {
                isFinished = 1;
            }
            map.put("isFinished", isFinished);
            result.add(map);
        }
        return result;
    }

    @PostMapping("/char/update")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "观看视频后数据保存", notes = "修改当前学习课程，修改观看状态，修改完成汉字数")
    public R charUpdate(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "教材id", required = true) @RequestParam Integer textbookId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId,
            @ApiParam(value = "汉字id", required = true) @RequestParam Integer characterId
    ) {
        //学习记录
        LocalDateTime now = LocalDateTime.now();
        UserCharacter union = userCharacterService.findUnionByCharacterId(lessonId, characterId, userId);
        if (union == null) {
            //新建UC
            union = new UserCharacter();
            union.setUserId(userId);
            union.setLessonId(lessonId);
            //查textbook_lesson_character获取id
            Integer lessonCharacterId = getTextbookLessonCharacterId(lessonId, characterId);
            if (lessonCharacterId == null) {
                return R.fail("获取不到课程下的汉字");
            }
            union.setCharacterLessonId(lessonCharacterId);
            union.setCharacterId(characterId);
            union.setFinishedPercent(0);
            union.setLastVisitedTime(now);
            union.setCreateDate(now);
            union.setModifyDate(now);
            userCharacterService.save(union);
        }
        if (union.getFinishedPercent() == 100) {
            return R.status(true);
        }
        int finishedPercent = 100;

        //修改 UC finishedPercent, lastVisitedTime
        union.setFinishedPercent(finishedPercent);
        union.setWatchProgress(1000);
        union.setLastVisitedTime(now);
        union.setModifyDate(now);
        userCharacterService.updateById(union);

        //修改完成汉字数
        UserLesson userLesson = userLessonService.findUnionByLessonIdAndUserId(lessonId, userId);
        if (userLesson == null) {
            R<List<TextbookLessonCharacter>> result = textbookClient.allLessonCharacters(lessonId);
            Integer charCount = 0;
            if (result.getData() != null) {
                charCount = result.getData().size();
            }
            userLesson = new UserLesson();
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
        }
        Integer charCount = userLesson.getCharCount();
        Integer finishedCharCount = userCharacterService.findFinishedCharCountOfLesson(lessonId, userId);
        userLesson.setFinishedCharCount(finishedCharCount);
        userLesson.setModifyDate(now);
        userLessonService.updateById(userLesson);

        //修改完成汉字数，当前课程

        UserTextbook ut = userTextbookService.findUnionByTextbookIdAndUserId(textbookId, userId);
        if (ut == null) {
            ut = new UserTextbook();
            ut.setUserId(userId);
            ut.setTextbookId(textbookId);
            ut.setOwnedTime(now);
            ut.setActiveTime(now);
            ut.setStartTime(now);
            ut.setCreateDate(now);
            ut.setModifyDate(now);
            userTextbookService.save(ut);
        }
        ut.setActiveLessonId(lessonId);

        int textbookCharCount = getTextbookCharCount(textbookId);
        int textbookFinishedCharCount = userCharacterService.findFinishedCharCountOfTextbook(textbookId, userId);
        //教材完成进度
        int textbookFinishedPercent = textbookFinishedCharCount * 100 / textbookCharCount;

        ut.setFinishedCharCount(textbookFinishedCharCount);
        ut.setFinishedPercent(textbookFinishedPercent);

        if (charCount.equals(finishedCharCount)) {
            int finishedLessonCount = userLessonService.findFinishedLessonCountOfTextbook(textbookId, userId);
            ut.setFinishedLessonCount(finishedLessonCount);
        }
        if (textbookFinishedPercent == 100) {
            ut.setFinishTime(now);
        }
        userTextbookService.updateByTextbookIdAndUserId(ut);
        return R.data(true);
    }


    /**
     * 新增用户课程，用户汉字
     */
    private void createUserLessonAndCharacter(Integer userId, Integer textbookId, Integer lessonId, boolean isLocked) {
        LocalDateTime now = LocalDateTime.now();
        Integer charCount = 0;
        //获取课程所有汉字
        List<TextbookLessonCharacter> lessonCharacterList = null;
        R<List<TextbookLessonCharacter>> result = textbookClient.allLessonCharacters(lessonId);
        if (result.isSuccess() && SUCCESS_CODE == result.getCode()) {
            lessonCharacterList = result.getData();
        } else {
            return;
        }
        if (lessonCharacterList != null && !lessonCharacterList.isEmpty()) {
            charCount = lessonCharacterList.size();
        }

        UserLesson union = userLessonService.findUnionByLessonIdAndUserId(lessonId, userId);
        if (union != null) {
            return;
        }

        try {
            //目前只新增当前课程，其余课程在"激活阶段"新增
            UserLesson userLesson = new UserLesson();
            userLesson.setUserId(userId);
            userLesson.setTextbookId(textbookId);
            userLesson.setLessonId(lessonId);
            userLesson.setCharCount(charCount);
            userLesson.setFinishedCharCount(0);
            userLesson.setLocked(isLocked);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取课程汉字id
     * @return
     */
    private Integer getTextbookLessonCharacterId(Integer lessonId, Integer characterId) {
        Integer id = null;
        R<TextbookLessonCharacter> result = textbookClient.lessonCharacterDetail(lessonId, characterId);
        if (result.isSuccess() && SUCCESS_CODE == result.getCode()) {
            TextbookLessonCharacter data = result.getData();
            if (data != null) {
                id = data.getId();
            }
        }
        return id;
    }

    /**
     * 获取教材总汉字数
     */
    private int getTextbookCharCount(Integer textbookId) {
        int charCount = 0;
        R<Textbook> result = textbookClient.detail(textbookId);
        if (result.isSuccess() && SUCCESS_CODE == result.getCode()) {
            Textbook textBook = result.getData();
            if (textBook != null && textBook.getCharCount() != null) {
                charCount = textBook.getCharCount();
            }
        }
        return charCount;
    }

    /**
     * 获取教材总课程数
     */
    private List<TextbookLessonVO> getAllLessons(Integer textbookId) {
        R<List<TextbookLessonVO>> result = textbookClient.allLessons(textbookId);
        if (result.isSuccess() && SUCCESS_CODE == result.getCode()) {
            return result.getData();
        } else {
            return null;
        }
    }

    /**
     * 新增UL或解锁UL
     */
    private void saveOrUpdateUserLesson(Integer userId, Integer textbookId, Integer lessonId) {
        UserLesson union = userLessonService.findUnionByLessonIdAndUserId(lessonId, userId);
        if (union != null) {
            if (union.getLocked()) {
                UserLesson ul = new UserLesson();
                ul.setId(union.getId());
                ul.setLocked(false);
                userLessonService.updateById(ul);
            }
        } else {
            createUserLessonAndCharacter(userId, textbookId, lessonId, false);
        }
    }



    /**
     * 批量解锁教材课程
     * @param userId
     * @param textbookIds
     * @return
     */
    @PostMapping("/batch/create")
    @ApiOperationSupport(order = 99)
    @ApiOperation(value = "批量解锁课程", notes = "批量解锁课程")
    public R batchCreateTextbook(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "教材id，多个用逗号隔开", required = true) @RequestParam String textbookIds
    ) {
        List<Integer> ids = Func.toIntList(textbookIds);
        if (ids == null || ids.isEmpty()) {
            return R.status(false);
        }
        LocalDateTime now = LocalDateTime.now();
        UserTextbook textbook = null;
        List<TextbookLessonVO> lessonList = null;
        for (Integer textbookId : ids) {
            textbook = userTextbookService.findUnionByTextbookIdAndUserId(textbookId, userId);
            if (textbook == null) {
                textbook = new UserTextbook();
                textbook.setUserId(userId);
                textbook.setTextbookId(textbookId);
                textbook.setOwnedTime(now);
                textbook.setActiveTime(now);
                textbook.setStartTime(now);
                textbook.setCreateDate(now);
                textbook.setModifyDate(now);
                userTextbookService.save(textbook);
            }

            lessonList = getAllLessons(textbookId);
            if (lessonList == null || lessonList.isEmpty()) {
                continue;
            }
            for (TextbookLessonVO lessonVO : lessonList) {
                saveOrUpdateUserLesson(userId, textbookId, lessonVO.getId());
            }
        }
        return R.status(true);
    }
}
