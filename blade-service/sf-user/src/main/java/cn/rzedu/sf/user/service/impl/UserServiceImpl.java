package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.User;
import cn.rzedu.sf.user.utils.RandomUUIDUtil;
import cn.rzedu.sf.user.vo.UserVO;
import cn.rzedu.sf.user.mapper.UserMapper;
import cn.rzedu.sf.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 用户表
 * 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public IPage<UserVO> selectUserPage(IPage<UserVO> page, UserVO user) {
        return page.setRecords(baseMapper.selectUserPage(page, user));
    }

    @Override
    public List<String> getRandomIcons() {
        return baseMapper.getRandomIcons();
    }

    @Override
    public List<User> getRandomUser(Integer number) {
        if (number == null) {
            number = 10;
        }
        return baseMapper.getRandomUser(number);
    }

    @Override
    public UserVO findByOpenId(String openId) {
        return baseMapper.findByOpenId(openId);
    }

    @Override
    public UserVO findByOpenId(String openId, String name, String headImgUrl) {
        UserVO userVO = baseMapper.findByOpenId(openId);
        if (userVO != null) {
            if (!name.equals(userVO.getName()) || !headImgUrl.equals(userVO.getIcon())) {
                userVO.setName(name);
                userVO.setIcon(headImgUrl);
                userVO.setModifyDate(LocalDateTime.now());
                baseMapper.updateById(userVO);
            }
        } else {
            userVO = new UserVO();
            userVO.setName(name);
            userVO.setIcon(headImgUrl);
            userVO.setOpenId(openId);
            userVO.setSex(0);
            userVO.setUuid(generateUUID());
            userVO.setCreateDate(LocalDateTime.now());
            userVO.setModifyDate(LocalDateTime.now());
            userVO.setIsDeleted(0);
            userVO.setSourceType(0);
            baseMapper.insert(userVO);
        }
        return userVO;
    }

    @Override
    public UserVO addUserVO(String openId, String name, String headImgUrl, Integer sourceType) {
        UserVO userVO = baseMapper.findByOpenId(openId);
        if (userVO != null) {
            if (!name.equals(userVO.getName()) || !headImgUrl.equals(userVO.getIcon()) || !sourceType.equals(userVO.getSourceType())) {
                userVO.setName(name);
//                userVO.setSourceType(sourceType);
                userVO.setIcon(headImgUrl);
                userVO.setModifyDate(LocalDateTime.now());
                baseMapper.updateById(userVO);
            }
        } else {
            userVO = new UserVO();
            userVO.setName(name);
            userVO.setIcon(headImgUrl);
            userVO.setOpenId(openId);
            userVO.setSex(0);
            userVO.setUuid(generateUUID());
            userVO.setSourceType(sourceType);
            userVO.setCreateDate(LocalDateTime.now());
            userVO.setModifyDate(LocalDateTime.now());
            userVO.setIsDeleted(0);
            baseMapper.insert(userVO);
        }
        return userVO;
    }

    /**
     * 根据用户最大值生成随机数
     * <10000，4位数字，总位数6
     * 10000~100000，5位数字，总位数7
     * 100000~1000000，6位数字，总位数8
     * @return
     */
    private String generateUUID() {
        int max = baseMapper.getMaxNumber();
        //log10: 10000以下=3; 10000~100000=4; 100000~1000000=5
        int number = (int) (Math.floor(Math.log10(max)));
        int digit = number + 3;
        if (number <= 3) {
            digit = 6;
        }
        return generateNoRepeatUUId(digit);

    }

    private String generateNoRepeatUUId(int number) {
        String uuid = RandomUUIDUtil.getUUIDString(number);
        User user = baseMapper.findByUUID(uuid);
        if (user != null) {
            uuid = generateNoRepeatUUId(number);
        }
        return uuid;
    }

    @Override
    public int getUserStars(Integer userId) {
        Integer number = baseMapper.getUserStars(userId);
        if (number != null) {
            return number.intValue();
        }
        return 0;
    }

    @Override
    public boolean updateStudyGrade(String uuid, String studyGrade) {
        return SqlHelper.retBool(baseMapper.updateStudyGrade(uuid, studyGrade));
    }

    @Override
    public Map<String, Object> findAllTypeUser(Boolean isUpdate) {
        List<UserVO> newUser = baseMapper.findNewUser(1);
        List<UserVO> newUserList = baseMapper.findNewUser(2);
        List<UserVO> otherUser = baseMapper.findOtherUser();
        List<UserVO> oldUser = new ArrayList<>();

        if (newUserList != null && !newUser.isEmpty()) {
            Integer userId = null;
            LocalDateTime time = null;
            boolean flag = false;
            long days = 0;
            for (UserVO vo : newUserList) {
                //不同用户，状态重置；同一用户，跟第一个视频时间对比，有超过24h的就是老用户
                if (!vo.getId().equals(userId)) {
                    userId = vo.getId();
                    time = vo.getModifyDate();
                    flag = false;
                }
                if (flag) {
                    continue;
                }
                days = Duration.between(time, vo.getModifyDate()).toDays();
                if (days > 1) {
                    flag = true;
                    oldUser.add(vo);

                    for (UserVO userVO : newUser) {
                        if (userVO.getId().equals(vo.getId())) {
                            newUser.remove(userVO);
                            break;
                        }
                    }
                }
            }
        }
        Map<String, Object> result = new HashMap<>(6);
        result.put("new", transferUser(newUser));
        result.put("old", transferUser(oldUser));
        result.put("other", transferUser(otherUser));
        result.put("newCount", getUserCount(newUser));
        result.put("oldCount", getUserCount(oldUser));
        result.put("otherCount", getUserCount(otherUser));
        if (isUpdate != null && isUpdate) {
            batchUpdateDimension(1, newUser);
            batchUpdateDimension(2, oldUser);
            batchUpdateDimension(3, otherUser);
        }
        return result;
    }

    private List<Map<String, Object>> transferUser(List<UserVO> list) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = null;
        if (list != null && !list.isEmpty()) {
            for (UserVO vo : list) {
                map = new HashMap<>(3);
                map.put("id", vo.getId());
                map.put("uuid", vo.getUuid());
                map.put("name", vo.getName());
                result.add(map);
            }
        }
        return result;
    }

    private int getUserCount(List<UserVO> list) {
        int count = 0;
        if (list != null && !list.isEmpty()) {
            count = list.size();
        }
        return count;
    }

    private void batchUpdateDimension(Integer dimension, List<UserVO> list) {
        if (list != null && !list.isEmpty()) {
            List<Integer> ids = new ArrayList<>(list.size());
            for (UserVO vo : list) {
                ids.add(vo.getId());
            }
            baseMapper.updateDimensionByIds(dimension, ids);
        }
    }

    @Override
    public List<UserVO> findInitiatorByAssistNumber(Integer greaterNumber, Integer lessNumber) {
        return baseMapper.findInitiatorByAssistNumber(greaterNumber, lessNumber);
    }

	@Override
	public List<UserVO> selectAllUser() {
		return baseMapper.selectAllUser();
	}
}
