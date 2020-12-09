package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.GrouponGroupGoods;
import cn.rzedu.sc.goods.vo.GrouponGroupGoodsVO;

/**
 * 团购的商品。某团购团购买的商品项目包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-16
 */
public class GrouponGroupGoodsWrapper extends BaseEntityWrapper<GrouponGroupGoods, GrouponGroupGoodsVO> {

    public static GrouponGroupGoodsWrapper build() {
        return new GrouponGroupGoodsWrapper();
    }

    @Override
    public GrouponGroupGoodsVO entityVO(GrouponGroupGoods grouponGroupGoods) {
        GrouponGroupGoodsVO grouponGroupGoodsVO = BeanUtil.copy(grouponGroupGoods, GrouponGroupGoodsVO.class);

        return grouponGroupGoodsVO;
    }

}
