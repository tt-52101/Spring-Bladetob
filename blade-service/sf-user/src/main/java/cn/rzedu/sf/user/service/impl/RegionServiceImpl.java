package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.Region;
import cn.rzedu.sf.user.mapper.RegionMapper;
import cn.rzedu.sf.user.service.IRegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 地区 服务实现类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

    @Override
    public IPage<Region> selectRegionPage(IPage<Region> page, Region region) {
        return page.setRecords(baseMapper.selectRegionPage(page, region));
    }

    @Override
    public List<Region> findRegionList(Integer parent, Integer level) {
        return baseMapper.findRegionList(parent, level);
    }

    @Override
    public boolean saveOrUpdateRegion(Region region) {
        if (region == null) {
            return true;
        }
        String code = region.getCode();
        String name = region.getName();
        Region unique = baseMapper.findByCode(code);
        if (unique != null) {
            unique.setName(name);
            unique.setShortName(name);
            Integer level = unique.getLevel();
            if (level == 1) {
                unique.setProvinceName(name);
            } else if (level == 2) {
                unique.setCityName(name);
            } else if (level == 3) {
                unique.setDistrictName(name);
            }
            baseMapper.updateById(unique);
        } else {
            Integer parent = region.getParent();
            int level = 1;
            String provinceName = "";
            String cityName = "";
            String districtName = "";
            if (parent == null) {
                parent = 0;
            }
            if (parent != 0) {
                Region parentRegion = baseMapper.findByCode(String.valueOf(parent));
                if (parentRegion != null) {
                    level = parentRegion.getLevel() + 1;
                    if (level == 2) {
                        provinceName = parentRegion.getProvinceName();
                        cityName = name;
                    } else if (level == 3) {
                        provinceName = parentRegion.getProvinceName();
                        cityName = parentRegion.getCityName();
                        districtName = name;
                    } else if (level == 4) {
                        provinceName = parentRegion.getProvinceName();
                        cityName = parentRegion.getCityName();
                        districtName = parentRegion.getDistrictName();
                    }
                }
            } else {
                provinceName = name;
            }
            unique = new Region();
            unique.setId(Integer.valueOf(code));
            unique.setCode(code);
            unique.setParent(parent);
            unique.setName(name);
            unique.setShortName(name);
            unique.setSortOrder(1);
            unique.setLevel(level);
            unique.setProvinceName(provinceName);
            unique.setCityName(cityName);
            unique.setDistrictName(districtName);
            unique.setCreateDate(LocalDateTime.now());
            unique.setModifyDate(LocalDateTime.now());
            baseMapper.insert(unique);
        }
        return true;
    }

    @Override
    public boolean removeRegion(String code, Integer delChildren) {
        Integer id = Integer.valueOf(code);
        int i = baseMapper.deleteById(id);
        if (i == 1 && delChildren == 1) {
            baseMapper.removeByParent(id);
        }
        return SqlHelper.retBool(i);
    }
}
