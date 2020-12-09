package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.User;
import cn.rzedu.sf.user.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 用户表
 * Mapper 接口
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义分页
     *
     * @param page
     * @param user
     * @return
     */
    List<UserVO> selectUserPage(IPage page, UserVO user);

    /**
     * 随机获取10个用户头像
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
     * 获取用户获得的所有星数
     * @param id 用户id
     * @return
     */
    Integer getUserStars(Integer id);

    /**
     * 根据uuid获取用户
     * @param uuid
     * @return
     */
    User findByUUID(String uuid);

    /**
     * 用户总数
     * @return
     */
    Integer getMaxNumber();

    /**
     * 根据用户uuid修改就读年级
     * @param uuid
     * @param studyGrade
     * @return
     */
    Integer updateStudyGrade(String uuid, String studyGrade);

    /**
     * 新用户：看完过一个视频以上的用户
     * @param groupType 分组类型，默认1： 1=用户 2=用户，视频
     * @return
     */
    List<UserVO> findNewUser(Integer groupType);

    /**
     * 其他用户：未看/未完成看完 一个视频的用户
     * @return
     */
    List<UserVO> findOtherUser();

    /**
     * 批量修改用户维度类型
     * @param dimension
     * @param list
     * @return
     */
    int updateDimensionByIds(Integer dimension, List<Integer> list);

    /**
     * 助力人数在一定范围内的 发起人用户
     * @param greaterNumber
     * @param lessNumber
     * @return
     */
    List<UserVO> findInitiatorByAssistNumber(Integer greaterNumber, Integer lessNumber);
    
    List<UserVO> selectAllUser();
}
