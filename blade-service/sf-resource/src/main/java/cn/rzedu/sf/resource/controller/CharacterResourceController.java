package cn.rzedu.sf.resource.controller;

import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.entity.CharacterResourceFile;
import cn.rzedu.sf.resource.entity.TextbookLessonCharacter;
import cn.rzedu.sf.resource.service.ICharacterResourceFileService;
import cn.rzedu.sf.resource.service.ICharacterService;
import cn.rzedu.sf.resource.service.ITextbookLessonCharacterService;
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
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.rzedu.sf.resource.entity.CharacterResource;
import cn.rzedu.sf.resource.vo.CharacterResourceVO;
import cn.rzedu.sf.resource.wrapper.CharacterResourceWrapper;
import cn.rzedu.sf.resource.service.ICharacterResourceService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 新增 汉字资源
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入characterResource，必填项：characterId,nameTr,keyword,resourceType,视频文件")
    public R save(@Valid @RequestBody CharacterResource characterResource) {
        Integer characterId = characterResource.getCharacterId();
        //判断汉字是否存在
        Character character = characterService.getById(characterId);
        if (character == null) {
            return R.success(ResultCode.FAILURE, "找不到汉字，无法新增资源");
        }
//        //判断资源标题是否重复
//        boolean isExist = characterResourceService.judgeRepeatByName(characterId,
//                characterResource.getNameTr(), characterResource.getSubject(), null);
//        if (isExist) {
//            return R.success(ResultCode.PARAM_VALID_ERROR, "资源标题已有，不能重复");
//        }
        characterResource.setCharS(character.getCharS());
        characterResource.setCharT(character.getCharT());
        characterResource.setResourceType(1);
        boolean status = characterResourceService.save(characterResource);
        //修改汉字资源数
        if (status) {
            characterService.updateResCount(characterId);

            //将资源放入resource_file
            CharacterResourceFile crf = new CharacterResourceFile();
            crf.setResourceId(characterResource.getId());
            crf.setCharacterId(characterId);
            crf.setSubject(characterResource.getSubject());
            crf.setResourceType(1);
            crf.setObjectId("writing");
            crf.setObjectType("video");
            crf.setUuid(characterResource.getVideoId());
            crf.setCreateDate(LocalDateTime.now());
            characterResourceFileService.save(crf);
        }
        return R.status(status);
    }

    /**
     * 修改 汉字资源
     */
    @ApiIgnore
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入characterResource，必填项：id,characterId,nameTr,keyword,resourceType,视频文件")
    public R update(@Valid @RequestBody CharacterResource characterResource) {
        boolean isExist = characterResourceService.judgeRepeatByName(characterResource.getCharacterId(),
                characterResource.getNameTr(), characterResource.getSubject(), characterResource.getId());
        if (isExist) {
            return R.success(ResultCode.PARAM_VALID_ERROR, "资源标题已有，不能重复");
        }
        return R.status(characterResourceService.updateById(characterResource));
    }

    /**
     * 新增或修改汉字资源文件
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "新增或修改汉字资源文件", notes = "根据有无id值判断是新增还是修改，" +
            "必填项：characterId,nameTr,keyword,resourceType,subject。fileList必填项：object_id，object_type，content或uuid")
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
                        CharacterResourceFile union = characterResourceFileService.findUnionByResourceIdAndObjectId(id, file.getObjectId());
                        if (union != null) {
                            file.setId(union.getId());
                            characterResourceFileService.updateById(file);
                        } else {
                            crf = new CharacterResourceFile();
                            crf.setResourceId(id);
                            crf.setCharacterId(characterId);
                            crf.setSubject(characterResourceVO.getSubject());
                            crf.setResourceType(characterResourceVO.getResourceType());
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
     * 批量导出二维码 软/硬笔资源
     * @return
     */
    @GetMapping("/export/{subject}")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "批量导出二维码", notes = "批量导出软/硬笔资源的二维码，有多选就只导出已选的，没有就导出全部（筛选条件下的全部）")
    public void export(
            @ApiParam(value = "资源学科 71=软笔书法 72=硬笔书法", required = true) @PathVariable(value = "subject") Integer subject,
            @ApiParam(value = "是否有关联资源") @RequestParam(value = "hasResource", required = false) Integer hasResource,
            @ApiParam(value = "关键字") @RequestParam(value = "keyword", required = false) String keyword,
            @ApiParam(value = "主键id，多个用逗号隔开，如：1,2,3") @RequestParam(value = "ids", required = false) String ids,
            HttpServletResponse response, HttpServletRequest request
    ) {
        CharacterVO characterVO = new CharacterVO();
        characterVO.setSubject(subject);
        characterVO.setHasResource(hasResource);
        characterVO.setKeyword(keyword);
        List<CharacterVO> list = characterService.findWithVisitedCountByIds(ids, characterVO);

        int size = 0;
        if (list != null && !list.isEmpty()) {
            size = list.size();
        }
        String ryb = "";
        if (subject == 71) {
            ryb = "软笔";
        } else if (subject == 72) {
            ryb = "硬笔";
        }

        String fileName = ryb + "资源二维码.xls";
        String sheetName = ryb + "资源二维码";
        String[] title = {"简体字", "繁体字", "关键字", "资源", "访问量", "二维码"};
        String[][] content = new String[size][title.length];

        if (list != null) {
            CharacterVO vo = null;
            for (int i = 0; i < list.size(); i++) {
                vo = list.get(i);
                content[i][0] = vo.getCharS();
                content[i][1] = vo.getCharT();
                content[i][2] = vo.getKeyword();
                if (subject == 71) {
                    content[i][3] = String.valueOf(vo.getSoftResCount());
                    content[i][4] = String.valueOf(vo.getSoftResVisitedCount());
                    content[i][5] = vo.getSoftErCode();
                } else if (subject == 72) {
                    content[i][3] = String.valueOf(vo.getHardResCount());
                    content[i][4] = String.valueOf(vo.getHardResVisitedCount());
                    content[i][5] = vo.getHardErCode();
                }
            }
        }
        ExcelUtil.exportExcel(fileName, sheetName, title, content, response, request);

    }


    /**
     * 软笔观察、分析、笔法资源
     */
    @GetMapping("/soft/{characterId}")
    @ApiOperationSupport(order = 9)
    @Cacheable(cacheNames = "soft-resource", key = "#characterId")
    @ApiOperation(value = "软笔观察、分析、笔法资源", notes = "软笔观察、分析、笔法资源")
    public R softResource(
            @ApiParam(value = "汉字id", required = true) @PathVariable(value = "characterId") Integer characterId) {
        Map<String, Object> map = characterResourceService.findSoftResource(characterId);
        return R.data(map);
    }

    /**
     * 删除软笔资源缓存
     */
    @GetMapping("/remove-resource-cache/{characterId}")
    @ApiOperationSupport(order = 10)
    @CacheEvict(cacheNames = "soft-resource", key = "#characterId")
    @ApiOperation(value = "删除软笔资源缓存", notes = "删除软笔资源缓存")
    public R removeResourceCache(@ApiParam(value = "汉字id", required = true) @PathVariable(value = "characterId") Integer characterId) {
        return R.success("删除缓存成功，characterId：" + characterId);
    }

    /**
     * 软笔观察、分析、笔法资源(整个课程所有汉字)
     */
    @GetMapping("/soft/list/{lessonId}")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "软笔观察、分析、笔法资源(整个课程所有汉字)", notes = "软笔观察、分析、笔法资源(整个课程所有汉字)")
    public R softResourceList(
            @ApiParam(value = "课程id", required = true) @PathVariable(value = "lessonId") Integer lessonId) {
        List<Map<String, Object>> list = new ArrayList<>();

        List<TextbookLessonCharacter> characterList = textbookLessonCharacterService.findByLessonId(lessonId);
        Map<String, Object> map = null;
        if (characterList != null && !characterList.isEmpty()) {
            for (TextbookLessonCharacter tlc : characterList) {
                map = characterResourceService.findSoftResource(tlc.getCharacterId());
                list.add(map);
            }

        }
        return R.data(list);
    }
}
