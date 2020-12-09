package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.ActivityUserAssist;
import cn.rzedu.sf.user.vo.ActivityUserAssistVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 用户助力表 服务类
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface IActivityUserAssistService extends IService<ActivityUserAssist> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activityUserAssist
     * @return
     */
    IPage<ActivityUserAssistVO> selectActivityUserAssistPage(IPage<ActivityUserAssistVO> page,
                                                             ActivityUserAssistVO activityUserAssist);

    /**
     * 发起人助力数据
     * @param userId
     * @return
     */
    List<ActivityUserAssist> findByInitiatorId(Integer userId);

    /**
     * 助力人助力数据
     * @param userId
     * @return
     */
    ActivityUserAssist findByAssistantId(Integer userId);

    /**
     * 生成助力数据
     * @param initiatorId   发起人userId
     * @param assistantId   助力人userId
     * @return
     */
    boolean createUserAssist(Integer initiatorId, Integer assistantId);

    /**
     * 批量生成用户数据
     */
    void batchCreateUserAssist();
}
