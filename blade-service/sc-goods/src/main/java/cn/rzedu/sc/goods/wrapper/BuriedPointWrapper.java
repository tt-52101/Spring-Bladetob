package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.BuriedPoint;
import cn.rzedu.sc.goods.vo.BuriedPointVO;

/**
 * 商城埋点包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-12-02
 */
public class BuriedPointWrapper extends BaseEntityWrapper<BuriedPoint, BuriedPointVO> {

    public static BuriedPointWrapper build() {
        return new BuriedPointWrapper();
    }

    @Override
    public BuriedPointVO entityVO(BuriedPoint buriedPoint) {
        BuriedPointVO buriedPointVO = BeanUtil.copy(buriedPoint, BuriedPointVO.class);

        return buriedPointVO;
    }

}
