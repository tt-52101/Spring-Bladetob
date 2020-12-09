package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.UserCharacter;
import cn.rzedu.sf.user.vo.UserCharacterVO;

/**
 * 学生汉字学习的情况
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-08-05
 */
public class UserCharacterWrapper extends BaseEntityWrapper<UserCharacter, UserCharacterVO> {

    public static UserCharacterWrapper build() {
        return new UserCharacterWrapper();
    }

    @Override
    public UserCharacterVO entityVO(UserCharacter userCharacter) {
        UserCharacterVO userCharacterVO = BeanUtil.copy(userCharacter, UserCharacterVO.class);

        return userCharacterVO;
    }

}
