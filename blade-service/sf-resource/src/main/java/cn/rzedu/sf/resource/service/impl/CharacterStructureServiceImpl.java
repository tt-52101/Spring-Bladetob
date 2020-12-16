package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.CharacterStructure;
import cn.rzedu.sf.resource.vo.CharacterStructureVO;
import cn.rzedu.sf.resource.mapper.CharacterStructureMapper;
import cn.rzedu.sf.resource.service.ICharacterStructureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.*;

/**
 * 汉字结构 服务实现类
 *
 * @author Blade
 * @since 2020-12-10
 */
@Service
public class CharacterStructureServiceImpl extends ServiceImpl<CharacterStructureMapper, CharacterStructure> implements ICharacterStructureService {

	@Override
	public IPage<CharacterStructureVO> selectCharacterStructurePage(IPage<CharacterStructureVO> page, CharacterStructureVO characterStructure) {
		return page.setRecords(baseMapper.selectCharacterStructurePage(page, characterStructure));
	}

	@Override
	public List<Map<String, Object>> findAll() {
		List<CharacterStructure> list = baseMapper.findAllStructure();
		if (list == null || list.isEmpty()) {
			return null;
		}

		Map<String, List<CharacterStructure>> map = new LinkedHashMap<>();
		List<CharacterStructure> valueList = null;
		String type = "";
		for (CharacterStructure stroke : list) {
			type = stroke.getType();
			if (map.containsKey(type)) {
				valueList = map.get(type);
			} else {
				valueList = new ArrayList<>();
			}
			valueList.add(stroke);
			map.put(type, valueList);
		}

		List<Map<String, Object>> result = new ArrayList<>(map.size());
		Map<String, Object> resultMap = null;
		for (Map.Entry<String, List<CharacterStructure>> entry : map.entrySet()) {
			resultMap = new HashMap<>(2);
			resultMap.put("type", entry.getKey());
			resultMap.put("list", transferData(entry.getValue()));
			result.add(resultMap);
		}
		return result;
	}

	private List<Map<String, Object>> transferData(List<CharacterStructure> list) {
		List<Map<String, Object>> result = new ArrayList<>();
		if (list == null || list.isEmpty()) {
			return null;
		}
		Map<String, Object> map = null;
		for (CharacterStructure stroke : list) {
			map = new HashMap<>(8);
			map.put("name", stroke.getName());
			map.put("image", stroke.getImage());
			map.put("recognitionImage", stroke.getRecognitionImage());
			map.put("recognitionVideo", stroke.getRecognitionVideo());
			result.add(map);
		}
		return result;
	}

	@Override
	public Map<String, Object> findByName(String name) {
		CharacterStructure stroke = baseMapper.findByName(name);
		if (stroke == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<>(9);
		map.put("type", stroke.getType());
		map.put("name", stroke.getName());
		map.put("image", stroke.getImage());
		map.put("recognitionImage", stroke.getRecognitionImage());
		map.put("recognitionVideo", stroke.getRecognitionVideo());
		return map;
	}
}
