package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.BuriedPointUser;
import cn.rzedu.sc.goods.vo.BuriedPointUserVO;

/**
 * 商城埋点-操作人员包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-12-02
 */
public class BuriedPointUserWrapper extends BaseEntityWrapper<BuriedPointUser, BuriedPointUserVO> {

    public static BuriedPointUserWrapper build() {
        return new BuriedPointUserWrapper();
    }

    @Override
    public BuriedPointUserVO entityVO(BuriedPointUser buriedPointUser) {
        BuriedPointUserVO buriedPointUserVO = BeanUtil.copy(buriedPointUser, BuriedPointUserVO.class);

        return buriedPointUserVO;
    }

}
