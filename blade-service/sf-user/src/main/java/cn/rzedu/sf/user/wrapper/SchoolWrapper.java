package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.School;
import cn.rzedu.sf.user.vo.SchoolVO;

/**
 * 学校组织包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2021-04-21
 */
public class SchoolWrapper extends BaseEntityWrapper<School, SchoolVO>  {

    public static SchoolWrapper build() {
        return new SchoolWrapper();
    }

	@Override
	public SchoolVO entityVO(School school) {
		SchoolVO schoolVO = BeanUtil.copy(school, SchoolVO.class);

		return schoolVO;
	}

}
