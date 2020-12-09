package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.BuriedPoint;
import cn.rzedu.sc.goods.vo.BuriedPointVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商城埋点 Mapper 接口
 *
 * @author Blade
 * @since 2020-12-02
 */
public interface BuriedPointMapper extends BaseMapper<BuriedPoint> {

    /**
     * 自定义分页
     *
     * @param page
     * @param buriedPoint
     * @return
     */
    List<BuriedPointVO> selectBuriedPointPage(IPage page, BuriedPointVO buriedPoint);

    /**
     * 埋点类型
     * @param type
     * @param objectId  关联的表主键
     * @param time      访问时间，只判断年月日
     * @return
     */
    BuriedPoint findByType(Integer type, Integer objectId, LocalDateTime time);
}
