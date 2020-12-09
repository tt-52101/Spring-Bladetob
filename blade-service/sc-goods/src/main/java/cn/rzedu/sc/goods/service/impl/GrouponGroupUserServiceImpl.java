package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.GrouponGroupUser;
import cn.rzedu.sc.goods.vo.GrouponGroupUserVO;
import cn.rzedu.sc.goods.mapper.GrouponGroupUserMapper;
import cn.rzedu.sc.goods.service.IGrouponGroupUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 团购成团的成员。说明某个参团的成员，是groupon_group表的子表 服务实现类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Service
public class GrouponGroupUserServiceImpl extends ServiceImpl<GrouponGroupUserMapper, GrouponGroupUser> implements IGrouponGroupUserService {

    @Override
    public IPage<GrouponGroupUserVO> selectGrouponGroupUserPage(IPage<GrouponGroupUserVO> page,
																GrouponGroupUserVO grouponGroupUser) {
        return page.setRecords(baseMapper.selectGrouponGroupUserPage(page, grouponGroupUser));
    }

    @Override
    public List<GrouponGroupUserVO> findByGrouponGroupId(Integer grouponGroupId) {
        return baseMapper.findByGrouponGroupId(grouponGroupId);
    }
}
