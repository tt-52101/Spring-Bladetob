package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.HomepageEvent;
import cn.rzedu.sf.resource.vo.HomepageEventVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 首页轮播导航 Mapper 接口
 *
 * @author Blade
 * @since 2021-04-15
 */
public interface HomepageEventMapper extends BaseMapper<HomepageEvent> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param homepageEvent
	 * @return
	 */
	List<HomepageEventVO> selectHomepageEventPage(IPage page, HomepageEventVO homepageEvent);

	/**
	 * 根据类型获取，按序号降序
	 * @param type
	 * @param isAll
	 * @return
	 */
	List<HomepageEventVO> findByType(Integer type, Integer isAll);
}
