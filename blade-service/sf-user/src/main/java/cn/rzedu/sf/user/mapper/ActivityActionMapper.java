package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.ActivityAction;
import cn.rzedu.sf.user.vo.ActivityActionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 活动行为表 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface ActivityActionMapper extends BaseMapper<ActivityAction> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activityAction
     * @return
     */
    List<ActivityActionVO> selectActivityActionPage(IPage page, ActivityActionVO activityAction);

    /**
     * 修改发送次数
     * @param type
     * @param number
     * @return
     */
    int updateSendCount(Integer type, Integer number);

    /**
     * 修改发送次数
     * @param type
     * @param number
     * @return
     */
    int updateClickCount(Integer type, Integer number);
}
