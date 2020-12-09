package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.UserTextbook;
import cn.rzedu.sf.user.vo.UserTextbookVO;

/**
 * 学生教材学习情况表
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-08-05
 */
public class UserTextbookWrapper extends BaseEntityWrapper<UserTextbook, UserTextbookVO> {

    public static UserTextbookWrapper build() {
        return new UserTextbookWrapper();
    }

    @Override
    public UserTextbookVO entityVO(UserTextbook userTextbook) {
        UserTextbookVO userTextbookVO = BeanUtil.copy(userTextbook, UserTextbookVO.class);

        return userTextbookVO;
    }

}
