package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.CharacterBrushwork;
import cn.rzedu.sf.resource.vo.CharacterBrushworkVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 汉字笔法。包含标准笔法和基本笔画，基本笔画分不同字体 服务类
 *
 * @author Blade
 * @since 2020-12-15
 */
public interface ICharacterBrushworkService extends IService<CharacterBrushwork> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param characterBrushwork
	 * @return
	 */
	IPage<CharacterBrushworkVO> selectCharacterBrushworkPage(IPage<CharacterBrushworkVO> page,
                                                             CharacterBrushworkVO characterBrushwork);

	/**
	 * 获取标准笔法和基本笔画
	 * @param font
	 * @return
	 */
	Map<String, Object> findBrushwork(String font);
}
