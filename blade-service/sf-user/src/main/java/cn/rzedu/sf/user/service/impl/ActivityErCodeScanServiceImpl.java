package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.ActivityErCodeScan;
import cn.rzedu.sf.user.vo.ActivityErCodeScanVO;
import cn.rzedu.sf.user.mapper.ActivityErCodeScanMapper;
import cn.rzedu.sf.user.service.IActivityErCodeScanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 活动二维码扫码用户 服务实现类
 *
 * @author Blade
 * @since 2020-09-15
 */
@Service
public class ActivityErCodeScanServiceImpl extends ServiceImpl<ActivityErCodeScanMapper, ActivityErCodeScan> implements IActivityErCodeScanService {

	@Override
	public IPage<ActivityErCodeScanVO> selectActivityErCodeScanPage(IPage<ActivityErCodeScanVO> page, ActivityErCodeScanVO activityErCodeScan) {
		return page.setRecords(baseMapper.selectActivityErCodeScanPage(page, activityErCodeScan));
	}

	@Override
	public ActivityErCodeScan findByScanUserAndCode(Integer scanUserId, Integer codeId) {
		return baseMapper.findByScanUserAndCode(scanUserId, codeId);
	}

	@Override
	public ActivityErCodeScan findByScanUserAndTypeUser(Integer scanUserId, Integer type, Integer userId) {
		return baseMapper.findByScanUserAndTypeUser(scanUserId, type, userId);
	}
}
