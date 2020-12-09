package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.UserLoginLog;
import cn.rzedu.sf.user.vo.UserLoginLogVO;

/**
 * 用户每日登陆记录包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-09-24
 */
public class UserLoginLogWrapper extends BaseEntityWrapper<UserLoginLog, UserLoginLogVO> {

    public static UserLoginLogWrapper build() {
        return new UserLoginLogWrapper();
    }

    @Override
    public UserLoginLogVO entityVO(UserLoginLog userLoginLog) {
        UserLoginLogVO userLoginLogVO = BeanUtil.copy(userLoginLog, UserLoginLogVO.class);

        return userLoginLogVO;
    }

}
