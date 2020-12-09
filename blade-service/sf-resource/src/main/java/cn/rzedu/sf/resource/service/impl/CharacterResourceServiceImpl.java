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

import cn.rzedu.sf.resource.entity.CharacterResource;
import cn.rzedu.sf.resource.entity.CharacterResourceFile;
import cn.rzedu.sf.resource.service.ICharacterResourceFileService;
import cn.rzedu.sf.resource.vo.CharResFileVO;
import cn.rzedu.sf.resource.vo.CharacterResourceVO;
import cn.rzedu.sf.resource.mapper.CharacterResourceMapper;
import cn.rzedu.sf.resource.service.ICharacterResourceService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 汉字资源
汉字的（视频）资源
一个汉字会有多个资源记录，软笔和硬笔资源都放在这个表，软硬笔作为不同科目来区分
 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@AllArgsConstructor
@Service
public class CharacterResourceServiceImpl extends ServiceImpl<CharacterResourceMapper, CharacterResource> implements ICharacterResourceService {

	private ICharacterResourceFileService characterResourceFileService;

	@Override
	public IPage<CharacterResourceVO> selectCharacterResourcePage(IPage<CharacterResourceVO> page, CharacterResourceVO characterResource) {
		return page.setRecords(baseMapper.selectCharacterResourcePage(page, characterResource));
	}

	@Override
	public boolean save(CharacterResource characterResource) {
		characterResource.setCreateDate(LocalDateTime.now());
		characterResource.setModifyDate(LocalDateTime.now());
		return SqlHelper.retBool(this.getBaseMapper().insert(characterResource));
	}

	@Override
	public boolean judgeRepeatByName(Integer characterId, String nameTr, Integer subject, Integer id) {
		boolean isExist = false;
		CharacterResource resource = new CharacterResource();
		resource.setCharacterId(characterId);
		resource.setNameTr(nameTr);
		resource.setSubject(subject);
		List<CharacterResource> list = baseMapper.selectList(Wrappers.query(resource));
		if (list != null && !list.isEmpty()) {
			if (id != null) {
				isExist = list.stream().anyMatch(cr -> cr.getNameTr().equals(nameTr) && !cr.getId().equals(id));
			} else {
				isExist = true;
			}
		}
		return isExist;
	}

	@Override
	public boolean updateDisable(Integer characterId, Integer subject, Integer resourceType) {
		return SqlHelper.retBool(baseMapper.updateDisable(characterId, subject, resourceType));
	}

	@Override
	public boolean updateCharST(Integer characterId, String charS, String charT) {
		return SqlHelper.retBool(baseMapper.updateCharST(characterId, charS, charT));
	}

	@Override
	public boolean updateVisitedCount(Integer characterId, Integer subject, Integer resourceType) {
		return SqlHelper.retBool(baseMapper.updateVisitedCount(characterId, subject, resourceType));
	}

	@Override
	public CharacterResource findUnion(Integer characterId, Integer subject, Integer resourceType) {
		return baseMapper.findUnion(characterId, subject, resourceType);
	}

	@Override
	public Map<String, Object> findSoftResource(Integer characterId) {
		Integer subject = 71;
		//设置默认值
		String name = "";
		String videoId = "";
		List<CharResFileVO> type2 = new ArrayList<>();
		type2.add(new CharResFileVO("spell", "text"));
		type2.add(new CharResFileVO("character", "image"));
		type2.add(new CharResFileVO("tablet", "image"));
		type2.add(new CharResFileVO("stroke_dot", "image"));
		type2.add(new CharResFileVO("stroke_arrow", "image"));
		type2.add(new CharResFileVO("stroke_triangle", "image"));
		List<CharResFileVO> type3 = new ArrayList<>();
		type3.add(new CharResFileVO("analyse", "image"));
		type3.add(new CharResFileVO("stroke_text", "text"));
		type3.add(new CharResFileVO("stroke_audio", "audio"));
		type3.add(new CharResFileVO("space_text", "text"));
		type3.add(new CharResFileVO("space_audio", "audio"));
		List<CharResFileVO> type4 = new ArrayList<>();
		type4.add(new CharResFileVO("line", "video"));
		type4.add(new CharResFileVO("gesture", "video"));

		//视频
		CharacterResource cr1 = baseMapper.findUnion(characterId, subject, 1);
		if (cr1 != null) {
			name = cr1.getCharS();
			List<CharacterResourceFile> list1 = characterResourceFileService.findByResourceId(cr1.getId());
			if (list1 != null && !list1.isEmpty()) {
				videoId = list1.get(0).getUuid();
			}
		}
		//观察
		addValueByType(type2, characterId, subject, 2);
		//分析
		addValueByType(type3, characterId, subject, 3);
		//笔法
		addValueByType(type4, characterId, subject, 4);

		Map<String, Object> map = new HashMap<>(6);
		map.put("characterId", characterId);
		map.put("name", name);
		map.put("videoId", videoId);
		map.put("type2", type2);
		map.put("type3", type3);
		map.put("type4", type4);
		return map;
	}

	private void addValueByType(List<CharResFileVO> voList,
								Integer characterId, Integer subject, Integer resourceType) {
		CharacterResource cr = baseMapper.findUnion(characterId, subject, resourceType);
		if (cr != null) {
			List<CharacterResourceFile> list = characterResourceFileService.findByResourceId(cr.getId());
			addValueFromCRFList(voList, list);
		}
	}

	private void addValueFromCRFList(List<CharResFileVO> voList, List<CharacterResourceFile> list) {
		if (list != null && !list.isEmpty()) {
			for (CharResFileVO vo : voList) {
				for (CharacterResourceFile crf : list) {
					if (vo.getObjectId().equals(crf.getObjectId())) {
						if (vo.getObjectType().equals("text")) {
							vo.setObjectValue(crf.getContent());
						} else {
							vo.setObjectValue(crf.getUuid());
						}
						break;
					}
				}
			}
		}
	}
}
