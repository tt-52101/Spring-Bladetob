package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.CharacterResource;
import cn.rzedu.sf.resource.vo.CharacterResourceVO;

/**
 * 汉字资源
 * 汉字的（视频）资源
 * 一个汉字会有多个资源记录，软笔和硬笔资源都放在这个表，软硬笔作为不同科目来区分
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-08-05
 */
public class CharacterResourceWrapper extends BaseEntityWrapper<CharacterResource, CharacterResourceVO> {

    public static CharacterResourceWrapper build() {
        return new CharacterResourceWrapper();
    }

    @Override
    public CharacterResourceVO entityVO(CharacterResource characterResource) {
        CharacterResourceVO characterResourceVO = BeanUtil.copy(characterResource, CharacterResourceVO.class);

        return characterResourceVO;
    }

}
