package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.constant.ActivityConstant;
import cn.rzedu.sf.user.entity.ActivityAwardUser;
import cn.rzedu.sf.user.entity.User;
import cn.rzedu.sf.user.service.IActivityAwardService;
import cn.rzedu.sf.user.service.IUserService;
import cn.rzedu.sf.user.vo.ActivityAwardUserVO;
import cn.rzedu.sf.user.mapper.ActivityAwardUserMapper;
import cn.rzedu.sf.user.service.IActivityAwardUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;

/**
 * 活动奖品领取用户 服务实现类
 *
 * @author Blade
 * @since 2020-09-15
 */
@AllArgsConstructor
@Service
public class ActivityAwardUserServiceImpl extends ServiceImpl<ActivityAwardUserMapper, ActivityAwardUser> implements IActivityAwardUserService {

	private IActivityAwardService activityAwardService;

	private IUserService userService;

	@Override
	public IPage<ActivityAwardUserVO> selectActivityAwardUserPage(IPage<ActivityAwardUserVO> page, ActivityAwardUserVO activityAwardUser) {
		return page.setRecords(baseMapper.selectActivityAwardUserPage(page, activityAwardUser));
	}

	@Override
	public ActivityAwardUser findByTypeAndUserId(Integer type, Integer userId, Integer awardType) {
		ActivityAwardUser awardUser = baseMapper.findByTypeAndUserId(type, userId);
		if (awardUser != null) {
			return awardUser;
		}
		awardUser = new ActivityAwardUser();
		awardUser.setType(type);
		awardUser.setUserId(userId);
		awardUser.setStatus(1);
		awardUser.setClickStatus(0);
		User user = userService.getById(userId);
		if (user != null) {
			awardUser.setUserUuid(user.getUuid());
		}
		if (ActivityConstant.AWARD_TYPE_GIFT == type) {
			if (awardType == null) {
				awardType = activityAwardService.getExistingType();
			}
			awardUser.setAwardType(awardType);
		}
		awardUser.setCreateDate(LocalDateTime.now());
		baseMapper.insert(awardUser);
		return awardUser;
	}
}
