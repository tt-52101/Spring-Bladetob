package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.Goods;
import cn.rzedu.sc.goods.vo.GoodsVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 商品表，即SPU：Standard Product Unit （标准产品单位），是商品信息聚合的最小单位 服务类
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface IGoodsService extends IService<Goods> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param goods
	 * @return
	 */
	IPage<GoodsVO> selectGoodsPage(IPage<GoodsVO> page, GoodsVO goods);

	/**
	 * 根据商品编号判断是否重复
	 * @param code
	 * @param id
	 * @return	true=已存在/重复
	 */
	boolean judgeRepeatByCode(String code, Integer id);

	/**
	 * 通用课程--商品
	 * @return
	 */
	List<Goods> findCourseGoods();
}
