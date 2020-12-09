package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.SnsUser;
import cn.rzedu.sf.user.vo.SnsUserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 少年说--用户报名信息 服务类
 *
 * @author Blade
 * @since 2020-11-11
 */
public interface ISnsUserService extends IService<SnsUser> {

    /**
     * 自定义分页
     *
     * @param page
     * @param snsUser
     * @return
     */
    IPage<SnsUserVO> selectSnsUserPage(IPage<SnsUserVO> page, SnsUserVO snsUser);

    /**
     * 根据微信openId获取用户
     * @param openId
     * @return
     */
    SnsUserVO findByOpenId(String openId);

    /**
     * 根据openId新增或修改用户
     * @param openId
     * @param snsUser
     * @return
     */
    SnsUser saveOrUpdate(String openId, SnsUser snsUser);

    /**
     * 查询或新增用户
     * @param openId
     * @param nickname
     * @param headImgUrl
     * @return
     */
    SnsUserVO findByOpenId(String openId, String nickname, String headImgUrl);
}
