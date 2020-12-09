package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.PropValue;
import cn.rzedu.sc.goods.vo.PropValueVO;
import cn.rzedu.sc.goods.mapper.PropValueMapper;
import cn.rzedu.sc.goods.service.IPropValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 属性值。说明属性项有多少个可以使用的值 服务实现类
 *
 * @author Blade
 * @since 2020-10-16
 */
@Service
public class PropValueServiceImpl extends ServiceImpl<PropValueMapper, PropValue> implements IPropValueService {

    @Override
    public IPage<PropValueVO> selectPropValuePage(IPage<PropValueVO> page, PropValueVO propValue) {
        return page.setRecords(baseMapper.selectPropValuePage(page, propValue));
    }

}
