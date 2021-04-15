package cn.rzedu.sf.resource.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.HomepageEvent;
import cn.rzedu.sf.resource.vo.HomepageEventVO;

/**
 * 首页轮播导航包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-15
 */
public class HomepageEventWrapper extends BaseEntityWrapper<HomepageEvent, HomepageEventVO>  {

    public static HomepageEventWrapper build() {
        return new HomepageEventWrapper();
    }

	@Override
	public HomepageEventVO entityVO(HomepageEvent homepageEvent) {
		HomepageEventVO homepageEventVO = BeanUtil.copy(homepageEvent, HomepageEventVO.class);

		return homepageEventVO;
	}

}
