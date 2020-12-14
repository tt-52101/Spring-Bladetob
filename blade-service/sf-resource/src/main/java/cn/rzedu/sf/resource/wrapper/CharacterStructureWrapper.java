package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.CharacterStructure;
import cn.rzedu.sf.resource.vo.CharacterStructureVO;

/**
 * 汉字结构包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-12-10
 */
public class CharacterStructureWrapper extends BaseEntityWrapper<CharacterStructure, CharacterStructureVO>  {

    public static CharacterStructureWrapper build() {
        return new CharacterStructureWrapper();
    }

	@Override
	public CharacterStructureVO entityVO(CharacterStructure characterStructure) {
		CharacterStructureVO characterStructureVO = BeanUtil.copy(characterStructure, CharacterStructureVO.class);

		return characterStructureVO;
	}

}
