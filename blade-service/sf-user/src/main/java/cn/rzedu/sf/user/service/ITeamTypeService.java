package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.TeamType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 班级类型 服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface ITeamTypeService extends IService<TeamType> {

    /**
     * 根据类型获取
     * @param groupType
     * @return
     */
    List<TeamType> findByType(Integer groupType);

    /**
     * 增/改
     * @param groupType
     * @param name
     * @param listOrder
     * @param id
     * @return
     */
    boolean saveOrUpdateTeamType(Integer groupType, String name, Integer listOrder, Integer id);
}
