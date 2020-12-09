package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.Brand;
import cn.rzedu.sc.goods.vo.BrandVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 商品品牌 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface BrandMapper extends BaseMapper<Brand> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param brand
	 * @return
	 */
	List<BrandVO> selectBrandPage(IPage page, BrandVO brand);

}
