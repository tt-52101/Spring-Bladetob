package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.School;
import cn.rzedu.sf.user.vo.SchoolVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 学校组织 Mapper 接口
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface SchoolMapper extends BaseMapper<School> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param school
	 * @return
	 */
	List<SchoolVO> selectSchoolPage(IPage page, SchoolVO school);

	/**
	 * 根据类型获取
	 * @param groupType
	 * @return
	 */
	List<School> findByType(Integer groupType);

}
