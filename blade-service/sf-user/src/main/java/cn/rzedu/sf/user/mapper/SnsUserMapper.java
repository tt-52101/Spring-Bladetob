package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.SnsUser;
import cn.rzedu.sf.user.vo.SnsUserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 少年说--用户报名信息 Mapper 接口
 *
 * @author Blade
 * @since 2020-11-11
 */
public interface SnsUserMapper extends BaseMapper<SnsUser> {

    /**
     * 自定义分页
     *
     * @param page
     * @param snsUser
     * @return
     */
    List<SnsUserVO> selectSnsUserPage(IPage page, SnsUserVO snsUser);

    /**
     * 根据微信openId获取用户
     * @param openId
     * @return
     */
    SnsUserVO findByOpenId(String openId);
}
