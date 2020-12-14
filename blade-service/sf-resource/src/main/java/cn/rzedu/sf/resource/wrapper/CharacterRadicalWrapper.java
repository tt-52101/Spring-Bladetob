package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.CharacterRadical;
import cn.rzedu.sf.resource.vo.CharacterRadicalVO;

/**
 * 汉字偏旁包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-12-10
 */
public class CharacterRadicalWrapper extends BaseEntityWrapper<CharacterRadical, CharacterRadicalVO>  {

    public static CharacterRadicalWrapper build() {
        return new CharacterRadicalWrapper();
    }

	@Override
	public CharacterRadicalVO entityVO(CharacterRadical characterRadical) {
		CharacterRadicalVO characterRadicalVO = BeanUtil.copy(characterRadical, CharacterRadicalVO.class);

		return characterRadicalVO;
	}

}
