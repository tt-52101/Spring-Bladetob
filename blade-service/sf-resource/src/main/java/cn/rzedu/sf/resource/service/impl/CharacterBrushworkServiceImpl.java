package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.CharacterBrushwork;
import cn.rzedu.sf.resource.vo.CharacterBrushworkVO;
import cn.rzedu.sf.resource.mapper.CharacterBrushworkMapper;
import cn.rzedu.sf.resource.service.ICharacterBrushworkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 汉字笔法。包含标准笔法和基本笔画，基本笔画分不同字体 服务实现类
 *
 * @author Blade
 * @since 2020-12-15
 */
@Service
public class CharacterBrushworkServiceImpl extends ServiceImpl<CharacterBrushworkMapper, CharacterBrushwork> implements ICharacterBrushworkService {

	@Override
	public IPage<CharacterBrushworkVO> selectCharacterBrushworkPage(IPage<CharacterBrushworkVO> page, CharacterBrushworkVO characterBrushwork) {
		return page.setRecords(baseMapper.selectCharacterBrushworkPage(page, characterBrushwork));
	}

	@Override
	public Map<String, Object> findBrushwork(String font) {
		Map<String, Object> map = new HashMap<>(2);
		List<CharacterBrushwork> standardList = baseMapper.findByType(1, null);
		List<CharacterBrushwork> basicList = baseMapper.findByType(2, font);
		//标准笔法
		List<Map<String, Object>> standard = getContent(standardList);
		//基本笔画
		List<Map<String, Object>> basic = getContent(basicList);
		map.put("standard", standard);
		map.put("basic", basic);
		return map;
	}

	private List<Map<String, Object>> getContent(List<CharacterBrushwork> list) {
		List<Map<String, Object>> result = new ArrayList<>();
		if (list != null && !list.isEmpty()) {
			Map<String, Object> map = null;
			for (CharacterBrushwork brushwork : list) {
				map = new HashMap<>(4);
				map.put("name", brushwork.getName());
				map.put("content", brushwork.getContent());
				map.put("audio", brushwork.getAudioId());
				map.put("video", brushwork.getVideoId());
				result.add(map);
			}
		}
		return result;
	}
}
