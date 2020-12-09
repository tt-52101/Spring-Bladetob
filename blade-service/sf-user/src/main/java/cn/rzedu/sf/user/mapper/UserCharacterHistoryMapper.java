package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.UserCharacterHistory;
import cn.rzedu.sf.user.vo.UserCharacterHistoryVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 学生学习汉字的历史记录
 * Mapper 接口
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface UserCharacterHistoryMapper extends BaseMapper<UserCharacterHistory> {

    /**
     * 自定义分页
     *
     * @param page
     * @param userCharacterHistory
     * @return
     */
    List<UserCharacterHistoryVO> selectUserCharacterHistoryPage(IPage page,
                                                                UserCharacterHistoryVO userCharacterHistory);

}
