package cn.rzedu.sf.resource.controller;

import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.entity.CharacterResourceFile;
import cn.rzedu.sf.resource.entity.TextbookLessonCharacter;
import cn.rzedu.sf.resource.service.*;
import cn.rzedu.sf.resource.vo.CharacterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springblade.common.tool.ExcelUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.api.ResultCode;
import org.springblade.core.tool.utils.Func;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.rzedu.sf.resource.entity.CharacterResource;
import cn.rzedu.sf.resource.vo.CharacterResourceVO;
import cn.rzedu.sf.resource.wrapper.CharacterResourceWrapper;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 汉字资源 控制器
 *
 * @author Blade
 * @since 2020-08-05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/characterResource")
@Api(value = "汉字资源 ", tags = "汉字资源")
public class CharacterResourceController extends BladeController {

    private ICharacterResourceService characterResourceService;

    private ICharacterService characterService;

    private ITextbookLessonCharacterService textbookLessonCharacterService;

    private ICharacterResourceFileService characterResourceFileService;

    private ICharacterBrushworkService characterBrushworkService;

    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 软/硬笔资源
     * @param query
     * @return
     */
    @GetMapping("/list/{subject}")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "软/硬笔资源", notes = "字典汉字的软硬笔资源数")
    public R<IPage<CharacterVO>> list(
            @ApiParam(value = "资源学科 71=软笔书法 72=硬笔书法", required = true) @PathVariable(value = "subject") Integer subject,
            @ApiParam(value = "是否有关联资源") @RequestParam(value = "hasResource", required = false) Integer hasResource,
            @ApiParam(value = "关键字") @RequestParam(value = "keyword", required = false) String keyword,
            Query query) {
        CharacterVO characterVO = new CharacterVO();
        characterVO.setSubject(subject);
        characterVO.setHasResource(hasResource);
        characterVO.setKeyword(keyword);
        IPage<CharacterVO> pages =
                characterService.findCharacterVoWithVisitedCount(Condition.getPage(query), characterVO);
        return R.data(pages);
    }

    /**
     * 软/硬笔资源-单字
     */
    @GetMapping("/list/{subject}/{characterId}")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "软/硬笔资源-单字", notes = "根据汉字ID获取所属的资源")
    public R listOfSingleChar(
            @ApiParam(value = "资源学科 71=软笔书法 72=硬笔书法", required = true) @PathVariable(value = "subject") Integer subject,
            @ApiParam(value = "汉字ID", required = true) @PathVariable(value = "characterId") Integer characterId,
            Query query) {
        Map<String, Object> result = new HashMap<>();
        Character character = characterService.getById(characterId);
        if (character != null) {
            result.put("charS", character.getCharS());
            result.put("charT", character.getCharT());
            result.put("keyword", character.getKeyword());
        }

        CharacterResourceVO characterResource = new CharacterResourceVO();
        characterResource.setCharacterId(characterId);
        characterResource.setSubject(subject);
        IPage<CharacterResourceVO> pages =
                characterResourceService.selectCharacterResourcePage(Condition.getPage(query), characterResource);
        result.put("list", pages);
        return R.data(result);
    }


    /**
     * 根据id获取详情
     */
    @GetMapping("/detail/{id}")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "资源详情", notes = "传入id获取详情")
    public R<CharacterResourceVO> detail(
            @ApiParam(value = "主键", required = true) @PathVariable(value = "id") Integer id) {
        CharacterResource detail = characterResourceService.getById(id);
        CharacterResourceVO resourceVO = CharacterResourceWrapper.build().entityVO(detail);
        if (detail != null) {
            List<CharacterResourceFile> list = characterResourceFileService.findByResourceId(detail.getId());
            resourceVO.setFileList(list);
        }
        return R.data(resourceVO);
    }



    /**
     * 新增或修改汉字资源文件
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "新增或修改汉字资源文件", notes = "根据有无id值判断是新增还是修改，" +
            "必填项：characterId,nameTr,keyword,resourceType,subject。fileList必填项：font,object_id，object_type，content或uuid")
    public R saveOrUpdate(@Valid @RequestBody CharacterResourceVO characterResourceVO) {
        Integer characterId = characterResourceVO.getCharacterId();
        //判断汉字是否存在
        Character character = characterService.getById(characterId);
        if (character == null) {
            return R.success(ResultCode.FAILURE, "找不到汉字，无法新增资源");
        }
        Integer id = characterResourceVO.getId();
        if (id != null) {
            characterResourceService.updateById(characterResourceVO);
        } else {
            characterResourceVO.setCharS(character.getCharS());
            characterResourceVO.setCharT(character.getCharT());
            boolean status = characterResourceService.save(characterResourceVO);
            //修改汉字资源数
            if (status) {
                characterService.updateResCount(characterId);
            }
            id = characterResourceVO.getId();
        }

        if (id != null) {
            List<CharacterResourceFile> fileList = characterResourceVO.getFileList();
            if (fileList != null && !fileList.isEmpty()) {
                CharacterResourceFile crf = null;
                LocalDateTime now = LocalDateTime.now();
                for (CharacterResourceFile file : fileList) {
                    if (file.getId() != null) {
                        characterResourceFileService.updateById(file);
                    } else {
                        //id不传，再根据resourceId和objectId判断唯一
                        CharacterResourceFile union =
                                characterResourceFileService.findUnionByResourceIdAndObjectId(id, file.getObjectId(), file.getFont());
                        if (union != null) {
                            file.setId(union.getId());
                            characterResourceFileService.updateById(file);
                        } else {
                            crf = new CharacterResourceFile();
                            crf.setResourceId(id);
                            crf.setCharacterId(characterId);
                            crf.setSubject(characterResourceVO.getSubject());
                            crf.setResourceType(characterResourceVO.getResourceType());
                            crf.setFont(file.getFont());
                            crf.setObjectId(file.getObjectId());
                            crf.setObjectType(file.getObjectType());
                            crf.setContent(file.getContent());
                            crf.setUuid(file.getUuid());
                            crf.setCreateDate(now);
                            characterResourceFileService.save(crf);
                        }
                    }
                }
            }
        }
        return R.status(true);
    }


    /**
     * 修改启用状态
     * 统一类型资源下，只能同时启用一个
     * 先获取启用状态，修改为相反的；若改为启用时，需改变其他数据为未启用
     * @return
     */
    @PostMapping("/enable")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "修改启用状态", notes = "根据id修改启用状态，同一汉字同类型资源下，只能同时启用一个")
    public R updateEnable(@ApiParam(value = "主键", required = true) @RequestParam Integer id) {
        CharacterResource resource = characterResourceService.getById(id);
        if (resource == null) {
            return R.success(ResultCode.FAILURE, "资源不存在，无法执行操作");
        }
        Boolean enabled = resource.getEnabled();
        if (enabled == null) {
            enabled = true;
        }
        //原状态为启用，改为未启用；原状态为未启用，需先将其他资源都改为未启用
        if (!enabled) {
            characterResourceService.updateDisable(resource.getCharacterId(), resource.getSubject(), resource.getResourceType());
        }
        CharacterResource cr = new CharacterResource();
        cr.setId(id);
        cr.setEnabled(!enabled);
        boolean status = characterResourceService.updateById(cr);
        return R.status(status);
    }

    /**
     * 删除 汉字资源
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        List<CharacterResource> list = characterResourceService.listByIds(Func.toLongList(ids));
        boolean status = characterResourceService.removeByIds(Func.toLongList(ids));
        if (status) {
            list.forEach(characterResource -> characterService.updateResCount(characterResource.getCharacterId()));
        }
        return R.status(status);
    }


    /**
     * 软硬笔资源包
     * @param characterId
     * @return
     */
    @GetMapping("/bag/{subject}/{characterId}")
    @ApiOperationSupport(order = 9)
//    @Cacheable(cacheNames = "resource-bag", key = "T(String).valueOf(#characterId).concat('_').concat(#subject)")
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
    @GetMapping("/remove-resource-cache/{subject}/{characterId}")
    @ApiOperationSupport(order = 10)
//    @CacheEvict(cacheNames = "resource-bag", key = "T(String).valueOf(#characterId).concat('_').concat(#subject)")
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
    @GetMapping("/bag/basic")
    @ApiOperationSupport(order = 11)
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
    @GetMapping("/remove-resource-cache/basic")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "删除标准笔法和基本笔画缓存", notes = "删除标准笔法和基本笔画缓存")
    public R removeResourceCacheBasic(
            @ApiParam(value = "字体") @RequestParam(value = "font", required = false, defaultValue = "") String font) {
        String key = "resource-bag-basic-#" + font;
        redisTemplate.delete(key);
        return R.success("删除缓存成功，字体：#" + font);
    }


    /**
     * 软笔观察、分析、笔法资源
     */
    @GetMapping("/softBag")
    @ApiOperationSupport(order = 14)
    @ApiOperation(value = "软笔观察、分析、笔法资源", notes = "软笔观察、分析、笔法资源" +
            "<br/>observation_1:观察; analysis_2:分析; writing_3:笔法; recognition_0:认读" +
            "<br/>spell:拼音; white_character:黑底白字图; tablet:源碑文; observe_dot:点-米字图; observe_arrow:箭头-米字图; observe_triangle:三角-米字图; " +
            "<br/>analyse_image:字体分析图; stroke_text:笔画特征文字; stroke_audio:笔画特征音频; space_text:空间特征文字; space_audio:空间特征音频; " +
            "<br/>technique_line:行笔路线视频; technique_gesture:提案笔势视频;" +
            "<br/>spell:读音; simple:简体; radical:部首; stroke_number:笔画; usage_text:用法文字; usage_audio:用法音频; ")
    public R softResource(
            @ApiParam(value = "汉字id", required = true) @RequestParam(value = "characterId") Integer characterId,
            @ApiParam(value = "字体", required = true) @RequestParam(value = "font") String font
    ) {
        String key = "soft-bag-#" + characterId + "#" + font;
        Object result = redisTemplate.opsForValue().get(key);
        if (result == null) {
            Map<String, Object> map = characterResourceService.findSoftResource(characterId, font);
            redisTemplate.opsForValue().set(key, map, 2, TimeUnit.HOURS);
            result = map;
        }
        return R.data(result);
    }

    /**
     * 删除软笔观察、分析、笔法资源缓存
     */
    @GetMapping("/remove-softBag-cache")
    @ApiOperationSupport(order = 15)
    @ApiOperation(value = "删除软笔观察、分析、笔法资源缓存", notes = "删除软笔资源缓存")
    public R removeSoftResourceCache(
            @ApiParam(value = "汉字id", required = true) @RequestParam(value = "characterId") Integer characterId,
            @ApiParam(value = "字体") @RequestParam(value = "font") String font) {
        String key = "soft-bag-#" + characterId + "#" + font;
        redisTemplate.delete(key);
        return R.success("删除缓存成功，characterId：" + characterId + "#" + font);
    }

    /**
     * 硬笔认读资源
     */
    @GetMapping("/hardBag")
    @ApiOperationSupport(order = 16)
    @ApiOperation(value = "硬笔认读资源", notes = "硬笔认读资源" +
            "<br/>recognition_0:认读" +
            "<br/>spell:读音; simple:简体; radical:部首; stroke_number:笔画; paraphrase_text:释义文字; paraphrase_video:释义音频; ")
    public R hardResource(
            @ApiParam(value = "汉字id", required = true) @RequestParam(value = "characterId") Integer characterId,
            @ApiParam(value = "字体", required = true) @RequestParam(value = "font", defaultValue = "楷书") String font
    ) {
        String key = "hard-bag-#" + characterId + "#" + font;
        Object result = redisTemplate.opsForValue().get(key);
        if (result == null) {
            Map<String, Object> map = characterResourceService.findHardResource(characterId, font);
            redisTemplate.opsForValue().set(key, map, 2, TimeUnit.HOURS);
            result = map;
        }
        return R.data(result);
    }

    /**
     * 删除硬笔认读资源缓存
     */
    @GetMapping("/remove-hardBag-cache")
    @ApiOperationSupport(order = 17)
    @ApiOperation(value = "删除硬笔认读资源缓存", notes = "删除硬笔认读资源缓存")
    public R removeHardResourceCache(
            @ApiParam(value = "汉字id", required = true) @RequestParam(value = "characterId") Integer characterId,
            @ApiParam(value = "字体") @RequestParam(value = "font", defaultValue = "楷书") String font) {
        String key = "hard-bag-#" + characterId + "#" + font;
        redisTemplate.delete(key);
        return R.success("删除缓存成功，characterId：" + characterId + "#" + font);
    }

    /**
     * 单字视频资源
     */
    @GetMapping("/video")
    @ApiOperationSupport(order = 18)
    @ApiOperation(value = "单字视频资源", notes = "根据汉字、字体获取视频资源" +
            "<br/>字体类型：1=楷书;2=颜勤;3=赵体;4=柳体;5=颜多;6=欧体;7=现代简楷;8=字体1;")
    public R videoResource(
            @ApiParam(value = "汉字id", required = true) @RequestParam(value = "characterId") Integer characterId,
            @ApiParam(value = "字体类型", required = true) @RequestParam(value = "font") Integer fontType,
            @ApiParam(value = "软硬笔类型 1=软笔 2=硬笔") @RequestParam(value = "subject", required = false) Integer subject
    ) {
        String font = "";
        switch (fontType){
            case 1: font = "楷书"; break;
            case 2: font = "颜勤"; break;
            case 3: font = "赵体"; break;
            case 4: font = "柳体"; break;
            case 5: font = "颜多"; break;
            case 6: font = "欧体"; break;
            case 7: font = "现代简楷"; break;
            case 8: font = "字体1"; break;
        }
        if (subject != null) {
            switch (subject) {
                case 1: subject = 71; break;
                case 2: subject = 72; break;
            }
        }
        Map<String, Object> map = characterResourceService.findCharVideoResource(characterId, font, subject);
        List<Map<String, Object>> list = new ArrayList();
        list.add(map);
        return R.data(list);
    }
}
