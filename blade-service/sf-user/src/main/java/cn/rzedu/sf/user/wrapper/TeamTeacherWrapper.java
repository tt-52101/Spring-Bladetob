package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.TeamTeacher;
import cn.rzedu.sf.user.vo.TeamTeacherVO;

/**
 * 班级学生包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-21
 */
public class TeamTeacherWrapper extends BaseEntityWrapper<TeamTeacher, TeamTeacherVO>  {

    public static TeamTeacherWrapper build() {
        return new TeamTeacherWrapper();
    }

	@Override
	public TeamTeacherVO entityVO(TeamTeacher teamTeacher) {
		TeamTeacherVO teamTeacherVO = BeanUtil.copy(teamTeacher, TeamTeacherVO.class);

		return teamTeacherVO;
	}

}
