package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.School;
import cn.rzedu.sf.user.vo.SchoolVO;
import cn.rzedu.sf.user.mapper.SchoolMapper;
import cn.rzedu.sf.user.service.ISchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 学校组织 服务实现类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements ISchoolService {

	@Override
	public IPage<SchoolVO> selectSchoolPage(IPage<SchoolVO> page, SchoolVO school) {
		return page.setRecords(baseMapper.selectSchoolPage(page, school));
	}

	@Override
	public List<School> findByType(Integer groupType) {
		return baseMapper.findByType(groupType);
	}

	@Override
	public boolean saveOrUpdateSchool(School school) {
		if (school == null) {
			return false;
		}
		Integer id = school.getId();
		if (id != null) {
			baseMapper.updateById(school);
		} else{
			school.setCreateDate(LocalDateTime.now());
			baseMapper.insert(school);
		}
		return true;
	}
}
