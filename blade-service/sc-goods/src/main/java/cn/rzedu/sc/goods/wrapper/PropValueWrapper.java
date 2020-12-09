package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.PropValue;
import cn.rzedu.sc.goods.vo.PropValueVO;

/**
 * 属性值。说明属性项有多少个可以使用的值包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-16
 */
public class PropValueWrapper extends BaseEntityWrapper<PropValue, PropValueVO>  {

    public static PropValueWrapper build() {
        return new PropValueWrapper();
    }

	@Override
	public PropValueVO entityVO(PropValue propValue) {
		PropValueVO propValueVO = BeanUtil.copy(propValue, PropValueVO.class);

		return propValueVO;
	}

}
