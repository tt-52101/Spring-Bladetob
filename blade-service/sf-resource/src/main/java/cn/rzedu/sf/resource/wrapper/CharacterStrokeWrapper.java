package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.CharacterStroke;
import cn.rzedu.sf.resource.vo.CharacterStrokeVO;

/**
 * 汉字笔画包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-12-10
 */
public class CharacterStrokeWrapper extends BaseEntityWrapper<CharacterStroke, CharacterStrokeVO>  {

    public static CharacterStrokeWrapper build() {
        return new CharacterStrokeWrapper();
    }

	@Override
	public CharacterStrokeVO entityVO(CharacterStroke characterStroke) {
		CharacterStrokeVO characterStrokeVO = BeanUtil.copy(characterStroke, CharacterStrokeVO.class);

		return characterStrokeVO;
	}

}
