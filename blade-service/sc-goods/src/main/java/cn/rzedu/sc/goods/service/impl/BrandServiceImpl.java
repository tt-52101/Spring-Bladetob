package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.Brand;
import cn.rzedu.sc.goods.vo.BrandVO;
import cn.rzedu.sc.goods.mapper.BrandMapper;
import cn.rzedu.sc.goods.service.IBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 商品品牌 服务实现类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Override
    public IPage<BrandVO> selectBrandPage(IPage<BrandVO> page, BrandVO brand) {
        return page.setRecords(baseMapper.selectBrandPage(page, brand));
    }

}
