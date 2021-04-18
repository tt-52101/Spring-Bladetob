package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.Publisher;
import cn.rzedu.sf.resource.vo.PublisherVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 出版社 Mapper 接口
 *
 * @author Blade
 * @since 2020-12-15
 */
public interface PublisherMapper extends BaseMapper<Publisher> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param publisher
	 * @return
	 */
	List<PublisherVO> selectPublisherPage(IPage page, PublisherVO publisher);

	/**
	 * 根据学科获取出版社数据
	 * @param subject
	 * @return
	 */
	List<Publisher> findBySubject(Integer subject);

	List<Publisher> findBySubjectAndStageCode(Integer subject, String stageCode);
}
