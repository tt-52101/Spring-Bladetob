package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.ActivityErCode;
import cn.rzedu.sf.user.vo.ActivityErCodeVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 活动二维码 服务类
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface IActivityErCodeService extends IService<ActivityErCode> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activityErCode
     * @return
     */
    IPage<ActivityErCodeVO> selectActivityErCodePage(IPage<ActivityErCodeVO> page, ActivityErCodeVO activityErCode);

    /**
     * 查询 或新增 用户活动二维码
     * @param type
     * @param userId
     * @return
     */
    ActivityErCode findByTypeAndUserId(Integer type, Integer userId);

    /**
     * 批量生成用户海报二维码数据
     */
    void batchCreateErCode();

    /**
     * 修改二维码扫码次数
     * @param type      二维码类型
     * @param userId    二维码所属用户id
     * @return
     */
    boolean updateScanCount(Integer type, Integer userId);

    /**
     * 修改二维码扫码人数
     * @param type          二维码类型
     * @param userId        二维码所属用户id
     * @param scanUserId    扫码用户id
     * @return
     */
    boolean updateScanPeopleCount(Integer type, Integer userId, Integer scanUserId);

    /**
     * 助力流程
     * @param initiatorId   发起人userId
     * @param assistantId   助力人userId
     * @param type          助力活动类型，针对不同的奖品活动
     * @return   返回助力人数  0=已助力过/或助力人数超过8人
     */
    int assistantProcess(Integer initiatorId, Integer assistantId, Integer type);
}
