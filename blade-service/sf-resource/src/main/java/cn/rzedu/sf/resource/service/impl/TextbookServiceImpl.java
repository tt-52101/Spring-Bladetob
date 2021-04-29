/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.*;
import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.service.*;
import cn.rzedu.sf.resource.vo.CharLinkVO;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import cn.rzedu.sf.resource.vo.TextbookVO;
import cn.rzedu.sf.resource.mapper.TextbookMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.utils.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 书法教材 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@AllArgsConstructor
@Service
public class TextbookServiceImpl extends ServiceImpl<TextbookMapper, Textbook> implements ITextbookService {

    private ITextbookLessonService textbookLessonService;

    private ITextbookLessonCharacterService textbookLessonCharacterService;

    private ICharacterService characterService;

    private IPublisherService publisherService;

    @Override
    public IPage<TextbookVO> selectTextbookPage(IPage<TextbookVO> page, TextbookVO textbook) {
        return page.setRecords(baseMapper.selectTextbookPage(page, textbook));
    }

    @Override
    public TextbookVO findTextBookById(Integer id) {
        Textbook textbook = baseMapper.selectById(id);
        TextbookVO textbookVO = BeanUtil.copy(textbook, TextbookVO.class);
        List<TextbookLessonVO> lessonVOList = textbookLessonService.findLessonByTextbookId(id);
        textbookVO.setTextbookLessonVOList(lessonVOList);
        return textbookVO;
    }


    @Override
    public boolean judgeRepeatByName(String name, String publisher, Integer subject, Integer id) {
        boolean isExist = false;
        Textbook textbook = new Textbook();
        textbook.setSubject(subject);
        textbook.setPublisher(publisher);
        textbook.setName(name);
        List<Textbook> list = baseMapper.selectList(Wrappers.query(textbook));
        if (list != null && !list.isEmpty()) {
            if (id != null) {
                isExist = list.stream().anyMatch(tb -> tb.getName().equals(name) && !tb.getId().equals(id));
            } else {
                isExist = true;
            }
        }
        return isExist;
    }

    @Override
    public boolean updateLessonAndCharCount(Integer id) {
        return SqlHelper.retBool(baseMapper.updateLessonAndCharCount(id));
    }

    @Override
    public boolean updateVisitedCount(Integer id) {
        return SqlHelper.retBool(baseMapper.updateVisitedCount(id));
    }

    @Override
    public List<TextbookVO> findByIds(String ids, TextbookVO textbook) {
        return baseMapper.findByIds(ids, textbook);
    }

    @Override
    public List<CharLinkVO> findLessonByCodeAndType(String type, String code) {
        List<CharLinkVO> list = new ArrayList<>();
        if ("1".equals(type)) {
            //软笔
            list = getSoftCharacterInfo(code);
        } else if ("2".equals(type)) {
            //硬笔
            list.add(getHardCharacterInfo(code));
        }
        return list;
    }

    /**
     * 软笔汉字课程
     * @param code
     * @return
     */
    private List<CharLinkVO> getSoftCharacterInfo(String code) {
        List<CharLinkVO> list = new ArrayList<>();
        CharLinkVO vo = null;
        Integer characterId = null;
        String characterName = "";
        Character character = characterService.findByCodeAndType(code, 1);
        if (character != null) {
            characterId = character.getId();
            characterName = character.getCharS();
        }
        if (characterId != null) {
            TextbookLessonCharacter tlc = new TextbookLessonCharacter();
            tlc.setCharacterId(characterId);
            tlc.setSubject(71);
            List<TextbookLessonCharacter> tlcList = textbookLessonCharacterService.list(Condition.getQueryWrapper(tlc));

            if (tlcList != null && !tlcList.isEmpty()) {
                for (TextbookLessonCharacter lessonCharacter : tlcList) {
                    vo = new CharLinkVO();

                    TextbookLesson lesson = textbookLessonService.getById(lessonCharacter.getLessonId());
                    if (lesson != null) {
                        vo.setLessonId(lesson.getId());
                        vo.setLessonName(lesson.getName());

                        Textbook textbook = baseMapper.selectById(lesson.getTextbookId());
                        if (textbook != null) {
                            vo.setTextbookId(textbook.getId());
                            vo.setTextbookName(textbook.getName());
                            vo.setGradeCode(textbook.getGradeCode());
                            vo.setSubject(textbook.getSubject());
                        }
                    }

                    vo.setCharacterId(characterId);
                    vo.setCharacterName(characterName);
                    list.add(vo);
                }
            }
        }
        return list;
    }

    /**
     * 硬笔汉字课程
     * @param code
     * @return
     */
    private CharLinkVO getHardCharacterInfo(String code) {
        CharLinkVO vo = new CharLinkVO();
        Integer textbookId = null;
        Integer lessonId = null;
        Integer characterId = null;
        TextbookLesson textbookLesson = textbookLessonService.findLessonByCode(code);
        if (textbookLesson != null) {
            textbookId = textbookLesson.getTextbookId();
            lessonId = textbookLesson.getId();
            //硬笔：一课多字，默认拿第一个字
            List<TextbookLessonCharacter> characterList = textbookLessonCharacterService.findByLessonId(lessonId);
            if (characterList != null && !characterList.isEmpty()) {
                characterId = characterList.get(0).getCharacterId();
            }

            vo.setTextbookId(textbookId);
            vo.setLessonId(lessonId);
            vo.setCharacterId(characterId);
            vo.setLessonName(textbookLesson.getName());
        }
        if (textbookId != null) {
            Textbook textbook = baseMapper.selectById(textbookId);
            if (textbook != null) {
                vo.setTextbookName(textbook.getName());
                vo.setGradeCode(textbook.getGradeCode());
                vo.setSubject(textbook.getSubject());
            }
        }
        if (characterId != null) {
            Character character = characterService.getById(characterId);
            if (character != null) {
                vo.setCharacterName(character.getCharS());
            }
        }
        return vo;
    }

    @Override
    public List<String> findPublisherList(Integer subject) {
        return baseMapper.findPublisherList(subject);
    }

    @Override
    public TextbookVO findTextBookByIdWithUnit(Integer id) {
        Textbook textbook = baseMapper.selectById(id);
        if (textbook == null) {
            return null;
        }
        TextbookVO textbookVO = BeanUtil.copy(textbook, TextbookVO.class);
        List<TextbookLessonVO> lessonVOList = textbookLessonService.findLessonByTextbookId(id);
        List unitList = transferUnitList(lessonVOList);
        textbookVO.setUnitList(unitList);
        return textbookVO;
    }

    private List transferUnitList(List<TextbookLessonVO> list) {
        Map<String, List<TextbookLessonVO>> map = new LinkedHashMap<>();
        List<TextbookLessonVO> valueList = null;
        String section = "";
        for (TextbookLessonVO vo : list) {
            section = vo.getSection() + "_" + vo.getSectionName();
            if (map.containsKey(section)) {
                valueList = map.get(section);
            } else {
                valueList = new ArrayList<>();
            }
            valueList.add(vo);

            //排序
            Comparator<TextbookLessonVO> ageComparator = (o1, o2)->o1.getListOrder().compareTo(o2.getListOrder());
            valueList.sort(ageComparator);

            map.put(section, valueList);
        }

        List<Map<String, Object>> result = new ArrayList<>(map.size());
        Map<String, Object> resultMap = null;
        for (Map.Entry<String, List<TextbookLessonVO>> entry : map.entrySet()) {
            resultMap = new HashMap<>(3);
            String[] str = entry.getKey().split("_");
            if ("null".equals(str[1])) {
                str[1] = "";
            }
            resultMap.put("section", str[0]);
            resultMap.put("sectionName", str[1]);
            resultMap.put("lessonList", entry.getValue());
            result.add(resultMap);
        }
        return result;
    }

    @Override
    public List findAllTextbook() {
        int soft = 71;
        int hard = 72;
        List list = new ArrayList();
        Map<String, Object> hardMap = new HashMap<>(3);
        hardMap.put("subject", hard);
        hardMap.put("name", "硬笔");
        hardMap.put("publisherList", getPublishersBySubject(hard));
        list.add(hardMap);
        Map<String, Object> softMap = new HashMap<>(3);
        softMap.put("subject", soft);
        softMap.put("name", "软笔");
        softMap.put("publisherList", getPublishersBySubject(soft));
        list.add(softMap);
        return list;
    }

    private List getPublishersBySubject(Integer subject) {
        List list = new ArrayList();
        Map<String, Object> map = new HashMap<>(2);
//        map.put("name", "全部");
//        map.put("textbookList", getTextbookByPublisher(subject, null));
//        list.add(map);

        List<Publisher> publisherList = publisherService.findPublisherBySubject(subject);
        if (publisherList != null && !publisherList.isEmpty()) {
            for (Publisher publisher : publisherList) {
                map = new HashMap<>(2);
                map.put("name", publisher.getName());
                map.put("textbookList", getTextbookByPublisher(subject, publisher.getName()));
                list.add(map);
            }
        }
        return list;
    }

    private List getTextbookByPublisher(Integer subject, String publisher) {
        List list = new ArrayList();
        Map<String, Object> map = null;

        List<Textbook> textbookList = baseMapper.findBySubjectAndPublisher(subject, publisher);
        for (Textbook tb : textbookList) {
            map = new HashMap<>();
            map.put("textbookId", tb.getId());
            map.put("name", tb.getName());
            map.put("publisher", tb.getPublisher());
            map.put("stageCode", tb.getStageCode());
            map.put("gradeCode", tb.getGradeCode());
            map.put("volume", tb.getVolume());
            map.put("coverImage", tb.getCoverImage());
            map.put("font", tb.getFont());
            list.add(map);
        }
        return list;
    }
}
