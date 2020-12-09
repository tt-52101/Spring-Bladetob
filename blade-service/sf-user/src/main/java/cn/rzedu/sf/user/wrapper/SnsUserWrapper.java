package cn.rzedu.sf.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.user.entity.SnsUser;
import cn.rzedu.sf.user.vo.SnsUserVO;

/**
 * 少年说--用户报名信息包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-11-11
 */
public class SnsUserWrapper extends BaseEntityWrapper<SnsUser, SnsUserVO> {

    public static SnsUserWrapper build() {
        return new SnsUserWrapper();
    }

    @Override
    public SnsUserVO entityVO(SnsUser snsUser) {
        SnsUserVO snsUserVO = BeanUtil.copy(snsUser, SnsUserVO.class);

        return snsUserVO;
    }

}
