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
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "课程标题-详情", notes = "课程标题，教材名，第几课，总课时")
    public R<Map<String, Object>> detail(
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId
    ) {
        Map<String, Object> result = userLessonService.getLessonMessage(lessonId);
        return R.data(result);
    }


    /**
     * 用户课程星数
     * 课程的总汉字数，星数配置（3颗所在汉字位置），用户已完成汉字数，用户已获得星星数，用户待练字（总数-已完成）
     */
    @GetMapping("/star")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "用户课程星数", notes = "课程的总汉字数，星数配置（3颗所在汉字位置），用户已完成汉字数，用户已获得星星数，用户待练字（总数-已完成）")
    public R<UserLessonVO> star(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId
    ) {
        //生成课程星数，激活关卡时不会生成，在查询前补充
        textbookClient.createLessonStarConfig(lessonId);
        UserLessonVO result = userLessonService.getLessonStars(lessonId, userId);
        return R.data(result);
    }


    /**
     * 课程汉字列表
     * 学习人数，用户学习情况（是否已完成）
     */
    @GetMapping("/characters")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "课程汉字列表", notes = "学习人数，用户学习情况（是否已完成）")
    public R<List<UserCharacterVO>> characters(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId
    ) {
        List<UserCharacterVO> list = userCharacterService.findAllCharsByLessonId(lessonId, userId);
        changeLearnCount(list);
        addHardKnowledgeExtension(list);
        return R.data(list);
    }

    /**
     * 添加硬笔-知识扩展视频
     * @param list
     */
    private void addHardKnowledgeExtension(List<UserCharacterVO> list) {
        if (list != null && !list.isEmpty()) {
            R<List<CourseLessonVO>> result = textbookClient.findKnowledgeExtensionLesson();
            List<CourseLessonVO> courseLessonList = result.getData();
            if (courseLessonList != null && !courseLessonList.isEmpty()) {
                int num = 0;
                for (UserCharacterVO vo : list) {
                    num = (int) (Math.random() * courseLessonList.size());
                    vo.setExtensionId(courseLessonList.get(num).getUuids());
                }
            }
        }
    }

    /**
     * 设置汉字学习人数（随机）
     * @param list
     */
    private void changeLearnCount(List<UserCharacterVO> list){
        if (list != null && !list.isEmpty()) {
            for (UserCharacterVO vo : list) {
                vo.setLearnCount(RandomNumberUtil.getRandomCount(vo.getLearnCount()));
            }
        }
    }


    /**
     * 汉字视频观看进度
     */
    @ApiIgnore
    @GetMapping("/char/process")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "汉字视频观看进度", notes = "汉字视频观看进度")
    public R charaProcess(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId,
            @ApiParam(value = "汉字id", required = true) @RequestParam Integer characterId
    ) {
        int finishedPercent = 0;
        UserCharacter union = userCharacterService.findUnionByCharacterId(lessonId, characterId, userId);
        if (union != null && union.getFinishedPercent() != null) {
            finishedPercent = union.getFinishedPercent();
        }
        return R.data(finishedPercent);
    }


    /**
     * 视频正在看的人数
     */
    @GetMapping("/audience")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "视频正在看的人数", notes = "视频正在看的人数、用户头像")
    public R audience() {
        List<String> iconList = userService.getRandomIcons();
        String[] icons = new String[]{};
        if (iconList != null && !iconList.isEmpty()) {
            icons = iconList.toArray(icons);
        }
        Map<String, Object> result = new HashMap<>(2);
        result.put("icons", icons);
        //TODO 当前观看人数还不知怎么处理
        result.put("count", 0);
        return R.data(result);
    }


    /**
     * 进入详情页判断
     *
     * 判断用户是否已激活该关卡（无数据也为否）。是：停留在原页面；否：弹窗提示，点击跳到闯关详情页。
     * 判断激活前，先判断UT是否有数据，无：UT、UL、UC创建数据
     */
    @PostMapping("/judge")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "进入详情页判断，用户是否已激活关卡", notes = "判断用户是否已激活该关卡（无数据也为否）。是：停留在原页面；否：弹窗提示，点击跳到闯关详情页。")
    public R judge(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "教材id", required = true) @RequestParam Integer textbookId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId
    ) {
        //是否激活（解锁）
        boolean unlock = false;
        UserLesson union = userLessonService.findUnionByLessonIdAndUserId(lessonId, userId);
        if (union != null) {
            if (union.getLocked() != null && !union.getLocked()) {
                unlock = true;
            }
        } else {
            //判断UT是否有数据，无：UT、UL、UC创建数据；默认返回未解锁
            LocalDateTime now = LocalDateTime.now();
            UserTextbook textbook = userTextbookService.findUnionByTextbookIdAndUserId(textbookId, userId);
            if (textbook == null) {
                textbook = new UserTextbook();
                textbook.setUserId(userId);
                textbook.setTextbookId(textbookId);
                textbook.setOwnedTime(now);
                textbook.setActiveTime(now);
                textbook.setStartTime(now);
                textbook.setActiveLessonId(lessonId);
                textbook.setCreateDate(now);
                textbook.setModifyDate(now);
                userTextbookService.save(textbook);
            }

            //判断课程存不存在
            //新建UL、UC，当listOrder=1时，默认解锁，其余关卡未解锁
            //默认激活关卡，更改为可配置的，不一定是第1关；根据配置，前几关都可激活
            boolean isLocked = true;
            R<TextbookLesson> result = textbookClient.lessonDetail(lessonId);
            if (result.isSuccess() && SUCCESS_CODE == result.getCode()) {
                TextbookLesson lesson = result.getData();
                if (lesson == null || lesson.getId() == null) {
                    return R.data(false,"课程不存在");
                } else if (lesson.getListOrder() != null && lesson.getListOrder() <= ACTIVATE_LESSON_NUMBER) {
                    isLocked = false;
                }
            }

            unlock = !isLocked;
            createUserLessonAndCharacter(userId, textbookId, lessonId, isLocked);

            //增加当前课程星数配置
            textbookClient.createLessonStarConfig(lessonId);
//            //判断是否第一课，且第一课是否存在，不存在则新增并激活 -- 非激活关卡需回到闯关详情，并激活第一关
//            if (isLocked) {
//                createFirstUserLesson(userId, textbookId);
//            }

            //激活前几关，非激活关卡需回到闯关详情
            if (isLocked) {
                createPrepositionUserLesson(userId, textbookId, ACTIVATE_LESSON_NUMBER);
            }
        }
        return R.data(unlock);
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
     * 新增并激活用户课程第一关
     * @param userId
     * @param textbookId
     */
    private void createFirstUserLesson(Integer userId, Integer textbookId) {
        Integer firstLessonId = null;
        R<TextbookLesson> result = textbookClient.firstLesson(textbookId);
        if (result.isSuccess() && SUCCESS_CODE == result.getCode()) {
            TextbookLesson lesson = result.getData();
            if (lesson != null && lesson.getId() != null) {
                firstLessonId = lesson.getId();
            }
        }
//        //当前课程非第一课时，走新增流程
//        if (lessonId.intValue() == firstLessonId) {
//            return;
//        }
        if (firstLessonId == null) {
            return;
        }
        UserLesson union = userLessonService.findUnionByLessonIdAndUserId(firstLessonId, userId);
        if (union == null) {
            createUserLessonAndCharacter(userId, textbookId, firstLessonId, false);
        }
    }

    /**
     * 激活用户课程前几关
     */
    private void createPrepositionUserLesson(Integer userId, Integer textbookId, Integer number) {
        List<TextbookLessonVO> list = getAllLessons(textbookId);
        if (list == null || list.isEmpty()) {
            return;
        }
        Integer lessonId = null;
        Integer listOrder = null;
        UserLesson union = null;
        UserLesson ul = null;
        for (TextbookLessonVO vo : list) {
            lessonId = vo.getId();
            listOrder = vo.getListOrder();
            if (lessonId != null && listOrder != null && listOrder <= number) {
                union = userLessonService.findUnionByLessonIdAndUserId(lessonId, userId);
                if (union == null) {
                    createUserLessonAndCharacter(userId, textbookId, lessonId, false);
                } else if (union.getLocked()) {
                    ul = new UserLesson();
                    ul.setId(union.getId());
                    ul.setLocked(false);
                    userLessonService.updateById(ul);
                }
            }
        }
    }


    /**
     * 观看视频后数据保存
     *
     * 修改当前学习课程，修改观看百分比
     * 若观看进度100%，修改完成汉字数，修改获得星星数，添加观看完成记录
     * 根据获得星星数，激活关卡
     *
     * 当完成星数>2时，激活下一关卡。激活规则：只激活连续完成的关的后2关。
     * e.g. 完成第1关，激活2,3。 完成第1,2关，激活3,4。
     *     只完成1,2,4关，不激活，因为连续完成数只到2，只能激活3,4，完成第3关，可激活5,6（连续完成数=4）
     */
    @PostMapping("/char/update")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "观看视频后数据保存", notes = "修改当前学习课程，修改观看百分比，修改完成汉字数，修改获得星星数，添加观看完成记录，激活关卡")
    public R charUpdate(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "教材id", required = true) @RequestParam Integer textbookId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId,
            @ApiParam(value = "汉字id", required = true) @RequestParam Integer characterId,
            @ApiParam(value = "完成进度 0~100", required = true) @RequestParam Integer finishedPercent,
            @ApiParam(value = "视频已看秒数 0~1000", required = true) @RequestParam Integer watchProcess
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

        //0.必须变动的，教材、汉字、视频的访问量，每观看一次视频，访问量+1
        //修改 T visitedCount
        //修改 TLC visitedCount
        //修改 C softResVisitedCount/hardResVisitedCount
        //修改 CR visitedCount
        textbookClient.updateCharVisitedCount(textbookId, lessonId, characterId);

        //0.对比进度，判断原进度，跟当前进度是否一致，true：只更改访问量；false：更改其他
        //原进度=100时，课程已完成，无需改动
//        if (union.getFinishedPercent().intValue() == finishedPercent) {
//            return R.status(true);
//        }
        if (union.getFinishedPercent() == 100) {
            return R.status(true);
        }

        //1.必须改动
        //修改 UC finishedPercent, lastVisitedTime
        union.setFinishedPercent(finishedPercent);
        union.setWatchProgress(watchProcess);
        union.setLastVisitedTime(now);
        union.setModifyDate(now);
        userCharacterService.updateById(union);
        //修改 UT activeLessonId
        UserTextbook ut = new UserTextbook();
        ut.setUserId(userId);
        ut.setTextbookId(textbookId);
        ut.setActiveLessonId(lessonId);
        userTextbookService.updateByTextbookIdAndUserId(ut);


        //2.100完成的
        if (finishedPercent != 100) {
            return R.status(true);
        }
        //新增 UCH
        //修改 UL finishedCharCount, starCount（根据lesson_star_config判断，能获得几颗星）
        //修改 UT activeLessonId，finishedCharCount, finishedPercent
        UserCharacterHistory uch = new UserCharacterHistory();
        uch.setUserId(userId);
        uch.setLessonId(lessonId);
        uch.setCharacterId(characterId);
        uch.setCharacterLessonId(union.getCharacterLessonId());
        uch.setFinishedPercent(100);
        uch.setLastVisitedTime(now);
        uch.setCreateDate(now);
        uch.setModifyDate(now);
        userCharacterHistoryService.save(uch);

        UserLesson userLesson = userLessonService.findUnionByLessonIdAndUserId(lessonId, userId);
        Integer charCount = userLesson.getCharCount();
        //新的 完成汉字数。课程汉字完成数不能直接+1，要拿UC中100进度的记录数，本次UC记录上面步骤已更新，不用+1
//        Integer finishedCharCount = userLesson.getFinishedCharCount() + 1;
        Integer finishedCharCount = userCharacterService.findFinishedCharCountOfLesson(lessonId, userId);
        //新的星数 根据lesson_star_config判断，能获得几颗星
        Integer starCount = getFinishedStarCount(lessonId, finishedCharCount);

        userLesson.setFinishedCharCount(finishedCharCount);
        userLesson.setStarCount(starCount);
        userLesson.setModifyDate(now);
        userLessonService.updateById(userLesson);

        ut = userTextbookService.findUnionByTextbookIdAndUserId(textbookId, userId);
        //教材汉字总数
        int textbookCharCount = getTextbookCharCount(textbookId);
        //教材汉字完成数。不能直接+1，从UC中获取，100进度的
//        int textbookFinishedCharCount = ut.getFinishedCharCount() + 1;
        int textbookFinishedCharCount = userCharacterService.findFinishedCharCountOfTextbook(textbookId, userId);
        //教材完成进度
        int textbookFinishedPercent = textbookFinishedCharCount * 100 / textbookCharCount;
        ut.setFinishedCharCount(textbookFinishedCharCount);
        ut.setFinishedPercent(textbookFinishedPercent);
        if (charCount.equals(finishedCharCount)) {
            //3.课程全部完成，修改 UT finishedLessonCount
            //课程完成数不能直接+1，从UL中获取，UL的charCount=finishedCharCount
            int finishedLessonCount = userLessonService.findFinishedLessonCountOfTextbook(textbookId, userId);
            ut.setFinishedLessonCount(finishedLessonCount);
        }
        if (textbookFinishedPercent == 100) {
            //3.课程全部完成，修改 UT finishedTime
            ut.setFinishTime(now);
        }
        userTextbookService.updateByTextbookIdAndUserId(ut);

        //4.星数>2, 判断是否激活关卡，新增UL、UC
        if (starCount > 2) {
            //获取教材所有课程 TL
            //获取用户课程，按课程数升序，逐个判断，获取连续完成数（循环，有一关<=2的，上一关就是最大连续数）
            //根据最大连续数，获取后两关的lessonId，新建UL、UC，UL已存在的，修改locked=false
//            activateUserLesson(userId, textbookId);
        }

        return R.data("success");
    }

    /**
     * 激活新关卡
     * 获取用户课程，按课程数升序，逐个判断，获取连续完成数（循环，有一关<=2的，上一关就是最大连续数）
     * 根据最大连续数，获取后两关的lessonId，新建UL、UC；UL已存在的，修改locked=false
     * @param userId
     * @param textbookId
     * @return  true=有执行激活操作
     */
    private boolean activateUserLesson(Integer userId, Integer textbookId) {
        //是否有执行激活操作
        boolean haveOperate = false;
        List<TextbookLessonVO> allLessons = getAllLessons(textbookId);
        if (allLessons == null) {
            return false;
        }
        //先激活默认关卡
        createPrepositionUserLesson(userId, textbookId, ACTIVATE_LESSON_NUMBER);

        List<UserLessonVO> ulList = userLessonService.findAllLessonWithUser(textbookId, userId);
        int topSection = 0;
        for (UserLessonVO vo : ulList) {
            if (vo.getStarCount() > 2) {
                topSection = vo.getListOrder();
            } else {
                break;
            }
        }
        //第一关都没完成时，topSection才会为0，无需激活关卡
        if (topSection == 0) {
            return false;
        }
        Integer lessonId_n1 = null;
        Integer lessonId_n2 = null;
        for (TextbookLessonVO vo : allLessons) {
            if (vo.getListOrder() == topSection + 1) {
                lessonId_n1 = vo.getId();
            } else if (vo.getListOrder() == topSection + 2) {
                lessonId_n2 = vo.getId();
            }
        }
        if (lessonId_n1 != null) {
            saveOrUpdateUserLesson(userId, textbookId, lessonId_n1);
            haveOperate = true;
        }
        if (lessonId_n2 != null) {
            saveOrUpdateUserLesson(userId, textbookId, lessonId_n2);
            haveOperate = true;
        }
        return haveOperate;
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
     * 用户能获得几颗星
     * 根据完成汉字数和配置比较
     */
    private int getFinishedStarCount(Integer lessonId, Integer finishedCharCount){
        int finishedStarCount = 0;
        R<LessonStarConfig> result = textbookClient.createLessonStarConfig(lessonId);
        if (result.isSuccess() && SUCCESS_CODE == result.getCode()) {
            LessonStarConfig config = result.getData();
            Integer n1 = config.getStarsN1();
            Integer n2 = config.getStarsN2();
            Integer n3 = config.getStarsN3();
            if (finishedCharCount >= n3) {
                finishedStarCount = 3;
            } else if (finishedCharCount >= n2) {
                finishedStarCount = 2;
            } else if (finishedCharCount >= n1) {
                finishedStarCount = 1;
            } else {
                finishedStarCount = 0;
            }
        }
        return finishedStarCount;
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
     * 检测并激活新关卡
     * @param userId
     * @param textbookId
     * @return
     */
    @PostMapping("/activate/lesson")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "检测并激活新关卡", notes = "检测并激活新关卡")
    public R checkAndActiveLesson(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "教材id", required = true) @RequestParam Integer textbookId
    ) {
        boolean haveOperate = activateUserLesson(userId, textbookId);
        if (haveOperate) {
            //有执行操作的，验证总汉字数，判断完成进步是否有改动，有则修改
            UserTextbook userTextbook = userTextbookService.findUnionByTextbookIdAndUserId(textbookId, userId);
            if (userTextbook != null) {
                //教材总汉字数
                int charCount = getTextbookCharCount(textbookId);
                //教材学习（汉字）进度百分比
                int finishedPercent = userTextbook.getFinishedCharCount() * 100 / charCount;
                if (finishedPercent != userTextbook.getFinishedPercent()) {
                    UserTextbook ut = new UserTextbook();
                    ut.setId(userTextbook.getId());
                    ut.setFinishedPercent(finishedPercent);
                    userTextbookService.updateById(ut);
                }
            }
        }
        return R.status(true);
    }


    /**
     * 闯关成功练字统计--海报分享
     * @param userId
     * @param lessonId
     * @return
     */
    @GetMapping("/share/stat")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "闯关成功练字统计--海报分享", notes = "闯关成功练字统计，含：关卡数，用户微信名，完成汉字数，课程学习天数，测字平均得分")
    public R shareStat(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId
    ) {
        Integer number = 0;
        String name = "";
        Integer charCount = 0;
        long day = 0;
        Float score = 98.3f;

        R<TextbookLesson> detail = textbookClient.lessonDetail(lessonId);
        if (detail != null) {
            TextbookLesson lesson = detail.getData();
            if (lesson != null && lesson.getListOrder() != null) {
                number = lesson.getListOrder();
            }
        }
        User user = userService.getById(userId);
        if (user != null) {
            name = user.getName();
        }
        UserLesson userLesson = userLessonService.findUnionByLessonIdAndUserId(lessonId, userId);
        if (userLesson != null) {
            charCount = userLesson.getFinishedCharCount();

            UserTextbook userTextbook = userTextbookService.findUnionByTextbookIdAndUserId(userLesson.getTextbookId(), userId);
            if (userTextbook != null) {
                LocalDateTime startTime = userTextbook.getStartTime();
                if (startTime != null) {
                    day = Duration.between(startTime, LocalDateTime.now()).toDays() + 1;
                }
            }
        }

        Map<String, Object> result = new HashMap<>(5);
        result.put("number", number);
        result.put("name", name);
        result.put("charCount", charCount);
        result.put("day", day);
        result.put("score", score);
        return R.data(result);
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
