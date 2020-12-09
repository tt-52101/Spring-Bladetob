package cn.rzedu.sf.user.controller;

import cn.rzedu.sf.resource.entity.LessonStarConfig;
import cn.rzedu.sf.resource.entity.Textbook;
import cn.rzedu.sf.resource.entity.TextbookLesson;
import cn.rzedu.sf.resource.entity.TextbookLessonCharacter;
import cn.rzedu.sf.resource.feign.ITextbookClient;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import cn.rzedu.sf.user.service.IUserCharacterService;
import cn.rzedu.sf.user.service.IUserLessonService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * feign远程调用测试
 * 控制器
 *
 * @author Blade
 * @since 2020-08-15
 */
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping("/user/test")
@Api(value = " 远程调用测试 ", tags = " 远程调用测试")
public class TestFeignController {

    private ITextbookClient textbookClient;

    private IUserLessonService userLessonService;

    private IUserCharacterService userCharacterService;

    /**
     * 获取单个教材详情
     */
    @GetMapping("/textbook/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取单个教材详情", notes = "获取单个教材详情")
    public R testTextbookDetail(@ApiParam(value = "教材id", required = true) @RequestParam Integer textbookId) {
        R<Textbook> textbook = textbookClient.detail(textbookId);
        return textbook;
    }

    /**
     * 获取教材所有课程
     * @param textbookId
     * @return
     */
    @GetMapping("/lessons")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取教材所有课程", notes = "获取教材所有课程")
    R<List<TextbookLessonVO>> allLessons(@ApiParam(value = "教材id", required = true) @RequestParam Integer textbookId){
        return textbookClient.allLessons(textbookId);
    }

    /**
     * 获取单个课程详情
     * @param lessonId
     * @return
     */
    @GetMapping("/lesson/detail")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "获取单个课程详情", notes = "获取单个课程详情")
    R<TextbookLesson> lessonDetail(@ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId){
        return textbookClient.lessonDetail(lessonId);
    }


    /**
     * 获取课程所有汉字
     * @param lessonId
     * @return
     */
    @GetMapping("/lesson/chars")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "获取课程所有汉字", notes = "获取课程所有汉字")
    R<List<TextbookLessonCharacter>> allLessonCharacters(
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId
    ){
        return textbookClient.allLessonCharacters(lessonId);
    }


    /**
     * 获取单个课程汉字
     * @param lessonId
     * @Param characterId
     * @return
     */
    @GetMapping("/lesson/char/detail")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "获取单个课程汉字", notes = "获取单个课程汉字")
    R<TextbookLessonCharacter> lessonCharacterDetail(
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId,
            @ApiParam(value = "汉字id", required = true) @RequestParam Integer characterId
    ) {
        return textbookClient.lessonCharacterDetail(lessonId, characterId);
    }

    /**
     * 课程星数配置
     * @param lessonId
     * @return
     */
    @GetMapping("/lesson/star")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "课程星数配置", notes = "课程星数配置")
    R<LessonStarConfig> lessonStar(@ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId) {
        return textbookClient.lessonStar(lessonId);
    }

    /**
     * 更新汉字资源访问量，自动+1
     * @param textbookId
     * @param lessonId
     * @param characterId
     * @return
     */
    @GetMapping("/visitor/update")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "更新汉字资源访问量", notes = "更新汉字资源访问量")
    R updateCharVisitedCount(
            @ApiParam(value = "教材id", required = true) @RequestParam Integer textbookId,
            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId,
            @ApiParam(value = "汉字id", required = true) @RequestParam Integer characterId
    ) {
        return textbookClient.updateCharVisitedCount(textbookId, lessonId, characterId);
    }

//    @GetMapping("/finished")
//    @ApiOperationSupport(order = 8)
//    @ApiOperation(value = "测试汉字完成数", notes = "测试汉字完成数")
//    R finishedCount(
//            @ApiParam(value = "教材id", required = true) @RequestParam Integer textbookId,
//            @ApiParam(value = "课程id", required = true) @RequestParam Integer lessonId
//    ) {
//        int n1 = userCharacterService.findFinishedCharCountOfLesson(lessonId, 1);
//        int n2 = userCharacterService.findFinishedCharCountOfTextbook(textbookId, 1);
//        int n3 = userLessonService.findFinishedLessonCountOfTextbook(textbookId, 1);
//        Map<String, Object> result = new HashMap<>(3);
//        result.put("cl", n1);
//        result.put("ct", n2);
//        result.put("lt", n3);
//        return R.data(result);
//    }
}
