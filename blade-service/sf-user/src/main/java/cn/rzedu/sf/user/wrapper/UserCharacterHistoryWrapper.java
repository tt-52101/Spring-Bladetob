package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.UserCharacterHistory;
import cn.rzedu.sf.user.vo.UserCharacterHistoryVO;

/**
 * 学生学习汉字的历史记录
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-08-05
 */
public class UserCharacterHistoryWrapper extends BaseEntityWrapper<UserCharacterHistory, UserCharacterHistoryVO> {

    public static UserCharacterHistoryWrapper build() {
        return new UserCharacterHistoryWrapper();
    }

    @Override
    public UserCharacterHistoryVO entityVO(UserCharacterHistory userCharacterHistory) {
        UserCharacterHistoryVO userCharacterHistoryVO = BeanUtil.copy(userCharacterHistory,
				UserCharacterHistoryVO.class);

        return userCharacterHistoryVO;
    }

}
