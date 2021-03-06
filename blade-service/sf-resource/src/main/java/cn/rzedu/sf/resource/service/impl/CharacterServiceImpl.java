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
import cn.rzedu.sf.resource.entity.TextbookLessonCharacter;
import cn.rzedu.sf.resource.mapper.TextbookLessonCharacterMapper;
import cn.rzedu.sf.resource.service.ITextbookLessonCharacterService;
import cn.rzedu.sf.resource.vo.CharacterVO;
import cn.rzedu.sf.resource.mapper.CharacterMapper;
import cn.rzedu.sf.resource.service.ICharacterService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 汉字表
对应字典里面的汉字
一个汉字对应唯一一个记录，同时包含该汉字的软笔和硬笔的资源总属性
 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@AllArgsConstructor
@Service
public class CharacterServiceImpl extends ServiceImpl<CharacterMapper, Character> implements ICharacterService {

	private TextbookLessonCharacterMapper textbookLessonCharacterMapper;

	@Override
	public IPage<CharacterVO> selectCharacterPage(IPage<CharacterVO> page, CharacterVO character) {
		return page.setRecords(baseMapper.selectCharacterPage(page, character));
	}

	@Override
	public boolean save(Character character) {
		character.setCreateDate(LocalDateTime.now());
		character.setModifyDate(LocalDateTime.now());
		return SqlHelper.retBool(this.getBaseMapper().insert(character));
	}

	@Override
	public IPage<CharacterVO> findCharacterByChar(IPage<CharacterVO> page, String name) {
		return page.setRecords(baseMapper.findCharacterByChar(page, name));
	}

	@Override
	public boolean judgeRepeatByCharS(String charS, Integer id) {
		boolean isExist = false;
		Character param = new Character();
		param.setCharS(charS);
		List<Character> list = baseMapper.selectList(Wrappers.query(param));
		if (list != null && !list.isEmpty()) {
			if (id != null) {
				isExist = list.stream().anyMatch(character -> character.getCharS().equals(charS) && !character.getId().equals(id));
			} else {
				isExist = true;
			}
		}
		return isExist;
	}

	@Override
	public IPage<CharacterVO> findCharacterVoWithVisitedCount(IPage<CharacterVO> page, CharacterVO character) {
		return page.setRecords(baseMapper.findCharacterVoWithVisitedCount(page, character));
	}

	@Override
	public List<CharacterVO> findWithVisitedCountByIds(String ids, CharacterVO character) {
		return baseMapper.findWithVisitedCountByIds(ids, character);
	}

	@Override
	public boolean updateResCount(Integer id) {
		return SqlHelper.retBool(baseMapper.updateResCount(id));
	}

	@Override
	public List<Character> findByChars(String chars) {
		List<Character> list = new ArrayList<Character>();
		List<String> stringList = Func.toStrList("、", chars);
		for (String s : stringList) {
			Character character = baseMapper.findCharacterByKeyword(s);
			if(character != null){
				list.add(character);
			} else {
				Integer type = 1;
				if(s.length() > 1){
					type = 2;
				}

				character = new Character();
				character.setCharS(s);
				character.setCharT(s);
				character.setKeyword(s);
				character.setType(type);
				character.setCreateDate(LocalDateTime.now());
				character.setModifyDate(LocalDateTime.now());
				character.setIsDeleted(0);

				baseMapper.insert(character);
				list.add(character);
			}
		}

		return list;
	}

	@Override
	public boolean updateSoftVisitedCount(Integer id) {
		return SqlHelper.retBool(baseMapper.updateSoftVisitedCount(id));
	}

	@Override
	public boolean updateHardVisitedCount(Integer id) {
		return SqlHelper.retBool(baseMapper.updateHardVisitedCount(id));
	}

	@Override
	public Character findByCodeAndType(String code, Integer type) {
		return baseMapper.findByCodeAndType(code, type);
	}

	@Override
	public List<Character> findErCodeFromLesson(Integer textbookId) {
		return baseMapper.findErCodeFromLesson(textbookId);
	}

	@Override
	public Character findCharacterByKeyword(String name) {
		return baseMapper.findCharacterByKeyword(name);
	}

	@Override
	public boolean isExistHardResourceByCharacterId(Integer id) {
		if(id == null){
			return false;
		}
		List<TextbookLessonCharacter> list = textbookLessonCharacterMapper.isExistHardResourceByCharacterId(id);
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
}
