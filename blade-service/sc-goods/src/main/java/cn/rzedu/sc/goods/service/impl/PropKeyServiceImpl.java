package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.PropKey;
import cn.rzedu.sc.goods.vo.PropKeyVO;
import cn.rzedu.sc.goods.mapper.PropKeyMapper;
import cn.rzedu.sc.goods.service.IPropKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 属性项。描述商品的属性，从描述特征有SPU属性和SKU属性，从描述范围有类目属性和商品属性，本表是类目属性 服务实现类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Service
public class PropKeyServiceImpl extends ServiceImpl<PropKeyMapper, PropKey> implements IPropKeyService {

    @Override
    public IPage<PropKeyVO> selectPropKeyPage(IPage<PropKeyVO> page, PropKeyVO propKey) {
        return page.setRecords(baseMapper.selectPropKeyPage(page, propKey));
    }

}
