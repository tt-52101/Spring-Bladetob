package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.PropValue;
import cn.rzedu.sc.goods.vo.PropValueVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 属性值。说明属性项有多少个可以使用的值 服务类
 *
 * @author Blade
 * @since 2020-10-16
 */
public interface IPropValueService extends IService<PropValue> {

    /**
     * 自定义分页
     *
     * @param page
     * @param propValue
     * @return
     */
    IPage<PropValueVO> selectPropValuePage(IPage<PropValueVO> page, PropValueVO propValue);

}
