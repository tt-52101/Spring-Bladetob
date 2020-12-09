package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.ActivityAwardUser;
import cn.rzedu.sf.user.vo.ActivityAwardUserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 活动奖品领取用户 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface ActivityAwardUserMapper extends BaseMapper<ActivityAwardUser> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activityAwardUser
     * @return
     */
    List<ActivityAwardUserVO> selectActivityAwardUserPage(IPage page, ActivityAwardUserVO activityAwardUser);

    /**
     * 获取用户奖品
     * @param type
     * @param userId
     * @return
     */
    ActivityAwardUser findByTypeAndUserId(Integer type, Integer userId);

}
