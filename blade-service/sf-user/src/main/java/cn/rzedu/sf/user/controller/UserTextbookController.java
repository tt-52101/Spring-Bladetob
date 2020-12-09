package cn.rzedu.sf.user.controller;

import cn.rzedu.sf.user.service.IUserLessonService;
import cn.rzedu.sf.user.utils.RandomNumberUtil;
import cn.rzedu.sf.user.vo.UserLessonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.rzedu.sf.user.entity.UserTextbook;
import cn.rzedu.sf.user.vo.UserTextbookVO;
import cn.rzedu.sf.user.wrapper.UserTextbookWrapper;
import cn.rzedu.sf.user.service.IUserTextbookService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生教材学习情况表
 * 控制器
 *
 * @author Blade
 * @since 2020-08-05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user/textbook")
@Api(value = " 学生教材学习情况 ", tags = "教材学习情况")
public class UserTextbookController extends BladeController {

    private IUserTextbookService userTextbookService;

    private IUserLessonService userLessonService;

    /**
     * 热门课程
     * 购买人数降序，分页显示
     */
    @GetMapping("/hot")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "热门课程", notes = "热门课程，按购买人数降序")
    public R<IPage<UserTextbookVO>> hot(Query query) {
        IPage<UserTextbookVO> pages = userTextbookService.findHotTextBookList(Condition.getPage(query));
        changeBoughtCount(pages);
        return R.data(pages);
    }

    private void changeBoughtCount(IPage<UserTextbookVO> pages) {
        List<UserTextbookVO> list = pages.getRecords();
        if (list != null && !list.isEmpty()) {
            for (UserTextbookVO vo : list) {
                vo.setBoughtCount(RandomNumberUtil.getBuyRandomCount(vo.getBoughtCount()));
            }
        }
        pages.setRecords(list);
    }

    private void changeLearnCount(IPage<UserTextbookVO> pages) {
        List<UserTextbookVO> list = pages.getRecords();
        if (list != null && !list.isEmpty()) {
            for (UserTextbookVO vo : list) {
                vo.setBoughtCount(RandomNumberUtil.getRandomCount(vo.getBoughtCount()));
            }
        }
        pages.setRecords(list);
    }

    /**
     * 已购课程
     * 最后修改时间降序，分页显示
     */
    @GetMapping("/buy")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "已购课程", notes = "获取用户已购课程，按最后修改时间降序")
    public R<IPage<UserTextbookVO>> buy(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            Query query) {
        IPage<UserTextbookVO> pages = userTextbookService.findBoughtTextBookList(Condition.getPage(query), userId);
        changeLearnCount(pages);
        return R.data(pages);
    }

    /**
     * 已学课程
     * 分页显示
     */
    @GetMapping("/finish")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "已学课程", notes = "获取用户已学课程")
    public R<IPage<UserTextbookVO>> finish(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            Query query) {
        IPage<UserTextbookVO> pages = userTextbookService.findFinishedTextBookList(Condition.getPage(query), userId);
        changeLearnCount(pages);
        return R.data(pages);
    }


    /**
     * 已购/已完成课程（含其他课程）
     */
    @GetMapping("/buy/list")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "已购/已完成课程（含其他课程）", notes = "获取用户软硬笔+其他课程，按最后修改时间降序")
    public R buyList(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "完成状态，1=已完成", required = true) @RequestParam(required = false, defaultValue = "0") Integer isFinished
    ) {
        List<Map<String, Object>> list = userTextbookService.findBoughtList(userId, isFinished);
        changeCountV1(list);
        return R.data(list);
    }

    //改变学习人数
    private void changeCountV1(List<Map<String, Object>> list) {
        if (list != null && !list.isEmpty()) {
            Integer boughtCount = 0;
            for (Map<String, Object> map : list) {
                if (map.get("boughtCount") != null) {
                    boughtCount = Integer.parseInt(String.valueOf(map.get("boughtCount")));
                } else {
                    boughtCount = 0;
                }
                map.put("boughtCount", RandomNumberUtil.getRandomCount(boughtCount));
            }
        }
    }


    /**
     * 闯关详情-课程列表
     *
     * @param userId
     * @param textbookId
     * @return
     */
    @GetMapping("/lessons")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "闯关详情-课程列表", notes = "获取教材所有课程，用户解锁关卡和每关完成星数。根据locked状态显示，true表示未解锁")
    public R<List<UserLessonVO>> lessons(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "教材id", required = true) @RequestParam Integer textbookId
    ) {
        List<UserLessonVO> list = userLessonService.findAllLessonWithUser(textbookId, userId);
        return R.data(list);
    }


    /**
     * 闯关详情-统计
     *
     * @param userId
     * @param textbookId
     * @return
     */
    @GetMapping("/statistics")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "闯关详情-汉字星数统计", notes = "教材总汉字数、总星数，用户完成总汉字数、总星数")
    public R statistics(
            @ApiParam(value = "用户id", required = true) @RequestParam Integer userId,
            @ApiParam(value = "教材id", required = true) @RequestParam Integer textbookId
    ) {
        UserTextbookVO textbookVO = userTextbookService.findFinishedStars(textbookId, userId);
        UserTextbookVO vo = userTextbookService.findTextbookStars(textbookId);
        Integer finishedCharCount = 0;
        Integer finishedLessonCount = 0;
        Integer finishedStarCount = 0;
        Integer charCount = 0;
        Integer lessonCount = 0;
        Integer starCount = 0;
        if (textbookVO != null) {
            finishedCharCount = textbookVO.getFinishedCharCount();
            finishedLessonCount = textbookVO.getFinishedLessonCount();
            finishedStarCount = textbookVO.getFinishedStarCount();
            charCount = textbookVO.getCharCount();
            lessonCount = textbookVO.getLessonCount();
            starCount = textbookVO.getStarCount();
        }
        if (vo != null) {
            charCount = vo.getCharCount();
            lessonCount = vo.getLessonCount();
            starCount = vo.getStarCount();
        }
        Map<String, Integer> result = new HashMap<>(6);
        result.put("finishedCharCount", finishedCharCount);
        result.put("finishedLessonCount", finishedLessonCount);
        result.put("finishedStarCount", finishedStarCount);
        result.put("charCount", charCount);
        result.put("lessonCount", lessonCount);
        result.put("starCount", starCount);
        return R.data(result);
    }
}
