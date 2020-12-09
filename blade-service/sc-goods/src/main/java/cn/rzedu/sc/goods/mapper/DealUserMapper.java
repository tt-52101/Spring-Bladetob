package cn.rzedu.sc.goods.mapper;

import cn.rzedu.sc.goods.entity.DealUser;
import cn.rzedu.sc.goods.vo.DealUserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *  用户-商品交易记录表。以用户和商品为单位的成交流水明细 Mapper 接口
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface DealUserMapper extends BaseMapper<DealUser> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dealUser
	 * @return
	 */
	List<DealUserVO> selectDealUserPage(IPage page, DealUserVO dealUser);

}
