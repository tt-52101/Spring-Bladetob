package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.ActivityAward;
import cn.rzedu.sf.user.vo.ActivityAwardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 活动奖品 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface ActivityAwardMapper extends BaseMapper<ActivityAward> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activityAward
     * @return
     */
    List<ActivityAwardVO> selectActivityAwardPage(IPage page, ActivityAwardVO activityAward);

    /**
     * 获取奖品
     * @param type
     * @return
     */
    ActivityAward findByType(Integer type);

    /**
     * 修改剩余数量，-1
     * @param type
     * @return
     */
    int modifyLeftNumber(Integer type);
}

