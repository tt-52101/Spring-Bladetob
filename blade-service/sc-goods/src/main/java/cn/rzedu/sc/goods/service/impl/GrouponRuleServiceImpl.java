package cn.rzedu.sc.goods.service.impl;

import cn.rzedu.sc.goods.entity.GrouponRule;
import cn.rzedu.sc.goods.vo.GrouponRuleVO;
import cn.rzedu.sc.goods.mapper.GrouponRuleMapper;
import cn.rzedu.sc.goods.service.IGrouponRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 团购规则。一个团购规则记录是针对一个商品进行一次团购销售而设计的 服务实现类
 *
 * @author Blade
 * @since 2020-10-12
 */
@Service
public class GrouponRuleServiceImpl extends ServiceImpl<GrouponRuleMapper, GrouponRule> implements IGrouponRuleService {

    @Override
    public IPage<GrouponRuleVO> selectGrouponRulePage(IPage<GrouponRuleVO> page, GrouponRuleVO grouponRule) {
        return page.setRecords(baseMapper.selectGrouponRulePage(page, grouponRule));
    }

    @Override
    public IPage<GrouponRuleVO> findByGrouponRuleVO(IPage<GrouponRuleVO> page, GrouponRuleVO grouponRule) {
        return page.setRecords(baseMapper.findByGrouponRuleVO(page, grouponRule));
    }

    @Override
    public GrouponRuleVO findDetailById(Integer id) {
        return baseMapper.findDetailById(id);
    }

    @Override
    public List<GrouponRuleVO> findGrouponRules(Integer excludeId) {
        List<GrouponRuleVO> list = baseMapper.findByGrouponRuleVO(null, null);
        if (list != null && !list.isEmpty() && excludeId != null) {
            for (GrouponRuleVO vo : list) {
                if (vo.getId().equals(excludeId)){
                    list.remove(vo);
                    break;
                }
            }
        }
        return list;
    }
}
