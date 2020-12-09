package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.BuriedPointUser;
import cn.rzedu.sc.goods.vo.BuriedPointUserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;

/**
 * 商城埋点-操作人员 服务类
 *
 * @author Blade
 * @since 2020-12-02
 */
public interface IBuriedPointUserService extends IService<BuriedPointUser> {

    /**
     * 自定义分页
     *
     * @param page
     * @param buriedPointUser
     * @return
     */
    IPage<BuriedPointUserVO> selectBuriedPointUserPage(IPage<BuriedPointUserVO> page,
                                                       BuriedPointUserVO buriedPointUser);

    /**
     * 埋点用户
     * @param buriedPointId
     * @param userId
     * @param time      访问时间，只判断年月日
     * @return
     */
    BuriedPointUser findByUserId(Integer buriedPointId, Integer userId, LocalDateTime time);

    /**
     * 新增埋点用户数据，重复的不新增
     * @param buriedPointId
     * @param userId
     * @return      新增的返回true，已有的返回false
     */
    boolean insertBuriedUser(Integer buriedPointId, Integer userId);
}
