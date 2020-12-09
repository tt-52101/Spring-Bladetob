package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.UserTextbook;
import cn.rzedu.sf.user.vo.UserTextbookVO;
import cn.rzedu.sf.user.mapper.UserTextbookMapper;
import cn.rzedu.sf.user.service.IUserTextbookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 学生教材学习情况表
 * 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Service
public class UserTextbookServiceImpl extends ServiceImpl<UserTextbookMapper, UserTextbook> implements IUserTextbookService {

    @Override
    public IPage<UserTextbookVO> selectUserTextbookPage(IPage<UserTextbookVO> page, UserTextbookVO userTextbook) {
        return page.setRecords(baseMapper.selectUserTextbookPage(page, userTextbook));
    }

    @Override
    public IPage<UserTextbookVO> findHotTextBookList(IPage<UserTextbookVO> page) {
        return page.setRecords(baseMapper.findHotTextBookList(page));
    }

    @Override
    public IPage<UserTextbookVO> findBoughtTextBookList(IPage<UserTextbookVO> page, Integer userId) {
        return page.setRecords(baseMapper.findBoughtTextBookList(page, userId));
    }

    @Override
    public IPage<UserTextbookVO> findFinishedTextBookList(IPage<UserTextbookVO> page, Integer userId) {
        return page.setRecords(baseMapper.findFinishedTextBookList(page, userId));
    }

    @Override
    public UserTextbookVO findFinishedStars(Integer textbookId, Integer userId) {
        return baseMapper.findFinishedStars(textbookId, userId);
    }

    @Override
    public UserTextbookVO findTextbookStars(Integer textbookId) {
        return baseMapper.findTextbookStars(textbookId);
    }

    @Override
    public UserTextbook findUnionByTextbookIdAndUserId(Integer textbookId, Integer userId) {
        return baseMapper.findUnionByTextbookIdAndUserId(textbookId, userId);
    }

    @Override
    public boolean updateByTextbookIdAndUserId(UserTextbook userTextbook) {
        return SqlHelper.retBool(baseMapper.updateByTextbookIdAndUserId(userTextbook));
    }

    @Override
    public List<Map<String, Object>> findBoughtList(Integer userId, Integer isFinished) {
        return baseMapper.findBoughtList(userId, isFinished);
    }
}
