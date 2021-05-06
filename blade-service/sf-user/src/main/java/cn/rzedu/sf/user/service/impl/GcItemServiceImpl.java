package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.GcItem;
import cn.rzedu.sf.user.mapper.GcItemMapper;
import cn.rzedu.sf.user.service.IGcItemService;
import cn.rzedu.sf.user.vo.GcItemVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 基础代码 服务实现类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Service
public class GcItemServiceImpl extends ServiceImpl<GcItemMapper, GcItem> implements IGcItemService {

    @Override
    public List<GcItemVO> findByCode(String code) {
        return baseMapper.findByCode(code);
    }

    @Override
    public List<GcItemVO> findByRegexValue(String code, String value) {
        return baseMapper.findByRegexValue(code, value);
    }

    @Override
    public List<GcItemVO> findByRegexName(String code, String name) {
        return baseMapper.findByRegexName(code, name);
    }

    @Override
    public String getItemName(String code, String value) {
        return baseMapper.getItemName(code, value);
    }

    @Override
    public String getItemValue(String code, String name) {
        return baseMapper.getItemValue(code, name);
    }

    @Override
    public String getNextValue(String code, String value) {
        return baseMapper.getNextValue(code, value);
    }

    @Override
    public int saveOrUpdateItem(GcItem gcItem) {
        if (gcItem == null) {
            return 0;
        }
        String code = gcItem.getCode();
        String name = gcItem.getName();
        String value = gcItem.getValue();
        Integer id = gcItem.getId();
        GcItemVO unique = baseMapper.findUnique(code, value);
        if (unique != null) {
            if (id != null && id.equals(unique.getId())) {
                GcItem item = new GcItem();
                item.setId(id);
                item.setName(name);
                item.setName(value);
                item.setSortOrder(gcItem.getSortOrder());
                item.setLevel(gcItem.getLevel());
                baseMapper.updateById(item);
            } else {
                return 2;
            }
        } else {
            GcItem item = new GcItem();
            item.setName(name);
            item.setName(value);
            if (id != null) {
                item.setId(id);
                item.setSortOrder(gcItem.getSortOrder());
                item.setLevel(gcItem.getLevel());
                baseMapper.updateById(item);
            } else {
                item.setCode(code);
                item.setSortOrder(gcItem.getSortOrder());
                item.setLevel(gcItem.getLevel());
                item.setCreateDate(LocalDateTime.now());
                baseMapper.insert(item);
            }
        }
        return 0;
    }
}
