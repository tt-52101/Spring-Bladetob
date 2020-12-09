package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.ActivityErCodeScan;
import cn.rzedu.sf.user.vo.ActivityErCodeScanVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 活动二维码扫码用户 服务类
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface IActivityErCodeScanService extends IService<ActivityErCodeScan> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activityErCodeScan
     * @return
     */
    IPage<ActivityErCodeScanVO> selectActivityErCodeScanPage(IPage<ActivityErCodeScanVO> page,
                                                             ActivityErCodeScanVO activityErCodeScan);

    /**
     * 二维码扫码用户唯一值
     * @param scanUserId
     * @param codeId
     * @return
     */
    ActivityErCodeScan findByScanUserAndCode(Integer scanUserId, Integer codeId);

    /**
     * 二维码扫码用户唯一值
     * @param scanUserId
     * @param type
     * @param userId
     * @return
     */
    ActivityErCodeScan findByScanUserAndTypeUser(Integer scanUserId, Integer type, Integer userId);
}
