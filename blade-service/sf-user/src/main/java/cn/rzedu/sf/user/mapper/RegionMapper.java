package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.Region;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 地区 Mapper 接口
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface RegionMapper extends BaseMapper<Region> {

    /**
     * 分页查询
     * @param page
     * @param region
     * @return
     */
    List<Region> selectRegionPage(IPage page, Region region);

    /**
     * 列表
     * @param parent
     * @param level
     * @return
     */
    List<Region> findRegionList(Integer parent, Integer level);

    /**
     * 根据区域代码获取
     * @param code
     * @return
     */
    Region findByCode(String code);

    /**
     * 删除子区域
     * @param parent
     * @return
     */
    int removeByParent(Integer parent);
}
