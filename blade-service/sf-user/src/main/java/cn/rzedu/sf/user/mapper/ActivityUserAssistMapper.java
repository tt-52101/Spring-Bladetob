package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.ActivityUserAssist;
import cn.rzedu.sf.user.vo.ActivityUserAssistVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 用户助力表 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface ActivityUserAssistMapper extends BaseMapper<ActivityUserAssist> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activityUserAssist
     * @return
     */
    List<ActivityUserAssistVO> selectActivityUserAssistPage(IPage page, ActivityUserAssistVO activityUserAssist);

    /**
     * 发起人助力数据
     *
     * @param userId
     * @return
     */
    List<ActivityUserAssist> findByInitiatorId(Integer userId);

    /**
     * 助力人助力数据
     *
     * @param userId
     * @return
     */
    ActivityUserAssist findByAssistantId(Integer userId);

    /**
     * 获取没有活动二维码的用户
     * @return
     */
    List<Map<String, Object>> findNotExistAssistUser();
}
