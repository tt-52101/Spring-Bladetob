package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.GrouponRule;
import cn.rzedu.sc.goods.vo.GrouponRuleVO;

/**
 * 团购规则。一个团购规则记录是针对一个商品进行一次团购销售而设计的包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-12
 */
public class GrouponRuleWrapper extends BaseEntityWrapper<GrouponRule, GrouponRuleVO> {

    public static GrouponRuleWrapper build() {
        return new GrouponRuleWrapper();
    }

    @Override
    public GrouponRuleVO entityVO(GrouponRule grouponRule) {
        GrouponRuleVO grouponRuleVO = BeanUtil.copy(grouponRule, GrouponRuleVO.class);

        return grouponRuleVO;
    }

}
