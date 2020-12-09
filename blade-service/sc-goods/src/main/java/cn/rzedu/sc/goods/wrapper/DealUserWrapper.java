package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.DealUser;
import cn.rzedu.sc.goods.vo.DealUserVO;

/**
 *  用户-商品交易记录表。以用户和商品为单位的成交流水明细包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-12
 */
public class DealUserWrapper extends BaseEntityWrapper<DealUser, DealUserVO>  {

    public static DealUserWrapper build() {
        return new DealUserWrapper();
    }

	@Override
	public DealUserVO entityVO(DealUser dealUser) {
		DealUserVO dealUserVO = BeanUtil.copy(dealUser, DealUserVO.class);

		return dealUserVO;
	}

}
