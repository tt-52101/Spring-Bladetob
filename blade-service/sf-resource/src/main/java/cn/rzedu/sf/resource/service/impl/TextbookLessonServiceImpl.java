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

import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.entity.Textbook;
import cn.rzedu.sf.resource.entity.TextbookLesson;
import cn.rzedu.sf.resource.mapper.CharacterMapper;
import cn.rzedu.sf.resource.mapper.TextbookLessonMapper;
import cn.rzedu.sf.resource.mapper.TextbookMapper;
import cn.rzedu.sf.resource.service.ITextbookLessonService;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.resource.feign.EntityFileClient;
import org.springblade.resource.vo.FileResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教材课程
1.教材textbook表包含的课程
2.部分课程（硬笔）包含二维码
 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@AllArgsConstructor
@Service
public class TextbookLessonServiceImpl extends ServiceImpl<TextbookLessonMapper, TextbookLesson> implements ITextbookLessonService {

	private TextbookMapper textbookMapper;
	private CharacterMapper characterMapper;
	private EntityFileClient entityFileClient;

	@Override
	public IPage<TextbookLessonVO> selectTextbookLessonPage(IPage<TextbookLessonVO> page, TextbookLessonVO textbookLesson) {
		return page.setRecords(baseMapper.selectTextbookLessonPage(page, textbookLesson));
	}

	@Override
	public List<TextbookLessonVO> findLessonByTextbookId(Integer textbookId) {
		return baseMapper.findLessonByTextbookId(textbookId);
	}

	@Override
	public boolean updateCharCount(Integer id) {
		return SqlHelper.retBool(baseMapper.updateCharCount(id));
	}

	@Override
	public TextbookLesson findLessonByCode(String code) {
		return baseMapper.findLessonByCode(code);
	}

	@Override
	public Map<String, Object> getLessonCharacterInfo(String code) {
		return baseMapper.getLessonCharacterInfo(code);
	}

	@Override
	public boolean removeByTextbookIds(List<Integer> textbookIds) {
		return SqlHelper.retBool(baseMapper.removeByTextbookIds(textbookIds));
	}

	@Override
	public Map<String, Object> findLessonById(Integer lessonId) {
		TextbookLessonVO lessonVO = baseMapper.findLessonById(lessonId);
		if (lessonVO == null) {
			return null;
		}
		Textbook textbook = textbookMapper.selectById(lessonVO.getTextbookId());
		String font = "";
		if (textbook != null) {
			font = textbook.getFont();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("id", lessonVO.getId());
		map.put("textbookId", lessonVO.getTextbookId());
		map.put("name", lessonVO.getName());
		map.put("listOrder", lessonVO.getListOrder());
		map.put("section", lessonVO.getSection());
		map.put("sectionName", lessonVO.getSectionName());
		map.put("chars", lessonVO.getChars());
		map.put("charCount", lessonVO.getCharCount());
		map.put("charIds", lessonVO.getCharIds());
		map.put("charList", getCharList(lessonVO.getChars(), lessonVO.getCharIds()));
		map.put("font", font);
		return map;
	}

	private List<Map<String, Object>> getCharList(String chars, String charIds) {
		if (StringUtil.isBlank(chars)) {
			return null;
		}
		List<String> charList = Func.toStrList("、", chars);
		List<Integer> charIdList = Func.toIntList(charIds);
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = null;
		for (int i = 0; i < charList.size(); i++) {
			map = new HashMap<>();
			Integer charId = charIdList.get(i);
			map.put("char", charList.get(i));
			map.put("charId", charId);
			Character character = characterMapper.selectById(charId);
			if(character != null){
				String uuid = character.getLightImage();
				map.put("type", character.getType());
				map.put("image", uuid);
				if(uuid != null && !"".equals(uuid)){
					try {
						FileResult fileResult = entityFileClient.findImageByUuid(uuid);
						if(fileResult != null){
							String link = fileResult.getLink();
							map.put("imageUrl", link);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> findAllLessonByTextbook(Integer textbookId) {
		List<TextbookLessonVO> list = baseMapper.findLessonByTextbookId(textbookId);
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> map = null;
		for (TextbookLessonVO vo : list) {
			map = new HashMap<>();
			map.put("lessonId", vo.getId());
			map.put("listOrder", vo.getListOrder());
			map.put("name", vo.getName());
			map.put("chars", vo.getChars());
			map.put("charCount", vo.getCharCount());
			map.put("charIds", vo.getCharIds());
			result.add(map);
		}
		return result;
	}
}
