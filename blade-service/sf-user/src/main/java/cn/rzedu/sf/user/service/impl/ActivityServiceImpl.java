package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.Activity;
import cn.rzedu.sf.user.vo.ActivityVO;
import cn.rzedu.sf.user.mapper.ActivityMapper;
import cn.rzedu.sf.user.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 活动 服务实现类
 *
 * @author Blade
 * @since 2020-09-18
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Override
    public IPage<ActivityVO> selectActivityPage(IPage<ActivityVO> page, ActivityVO activity) {
        return page.setRecords(baseMapper.selectActivityPage(page, activity));
    }

    @Override
    public Activity findZuLiActivity() {
        Activity activity = baseMapper.findByName("8人助力裂变");
        if (activity == null) {
            activity = baseMapper.selectById(1);
        }
        return activity;
    }
}
