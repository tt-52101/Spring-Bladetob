package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.PropKey;
import cn.rzedu.sc.goods.vo.PropKeyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 属性项。描述商品的属性，从描述特征有SPU属性和SKU属性，从描述范围有类目属性和商品属性，本表是类目属性 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-16
 */
public interface PropKeyMapper extends BaseMapper<PropKey> {

    /**
     * 自定义分页
     *
     * @param page
     * @param propKey
     * @return
     */
    List<PropKeyVO> selectPropKeyPage(IPage page, PropKeyVO propKey);

}
