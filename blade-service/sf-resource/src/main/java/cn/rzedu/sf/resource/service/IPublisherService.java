package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.Publisher;
import cn.rzedu.sf.resource.vo.PublisherVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 出版社 服务类
 *
 * @author Blade
 * @since 2020-12-15
 */
public interface IPublisherService extends IService<Publisher> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param publisher
	 * @return
	 */
	IPage<PublisherVO> selectPublisherPage(IPage<PublisherVO> page, PublisherVO publisher);

	/**
	 * 根据学科获取出版社数据
	 * @param subject
	 * @return
	 */
	List<Map<String,Object>> findBySubject(Integer subject);

	/**
	 * 根据学科获取出版社数据
	 * @param subject
	 * @return
	 */
	List<Map<String,Object>> findBySubjectAndStageCode(Integer subject, String stageCode);

	/**
	 * 根据学科获取出版社数据
	 * @param subject
	 * @return
	 */
	List<Publisher> findPublisherBySubject(Integer subject);
}
