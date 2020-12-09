package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.User;
import cn.rzedu.sf.user.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 用户表
 * 服务类
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface IUserService extends IService<User> {

    /**
     * 自定义分页
     *
     * @param page
     * @param user
     * @return
     */
    IPage<UserVO> selectUserPage(IPage<UserVO> page, UserVO user);

    /**
     * 随机获取20个用户头像
     * @return
     */
    List<String> getRandomIcons();

    /**
     * 随机获取用户
     * @param number 用户数量
     * @return
     */
    List<User> getRandomUser(Integer number);

    /**
     * 根据微信openid获取用户信息
     * @param openId
     * @return
     */
    UserVO findByOpenId(String openId);

    /**
     * 根据微信openid获取用户信息，无用户则新增，有则同步用户名和头像地址
     * @param openId
     * @param name
     * @param headImgUrl
     * @return
     */
    UserVO findByOpenId(String openId, String name, String headImgUrl);

    /**
     *  根据微信openid获取用户信息，无用户则新增，有则同步用户名、头像地址和用户来源
     * @param openId
     * @param name
     * @param headImgUrl
     * @param sourceType
     * @return
     */
    UserVO addUserVO(String openId, String name, String headImgUrl, Integer sourceType);

    /**
     * 获取用户获得的所有星数
     * @param userId
     * @return
     */
    int getUserStars(Integer userId);

    /**
     * 根据用户uuid修改就读年级
     * @param uuid
     * @param studyGrade
     * @return
     */
    boolean updateStudyGrade(String uuid, String studyGrade);

    /**
     * 新、老、其他 用户区分
     * @param isUpdate 是否更新用户类型
     * @return
     */
    Map<String, Object> findAllTypeUser(Boolean isUpdate);

    /**
     * 助力人数在一定范围内的 发起人用户
     * @param greaterNumber 大数
     * @param lessNumber    小数
     * @return
     */
    List<UserVO> findInitiatorByAssistNumber(Integer greaterNumber, Integer lessNumber);
    
    List<UserVO> selectAllUser();
}
