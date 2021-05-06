package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.TeamType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 班级类型 Mapper 接口
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface TeamTypeMapper extends BaseMapper<TeamType> {

    /**
     * 根据类型获取
     * @param groupType
     * @return
     */
    List<TeamType> findByType(Integer groupType);
}
