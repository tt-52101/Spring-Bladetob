package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.ActivityErCodeScan;
import cn.rzedu.sf.user.vo.ActivityErCodeScanVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 活动二维码扫码用户 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface ActivityErCodeScanMapper extends BaseMapper<ActivityErCodeScan> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activityErCodeScan
     * @return
     */
    List<ActivityErCodeScanVO> selectActivityErCodeScanPage(IPage page, ActivityErCodeScanVO activityErCodeScan);

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
