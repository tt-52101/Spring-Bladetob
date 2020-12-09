package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.BuriedPointUser;
import cn.rzedu.sc.goods.vo.BuriedPointUserVO;
import cn.rzedu.sc.goods.mapper.BuriedPointUserMapper;
import cn.rzedu.sc.goods.service.IBuriedPointUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;

/**
 * 商城埋点-操作人员 服务实现类
 *
 * @author Blade
 * @since 2020-12-02
 */
@Service
public class BuriedPointUserServiceImpl extends ServiceImpl<BuriedPointUserMapper, BuriedPointUser> implements IBuriedPointUserService {

    @Override
    public IPage<BuriedPointUserVO> selectBuriedPointUserPage(IPage<BuriedPointUserVO> page,
                                                              BuriedPointUserVO buriedPointUser) {
        return page.setRecords(baseMapper.selectBuriedPointUserPage(page, buriedPointUser));
    }

    @Override
    public BuriedPointUser findByUserId(Integer buriedPointId, Integer userId, LocalDateTime time) {
        return baseMapper.findByUserId(buriedPointId, userId, time);
    }

    @Override
    public boolean insertBuriedUser(Integer buriedPointId, Integer userId) {
        BuriedPointUser pointUser = baseMapper.findByUserId(buriedPointId, userId, null);
        if (pointUser != null) {
            return false;
        }
        pointUser = new BuriedPointUser();
        pointUser.setBuriedPointId(buriedPointId);
        pointUser.setUserId(userId);
        pointUser.setCreateDate(LocalDateTime.now());
        baseMapper.insert(pointUser);
        return true;
    }
}
