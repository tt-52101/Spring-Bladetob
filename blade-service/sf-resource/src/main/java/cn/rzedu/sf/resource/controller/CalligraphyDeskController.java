package cn.rzedu.sf.resource.controller;

import cn.rzedu.sf.resource.bo.CharacterResourceBO;
import cn.rzedu.sf.resource.bo.FileData;
import cn.rzedu.sf.resource.entity.Font;
import cn.rzedu.sf.resource.entity.Textbook;
import cn.rzedu.sf.resource.service.*;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import cn.rzedu.sf.resource.vo.TextbookVO;
import cn.rzedu.sf.resource.wrapper.TextbookWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.common.tool.WeChatUtil;
import org.springblade.core.tool.api.R;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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

    private final static Logger logger = LoggerFactory.getLogger(CalligraphyDeskController.class);

    private ICharacterService characterService;

    private ICharacterResourceService characterResourceService;

    private ICharacterResourceFileService characterResourceFileService;

    private ICharacterBrushworkService characterBrushworkService;

    private ITextbookService textbookService;

    private ITextbookLessonService textbookLessonService;

    private IPublisherService publisherService;

    private IFontService fontService;

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
                              @PathVariable(value = "subject") Integer subject,
                           @ApiParam(value = "适合学段", required = false, defaultValue = "2") @RequestParam(value = "stageCode", required = false, defaultValue = "2") String stageCode
    ) {
        List<Map<String, Object>> list = publisherService.findBySubjectAndStageCode(subject, stageCode);
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
            @ApiParam(value = "学段 2=小学 3=初中") @RequestParam(value = "stage", required = false, defaultValue = "2") String stage,
            @ApiParam(value = "是否含课程") @RequestParam(value = "includeLessons", defaultValue = "false") Boolean includeLessons) {
        Textbook text = new Textbook();
        text.setPublisher(publisher);
        text.setSubject(subject);
        text.setStageCode(stage);
        QueryWrapper<Textbook> query = Wrappers.query(text);
        query.orderByAsc("grade_code");
        query.orderByAsc("volume");
        List<Textbook> textbookList = textbookService.list(query);
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
     * 单个课程详情
     */
    @GetMapping("/lesson/detail/{lessonId}")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "单个课程详情", notes = "传入lessonId")
    public R lessonDetail(@ApiParam(value = "课程id", required = true) @PathVariable(value = "lessonId") Integer lessonId) {
        Map<String, Object> map = textbookLessonService.findLessonById(lessonId);
        return R.data(map);
    }

    /**
     * 软硬笔资源包
     * @param characterId
     * @return
     */
    @GetMapping("/character/resource/{subject}/{characterId}")
    @ApiOperationSupport(order = 5)
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
     * 新增或修改汉字资源文件
     */
    @SneakyThrows
    @PostMapping("/saveOrUpdate")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "书法B端新增或修改汉字资源文件", notes = "参数都是必填")
    @Transactional(rollbackFor=Exception.class)
    public R saveOrUpdate(@Valid @RequestBody CharacterResourceBO characterResourceBO) {
        System.out.println(characterResourceBO);
        Integer characterId = characterResourceBO.getCharacterId();
        String font = characterResourceBO.getFont();
        Integer subject = characterResourceBO.getSubject();
        List<FileData> fileDatas = characterResourceBO.getFileDatas();
        
        if(fileDatas != null){
            for (FileData fileData : fileDatas) {
                boolean result = false;

                String objectId = fileData.getObjectId();
                Integer resourceType = fileData.getResourceType();
                String objectValue = fileData.getObjectValue();

                if(objectId == null || "".equals(objectId) || objectValue == null || "".equals(objectValue) || resourceType == null || resourceType == 0){
                    return R.fail("数据错误：" + fileData);
                }

                if(subject == 71){
                    result = characterResourceService.createSoftResourceFile(characterId, resourceType, font, objectId, objectValue);
                } else if(subject == 72){
                    result = characterResourceService.createHardResourceFile(characterId, resourceType, font, objectId, objectValue);
                }

                if(!result){
                    throw new Exception("资源编辑错误");
                }
            }
        }

        return R.status(true);
    }

    /**
     * 生成并下载 小程序码
     */
    @PostMapping("/xcx/createQRCode")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "生成并下载二维码", notes = "生成并下载二维码")
    public byte[] createQRCode(@ApiIgnore HttpServletResponse response,
                         @ApiParam(value = "资源学科  71=软笔书法 72=硬笔书法", required = true) @RequestParam(value = "subject") Integer subject,
                         @ApiParam(value = "二维码类型  1=教材 2=课程 3=汉字", required = true) @RequestParam(value = "type") Integer type,
                         @ApiParam(value = "字体 type=3:必填", required = true) @RequestParam(value = "font", required = false) String font,
                         @ApiParam(value = "对应ID type=1:教材ID type=2:课程ID type=3:汉字ID ", required = true) @RequestParam(value = "objectId") Integer objectId) throws Exception {

        byte[] image = null;
        Integer sort = null;
        Map<String,Object> map = new HashMap<String,Object>();

        if(type == 1){ //教材
            if(subject == 71){
                sort = 1;
            } else if(subject == 72){
                sort = 2;
            }
            String scene = "type=" + sort + "&id=" + objectId;

            map.put("scene", scene);
            map.put("page", "pages/index/index");
            image = WeChatUtil.createQRCode(map);
        } else if(type == 2){ //课程
            if(subject == 71){
                sort = 3;
            } else if(subject == 72){
                sort = 4;
            }
            String scene = "type=" + sort + "&id=" + objectId;
            map.put("scene", scene);
            map.put("page", "pages/index/index");
            image = WeChatUtil.createQRCode(map);
        } else if(type == 3){ //汉字
            if(font == null || "".equals(font)){
                logger.error("生成并下载二维码异常：font不能为空");
                return null;
            }
            if(subject == 71){
                sort = 5;
            } else if(subject == 72){
                sort = 6;
            }
            Integer fontType = getFontType(font);
            String scene = "type=" + sort + "&id=" + objectId + "&fontType=" + fontType;
            System.out.println(scene);
            map.put("scene", scene);
            map.put("page", "pages/index/index");
            image = WeChatUtil.createQRCode(map);
//            ByteToFile(image);
        } else {
            logger.error("生成并下载二维码异常：二维码类型错误");
        }
        return image;
    }

    private Integer getFontType(String name){
        Integer fontType = null;
        QueryWrapper<Font> fontWrapper = new QueryWrapper<Font>();
        fontWrapper.eq("name", name);
        fontWrapper.eq("is_deleted", 0);
        Font font = fontService.getOne(fontWrapper);
        if(font != null){
            fontType = font.getId();
        }
        return fontType;
    }

    private static void ByteToFile(byte[] bytes)throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        BufferedImage bi1 =ImageIO.read(bais);
        try {
            File w2 = new File("C:\\Users\\80969\\Desktop\\test.jpg");//可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            bais.close();
        }
    }

    /**
     * 标准笔法和基本笔画
     */
    @GetMapping("/character/resource/basic")
    @ApiOperationSupport(order = 7)
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
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "删除标准笔法和基本笔画缓存", notes = "删除标准笔法和基本笔画缓存")
    public R removeResourceCacheBasic(
            @ApiParam(value = "字体") @RequestParam(value = "font", required = false, defaultValue = "") String font) {
        String key = "resource-bag-basic-#" + font;
        redisTemplate.delete(key);
        return R.success("删除缓存成功，字体：#" + font);
    }

    /**
     * 出版社教材列表
     */
    @GetMapping("/publisher/textbook")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "出版社教材列表", notes = "先按软硬笔分组，再按出版社分组显示")
    public R publisherTextbookList() {
        List list = textbookService.findAllTextbook();
        return R.data(list);
    }

    /**
     * 教材所有课程
     */
    @GetMapping("/lesson/list")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "教材所有课程", notes = "根据教材id获取所有课程")
    public R lessonList(@ApiParam(value = "教材id", required = true) @RequestParam(value = "textbookId") Integer textbookId) {
        List<Map<String, Object>> list = textbookLessonService.findAllLessonByTextbook(textbookId);
        return R.data(list);
    }

	/**
	 * 软硬笔资源包 返回 json 格式
	 * @param characterId
	 * @return
	 */
	@GetMapping("/character/resource/json/{subject}/{characterId}")
	public R resourceBagByJson(
			@ApiParam(value = "源学科 71=软笔书法 72=硬笔书法", required = true) @PathVariable(value = "subject") Integer subject,
			@ApiParam(value = "汉字id", required = true) @PathVariable(value = "characterId") Integer characterId,
			@ApiParam(value = "字体") @RequestParam(value = "font", required = false, defaultValue = "") String font
	) {
		Map<String, Object> map = characterResourceService.findResourcesByJson(characterId, subject, font);
		return R.data(map);
	}
}
