package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.BuriedPointUser;
import cn.rzedu.sc.goods.vo.BuriedPointUserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商城埋点-操作人员 Mapper 接口
 *
 * @author Blade
 * @since 2020-12-02
 */
public interface BuriedPointUserMapper extends BaseMapper<BuriedPointUser> {

    /**
     * 自定义分页
     *
     * @param page
     * @param buriedPointUser
     * @return
     */
    List<BuriedPointUserVO> selectBuriedPointUserPage(IPage page, BuriedPointUserVO buriedPointUser);

    /**
     * 埋点用户
     * @param buriedPointId
     * @param userId
     * @param time      访问时间，只判断年月日
     * @return
     */
    BuriedPointUser findByUserId(Integer buriedPointId, Integer userId, LocalDateTime time);
}
