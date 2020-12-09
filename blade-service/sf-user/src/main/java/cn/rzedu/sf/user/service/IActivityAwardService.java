package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.ActivityAward;
import cn.rzedu.sf.user.vo.ActivityAwardVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 活动奖品 服务类
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface IActivityAwardService extends IService<ActivityAward> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activityAward
     * @return
     */
    IPage<ActivityAwardVO> selectActivityAwardPage(IPage<ActivityAwardVO> page, ActivityAwardVO activityAward);

    /**
     * 获取奖品
     * @param type
     * @return
     */
    ActivityAward findByType(Integer type);

    /**
     * 现有的奖品礼包类型
     * 礼包A剩余数量为0，就是礼包B
     * @return
     */
    int getExistingType();

    /**
     * 修改剩余数量，-1
     * @param type
     * @return
     */
    boolean modifyLeftNumber(Integer type);
}
