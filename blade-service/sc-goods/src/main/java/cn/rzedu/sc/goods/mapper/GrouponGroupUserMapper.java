package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.GrouponGroupUser;
import cn.rzedu.sc.goods.vo.GrouponGroupUserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 团购成团的成员。说明某个参团的成员，是groupon_group表的子表 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface GrouponGroupUserMapper extends BaseMapper<GrouponGroupUser> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param grouponGroupUser
	 * @return
	 */
	List<GrouponGroupUserVO> selectGrouponGroupUserPage(IPage page, GrouponGroupUserVO grouponGroupUser);

	List<GrouponGroupUserVO> findByGrouponGroupId(Integer grouponGroupId);
}
