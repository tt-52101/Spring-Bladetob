package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.PropValue;
import cn.rzedu.sc.goods.vo.PropValueVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 属性值。说明属性项有多少个可以使用的值 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-16
 */
public interface PropValueMapper extends BaseMapper<PropValue> {

    /**
     * 自定义分页
     *
     * @param page
     * @param propValue
     * @return
     */
    List<PropValueVO> selectPropValuePage(IPage page, PropValueVO propValue);

}
