package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.CharacterResourceFile;
import cn.rzedu.sf.resource.vo.CharacterResourceFileVO;

/**
 * 汉字资源文件包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-05
 */
public class CharacterResourceFileWrapper extends BaseEntityWrapper<CharacterResourceFile, CharacterResourceFileVO> {

    public static CharacterResourceFileWrapper build() {
        return new CharacterResourceFileWrapper();
    }

    @Override
    public CharacterResourceFileVO entityVO(CharacterResourceFile characterResourceFile) {
        CharacterResourceFileVO characterResourceFileVO = BeanUtil.copy(characterResourceFile,
				CharacterResourceFileVO.class);

        return characterResourceFileVO;
    }

}
