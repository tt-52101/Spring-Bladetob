package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.GrouponRule;
import cn.rzedu.sc.goods.vo.GrouponRuleVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 团购规则。一个团购规则记录是针对一个商品进行一次团购销售而设计的 服务类
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface IGrouponRuleService extends IService<GrouponRule> {

    /**
     * 自定义分页
     *
     * @param page
     * @param grouponRule
     * @return
     */
    IPage<GrouponRuleVO> selectGrouponRulePage(IPage<GrouponRuleVO> page, GrouponRuleVO grouponRule);

    /**
     * 分页查询
     * @param page
     * @param grouponRule
     * @return
     */
    IPage<GrouponRuleVO> findByGrouponRuleVO(IPage<GrouponRuleVO> page, GrouponRuleVO grouponRule);

    /**
     * 详情，含商品名、购买人数
     * @param id
     * @return
     */
    GrouponRuleVO findDetailById(Integer id);

    /**
     * 全部商品，并排除某一个
     * @param excludeId 可为空
     * @return
     */
    List<GrouponRuleVO> findGrouponRules(Integer excludeId);
}
