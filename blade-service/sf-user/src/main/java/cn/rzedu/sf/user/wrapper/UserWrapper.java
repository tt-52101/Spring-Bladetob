package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.User;
import cn.rzedu.sf.user.vo.UserVO;

/**
 * 用户表
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-08-05
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

    public static UserWrapper build() {
        return new UserWrapper();
    }

    @Override
    public UserVO entityVO(User user) {
        UserVO userVO = BeanUtil.copy(user, UserVO.class);

        return userVO;
    }

}
