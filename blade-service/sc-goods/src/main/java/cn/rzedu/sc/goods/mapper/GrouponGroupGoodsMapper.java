package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.GrouponGroupGoods;
import cn.rzedu.sc.goods.vo.GrouponGroupGoodsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 团购的商品。某团购团购买的商品项目 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-16
 */
public interface GrouponGroupGoodsMapper extends BaseMapper<GrouponGroupGoods> {

    /**
     * 自定义分页
     *
     * @param page
     * @param grouponGroupGoods
     * @return
     */
    List<GrouponGroupGoodsVO> selectGrouponGroupGoodsPage(IPage page, GrouponGroupGoodsVO grouponGroupGoods);

    /**
     *
     * @param grouponGroupId
     * @return
     */
    List<GrouponGroupGoodsVO> findByGrouponGroupId(Integer grouponGroupId);
}
