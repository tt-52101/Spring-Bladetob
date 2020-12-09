package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.ActivityAction;
import cn.rzedu.sf.user.vo.ActivityActionVO;
import cn.rzedu.sf.user.mapper.ActivityActionMapper;
import cn.rzedu.sf.user.service.IActivityActionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 活动行为表 服务实现类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Service
public class ActivityActionServiceImpl extends ServiceImpl<ActivityActionMapper, ActivityAction> implements IActivityActionService {

	@Override
	public IPage<ActivityActionVO> selectActivityActionPage(IPage<ActivityActionVO> page, ActivityActionVO activityAction) {
		return page.setRecords(baseMapper.selectActivityActionPage(page, activityAction));
	}

	@Override
	public boolean updateSendCount(Integer type) {
		return SqlHelper.retBool(baseMapper.updateSendCount(type, 1));
	}

	@Override
	public boolean updateSendCount(Integer type, Integer count) {
		return SqlHelper.retBool(baseMapper.updateSendCount(type, count));
	}

	@Override
	public boolean updateClickCount(Integer type) {
		return SqlHelper.retBool(baseMapper.updateClickCount(type, 1));
	}

	@Override
	public boolean updateClickCount(Integer type, Integer count) {
		return SqlHelper.retBool(baseMapper.updateClickCount(type, count));
	}
}
