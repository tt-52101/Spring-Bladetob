package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.GrouponGroup;
import cn.rzedu.sc.goods.vo.GrouponGroupVO;

/**
 * 团购成团记录。针对某个团购的形成的一个团的总体情况包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-12
 */
public class GrouponGroupWrapper extends BaseEntityWrapper<GrouponGroup, GrouponGroupVO> {

    public static GrouponGroupWrapper build() {
        return new GrouponGroupWrapper();
    }

    @Override
    public GrouponGroupVO entityVO(GrouponGroup grouponGroup) {
        GrouponGroupVO grouponGroupVO = BeanUtil.copy(grouponGroup, GrouponGroupVO.class);

        return grouponGroupVO;
    }

}
