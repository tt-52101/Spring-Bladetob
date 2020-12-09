package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.UserLoginLog;
import cn.rzedu.sf.user.vo.UserLoginLogVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用户每日登陆记录 服务类
 *
 * @author Blade
 * @since 2020-09-24
 */
public interface IUserLoginLogService extends IService<UserLoginLog> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userLoginLog
	 * @return
	 */
	IPage<UserLoginLogVO> selectUserLoginLogPage(IPage<UserLoginLogVO> page, UserLoginLogVO userLoginLog);

	/**
	 * 添加当天登录记录
	 * @param userId
	 * @param loginSource
	 * @return
	 */
	boolean addCurrentDayLogin(Integer userId, Integer loginSource);

}
