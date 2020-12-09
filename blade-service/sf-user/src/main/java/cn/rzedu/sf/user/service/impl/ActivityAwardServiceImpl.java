package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.constant.ActivityConstant;
import cn.rzedu.sf.user.entity.ActivityAward;
import cn.rzedu.sf.user.vo.ActivityAwardVO;
import cn.rzedu.sf.user.mapper.ActivityAwardMapper;
import cn.rzedu.sf.user.service.IActivityAwardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 活动奖品 服务实现类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Service
public class ActivityAwardServiceImpl extends ServiceImpl<ActivityAwardMapper, ActivityAward> implements IActivityAwardService {

	@Override
	public IPage<ActivityAwardVO> selectActivityAwardPage(IPage<ActivityAwardVO> page, ActivityAwardVO activityAward) {
		return page.setRecords(baseMapper.selectActivityAwardPage(page, activityAward));
	}

	@Override
	public ActivityAward findByType(Integer type) {
		return baseMapper.findByType(type);
	}

	@Override
	public int getExistingType() {
		int awardType = ActivityConstant.AWARD_GIFT_B;
		ActivityAward award = baseMapper.findByType(ActivityConstant.AWARD_GIFT_A);
		if (award != null && award.getLeftNumber() > 0) {
			awardType = ActivityConstant.AWARD_GIFT_A;
		}
		return awardType;
	}

	@Override
	public boolean modifyLeftNumber(Integer type) {
		return SqlHelper.retBool(baseMapper.modifyLeftNumber(type));
	}
}
