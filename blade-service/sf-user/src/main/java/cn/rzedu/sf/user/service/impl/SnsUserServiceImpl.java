package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.SnsUser;
import cn.rzedu.sf.user.vo.SnsUserVO;
import cn.rzedu.sf.user.mapper.SnsUserMapper;
import cn.rzedu.sf.user.service.ISnsUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;

/**
 * 少年说--用户报名信息 服务实现类
 *
 * @author Blade
 * @since 2020-11-11
 */
@Service
public class SnsUserServiceImpl extends ServiceImpl<SnsUserMapper, SnsUser> implements ISnsUserService {

    @Override
    public IPage<SnsUserVO> selectSnsUserPage(IPage<SnsUserVO> page, SnsUserVO snsUser) {
        return page.setRecords(baseMapper.selectSnsUserPage(page, snsUser));
    }

    @Override
    public SnsUserVO findByOpenId(String openId) {
        return baseMapper.findByOpenId(openId);
    }

    @Override
    public SnsUser saveOrUpdate(String openId, SnsUser snsUser) {
        SnsUserVO snsUserVO = baseMapper.findByOpenId(openId);
        if (snsUserVO != null) {
            snsUser.setId(snsUserVO.getId());
            snsUser.setOpenId(openId);
            baseMapper.updateById(snsUser);
        } else {
            snsUser.setOpenId(openId);
            snsUser.setCreateDate(LocalDateTime.now());
            baseMapper.insert(snsUser);
        }
        return snsUser;
    }

    @Override
    public SnsUserVO findByOpenId(String openId, String nickname, String headImgUrl) {
        SnsUserVO snsUserVO = baseMapper.findByOpenId(openId);
        if (snsUserVO == null) {
            snsUserVO = new SnsUserVO();
            snsUserVO.setOpenId(openId);
            snsUserVO.setNickname(nickname);
            snsUserVO.setIcon(headImgUrl);
            snsUserVO.setCreateDate(LocalDateTime.now());
            baseMapper.insert(snsUserVO);
        } else {
            if (!nickname.equals(snsUserVO.getNickname()) || !headImgUrl.equals(snsUserVO.getIcon())) {
                SnsUser snsUser = new SnsUser();
                snsUser.setId(snsUserVO.getId());
                snsUser.setNickname(nickname);
                snsUser.setIcon(headImgUrl);
                baseMapper.updateById(snsUser);
            }
        }
        return snsUserVO;
    }
}
