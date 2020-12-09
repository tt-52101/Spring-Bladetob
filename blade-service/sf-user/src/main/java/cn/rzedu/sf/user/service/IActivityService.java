package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.Activity;
import cn.rzedu.sf.user.vo.ActivityVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 活动 服务类
 *
 * @author Blade
 * @since 2020-09-18
 */
public interface IActivityService extends IService<Activity> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activity
     * @return
     */
    IPage<ActivityVO> selectActivityPage(IPage<ActivityVO> page, ActivityVO activity);

    /**
     * 8人助力裂变活动
     * @return
     */
    Activity findZuLiActivity();

}
