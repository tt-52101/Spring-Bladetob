package cn.rzedu.sf.resource.service.impl;

import cn.rzedu.sf.resource.entity.Publisher;
import cn.rzedu.sf.resource.vo.PublisherVO;
import cn.rzedu.sf.resource.mapper.PublisherMapper;
import cn.rzedu.sf.resource.service.IPublisherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出版社 服务实现类
 *
 * @author Blade
 * @since 2020-12-15
 */
@Service
public class PublisherServiceImpl extends ServiceImpl<PublisherMapper, Publisher> implements IPublisherService {

	@Override
	public IPage<PublisherVO> selectPublisherPage(IPage<PublisherVO> page, PublisherVO publisher) {
		return page.setRecords(baseMapper.selectPublisherPage(page, publisher));
	}

	@Override
	public List<Map<String, Object>> findBySubject(Integer subject) {
		List<Publisher> publisherList = baseMapper.findBySubject(subject);
		if (publisherList == null || publisherList.isEmpty()) {
			return null;
		}
		List<Map<String, Object>> list = new ArrayList<>(publisherList.size());
		Map<String, Object> map = null;
		for (Publisher publisher : publisherList) {
			map = new HashMap<>(3);
			map.put("name", publisher.getName());
			map.put("logo", publisher.getLogo());
			map.put("coverImage", publisher.getCoverImage());
			list.add(map);
		}
		return list;
	}
}
