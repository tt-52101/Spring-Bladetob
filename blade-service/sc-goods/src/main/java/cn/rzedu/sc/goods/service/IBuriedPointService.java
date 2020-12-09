package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.BuriedPoint;
import cn.rzedu.sc.goods.vo.BuriedPointVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;

/**
 * 商城埋点 服务类
 *
 * @author Blade
 * @since 2020-12-02
 */
public interface IBuriedPointService extends IService<BuriedPoint> {

    /**
     * 自定义分页
     *
     * @param page
     * @param buriedPoint
     * @return
     */
    IPage<BuriedPointVO> selectBuriedPointPage(IPage<BuriedPointVO> page, BuriedPointVO buriedPoint);

    /**
     * 埋点类型
     * @param type
     * @param objectId  关联的表主键
     * @param time      访问时间，只判断年月日
     * @return
     */
    BuriedPoint findByType(Integer type, Integer objectId, LocalDateTime time);

    /**
     * 修改当日埋点人数
     * @param type
     * @param objectId
     * @param userId
     */
    void updateVisitCount(Integer type, Integer objectId, Integer userId);


}
