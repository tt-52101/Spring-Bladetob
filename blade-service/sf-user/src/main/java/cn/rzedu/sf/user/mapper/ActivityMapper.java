package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.Activity;
import cn.rzedu.sf.user.vo.ActivityVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 活动 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-18
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activity
     * @return
     */
    List<ActivityVO> selectActivityPage(IPage page, ActivityVO activity);

    /**
     * 根据名字获取活动
     * @param name
     * @return
     */
    Activity findByName(String name);

}
