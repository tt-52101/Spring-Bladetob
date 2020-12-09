package cn.rzedu.sc.goods.service;

import cn.rzedu.sc.goods.entity.DealUser;
import cn.rzedu.sc.goods.vo.DealUserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  用户-商品交易记录表。以用户和商品为单位的成交流水明细 服务类
 *
 * @author Blade
 * @since 2020-10-12
 */
public interface IDealUserService extends IService<DealUser> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dealUser
	 * @return
	 */
	IPage<DealUserVO> selectDealUserPage(IPage<DealUserVO> page, DealUserVO dealUser);

}
