package cn.rzedu.sf.resource.controller;

import cn.rzedu.sf.resource.entity.Textbook;
import cn.rzedu.sf.resource.service.*;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import cn.rzedu.sf.resource.vo.TextbookVO;
import cn.rzedu.sf.resource.wrapper.TextbookWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 书法桌B端接口
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/calligraphy")
@Api(value = "书法B端接口 ", tags = "书法B端接口")
public class CalligraphyDeskController {

    private ICharacterService characterService;

    private ICharacterResourceService characterResourceService;

    private ICharacterBrushworkService characterBrushworkService;

    private ITextbookService textbookService;

    private ITextbookLessonService textbookLessonService;

    private IPublisherService publisherService;

    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 出版社列表
     * @param subject
     * @return
     */
    @GetMapping("/publisher/{subject}")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "出版社列表", notes = "根据科目显示出版社")
    public R publisherList(@ApiParam(value = "资源学科 71=软笔书法 72=硬笔书法", required = true)
                              @PathVariable(value = "subject") Integer subject
    ) {
        List<Map<String, Object>> list = publisherService.findBySubject(subject);
        return R.data(list);
    }

    /**
     * 教材列表
     */
    @GetMapping("/textbook/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "教材列表", notes = "根据学科、出版社查询")
    public R<List<TextbookVO>> textbookList(
            @ApiParam(value = "出版社") @RequestParam(value = "publisher", required = false) String publisher,
            @ApiParam(value = "教材的学科 71=软笔书法 72=硬笔书法") @RequestParam(value = "subject", required = false) Integer subject,
            @ApiParam(value = "是否含课程") @RequestParam(value = "includeLessons", defaultValue = "false") Boolean includeLessons) {
        Map<String, Object> textbook = new HashMap<>();
        textbook.put("publisher", publisher);
        textbook.put("subject", subject);
        List<Textbook> textbookList = textbookService.listByMap(textbook);
        if (textbookList == null || textbookList.isEmpty()) {
            return R.data(null);
        }
        List<TextbookVO> textbookVOList = TextbookWrapper.build().listVO(textbookList);
        //传入课程数据
        if (includeLessons) {
            List<TextbookLessonVO> lessonList = null;
            for (TextbookVO vo : textbookVOList) {
                lessonList = textbookLessonService.findLessonByTextbookId(vo.getId());
                vo.setTextbookLessonVOList(lessonList);
            }
        }
        return R.data(textbookVOList);
    }

    /**
     * 教材课程详情
     */
    @GetMapping("/textbook/detail/{textbookId}")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "教材课程详情", notes = "传入textbookId")
    public R<TextbookVO> textbookDetail(@ApiParam(value = "教材id", required = true) @PathVariable(value = "textbookId") Integer textbookId) {
        TextbookVO textbookVO = textbookService.findTextBookByIdWithUnit(textbookId);
        return R.data(textbookVO);
    }

    /**
     * 软硬笔资源包
     * @param characterId
     * @return
     */
    @GetMapping("/character/resource/{subject}/{characterId}")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "软硬笔资源包", notes = "软硬笔资源包")
    public R resourceBag(
            @ApiParam(value = "源学科 71=软笔书法 72=硬笔书法", required = true) @PathVariable(value = "subject") Integer subject,
            @ApiParam(value = "汉字id", required = true) @PathVariable(value = "characterId") Integer characterId,
            @ApiParam(value = "字体") @RequestParam(value = "font", required = false, defaultValue = "") String font
    ) {
        String key = "resource-bag-#" + characterId + "_" + subject + "#" + font;
        Object result = redisTemplate.opsForValue().get(key);
        if (result == null) {
            Map<String, Object> map = characterResourceService.findResources(characterId, subject, font);
            redisTemplate.opsForValue().set(key, map, 2, TimeUnit.HOURS);
            result = map;
        }
        return R.data(result);
    }

    /**
     * 删除软硬笔资源包缓存
     */
    @GetMapping("/character/resource/removeCache/{subject}/{characterId}")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "删除软硬笔资源包缓存", notes = "删除软笔资源缓存")
    public R removeResourceCache(
            @ApiParam(value = "源学科 71=软笔书法 72=硬笔书法", required = true) @PathVariable(value = "subject") Integer subject,
            @ApiParam(value = "汉字id", required = true) @PathVariable(value = "characterId") Integer characterId,
            @ApiParam(value = "字体") @RequestParam(value = "font", required = false, defaultValue = "") String font) {
        String key = "resource-bag-#" + characterId + "_" + subject + "#" + font;
        redisTemplate.delete(key);
        return R.success("删除缓存成功，characterId：" + characterId + "_" + subject + "#" + font);
    }


    /**
     * 标准笔法和基本笔画
     */
    @GetMapping("/character/resource/basic")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "标准笔法和基本笔画", notes = "标准笔法和基本笔画")
    public R resourceBagBasic(
            @ApiParam(value = "字体") @RequestParam(value = "font", required = false, defaultValue = "") String font
    ) {
        String key = "resource-bag-basic-#" + font;
        Object result = redisTemplate.opsForValue().get(key);
        if (result == null) {
            Map<String, Object> map = characterBrushworkService.findBrushwork(font);
            redisTemplate.opsForValue().set(key, map, 2, TimeUnit.HOURS);
            result = map;
        }
        return R.data(result);
    }

    /**
     * 删除标准笔法和基本笔画缓存
     */
    @GetMapping("/character/resource/removeCache/basic")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "删除标准笔法和基本笔画缓存", notes = "删除标准笔法和基本笔画缓存")
    public R removeResourceCacheBasic(
            @ApiParam(value = "字体") @RequestParam(value = "font", required = false, defaultValue = "") String font) {
        String key = "resource-bag-basic-#" + font;
        redisTemplate.delete(key);
        return R.success("删除缓存成功，字体：#" + font);
    }


}
