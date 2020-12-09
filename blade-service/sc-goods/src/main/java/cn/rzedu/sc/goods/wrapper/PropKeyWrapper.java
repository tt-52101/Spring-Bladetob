package cn.rzedu.sc.goods.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sc.goods.entity.PropKey;
import cn.rzedu.sc.goods.vo.PropKeyVO;

/**
 * 属性项。描述商品的属性，从描述特征有SPU属性和SKU属性，从描述范围有类目属性和商品属性，本表是类目属性包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-10-16
 */
public class PropKeyWrapper extends BaseEntityWrapper<PropKey, PropKeyVO> {

    public static PropKeyWrapper build() {
        return new PropKeyWrapper();
    }

    @Override
    public PropKeyVO entityVO(PropKey propKey) {
        PropKeyVO propKeyVO = BeanUtil.copy(propKey, PropKeyVO.class);

        return propKeyVO;
    }

}
