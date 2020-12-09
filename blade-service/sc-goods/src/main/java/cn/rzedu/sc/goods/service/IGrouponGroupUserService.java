package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.GrouponGroupUser;
import cn.rzedu.sc.goods.vo.GrouponGroupUserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 团购成团的成员。说明某个参团的成员，是groupon_group表的子表 服务类
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface IGrouponGroupUserService extends IService<GrouponGroupUser> {

    /**
     * 自定义分页
     *
     * @param page
     * @param grouponGroupUser
     * @return
     */
    IPage<GrouponGroupUserVO> selectGrouponGroupUserPage(IPage<GrouponGroupUserVO> page,
                                                         GrouponGroupUserVO grouponGroupUser);

    List<GrouponGroupUserVO> findByGrouponGroupId(Integer grouponGroupId);
}
