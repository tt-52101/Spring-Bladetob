package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.School;
import cn.rzedu.sf.user.vo.SchoolVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 学校组织 服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface ISchoolService extends IService<School> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param school
	 * @return
	 */
	IPage<SchoolVO> selectSchoolPage(IPage<SchoolVO> page, SchoolVO school);

	/**
	 * 根据类型获取
	 * @param groupType
	 * @return
	 */
	List<School> findByType(Integer groupType);

	/**
	 * 增/改
	 * @param school
	 * @return
	 */
	boolean saveOrUpdateSchool(School school);
}
