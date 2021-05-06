package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.GcItem;
import cn.rzedu.sf.user.vo.GcItemVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 基础代码 服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface IGcItemService extends IService<GcItem> {

    /**
     * 某分类所有代码
     * @param code
     * @return
     */
    List<GcItemVO> findByCode(String code);

    /**
     * 某分类代码
     * @param code
     * @param value
     * @return
     */
    List<GcItemVO> findByRegexValue(String code, String value);

    /**
     * 某分类代码
     * @param code
     * @param name
     * @return
     */
    List<GcItemVO> findByRegexName(String code, String name);

    /**
     * 分类代码名
     * @param code
     * @param value
     * @return
     */
    String getItemName(String code, String value);

    /**
     * 分类代码值
     * @param code
     * @param name
     * @return
     */
    String getItemValue(String code, String name);

    /**
     * 获取最大值value+1
     * @param code
     * @param value
     * @return
     */
    String getNextValue(String code, String value);

    /**
     * 增/改
     * @param gcItem
     * @return
     */
    int saveOrUpdateItem(GcItem gcItem);
}
