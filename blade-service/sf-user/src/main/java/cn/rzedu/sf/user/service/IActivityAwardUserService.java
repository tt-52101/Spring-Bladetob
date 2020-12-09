package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.ActivityAwardUser;
import cn.rzedu.sf.user.vo.ActivityAwardUserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 活动奖品领取用户 服务类
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface IActivityAwardUserService extends IService<ActivityAwardUser> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param activityAwardUser
	 * @return
	 */
	IPage<ActivityAwardUserVO> selectActivityAwardUserPage(IPage<ActivityAwardUserVO> page,
                                                           ActivityAwardUserVO activityAwardUser);

	/**
	 * 获取 或新增 用户奖品
	 * @param type
	 * @param userId
	 * @param awardType 奖品类型，可为null
	 * @return
	 */
	ActivityAwardUser findByTypeAndUserId(Integer type, Integer userId, Integer awardType);
}
