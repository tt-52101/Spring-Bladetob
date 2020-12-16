package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.Publisher;
import cn.rzedu.sf.resource.vo.PublisherVO;

/**
 * 出版社包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-12-15
 */
public class PublisherWrapper extends BaseEntityWrapper<Publisher, PublisherVO>  {

    public static PublisherWrapper build() {
        return new PublisherWrapper();
    }

	@Override
	public PublisherVO entityVO(Publisher publisher) {
		PublisherVO publisherVO = BeanUtil.copy(publisher, PublisherVO.class);

		return publisherVO;
	}

}
