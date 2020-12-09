package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.User;
import cn.rzedu.sf.user.entity.UserLoginLog;
import cn.rzedu.sf.user.service.IUserService;
import cn.rzedu.sf.user.vo.UserLoginLogVO;
import cn.rzedu.sf.user.mapper.UserLoginLogMapper;
import cn.rzedu.sf.user.service.IUserLoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;

/**
 * 用户每日登陆记录 服务实现类
 *
 * @author Blade
 * @since 2020-09-24
 */
@AllArgsConstructor
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements IUserLoginLogService {

	private IUserService userService;

	@Override
	public IPage<UserLoginLogVO> selectUserLoginLogPage(IPage<UserLoginLogVO> page, UserLoginLogVO userLoginLog) {
		return page.setRecords(baseMapper.selectUserLoginLogPage(page, userLoginLog));
	}

	@Override
	public boolean addCurrentDayLogin(Integer userId, Integer loginSource) {
		LocalDateTime now = LocalDateTime.now();
		UserLoginLog loginLog = baseMapper.findByFormatTime(userId, now);
		if (loginLog != null) {
			return true;
		}
		User user = userService.getById(userId);
		if (user == null) {
			return false;
		}
		loginLog = new UserLoginLog();
		loginLog.setUserId(userId);
		loginLog.setUserUuid(user.getUuid());
		loginLog.setLoginTime(now);
		loginLog.setLoginSource(loginSource);
		loginLog.setCreateDate(now);
		loginLog.setModifyDate(now);
		return SqlHelper.retBool(baseMapper.insert(loginLog));
	}
}
