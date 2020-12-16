package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.CharacterStroke;
import cn.rzedu.sf.resource.vo.CharacterStrokeVO;
import cn.rzedu.sf.resource.mapper.CharacterStrokeMapper;
import cn.rzedu.sf.resource.service.ICharacterStrokeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.*;

/**
 * 汉字笔画 服务实现类
 *
 * @author Blade
 * @since 2020-12-10
 */
@Service
public class CharacterStrokeServiceImpl extends ServiceImpl<CharacterStrokeMapper, CharacterStroke> implements ICharacterStrokeService {

	@Override
	public IPage<CharacterStrokeVO> selectCharacterStrokePage(IPage<CharacterStrokeVO> page, CharacterStrokeVO characterStroke) {
		return page.setRecords(baseMapper.selectCharacterStrokePage(page, characterStroke));
	}

	@Override
	public List<Map<String, Object>> findAll() {
		List<CharacterStroke> list = baseMapper.findAllStroke();
		if (list == null || list.isEmpty()) {
			return null;
		}

		//key=type，value为list进行分组
		Map<String, List<CharacterStroke>> map = new LinkedHashMap<>();
		List<CharacterStroke> valueList = null;
		String type = "";
		for (CharacterStroke stroke : list) {
			type = stroke.getType();
			if (map.containsKey(type)) {
				valueList = map.get(type);
			} else {
				valueList = new ArrayList<>();
			}
			valueList.add(stroke);
			map.put(type, valueList);
		}

		//将map转为list存储
		List<Map<String, Object>> result = new ArrayList<>(map.size());
		Map<String, Object> resultMap = null;
		for (Map.Entry<String, List<CharacterStroke>> entry : map.entrySet()) {
			resultMap = new HashMap<>(2);
			resultMap.put("type", entry.getKey());
			resultMap.put("list", transferData(entry.getValue()));
			result.add(resultMap);
		}
		return result;
	}

	private List<Map<String, Object>> transferData(List<CharacterStroke> list) {
		List<Map<String, Object>> result = new ArrayList<>();
		if (list == null || list.isEmpty()) {
			return null;
		}
		Map<String, Object> map = null;
		for (CharacterStroke stroke : list) {
			map = new HashMap<>(8);
			map.put("name", stroke.getName());
			map.put("image", stroke.getImage());
			map.put("recognitionImage", stroke.getRecognitionImage());
			map.put("recognitionVideo", stroke.getRecognitionVideo());
			map.put("chalkText", stroke.getChalkText());
			map.put("chalkVideo", stroke.getChalkVideo());
			map.put("penText", stroke.getPenText());
			map.put("penVideo", stroke.getPenVideo());
			result.add(map);
		}
		return result;
	}

	@Override
	public Map<String, Object> findByName(String name) {
		CharacterStroke stroke = baseMapper.findByName(name);
		if (stroke == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<>(9);
		map.put("type", stroke.getType());
		map.put("name", stroke.getName());
		map.put("image", stroke.getImage());
		map.put("recognitionImage", stroke.getRecognitionImage());
		map.put("recognitionVideo", stroke.getRecognitionVideo());
		map.put("chalkText", stroke.getChalkText());
		map.put("chalkVideo", stroke.getChalkVideo());
		map.put("penText", stroke.getPenText());
		map.put("penVideo", stroke.getPenVideo());
		return map;
	}
}
