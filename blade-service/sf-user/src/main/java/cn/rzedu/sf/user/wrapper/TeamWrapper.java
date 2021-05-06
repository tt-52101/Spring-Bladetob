package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.Team;
import cn.rzedu.sf.user.vo.TeamVO;

/**
 * 班级包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-21
 */
public class TeamWrapper extends BaseEntityWrapper<Team, TeamVO>  {

    public static TeamWrapper build() {
        return new TeamWrapper();
    }

	@Override
	public TeamVO entityVO(Team team) {
		TeamVO teamVO = BeanUtil.copy(team, TeamVO.class);

		return teamVO;
	}

}
