package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.CharacterBrushwork;
import cn.rzedu.sf.resource.vo.CharacterBrushworkVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 汉字笔法。包含标准笔法和基本笔画，基本笔画分不同字体 Mapper 接口
 *
 * @author Blade
 * @since 2020-12-15
 */
public interface CharacterBrushworkMapper extends BaseMapper<CharacterBrushwork> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param characterBrushwork
	 * @return
	 */
	List<CharacterBrushworkVO> selectCharacterBrushworkPage(IPage page, CharacterBrushworkVO characterBrushwork);

	/**
	 * 根据类型获取
	 * @param type
	 * @param font
	 * @return
	 */
	List<CharacterBrushwork> findByType(Integer type, String font);
}
