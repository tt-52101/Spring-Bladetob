package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.UserLesson;
import cn.rzedu.sf.user.vo.UserLessonVO;

/**
 * 学生课程学习情况表
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-08-05
 */
public class UserLessonWrapper extends BaseEntityWrapper<UserLesson, UserLessonVO> {

    public static UserLessonWrapper build() {
        return new UserLessonWrapper();
    }

    @Override
    public UserLessonVO entityVO(UserLesson userLesson) {
        UserLessonVO userLessonVO = BeanUtil.copy(userLesson, UserLessonVO.class);

        return userLessonVO;
    }

}
