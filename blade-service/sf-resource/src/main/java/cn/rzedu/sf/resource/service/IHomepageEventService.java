package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.HomepageEvent;
import cn.rzedu.sf.resource.vo.HomepageEventVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 首页轮播导航 服务类
 *
 * @author Blade
 * @since 2021-04-15
 */
public interface IHomepageEventService extends IService<HomepageEvent> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param homepageEvent
	 * @return
	 */
	IPage<HomepageEventVO> selectHomepageEventPage(IPage<HomepageEventVO> page, HomepageEventVO homepageEvent);

	/**
	 * 根据类型获取，按序号降序
	 * @param type
	 * @return
	 */
	List<HomepageEvent> findByType(Integer type, Integer isAll);
}
