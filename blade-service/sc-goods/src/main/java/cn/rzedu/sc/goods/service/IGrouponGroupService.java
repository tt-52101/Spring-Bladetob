package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.GrouponGroup;
import cn.rzedu.sc.goods.vo.GrouponGroupVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 团购成团记录。针对某个团购的形成的一个团的总体情况 服务类
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface IGrouponGroupService extends IService<GrouponGroup> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param grouponGroup
	 * @return
	 */
	IPage<GrouponGroupVO> selectGrouponGroupPage(IPage<GrouponGroupVO> page, GrouponGroupVO grouponGroup);

	/**
	 * 只拿一个真实团，期限6小时以内的
	 * @param grouponId
	 * @return
	 */
	GrouponGroupVO findOneDeadlineGroup(Integer grouponId);

	/**
	 * 机器团数据
	 * @param grouponId
	 * @return
	 */
	List<GrouponGroupVO> findRobotGroup(Integer grouponId);

	/**
	 * 生成机器团
	 * @param grouponId
	 * @param source 	拼团来源（埋点）
	 * @return
	 */
	GrouponGroupVO createRobotGroup(Integer grouponId, Integer source);

	/**
	 * 拼团详情
	 * @param grouponGroupId
	 * @return
	 */
	GrouponGroupVO findGroupDetail(Integer grouponGroupId);

	/**
	 * 发起团购，生成正常团
	 * @param grouponId	团购商品id
	 * @param userId	发起人id
	 * @param skuIds 	团购sku集合
	 * @param source 	拼团来源（埋点）
	 * @return
	 */
	GrouponGroupVO createNormalGroup(Integer grouponId, Integer userId, String skuIds, Integer source);

	/**
	 * 自动成团，离结束时间1h以内的
	 */
	void autoCompleteGroupOneHourLeft();

	/**
	 * 已成团的 非机器人 参团用户
	 * @param grouponGroupId
	 * @return
	 */
	List<Integer> findNotRobotFinishGroupUser(Integer grouponGroupId);

	/**
	 * 发送消息给未完成的拼团
	 * @param greaterTime	大数，需小于的时间，单位：秒
	 * @param lessTime		小数，需大于的时间，单位：秒
	 */
	void sendMessageToTimeLackGroup(Long greaterTime, Long lessTime);
}
