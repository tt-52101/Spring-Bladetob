package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.bo.FileData;
import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.entity.CharacterResource;
import cn.rzedu.sf.resource.entity.CharacterResourceFile;
import cn.rzedu.sf.resource.mapper.CharacterResourceMapper;
import cn.rzedu.sf.resource.service.ICharacterResourceFileService;
import cn.rzedu.sf.resource.service.ICharacterResourceService;
import cn.rzedu.sf.resource.service.ICharacterService;
import cn.rzedu.sf.resource.vo.CharResFileVO;
import cn.rzedu.sf.resource.vo.CharacterResourceVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springblade.core.tool.utils.Func;
import org.springblade.resource.feign.EntityFileClient;
import org.springblade.resource.vo.FileResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

	private ICharacterService characterService;

	private EntityFileClient entityFileClient;

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
	public Map<String, Object> findSoftResource(Integer characterId, String font) {
		Integer subject = 71;
		//设置默认值
		String name = "";
		String videoId = "";
		List<CharResFileVO> type0 = new ArrayList<>();
		type0.add(new CharResFileVO("spell", "text"));
		type0.add(new CharResFileVO("simple", "text"));
		type0.add(new CharResFileVO("radical", "text"));
		type0.add(new CharResFileVO("stroke_number", "text"));
		type0.add(new CharResFileVO("usage_text", "text"));
		type0.add(new CharResFileVO("usage_audio", "audio"));
		type0.add(new CharResFileVO("evolve_image", "image"));

		List<CharResFileVO> type1 = new ArrayList<>();
		type1.add(new CharResFileVO("spell", "text"));
		type1.add(new CharResFileVO("white_character", "image"));
		type1.add(new CharResFileVO("tablet", "image"));
		type1.add(new CharResFileVO("observe_dot", "image"));
		type1.add(new CharResFileVO("observe_arrow", "image"));
		type1.add(new CharResFileVO("observe_triangle", "image"));

		List<CharResFileVO> type2 = new ArrayList<>();
		type2.add(new CharResFileVO("analyse_image", "image"));
		type2.add(new CharResFileVO("stroke_text", "text"));
		type2.add(new CharResFileVO("stroke_audio", "audio"));
		type2.add(new CharResFileVO("space_text", "text"));
		type2.add(new CharResFileVO("space_audio", "audio"));

		List<CharResFileVO> type3 = new ArrayList<>();
		type3.add(new CharResFileVO("technique_line", "video"));
		type3.add(new CharResFileVO("technique_gesture", "video"));

		List<CharResFileVO> type4 = new ArrayList<>();
		type4.add(new CharResFileVO("compare_text", "text"));
		type4.add(new CharResFileVO("compare_image", "text"));

		//视频
		CharacterResource cr1 = baseMapper.findUnion(characterId, subject, 716);
		if (cr1 != null) {
			name = cr1.getCharS();
			List<CharacterResourceFile> list1 = characterResourceFileService.findByResourceAndFont(cr1.getId(), font);
			if (list1 != null && !list1.isEmpty()) {
				for (CharacterResourceFile file : list1) {
					if ("learn_video".equals(file.getObjectId())) {
						videoId = file.getUuid();
					}
				}
			}
		}
		//认读
		addValueByTypeAndFont(type0, characterId, subject, 712, font);
		//观察
		addValueByTypeAndFont(type1, characterId, subject, 711, font);
		//观察
		addValueByTypeAndFont(type1, characterId, subject, 713, font);
		//分析
		addValueByTypeAndFont(type2, characterId, subject, 714, font);
		//笔法
		addValueByTypeAndFont(type3, characterId, subject, 715, font);
		//对比
		addValueByTypeAndFont(type4, characterId, subject, 717, font);

		Map<String, Object> compareMap = transferCharResFileVO(type4);
		compareMap.put("compare_text", transferCompareMap((String) compareMap.get("compare_text")));
		compareMap.put("compare_image", transferCompareMap((String) compareMap.get("compare_image")));

		Map<String, Object> map = new HashMap<>(6);
		map.put("characterId", characterId);
		map.put("name", name);
		map.put("videoId", videoId);
		map.put("recognition_0", transferCharResFileVO(type0));
		map.put("observation_1", transferCharResFileVO(type1));
		map.put("analysis_2", transferCharResFileVO(type2));
		map.put("writing_3", transferCharResFileVO(type3));
		map.put("compare_4", compareMap);
		return map;
	}

	private List<String> transferCompareMap(String value) {
		List<String> list = new ArrayList<>();
		if (StringUtils.isNotBlank(value)) {
			list = Func.toStrList(value);
		}
		return list;
	}

	@Override
	public Map<String, Object> findHardResource(Integer characterId, String font) {
		Integer subject = 72;
		//设置默认值
		String name = "";
		String videoId = "";
		List<CharResFileVO> type0 = new ArrayList<>();
		type0.add(new CharResFileVO("spell", "text"));
		type0.add(new CharResFileVO("simple", "text"));
		type0.add(new CharResFileVO("radical", "text"));
		type0.add(new CharResFileVO("stroke_number", "text"));
		type0.add(new CharResFileVO("paraphrase_text", "text"));
		type0.add(new CharResFileVO("paraphrase_video", "audio"));

		List<CharResFileVO> type1 = new ArrayList<>();
		type1.add(new CharResFileVO("learn_video", "video"));

		//视频
		CharacterResource cr1 = baseMapper.findUnion(characterId, subject, 723);
		if (cr1 != null) {
			name = cr1.getCharS();
			List<CharacterResourceFile> list1 = characterResourceFileService.findByResourceAndFont(cr1.getId(), font);
			if (list1 != null && !list1.isEmpty()) {
				for (CharacterResourceFile file : list1) {
					if ("pen_video".equals(file.getObjectId())) {
						videoId = file.getUuid();
					}
				}
			}
		}

		//认读
		addValueByTypeAndFont(type0, characterId, subject, 721, font);
		//识字
		addValueByTypeAndFont(type1, characterId, subject, 724, font);

		Map<String, Object> recognitionMap = transferCharResFileVO(type0);
		//特别处理
		recognitionMap.put("usage_audio", recognitionMap.get("paraphrase_video"));
		recognitionMap.remove("paraphrase_video");
        recognitionMap.put("usage_text", recognitionMap.get("paraphrase_text"));
        recognitionMap.remove("paraphrase_text");
		Map<String, Object> learnMap = transferCharResFileVO(type1);
		try {
			learnMap.put("learn_video", entityFileClient.findAudioByUuid((String) learnMap.get("learn_video")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<>(6);
		map.put("characterId", characterId);
		map.put("name", name);
		map.put("videoId", videoId);
		map.put("recognition_0", recognitionMap);
		map.put("learn_1", learnMap);
		return map;
	}

	private Map<String, Object> transferCharResFileVO(List<CharResFileVO> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		for (CharResFileVO vo : list) {
			if ("image".equals(vo.getObjectType()) && StringUtils.isNotBlank(vo.getImageUrl())) {
				map.put(vo.getObjectId(), vo.getImageUrl());
			} else if ("compare_image".equals(vo.getObjectId()) && StringUtils.isNotBlank(vo.getImageUrl())) {
				map.put(vo.getObjectId(), vo.getImageUrl());
			} else if ("audio".equals(vo.getObjectType()) && StringUtils.isNotBlank(vo.getAudioUrl())) {
				map.put(vo.getObjectId(), vo.getAudioUrl());
			} else {
				map.put(vo.getObjectId(), vo.getObjectValue());
			}
		}
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

	private void addValueByTypeAndFont(List<CharResFileVO> voList, Integer characterId, Integer subject,
									   Integer resourceType, String font) {
		CharacterResource cr = baseMapper.findUnion(characterId, subject, resourceType);
		if (cr != null) {
			List<CharacterResourceFile> list = characterResourceFileService.findByResourceAndFont(cr.getId(), font);
			addValueFromCRFList(voList, list);
		}
	}

	private void addValueByTypeAndFontByJson(List<FileData> voList, Integer characterId, Integer subject,
									   Integer resourceType, String font) {
		CharacterResource cr = baseMapper.findUnion(characterId, subject, resourceType);
		if (cr != null) {
			List<CharacterResourceFile> list = characterResourceFileService.findByResourceAndFont(cr.getId(), font);
			addValueFromCRFListByJson(voList, list);
		}
	}

	@SneakyThrows
	private void addValueFromCRFList(List<CharResFileVO> voList, List<CharacterResourceFile> list) {
		if (list != null && !list.isEmpty()) {
			for (CharResFileVO vo : voList) {
				for (CharacterResourceFile crf : list) {
					if (vo.getObjectId().equals(crf.getObjectId())) {
						if (vo.getObjectType().equals("text")) {
							String content = crf.getContent();
							vo.setObjectValue(content);
							if((vo.getObjectId().equals("compare_image") ||
									vo.getObjectId().equals("practice_images") || vo.getObjectId().equals("game")) && content != null && !"".equals(content)){
								String[] split = content.split(",");
								String link = "";
								for (String uuid : split) {
									FileResult fileResult = entityFileClient.findImageByUuid(uuid);
									if(fileResult != null){
										link += fileResult.getLink() + ",";
									}
								}
								if(!"".equals(link)){
									link = link.substring(0, link.length() -1);
								}
								vo.setImageUrl(link);
							}
						}else {
							String uuid = crf.getUuid();
							if(vo.getObjectType().equals("image")){
								FileResult fileResult = entityFileClient.findImageByUuid(uuid);
								if(fileResult != null){
									String link = fileResult.getLink();
									vo.setImageUrl(link);
								}
							} else if (vo.getObjectType().equals("audio")) {
								String audioUrl = entityFileClient.findAudioByUuid(uuid);
								vo.setAudioUrl(audioUrl);
							}
							vo.setObjectValue(uuid);
						}
						break;
					}
				}
			}
		}
	}

	@SneakyThrows
	private void addValueFromCRFListByJson(List<FileData> voList, List<CharacterResourceFile> list) {
		if (list != null && !list.isEmpty()) {
			for (FileData vo : voList) {
				for (CharacterResourceFile crf : list) {
					if (vo.getObjectId().equals(crf.getObjectId())) {
						String objectType = crf.getObjectType();
						if(objectType.equals("text")){
							String content = crf.getContent();
							vo.setObjectValue(content);
						} else {
							String uuid = crf.getUuid();
							vo.setObjectValue(uuid);
						}
						break;
					}
				}
			}
		}
	}

	@Override
	public Map<String, Object> findResources(Integer characterId, Integer subject, String font) {
		Character character = characterService.getById(characterId);
		if (character == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("characterId", characterId);
		map.put("name", character.getCharS());
		map.put("type", character.getType());
		if (subject == 71) {
			addSoftResources(map, characterId, font);
		} else if (subject == 72) {
			addHardResources(map, characterId, font);
		}
		return map;
	}

	private void addSoftResources(Map<String, Object> map, Integer characterId, String font) {
		Integer subject = 71;
		//欣赏
		List<CharResFileVO> appreciation = new ArrayList<>();
		appreciation.add(new CharResFileVO("spell", "text"));
		appreciation.add(new CharResFileVO("white_character", "image"));
		appreciation.add(new CharResFileVO("tablet", "image"));
		addValueByTypeAndFont(appreciation, characterId, subject, 711, font);
		//认读
		List<CharResFileVO> recognition = new ArrayList<>();
		recognition.add(new CharResFileVO("matts", "image"));
		recognition.add(new CharResFileVO("spell", "text"));
		recognition.add(new CharResFileVO("simple", "text"));
		recognition.add(new CharResFileVO("radical", "text"));
		recognition.add(new CharResFileVO("stroke_number", "text"));
		recognition.add(new CharResFileVO("usage_audio", "audio"));
		recognition.add(new CharResFileVO("usage_text", "text"));
		recognition.add(new CharResFileVO("evolve_image", "image"));
		addValueByTypeAndFont(recognition, characterId, subject, 712, font);
		//观察
		List<CharResFileVO> observation = new ArrayList<>();
		observation.add(new CharResFileVO("observe_dot", "image"));
		observation.add(new CharResFileVO("observe_arrow", "image"));
		observation.add(new CharResFileVO("observe_triangle", "image"));
//		observation.add(new CharResFileVO("observe_text", "text"));
//		observation.add(new CharResFileVO("observe_audio", "audio"));
		observation.add(new CharResFileVO("observe_image", "image"));
		addValueByTypeAndFont(observation, characterId, subject, 713, font);
		//分析
		List<CharResFileVO> analysis = new ArrayList<>();
		analysis.add(new CharResFileVO("analyse_image", "image"));
		analysis.add(new CharResFileVO("stroke_text", "text"));
		analysis.add(new CharResFileVO("stroke_audio", "audio"));
		analysis.add(new CharResFileVO("space_text", "text"));
		analysis.add(new CharResFileVO("space_audio", "audio"));
		addValueByTypeAndFont(analysis, characterId, subject, 714, font);
		//笔法
		List<CharResFileVO> writing = new ArrayList<>();
//		writing.add(new CharResFileVO("writing_text", "text"));
		writing.add(new CharResFileVO("technique_line", "video"));
		writing.add(new CharResFileVO("technique_gesture", "video"));
		addValueByTypeAndFont(writing, characterId, subject, 715, font);
		//视频
		List<CharResFileVO> learn = new ArrayList<>();
		learn.add(new CharResFileVO("learn_text", "text"));
		learn.add(new CharResFileVO("learn_video", "video"));
		addValueByTypeAndFont(learn, characterId, subject, 716, font);
		//对比
		List<CharResFileVO> compare = new ArrayList<>();
		compare.add(new CharResFileVO("compare_text", "text"));
		compare.add(new CharResFileVO("compare_image", "text"));
		addValueByTypeAndFont(compare, characterId, subject, 717, font);
		//随堂练习
		List<CharResFileVO> practice = new ArrayList<>();
		practice.add(new CharResFileVO("practice_images", "text"));
		addValueByTypeAndFont(practice, characterId, subject, 718, font);

		map.put("appreciation_1", appreciation);
		map.put("recognition_2", recognition);
		map.put("observation_3", observation);
		map.put("analysis_4", analysis);
		map.put("writing_5", writing);
		map.put("learn_6", learn);
		map.put("compare_7", compare);
		map.put("practice_8", practice);
		System.out.println(map);
	}

	private void addSoftResourcesByJson(Map<String, Object> map, Integer characterId, String font) {
		Integer subject = 71;
		List<FileData> list = new ArrayList<FileData>();
		//欣赏
		List<FileData> appreciation = new ArrayList<FileData>();
		appreciation.add(new FileData("spell", 711, ""));
		appreciation.add(new FileData("white_character", 711, ""));
		appreciation.add(new FileData("tablet", 711, ""));
		addValueByTypeAndFontByJson(appreciation, characterId, subject, 711, font);
		list.addAll(appreciation);
		//认读
		List<FileData> recognition = new ArrayList<>();
		recognition.add(new FileData("matts", 712, ""));
		recognition.add(new FileData("spell", 712, ""));
		recognition.add(new FileData("simple", 712, ""));
		recognition.add(new FileData("radical", 712, ""));
		recognition.add(new FileData("stroke_number", 712, ""));
		recognition.add(new FileData("usage_audio", 712, ""));
		recognition.add(new FileData("usage_text", 712, ""));
		recognition.add(new FileData("evolve_image", 712, ""));
		addValueByTypeAndFontByJson(recognition, characterId, subject, 712, font);
		list.addAll(recognition);
		//观察
		List<FileData> observation = new ArrayList<>();
		observation.add(new FileData("observe_dot", 713, ""));
		observation.add(new FileData("observe_arrow", 713, ""));
		observation.add(new FileData("observe_triangle", 713, ""));
//		observation.add(new FileData("observe_text", "text"));
//		observation.add(new FileData("observe_audio", "audio"));
		observation.add(new FileData("observe_image", 713, ""));
		addValueByTypeAndFontByJson(observation, characterId, subject, 713, font);
		list.addAll(observation);
		//分析
		List<FileData> analysis = new ArrayList<>();
		analysis.add(new FileData("analyse_image", 714, ""));
		analysis.add(new FileData("stroke_text", 714, ""));
		analysis.add(new FileData("stroke_audio", 714, ""));
		analysis.add(new FileData("space_text", 714, ""));
		analysis.add(new FileData("space_audio", 714, ""));
		addValueByTypeAndFontByJson(analysis, characterId, subject, 714, font);
		list.addAll(analysis);
		//笔法
		List<FileData> writing = new ArrayList<>();
//		writing.add(new FileData("writing_text", "text"));
		writing.add(new FileData("technique_line", 715, ""));
		writing.add(new FileData("technique_gesture", 715, ""));
		addValueByTypeAndFontByJson(writing, characterId, subject, 715, font);
		list.addAll(writing);
		//视频
		List<FileData> learn = new ArrayList<>();
		learn.add(new FileData("learn_text", 716, ""));
		learn.add(new FileData("learn_video", 716, ""));
		addValueByTypeAndFontByJson(learn, characterId, subject, 716, font);
		list.addAll(learn);
		//对比
		List<FileData> compare = new ArrayList<>();
		compare.add(new FileData("compare_text", 717, ""));
		compare.add(new FileData("compare_image", 717, ""));
		addValueByTypeAndFontByJson(compare, characterId, subject, 717, font);
		list.addAll(compare);
		//随堂练习
		List<FileData> practice = new ArrayList<>();
		practice.add(new FileData("practice_images", 718, ""));
		addValueByTypeAndFontByJson(practice, characterId, subject, 718, font);
		list.addAll(practice);

		map.put("fileDatas", JSONArray.parseArray(JSON.toJSONString(list)));
	}

	private void addHardResources(Map<String, Object> map, Integer characterId, String font) {
		Integer subject = 72;
		//认读
		List<CharResFileVO> recognition = new ArrayList<>();
		recognition.add(new CharResFileVO("character", "image"));
		recognition.add(new CharResFileVO("spell", "text"));
		recognition.add(new CharResFileVO("simple", "text"));
		recognition.add(new CharResFileVO("radical", "text"));
		recognition.add(new CharResFileVO("stroke_number", "text"));
		recognition.add(new CharResFileVO("paraphrase_video", "audio"));
		recognition.add(new CharResFileVO("paraphrase_text", "text"));
		addValueByTypeAndFont(recognition, characterId, subject, 721, font);
		//粉笔
		List<CharResFileVO> chalk = new ArrayList<>();
		chalk.add(new CharResFileVO("chalk_text", "text"));
		chalk.add(new CharResFileVO("chalk_video", "video"));
		addValueByTypeAndFont(chalk, characterId, subject, 722, font);
		//钢笔
		List<CharResFileVO> pen = new ArrayList<>();
		pen.add(new CharResFileVO("pen_text", "text"));
		pen.add(new CharResFileVO("pen_video", "video"));
		addValueByTypeAndFont(pen, characterId, subject, 723, font);
		//识字
		List<CharResFileVO> learn = new ArrayList<>();
		learn.add(new CharResFileVO("learn_text", "text"));
		learn.add(new CharResFileVO("learn_video", "video"));
		addValueByTypeAndFont(learn, characterId, subject, 724, font);
		//找茬游戏
		List<CharResFileVO> game = new ArrayList<>();
		game.add(new CharResFileVO("game", "text"));
		addValueByTypeAndFont(game, characterId, subject, 725, font);

		map.put("recognition_1", recognition);
		map.put("chalk_2", chalk);
		map.put("pen_3", pen);
		map.put("learn_4", learn);
		map.put("game_5", game);
	}

	private void addHardResourcesByJson(Map<String, Object> map, Integer characterId, String font) {
		Integer subject = 72;
		List<FileData> list = new ArrayList<FileData>();
		//认读
		List<FileData> recognition = new ArrayList<>();
		recognition.add(new FileData("character", 721, ""));
		recognition.add(new FileData("spell", 721, ""));
		recognition.add(new FileData("simple", 721, ""));
		recognition.add(new FileData("radical", 721, ""));
		recognition.add(new FileData("stroke_number", 721, ""));
		recognition.add(new FileData("paraphrase_video", 721, ""));
		recognition.add(new FileData("paraphrase_text", 721, ""));
		addValueByTypeAndFontByJson(recognition, characterId, subject, 721, font);
		list.addAll(recognition);
		//粉笔
		List<FileData> chalk = new ArrayList<>();
		chalk.add(new FileData("chalk_text", 722, ""));
		chalk.add(new FileData("chalk_video", 722, ""));
		addValueByTypeAndFontByJson(chalk, characterId, subject, 722, font);
		list.addAll(chalk);
		//钢笔
		List<FileData> pen = new ArrayList<>();
		pen.add(new FileData("pen_text", 723, ""));
		pen.add(new FileData("pen_video", 723, ""));
		addValueByTypeAndFontByJson(pen, characterId, subject, 723, font);
		list.addAll(pen);
		//识字
		List<FileData> learn = new ArrayList<>();
		learn.add(new FileData("learn_text", 724, ""));
		learn.add(new FileData("learn_video", 724, ""));
		addValueByTypeAndFontByJson(learn, characterId, subject, 724, font);
		list.addAll(learn);
		//找茬游戏
		List<FileData> game = new ArrayList<>();
		game.add(new FileData("game", 725, ""));
		addValueByTypeAndFontByJson(game, characterId, subject, 725, font);
		list.addAll(game);

		map.put("fileDatas", JSONArray.parseArray(JSON.toJSONString(list)));
	}

	@Override
	public boolean createResourceFile(String charS, Integer subject, Integer resourceType, String font,
									  String objectId, String objectType, String value) {
		List<Character> characterList = characterService.findByChars(charS);
		Integer characterId = null;
		if (characterList != null && !characterList.isEmpty()) {
			characterId = characterList.get(0).getId();
		} else {
			Character character = new Character();
			character.setCharS(charS);
			character.setCharT(charS);
			character.setKeyword(charS);
			character.setType(1);
			characterService.save(character);
			characterId = character.getId();
		}
		if (characterId == null) {
			return false;
		}

		return createResourceFile(characterId, subject, resourceType, font, objectId, objectType, value);
	}

	@Override
	public boolean createHardResourceFile(String charS, Integer resourceType, String font, String objectCode, String value) {
		String objectType = null;
		if ("character".equals(objectCode)) {
			objectType = "image";
		} else if ("chalk_video".equals(objectCode) || "pen_video".equals(objectCode) || "learn_video".equals(objectCode)) {
			objectType = "video";
		} else if ("paraphrase_video".equals(objectCode)) {
			objectType = "audio";
		} else {
			objectType = "text";
		}
		return createResourceFile(charS, 72, resourceType, font, objectCode, objectType, value);
	}

	@Override
	public boolean createSoftResourceFile(String charS, Integer resourceType, String font, String objectCode,
										  String value) {
		String objectType = null;
		if ("white_character".equals(objectCode) || "tablet".equals(objectCode)
				|| "matts".equals(objectCode) || "evolve_image".equals(objectCode)
				|| "observe_dot".equals(objectCode) || "observe_arrow".equals(objectCode)
				|| "observe_triangle".equals(objectCode) || "observe_image".equals(objectCode)
				|| "analyse_image".equals(objectCode)) {
			objectType = "image";
		} else if ("technique_line".equals(objectCode) || "technique_gesture".equals(objectCode)
				|| "learn_video".equals(objectCode)) {
			objectType = "video";
		} else if ("usage_audio".equals(objectCode) || "observe_audio".equals(objectCode)
				|| "stroke_audio".equals(objectCode) || "space_audio".equals(objectCode)) {
			objectType = "audio";
		} else {
			objectType = "text";
		}

		return createResourceFile(charS, 71, resourceType, font, objectCode, objectType, value);
	}

	@Override
	public boolean createHardResourceFile(Integer characterId, Integer resourceType, String font, String objectCode, String value) {
		String objectType = null;
		if ("character".equals(objectCode)) {
			objectType = "image";
		} else if ("chalk_video".equals(objectCode) || "pen_video".equals(objectCode) || "learn_video".equals(objectCode)) {
			objectType = "video";
		} else if ("paraphrase_video".equals(objectCode)) {
			objectType = "audio";
		} else {
			objectType = "text";
		}
		return createResourceFile(characterId, 72, resourceType, font, objectCode, objectType, value);
	}

	@Override
	public boolean createSoftResourceFile(Integer characterId, Integer resourceType, String font, String objectCode,
										  String value) {
		String objectType = null;
		if ("white_character".equals(objectCode) || "tablet".equals(objectCode)
				|| "matts".equals(objectCode) || "evolve_image".equals(objectCode)
				|| "observe_dot".equals(objectCode) || "observe_arrow".equals(objectCode)
				|| "observe_triangle".equals(objectCode) || "observe_image".equals(objectCode)
				|| "analyse_image".equals(objectCode)) {
			objectType = "image";
		} else if ("technique_line".equals(objectCode) || "technique_gesture".equals(objectCode)
				|| "learn_video".equals(objectCode)) {
			objectType = "video";
		} else if ("usage_audio".equals(objectCode) || "observe_audio".equals(objectCode)
				|| "stroke_audio".equals(objectCode) || "space_audio".equals(objectCode)) {
			objectType = "audio";
		} else {
			objectType = "text";
		}

		return createResourceFile(characterId, 71, resourceType, font, objectCode, objectType, value);
	}


	private boolean createResourceFile(Integer characterId, Integer subject, Integer resourceType, String font,
										String objectId, String objectType, String value){
		Character character = characterService.getById(characterId);

		Integer resourceId = null;
		CharacterResource cr = baseMapper.findUnion(characterId, subject, resourceType);
		if (cr != null) {
			resourceId = cr.getId();
		} else {
			cr = new CharacterResource();
			cr.setCharacterId(characterId);
			cr.setNameTr(character.getCharS());
			cr.setKeyword(character.getKeyword());
			cr.setCharS(character.getCharS());
			cr.setCharT(character.getCharT());
			cr.setEnabled(true);
			cr.setSubject(subject);
			cr.setResourceType(resourceType);
			cr.setCreateDate(LocalDateTime.now());
			baseMapper.insert(cr);
			resourceId = cr.getId();
		}
		if (resourceId == null) {
			return false;
		}

		String content = null;
		String uuid = null;
		if ("text".equals(objectType)) {
			content = value;
		} else {
			uuid = value;
		}

		CharacterResourceFile crf = characterResourceFileService.findUnionByResourceIdAndObjectId(resourceId, objectId, font);
		if(crf != null){
			crf.setContent(content);
			crf.setUuid(uuid);
			crf.setModifyDate(LocalDateTime.now());
			characterResourceFileService.updateById(crf);
		} else {
			crf = new CharacterResourceFile();
			crf.setResourceId(resourceId);
			crf.setCharacterId(characterId);
			crf.setSubject(subject);
			crf.setResourceType(resourceType);
			crf.setFont(font);
			crf.setObjectId(objectId);
			crf.setObjectType(objectType);
			crf.setContent(content);
			crf.setUuid(uuid);
			crf.setCreateDate(LocalDateTime.now());
			characterResourceFileService.save(crf);
		}
		if (crf.getId() == null) {
			return false;
		}
		return true;
	}

	@Override
	public Map<String, Object> findResourcesByJson(Integer characterId, Integer subject, String font) {
		Character character = characterService.getById(characterId);
		if (character == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("characterId", characterId);
//		map.put("name", character.getCharS());
		map.put("font", font);
		map.put("subject", subject);
//		map.put("type", character.getType());
		if (subject == 71) {
			addSoftResourcesByJson(map, characterId, font);
		} else if (subject == 72) {
			addHardResourcesByJson(map, characterId, font);
		}
		return map;
	}

	@Override
	public Map<String, Object> findCharVideoResource(Integer characterId, String font, Integer subject) {
		return baseMapper.findCharVideoResource(characterId, font, subject);
	}
}
