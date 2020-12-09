package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.GrouponGroup;
import cn.rzedu.sc.goods.vo.GrouponGroupVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import java.util.Map;

/**
 * 团购成团记录。针对某个团购的形成的一个团的总体情况 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface GrouponGroupMapper extends BaseMapper<GrouponGroup> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param grouponGroup
	 * @return
	 */
	List<GrouponGroupVO> selectGrouponGroupPage(IPage page, GrouponGroupVO grouponGroup);

	/**
	 *
	 * @param grouponId
	 * @return
	 */
	List<GrouponGroupVO> findByGrouponId(Integer grouponId);

	/**
	 * 真实团数据，离结束还有X小时
	 * status=0，is_robot=0，finish_time-now()<6*3600*1000
	 * @param grouponId
	 * @param time		小时数
	 * @return
	 */
	List<GrouponGroupVO> findDeadlineGroup(Integer grouponId, Integer time);

	/**
	 * 机器团数据
	 * status=0，is_robot=1
	 * @param grouponId
	 * @return
	 */
	List<GrouponGroupVO> findRobotGroup(Integer grouponId);

	/**
	 * 根据id获取参团数据
	 * @param id
	 * @return
	 */
	GrouponGroupVO findByGrouponGroupId(Integer id);

	/**
	 * 20条随机机器人用户
	 * @return
	 */
	List<Map<String, Object>> getRobotUser();

	/**
	 * 获取所有期限1小时以内的团
	 * status=0
	 * @return
	 */
	List<GrouponGroupVO> findOneHourLeftGroup();

	/**
	 * 结束时间在 XX~XX 之间的团（跟当前时间比较）
	 * @param greaterTime	大数，需小于它，单位：秒
	 * @param lessTime		小数，需大于它，单位：秒
	 * @return
	 */
	List<GrouponGroupVO> findLimitTimeGroup(Long greaterTime, Long lessTime);

	/**
	 * 已成团的 非机器人 参团用户
	 * @param grouponGroupId
	 * @return
	 */
	List<Integer> findNotRobotFinishGroupUser(Integer grouponGroupId);
}
