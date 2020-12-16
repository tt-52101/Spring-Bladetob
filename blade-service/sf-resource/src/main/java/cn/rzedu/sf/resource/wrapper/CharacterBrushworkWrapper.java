package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.CharacterBrushwork;
import cn.rzedu.sf.resource.vo.CharacterBrushworkVO;

/**
 * 汉字笔法。包含标准笔法和基本笔画，基本笔画分不同字体包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-12-15
 */
public class CharacterBrushworkWrapper extends BaseEntityWrapper<CharacterBrushwork, CharacterBrushworkVO>  {

    public static CharacterBrushworkWrapper build() {
        return new CharacterBrushworkWrapper();
    }

	@Override
	public CharacterBrushworkVO entityVO(CharacterBrushwork characterBrushwork) {
		CharacterBrushworkVO characterBrushworkVO = BeanUtil.copy(characterBrushwork, CharacterBrushworkVO.class);

		return characterBrushworkVO;
	}

}
