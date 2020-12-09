package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.constant.ActivityConstant;
import cn.rzedu.sf.user.entity.*;
import cn.rzedu.sf.user.service.*;
import cn.rzedu.sf.user.vo.ActivityErCodeVO;
import cn.rzedu.sf.user.mapper.ActivityErCodeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 活动二维码 服务实现类
 *
 * @author Blade
 * @since 2020-09-15
 */
@AllArgsConstructor
@Service
public class ActivityErCodeServiceImpl extends ServiceImpl<ActivityErCodeMapper, ActivityErCode> implements IActivityErCodeService {

	private IUserService userService;

	private IActivityActionService activityActionService;

	private IActivityAwardService activityAwardService;

	private IActivityAwardUserService activityAwardUserService;

	private IActivityErCodeScanService activityErCodeScanService;

	private IActivityUserAssistService activityUserAssistService;

	/** 助力所需人数--课程1 */
	private static final int ASSIST_NUMBER_C1 = 1;
	/** 助力所需人数--课程2 */
	private static final int ASSIST_NUMBER_C2 = 3;
	/** 助力所需人数--礼物 */
	private static final int ASSIST_NUMBER_GIFT = 6;
	/** 助力所需人数--奖品 */
	private static final int ASSIST_NUMBER_AWARD = 3;


	@Override
	public IPage<ActivityErCodeVO> selectActivityErCodePage(IPage<ActivityErCodeVO> page, ActivityErCodeVO activityErCode) {
		return page.setRecords(baseMapper.selectActivityErCodePage(page, activityErCode));
	}

	@Override
	public ActivityErCode findByTypeAndUserId(Integer type, Integer userId) {
		ActivityErCode erCode = baseMapper.findByTypeAndUserId(type, userId);
		if (erCode != null) {
			return erCode;
		}

		User user = userService.getById(userId);
		erCode = new ActivityErCode();
		erCode.setType(type);
		erCode.setErName(getCodeName(type));
		erCode.setUserId(userId);
		erCode.setScanCount(0);
		erCode.setScanPeopleCount(0);
		erCode.setAssistCount(0);
		erCode.setCreateDate(LocalDateTime.now());

		if (user != null) {
			erCode.setUserUuid(user.getUuid());
		}
		//如果是活动海报，需判断奖品类型：礼包A奖品数量等于0时，采用礼包B;礼包A数量减少在助力环节执行
		if (type == 1) {
			int awardType = activityAwardService.getExistingType();
			erCode.setAwardType(awardType);
		} else if (type >= 11 && type <= 14) {
			erCode.setAwardType(type);
		}
		baseMapper.insert(erCode);
		return erCode;
	}

	private String getCodeName(Integer type) {
		String name = "";
		switch (type) {
			case 1 : name = "活动海报二维码"; break;
			case 2 : name = "推文二维码"; break;
			case 3 : name = "课本二维码"; break;
			case 11 : name = "0元领活动--奖品1海报"; break;
			case 12 : name = "0元领活动--奖品2海报"; break;
			case 13 : name = "0元领活动--奖品3海报"; break;
			case 14 : name = "0元领活动--奖品4海报"; break;
		}
		return name;
	}

	@Override
	public void batchCreateErCode() {
		List<Map<String, Object>> list = baseMapper.findNotExistErCodeUser();
		if (list == null || list.isEmpty()) {
			return;
		}
		ActivityErCode erCode = null;
		LocalDateTime now = LocalDateTime.now();
		int awardType = activityAwardService.getExistingType();
		for (Map<String, Object> map : list) {
			erCode = new ActivityErCode();
			erCode.setType(ActivityConstant.ER_CODE_POSTER);
			erCode.setAwardType(awardType);
			erCode.setUserId(Integer.parseInt(String.valueOf(map.get("id"))));
			erCode.setUserUuid((String) map.get("uuid"));
			erCode.setErName("活动海报二维码");
			erCode.setScanCount(0);
			erCode.setScanPeopleCount(0);
			erCode.setAssistCount(0);
			erCode.setCreateDate(now);
			baseMapper.insert(erCode);
		}
	}

	@Override
	public boolean updateScanCount(Integer type, Integer userId) {
		return SqlHelper.retBool(baseMapper.updateScanCount(type, userId));
	}

	@Override
	public boolean updateScanPeopleCount(Integer type, Integer userId, Integer scanUserId) {
		ActivityErCodeScan scan = activityErCodeScanService.findByScanUserAndTypeUser(scanUserId, type, userId);
		if (scan != null) {
			return false;
		}
		ActivityErCode erCode = this.findByTypeAndUserId(type, userId);
		scan = new ActivityErCodeScan();
		scan.setCodeId(erCode.getId());
		scan.setUserId(userId);
		scan.setType(type);
		scan.setScanUserId(scanUserId);
		scan.setCreateDate(LocalDateTime.now());
		boolean status = activityErCodeScanService.save(scan);
		if (status) {
			return SqlHelper.retBool(baseMapper.updateScanPeopleCount(type, userId));
		} else {
			return false;
		}
	}

	@Override
	public int assistantProcess(Integer initiatorId, Integer assistantId, Integer type) {
		//1.助力表插入数据，插入成功，进入2，插入失败（已助力过），结束。发起人=0，结束。
		boolean isNew = activityUserAssistService.createUserAssist(initiatorId, assistantId);
		if (!isNew) {
			return 0;
		}
		if (initiatorId == 0){
			return 0;
		}
		if (type == null) {
			return 0;
		}
		//2.获取发起人助力人数
		List<ActivityUserAssist> assistList = activityUserAssistService.findByInitiatorId(initiatorId);
		int assistCountNew = 0;
		if (assistList != null && !assistList.isEmpty()) {
			assistCountNew = assistList.size();
		}
		//3.跟发起人成功助力人数比较，不同进入4，相同或小于不修改，结束。
		ActivityErCode erCode = this.findByTypeAndUserId(type, initiatorId);
		Integer assistCount = erCode.getAssistCount();
		if (assistCount >= assistCountNew) {
			return assistCountNew;
		}
		//4.修改成功助力人数
		//同时有多个活动，会有多个海报助力人数，总助力必大于海报人数，不能直接使用，当前海报助力人数只需加1
		assistCountNew = assistCount + 1;
		ActivityErCode modifyErCode = new ActivityErCode();
		modifyErCode.setId(erCode.getId());
		modifyErCode.setAssistCount(assistCountNew);
		//5.修改邀请系数。已有的不用修改 -- 将发起人作为助力人，获取父级助力人邀请系数+1，若父级助力人=0，则为1，
		if (erCode.getInviteCoef() == null) {
			//邀请系数必须根据不同的奖品type去区分
			Integer num = baseMapper.getInviteCoefByAssistant(initiatorId, type);
			if (num == null) {
				num = 0;
			}
			modifyErCode.setInviteCoef(num + 1);
		}
		baseMapper.updateById(modifyErCode);

		//6.助力人数达到一定数量，发送用户奖品
		if (ActivityConstant.ER_CODE_POSTER == type) {
			Integer awardType = erCode.getAwardType();
			if (assistCountNew == ASSIST_NUMBER_GIFT) {
				activityAwardUserService.findByTypeAndUserId(ActivityConstant.AWARD_TYPE_GIFT, initiatorId, awardType);
				//7.奖品为礼包A，数量-1
				activityAwardService.modifyLeftNumber(awardType);
			} else if (assistCountNew == ASSIST_NUMBER_C2) {
				activityAwardUserService.findByTypeAndUserId(ActivityConstant.AWARD_TYPE_SC, initiatorId, null);
			} else if (assistCountNew == ASSIST_NUMBER_C1) {
				activityAwardUserService.findByTypeAndUserId(ActivityConstant.AWARD_TYPE_QZW, initiatorId, null);
			}
			//8.返回助力总人数，用于发送消息，更新发送次数
			if (assistCountNew > ASSIST_NUMBER_GIFT){
				assistCountNew = 0;
			}
		} else if (ActivityConstant.ER_CODE_POSTER_N1 == type
				|| ActivityConstant.ER_CODE_POSTER_N2 == type
				|| ActivityConstant.ER_CODE_POSTER_N3 == type
				|| ActivityConstant.ER_CODE_POSTER_N4 == type ) {
			//新的助力活动只有达到8人才发奖品
			if (assistCountNew == ASSIST_NUMBER_AWARD) {
				//增加用户奖品获奖记录，修改奖品数量
				activityAwardUserService.findByTypeAndUserId(type, initiatorId, type);
				activityAwardService.modifyLeftNumber(type);
			}
			if (assistCountNew > ASSIST_NUMBER_AWARD) {
				assistCountNew = 0;
			}
		}
		return assistCountNew;
	}
}
