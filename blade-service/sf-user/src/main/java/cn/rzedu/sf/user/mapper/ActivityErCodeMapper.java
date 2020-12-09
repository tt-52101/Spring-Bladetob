package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.ActivityErCode;
import cn.rzedu.sf.user.vo.ActivityErCodeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 活动二维码 Mapper 接口
 *
 * @author Blade
 * @since 2020-09-15
 */
public interface ActivityErCodeMapper extends BaseMapper<ActivityErCode> {

    /**
     * 自定义分页
     *
     * @param page
     * @param activityErCode
     * @return
     */
    List<ActivityErCodeVO> selectActivityErCodePage(IPage page, ActivityErCodeVO activityErCode);

    /**
     * 查询用户活动二维码
     * @param type
     * @param userId
     * @return
     */
    ActivityErCode findByTypeAndUserId(Integer type, Integer userId);

    /**
     * 获取没有活动二维码的用户
     * @return
     */
    List<Map<String, Object>> findNotExistErCodeUser();

    /**
     * 修改二维码扫码次数
     * @param type      二维码类型
     * @param userId    二维码所属用户id
     * @return
     */
    int updateScanCount(Integer type, Integer userId);

    /**
     * 修改二维码扫码人数
     * @param type          二维码类型
     * @param userId        二维码所属用户id
     * @return
     */
    int updateScanPeopleCount(Integer type, Integer userId);

    /**
     * 通过助力者，获取发起者的邀请系数
     * 若发起者是系统，initiator_id = 0，返回空
     * @param userId    助力者userId
     * @return
     */
    Integer getInviteCoefByAssistant(Integer userId, Integer type);

}
