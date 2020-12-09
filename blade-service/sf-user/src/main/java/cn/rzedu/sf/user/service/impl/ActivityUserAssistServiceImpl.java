package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.ActivityUserAssist;
import cn.rzedu.sf.user.entity.User;
import cn.rzedu.sf.user.service.IUserService;
import cn.rzedu.sf.user.vo.ActivityUserAssistVO;
import cn.rzedu.sf.user.mapper.ActivityUserAssistMapper;
import cn.rzedu.sf.user.service.IActivityUserAssistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 用户助力表 服务实现类
 *
 * @author Blade
 * @since 2020-09-15
 */
@AllArgsConstructor
@Service
public class ActivityUserAssistServiceImpl extends ServiceImpl<ActivityUserAssistMapper, ActivityUserAssist> implements IActivityUserAssistService {

	private IUserService userService;

	@Override
	public IPage<ActivityUserAssistVO> selectActivityUserAssistPage(IPage<ActivityUserAssistVO> page, ActivityUserAssistVO activityUserAssist) {
		return page.setRecords(baseMapper.selectActivityUserAssistPage(page, activityUserAssist));
	}

	@Override
	public List<ActivityUserAssist> findByInitiatorId(Integer userId) {
		return baseMapper.findByInitiatorId(userId);
	}

	@Override
	public ActivityUserAssist findByAssistantId(Integer userId) {
		return baseMapper.findByAssistantId(userId);
	}

	@Override
	public boolean createUserAssist(Integer initiatorId, Integer assistantId) {
		ActivityUserAssist assist = baseMapper.findByAssistantId(assistantId);
		if (assist != null) {
			return false;
		}
		User initiator = userService.getById(initiatorId);
		User assistant = userService.getById(assistantId);

		assist = new ActivityUserAssist();
		assist.setInitiatorId(initiatorId);
		assist.setAssistantId(assistantId);
		assist.setCreateDate(LocalDateTime.now());
		if (initiator != null) {
			assist.setInitiatorUuid(initiator.getUuid());
		}
		if (assistant != null) {
			assist.setAssistantUuid(assistant.getUuid());
		}
		return SqlHelper.retBool(baseMapper.insert(assist));
	}

	@Override
	public void batchCreateUserAssist() {
		List<Map<String, Object>> list = baseMapper.findNotExistAssistUser();
		if (list == null || list.isEmpty()) {
			return;
		}
		ActivityUserAssist assist = null;
		LocalDateTime now = LocalDateTime.now();
		for (Map<String, Object> map : list) {
			assist = new ActivityUserAssist();
			assist.setInitiatorId(0);
			assist.setAssistantUuid("0");
			assist.setAssistantId(Integer.parseInt(String.valueOf(map.get("id"))));
			assist.setAssistantUuid((String) map.get("uuid"));
			assist.setCreateDate(now);
			baseMapper.insert(assist);
		}

	}
}
