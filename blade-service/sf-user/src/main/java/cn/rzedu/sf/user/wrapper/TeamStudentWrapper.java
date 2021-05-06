package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.TeamStudent;
import cn.rzedu.sf.user.vo.TeamStudentVO;

/**
 * 班级学生包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-21
 */
public class TeamStudentWrapper extends BaseEntityWrapper<TeamStudent, TeamStudentVO>  {

    public static TeamStudentWrapper build() {
        return new TeamStudentWrapper();
    }

	@Override
	public TeamStudentVO entityVO(TeamStudent teamStudent) {
		TeamStudentVO teamStudentVO = BeanUtil.copy(teamStudent, TeamStudentVO.class);

		return teamStudentVO;
	}

}
