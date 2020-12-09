package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.DealUser;
import cn.rzedu.sc.goods.vo.DealUserVO;
import cn.rzedu.sc.goods.mapper.DealUserMapper;
import cn.rzedu.sc.goods.service.IDealUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用户-商品交易记录表。以用户和商品为单位的成交流水明细 服务实现类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Service
public class DealUserServiceImpl extends ServiceImpl<DealUserMapper, DealUser> implements IDealUserService {

    @Override
    public IPage<DealUserVO> selectDealUserPage(IPage<DealUserVO> page, DealUserVO dealUser) {
        return page.setRecords(baseMapper.selectDealUserPage(page, dealUser));
    }

}
