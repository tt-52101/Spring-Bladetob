package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.GrouponRule;
import cn.rzedu.sc.goods.vo.GrouponRuleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 团购规则。一个团购规则记录是针对一个商品进行一次团购销售而设计的 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface GrouponRuleMapper extends BaseMapper<GrouponRule> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param grouponRule
	 * @return
	 */
	List<GrouponRuleVO> selectGrouponRulePage(IPage page, GrouponRuleVO grouponRule);

	/**
	 * 分页查询
	 * @param page
	 * @param grouponRule
	 * @return
	 */
	List<GrouponRuleVO> findByGrouponRuleVO(IPage page, GrouponRuleVO grouponRule);

	/**
	 * 详情，含商品名、购买人数
	 * @param id
	 * @return
	 */
	GrouponRuleVO findDetailById(Integer id);
}
