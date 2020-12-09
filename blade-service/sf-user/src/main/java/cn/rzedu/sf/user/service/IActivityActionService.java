package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.ActivityAction;
import cn.rzedu.sf.user.vo.ActivityActionVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 活动行为表 服务类
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface IActivityActionService extends IService<ActivityAction> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param activityAction
	 * @return
	 */
	IPage<ActivityActionVO> selectActivityActionPage(IPage<ActivityActionVO> page, ActivityActionVO activityAction);

	/**
	 * 修改发送次数，默认+1
	 * @param type
	 * @return
	 */
	boolean updateSendCount(Integer type);
	/**
	 * 修改发送次数，在原值上相加
	 * @param type
	 * @param count
	 * @return
	 */
	boolean updateSendCount(Integer type, Integer count);
	/**
	 * 修改点击次数，默认+1
	 * @param type
	 * @return
	 */
	boolean updateClickCount(Integer type);

	/**
	 * 修改点击次数，在原值上相加
	 * @param type
	 * @param count
	 * @return
	 */
	boolean updateClickCount(Integer type, Integer count);

}
