package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.UserLoginLog;
import cn.rzedu.sf.user.vo.UserLoginLogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户每日登陆记录 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-24
 */
public interface UserLoginLogMapper extends BaseMapper<UserLoginLog> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userLoginLog
	 * @return
	 */
	List<UserLoginLogVO> selectUserLoginLogPage(IPage page, UserLoginLogVO userLoginLog);

	/**
	 * 用户登录记录
	 * @param userId
	 * @return
	 */
	List<UserLoginLog> findByUserId(Integer userId);

	/**
	 * 用户某日登录记录
	 * @param userId
	 * @param loginTime
	 * @return
	 */
	UserLoginLog findByFormatTime(Integer userId, LocalDateTime loginTime);

}
