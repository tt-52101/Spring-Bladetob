package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.UserTextbook;
import cn.rzedu.sf.user.vo.UserTextbookVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 学生教材学习情况表
 * 服务类
 *
 * @author Blade
 * @since 2020-08-05
 */
public interface IUserTextbookService extends IService<UserTextbook> {

    /**
     * 自定义分页
     *
     * @param page
     * @param userTextbook
     * @return
     */
    IPage<UserTextbookVO> selectUserTextbookPage(IPage<UserTextbookVO> page, UserTextbookVO userTextbook);

    /**
     * 获取热门课程
     *
     * @param page
     * @return
     */
    IPage<UserTextbookVO> findHotTextBookList(IPage<UserTextbookVO> page);

    /**
     * 获取用户已购课程
     *
     * @param page
     * @param userId
     * @return
     */
    IPage<UserTextbookVO> findBoughtTextBookList(IPage<UserTextbookVO> page, Integer userId);

    /**
     * 获取用户完成课程
     *
     * @param page
     * @param userId
     * @return
     */
    IPage<UserTextbookVO> findFinishedTextBookList(IPage<UserTextbookVO> page, Integer userId);

    /**
     * 获取用户教材 星数和汉字完成情况
     *
     * @param textbookId
     * @param userId
     * @return
     */
    UserTextbookVO findFinishedStars(Integer textbookId, Integer userId);

    /**
     * 获取总星数和总汉字
     *
     * @param textbookId
     * @return
     */
    UserTextbookVO findTextbookStars(Integer textbookId);

    /**
     * 唯一记录
     * @param textbookId
     * @param userId
     * @return
     */
    UserTextbook findUnionByTextbookIdAndUserId(Integer textbookId, Integer userId);

    /**
     * 根据userId 和 textbookId 修改数据
     * @param userTextbook
     * @return
     */
    boolean updateByTextbookIdAndUserId(UserTextbook userTextbook);

    /**
     * 用户已购课程，软硬笔+其他课程，最后修改时间降序
     * @param userId
     * @param isFinished    1=已完成
     * @return
     */
    List<Map<String, Object>> findBoughtList(Integer userId, Integer isFinished);
}
