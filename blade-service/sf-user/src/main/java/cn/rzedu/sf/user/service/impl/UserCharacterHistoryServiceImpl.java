package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.UserCharacterHistory;
import cn.rzedu.sf.user.vo.UserCharacterHistoryVO;
import cn.rzedu.sf.user.mapper.UserCharacterHistoryMapper;
import cn.rzedu.sf.user.service.IUserCharacterHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 学生学习汉字的历史记录
 * 服务实现类
 *
 * @author Blade
 * @since 2020-08-05
 */
@Service
public class UserCharacterHistoryServiceImpl extends ServiceImpl<UserCharacterHistoryMapper, UserCharacterHistory> implements IUserCharacterHistoryService {

    @Override
    public IPage<UserCharacterHistoryVO> selectUserCharacterHistoryPage(IPage<UserCharacterHistoryVO> page,
																		UserCharacterHistoryVO userCharacterHistory) {
        return page.setRecords(baseMapper.selectUserCharacterHistoryPage(page, userCharacterHistory));
    }

}
