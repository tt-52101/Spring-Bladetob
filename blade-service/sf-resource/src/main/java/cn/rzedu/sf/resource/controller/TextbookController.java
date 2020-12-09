package cn.rzedu.sf.resource.controller;

import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.entity.TextbookLesson;
import cn.rzedu.sf.resource.entity.TextbookLessonCharacter;
import cn.rzedu.sf.resource.service.ICharacterService;
import cn.rzedu.sf.resource.service.ITextbookLessonCharacterService;
import cn.rzedu.sf.resource.service.ITextbookLessonService;
import cn.rzedu.sf.resource.utils.JcDataUtil;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springblade.common.tool.ExcelUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.api.ResultCode;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.rzedu.sf.resource.entity.Textbook;
import cn.rzedu.sf.resource.vo.TextbookVO;
import cn.rzedu.sf.resource.wrapper.TextbookWrapper;
import cn.rzedu.sf.resource.service.ITextbookService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 书法教材 控制器
 *
 * @author Blade
 * @since 2020-08-05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/textbook")
@Api(value = "书法教材", tags = "书法教材")
public class TextbookController extends BladeController {

    private ITextbookService textbookService;

    private ITextbookLessonService textbookLessonService;

    private ITextbookLessonCharacterService textbookLessonCharacterService;

    private ICharacterService characterService;

    /**
     * 教材列表
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "教材列表", notes = "传入textbook")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "教材名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "publisher", value = "出版社", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "subject", value = "教材的学科 71=软笔书法 72=硬笔书法", paramType = "query", dataType = "integer")
    })
    public R<IPage<TextbookVO>> list(@ApiIgnore @RequestParam Map<String, Object> textbook, Query query) {
        IPage<Textbook> pages = textbookService.page(Condition.getPage(query),
                Condition.getQueryWrapper(textbook, Textbook.class));
        IPage<TextbookVO> result = TextbookWrapper.build().pageVO(pages);
        //传入课程数据
        List<TextbookVO> textbookList = result.getRecords();
        if (textbookList != null && !textbookList.isEmpty()) {
            List<TextbookLessonVO> lessonList = null;
            for (TextbookVO vo : textbookList) {
                lessonList = textbookLessonService.findLessonByTextbookId(vo.getId());
                vo.setTextbookLessonVOList(lessonList);
            }
            result.setRecords(textbookList);
        }
        return R.data(result);
    }


    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "详情", notes = "传入textbookId")
    public R<TextbookVO> detail(@ApiParam(value = "主键", required = true) @PathVariable(value = "id") Integer id) {
        TextbookVO textbookVO = textbookService.findTextBookById(id);
        return R.data(textbookVO);
    }


    /**
     * 新增 书法教材表
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入textbookVO")
    public R save(@Valid @RequestBody TextbookVO textbookVO) {
        //判断教材名是否重复
        Integer subject = textbookVO.getSubject();
        boolean isExist = textbookService.judgeRepeatByName(textbookVO.getName(), textbookVO.getPublisher(),
                subject, null);
        if (isExist) {
            return R.success(ResultCode.PARAM_VALID_ERROR, "教材名已有，不能重复");
        }
        //新增教材
        LocalDateTime now = LocalDateTime.now();
        textbookVO.setCreateDate(now);
        textbookVO.setModifyDate(now);
        boolean statusT = textbookService.save(textbookVO);
        if (!statusT) {
            return R.success(ResultCode.FAILURE, "新增教材失败");
        }
        //新增课程
        Integer textbookId = textbookVO.getId();
        boolean statusTL = false;
        Integer lessonId = null;
        //变动的课程id，用于修改课程汉字数
        List<Integer> lessonIdList = new ArrayList<>();
        List<TextbookLessonVO> lessonVOList = textbookVO.getTextbookLessonVOList();
        if (lessonVOList != null && !lessonVOList.isEmpty()) {
            for (TextbookLessonVO lessonVO : lessonVOList) {
                lessonVO.setTextbookId(textbookId);
                lessonVO.setCreateDate(now);
                lessonVO.setModifyDate(now);
                statusTL = textbookLessonService.save(lessonVO);
                if (!statusTL) {
                    continue;
                }

                //课程-汉字建表时，判断汉字是否存在，不存在的不予保存，跳过
                lessonId = lessonVO.getId();
                lessonIdList.add(lessonId);
                String chars = lessonVO.getChars();
                addTextLessonCharacters(subject, lessonId, chars);
            }
        }
        //课程、汉字都添加完后，修改课程总数和汉字总数
        lessonIdList.forEach(LId -> textbookLessonService.updateCharCount(LId));
        textbookService.updateLessonAndCharCount(textbookId);
        return R.status(true);
    }

    private void addTextLessonCharacters(Integer subject, Integer lessonId, String chars) {
        if (StringUtils.isBlank(chars)) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        List<Character> characterList = characterService.findByChars(chars);
        if (characterList != null && !characterList.isEmpty()) {
            List<TextbookLessonCharacter> tlcList = new ArrayList<>();
            TextbookLessonCharacter tlc = null;
            for (Character character : characterList) {
                tlc = new TextbookLessonCharacter();
                tlc.setLessonId(lessonId);
                tlc.setCharacterId(character.getId());
                tlc.setSubject(subject);
                tlc.setCreateDate(now);
                tlc.setModifyDate(now);
                tlcList.add(tlc);
            }
            textbookLessonCharacterService.saveBatch(tlcList);
        }
    }

    /**
     * 修改 书法教材表
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入textbookVO。课程数据，课程名空的数据不用传，默认删除，有id的修改，无id的新增")
    public R update(@Valid @RequestBody TextbookVO textbookVO) {
        Integer subject = textbookVO.getSubject();
        Integer textbookId = textbookVO.getId();
        boolean isExist = textbookService.judgeRepeatByName(textbookVO.getName(), textbookVO.getPublisher(),
                subject, textbookId);
        if (isExist) {
            return R.success(ResultCode.PARAM_VALID_ERROR, "教材名已有，不能重复");
        }
        //修改教材
        boolean statusT = textbookService.updateById(textbookVO);
        if (!statusT) {
            return R.success(ResultCode.FAILURE, "修改教材失败");
        }
        //修改课程，根据有无id分成修改和新增两类
        boolean statusTL = false;
        Integer lessonId = null;
        //变动的课程id，用于修改课程汉字数
        List<Integer> lessonIdList = new ArrayList<>();
        List<TextbookLessonVO> lessonVOList = textbookVO.getTextbookLessonVOList();

        //原有课程
        List<TextbookLessonVO> existList = textbookLessonService.findLessonByTextbookId(textbookId);
        //需新增的课程
        List<TextbookLessonVO> addLessons = new ArrayList<>();
        //需修改的课程
        List<TextbookLessonVO> modifyLessons = new ArrayList<>();
        //需删除的课程
        List<Integer> deleteIds = new ArrayList<>();
        //课程为空时，删除全部
        if (lessonVOList == null || lessonVOList.isEmpty()) {
            if (existList != null && !existList.isEmpty()) {
                existList.forEach(el -> deleteIds.add(el.getId()));
                textbookLessonService.removeByIds(deleteIds);
                //删除 课程-汉字
                textbookLessonCharacterService.removeByLessonIds(deleteIds);
                return R.status(true);
            }
        }

        LocalDateTime now = LocalDateTime.now();
        for (TextbookLessonVO lessonVO : lessonVOList) {
            if (lessonVO.getId() != null && lessonVO.getId() != 0) {
                modifyLessons.add(lessonVO);
                lessonIdList.add(lessonVO.getId());
            } else {
                lessonVO.setTextbookId(textbookId);
                lessonVO.setCreateDate(now);
                lessonVO.setModifyDate(now);
                lessonVO.setId(null);
                addLessons.add(lessonVO);
            }
        }
        //原有的课程和 传输的课程id比较，匹配不到的就删除
        if (existList != null && !existList.isEmpty()) {
            boolean isHad = false;
            for (TextbookLessonVO vo : existList) {
                for (TextbookLessonVO lessonVO : lessonVOList) {
                    if (vo.getId().equals(lessonVO.getId())) {
                        isHad = true;
                        break;
                    }
                }
                if (!isHad) {
                    deleteIds.add(vo.getId());
                }
            }
        }

        //新增课程
        if (!addLessons.isEmpty()) {
            for (TextbookLessonVO vo : addLessons) {
                statusTL = textbookLessonService.save(vo);
                if (statusTL) {
                    lessonIdList.add(vo.getId());
                    addTextLessonCharacters(subject, vo.getId(), vo.getChars());
                }
            }
        }
        //修改课程
        if (!modifyLessons.isEmpty()) {
//            List<TextbookLesson> tlList = new ArrayList<>();
//            BeanUtil.copyProperties(modifyLessons, tlList);
            List<TextbookLesson> tlList =
                  modifyLessons.stream().map(ml -> BeanUtil.copy(ml, TextbookLesson.class)).collect(Collectors.toList());
            textbookLessonService.updateBatchById(tlList);
            //修改课程-汉字
            List<Integer> modifyIds = new ArrayList<>();
            modifyLessons.forEach(ml -> modifyIds.add(ml.getId()));
//            textbookLessonCharacterService.removeByLessonIds(modifyIds);
            for (TextbookLessonVO modifyLesson : modifyLessons) {
//                addTextLessonCharacters(subject, modifyLesson.getId(), modifyLesson.getChars());
                addOrRemoveTextLessonCharacters(subject, modifyLesson.getId(), modifyLesson.getChars());
            }
        }
        //删除课程
        if (!deleteIds.isEmpty()) {
            textbookLessonService.removeByIds(deleteIds);
            //删除 课程-汉字
            textbookLessonCharacterService.removeByLessonIds(deleteIds);
        }
        //课程、汉字都添加完后，修改课程总数和汉字总数
        lessonIdList.forEach(LId -> textbookLessonService.updateCharCount(LId));
        textbookService.updateLessonAndCharCount(textbookId);
        return R.status(true);
    }

    private void addOrRemoveTextLessonCharacters(Integer subject, Integer lessonId, String chars) {
        if (StringUtils.isBlank(chars)) {
            List<Integer> ids = new ArrayList<>();
            ids.add(lessonId);
            textbookLessonCharacterService.removeByLessonIds(ids);
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        List<Character> characterList = characterService.findByChars(chars);
        List<TextbookLessonCharacter> lessonCharacterList = textbookLessonCharacterService.findByLessonId(lessonId);

        if (characterList == null || characterList.isEmpty()) {
            List<Integer> ids = new ArrayList<>();
            ids.add(lessonId);
            textbookLessonCharacterService.removeByLessonIds(ids);
            return;
        }
        boolean isExist = false;
        List<TextbookLessonCharacter> addList = new ArrayList<>();
        TextbookLessonCharacter add = null;
        for (Character character : characterList) {
            isExist = false;
            for (TextbookLessonCharacter lessonCharacter : lessonCharacterList) {
                if (character.getId().intValue() == lessonCharacter.getCharacterId().intValue()) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                add = new TextbookLessonCharacter();
                add.setLessonId(lessonId);
                add.setCharacterId(character.getId());
                add.setSubject(subject);
                add.setCreateDate(now);
                add.setModifyDate(now);
                addList.add(add);
            }
        }
        if (!addList.isEmpty()) {
            textbookLessonCharacterService.saveBatch(addList);
        }

//        List<TextbookLessonCharacter> deleteList = new ArrayList<>();
        List<Integer> deleteList = new ArrayList<>();
        for (TextbookLessonCharacter lessonCharacter : lessonCharacterList) {
            isExist = false;
            for (Character character : characterList) {
                if (character.getId().intValue() == lessonCharacter.getCharacterId().intValue()) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                deleteList.add(lessonCharacter.getId());
            }
        }
        if (!deleteList.isEmpty()) {
            textbookLessonCharacterService.removeByIds(deleteList);
        }
    }


    /**
     * 删除 书法教材表
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        List<Integer> list = Func.toIntList(ids);
        boolean status = textbookService.removeByIds(list);
        if (status) {
            textbookLessonService.removeByTextbookIds(list);
            textbookLessonCharacterService.removeByTextbookIds(list);
        }
        return R.status(status);
    }


    /**
     * 根据课程编号获取教材id，课程id，汉字id
     * @param code
     * @param type
     * @return
     */
    @GetMapping("/char/info")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "根据课程编号获取教材id，课程id，汉字id", notes = "根据课程编号和类型，获取教材id，课程id，汉字id")
    public R getCharacterId(
            @ApiParam(value = "汉字|课程编号", required = true) @RequestParam String code,
            @ApiParam(value = "书法类型 1=软笔 2=硬笔", required = true) @RequestParam Integer type
    ) {
        Integer textbookId = null;
        Integer lessonId = null;
        Integer characterId = null;
        String textbookName = "";
        String lessonName = "";
        String characterName = "";

        TextbookLesson textbookLesson = textbookLessonService.findLessonByCode(code);
        if (textbookLesson != null) {
            textbookId = textbookLesson.getTextbookId();
            lessonId = textbookLesson.getId();
            //硬笔：一课多字，默认拿第一个字；软笔：一课一字
            List<TextbookLessonCharacter> characterList = textbookLessonCharacterService.findByLessonId(lessonId);
            if (characterList != null && !characterList.isEmpty()) {
                characterId = characterList.get(0).getCharacterId();
            }
            lessonName = textbookLesson.getName();
        }
        if (textbookId != null) {
            Textbook textbook = textbookService.getById(textbookId);
            if (textbook != null) {
                textbookName = textbook.getName();
            }
        }
        if (characterId != null) {
            Character character = characterService.getById(characterId);
            if (character != null) {
                characterName = character.getCharS();
            }
        }

        Map<String, Object> map = new HashMap<>(3);
        map.put("textbookId", textbookId);
        map.put("lessonId", lessonId);
        map.put("characterId", characterId);
        map.put("textbookName", textbookName);
        map.put("lessonName", lessonName);
        map.put("characterName", characterName);
        return R.data(map);
    }



    /**
     * 批量导出二维码 软/硬笔课程
     * @return
     */
    @GetMapping("/export/{subject}")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "批量导出课程", notes = "批量导出软/硬笔课程的二维码，有多选就只导出已选的，没有就导出全部（筛选条件下的全部）")
    public void export(
            @ApiParam(value = "资源学科 71=软笔书法 72=硬笔书法", required = true) @PathVariable(value = "subject") Integer subject,
            @ApiParam(value = "教材名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "出版社") @RequestParam(value = "publisher", required = false) String publisher,
            @ApiParam(value = "教材主键id，多个用逗号隔开，如：1,2,3") @RequestParam(value = "ids", required = false) String ids,
            HttpServletResponse response, HttpServletRequest request
    ) {
        TextbookVO textbookVO = new TextbookVO();
        textbookVO.setSubject(subject);
        textbookVO.setName(name);
        textbookVO.setPublisher(publisher);
        List<TextbookVO> list = textbookService.findByIds(ids, textbookVO);

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

        String fileName = ryb + "教材二维码.xls";
        String sheetName = ryb + "教材二维码";
        String[] title = {"名称", "年级", "册次", "出版社", "课程数量", "访问量", "二维码"};
        String[][] content = new String[size][title.length];

        if (list != null) {
            TextbookVO vo = null;
            for (int i = 0; i < list.size(); i++) {
                vo = list.get(i);
                content[i][0] = vo.getName();
                content[i][1] = JcDataUtil.getGradeName(vo.getGradeCode());
                content[i][2] = JcDataUtil.getVolumeName(vo.getVolume());
                content[i][3] = vo.getPublisher();
                content[i][4] = String.valueOf(vo.getLessonCount());
                content[i][5] = String.valueOf(vo.getVisitedCount());
                content[i][6] = vo.getErCode();
            }
        }
        ExcelUtil.exportExcel(fileName, sheetName, title, content, response, request);

    }

}
