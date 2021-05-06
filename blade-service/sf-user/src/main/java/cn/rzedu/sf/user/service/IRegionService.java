package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.Region;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 地区 服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface IRegionService extends IService<Region> {

    /**
     * 分页查询
     * @param page
     * @param region
     * @return
     */
    IPage<Region> selectRegionPage(IPage<Region> page, Region region);

    /**
     * 列表
     * @param parent
     * @param level
     * @return
     */
    List<Region> findRegionList(Integer parent, Integer level);

    /**
     * 新增或修改
     * @param region
     * @return
     */
    boolean saveOrUpdateRegion(Region region);

    /**
     * 删除
     * @param code
     * @param delChildren
     * @return
     */
    boolean removeRegion(String code, Integer delChildren);
}
