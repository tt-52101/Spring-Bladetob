package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.UserCharacterHistory;
import cn.rzedu.sf.user.vo.UserCharacterHistoryVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 学生学习汉字的历史记录
 * 服务类
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface IUserCharacterHistoryService extends IService<UserCharacterHistory> {

    /**
     * 自定义分页
     *
     * @param page
     * @param userCharacterHistory
     * @return
     */
    IPage<UserCharacterHistoryVO> selectUserCharacterHistoryPage(IPage<UserCharacterHistoryVO> page,
                                                                 UserCharacterHistoryVO userCharacterHistory);

}
