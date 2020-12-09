package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.Brand;
import cn.rzedu.sc.goods.vo.BrandVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 商品品牌 服务类
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface IBrandService extends IService<Brand> {

    /**
     * 自定义分页
     *
     * @param page
     * @param brand
     * @return
     */
    IPage<BrandVO> selectBrandPage(IPage<BrandVO> page, BrandVO brand);

}
