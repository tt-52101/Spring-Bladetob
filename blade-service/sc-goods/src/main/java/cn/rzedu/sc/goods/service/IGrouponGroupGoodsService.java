package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.GrouponGroupGoods;
import cn.rzedu.sc.goods.vo.GrouponGroupGoodsVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 团购的商品。某团购团购买的商品项目 服务类
 *
 * @author Blade
 * @since 2020-10-16
 */
public interface IGrouponGroupGoodsService extends IService<GrouponGroupGoods> {

    /**
     * 自定义分页
     *
     * @param page
     * @param grouponGroupGoods
     * @return
     */
    IPage<GrouponGroupGoodsVO> selectGrouponGroupGoodsPage(IPage<GrouponGroupGoodsVO> page,
                                                           GrouponGroupGoodsVO grouponGroupGoods);

    List<GrouponGroupGoodsVO> findByGrouponGroupId(Integer grouponGroupId);
}
