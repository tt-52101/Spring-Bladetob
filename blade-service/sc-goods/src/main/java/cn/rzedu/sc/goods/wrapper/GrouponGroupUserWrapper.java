package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.GrouponGroupUser;
import cn.rzedu.sc.goods.vo.GrouponGroupUserVO;

/**
 * 团购成团的成员。说明某个参团的成员，是groupon_group表的子表包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-12
 */
public class GrouponGroupUserWrapper extends BaseEntityWrapper<GrouponGroupUser, GrouponGroupUserVO>  {

    public static GrouponGroupUserWrapper build() {
        return new GrouponGroupUserWrapper();
    }

	@Override
	public GrouponGroupUserVO entityVO(GrouponGroupUser grouponGroupUser) {
		GrouponGroupUserVO grouponGroupUserVO = BeanUtil.copy(grouponGroupUser, GrouponGroupUserVO.class);

		return grouponGroupUserVO;
	}

}
