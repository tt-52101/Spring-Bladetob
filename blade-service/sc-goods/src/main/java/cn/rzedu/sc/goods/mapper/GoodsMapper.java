package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.Goods;
import cn.rzedu.sc.goods.vo.GoodsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 商品表，即SPU：Standard Product Unit （标准产品单位），是商品信息聚合的最小单位 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface GoodsMapper extends BaseMapper<Goods> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param goods
	 * @return
	 */
	List<GoodsVO> selectGoodsPage(IPage page, GoodsVO goods);

	/**
	 * 根据编号查询
	 * @param code
	 * @return
	 */
	List<Goods> findByCode(String code);
}
