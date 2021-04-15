package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.HomepageEvent;
import cn.rzedu.sf.resource.vo.HomepageEventVO;
import cn.rzedu.sf.resource.mapper.HomepageEventMapper;
import cn.rzedu.sf.resource.service.IHomepageEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 首页轮播导航 服务实现类
 *
 * @author Blade
 * @since 2021-04-15
 */
@Service
public class HomepageEventServiceImpl extends ServiceImpl<HomepageEventMapper, HomepageEvent> implements IHomepageEventService {

	@Override
	public IPage<HomepageEventVO> selectHomepageEventPage(IPage<HomepageEventVO> page, HomepageEventVO homepageEvent) {
		return page.setRecords(baseMapper.selectHomepageEventPage(page, homepageEvent));
	}

	@Override
	public List<HomepageEvent> findByType(Integer type, Integer isAll) {
		return baseMapper.findByType(type, isAll);
	}
}
