package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.Teacher;
import cn.rzedu.sf.user.vo.TeacherVO;
import cn.rzedu.sf.user.mapper.TeacherMapper;
import cn.rzedu.sf.user.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 教师 服务实现类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

	@Override
	public IPage<TeacherVO> selectTeacherPage(IPage<TeacherVO> page, TeacherVO teacher) {
		return page.setRecords(baseMapper.selectTeacherPage(page, teacher));
	}

	@Override
	public List<TeacherVO> findBySchoolId(Integer schoolId) {
		return baseMapper.findBySchoolId(schoolId);
	}

	@Override
	public boolean saveOrUpdateTeacher(Teacher teacher) {
		if (teacher == null) {
			return false;
		}
		if (teacher.getId() != null) {
			baseMapper.updateById(teacher);
		} else {
			teacher.setCreateDate(LocalDateTime.now());
			baseMapper.insert(teacher);
		}
		return true;
	}
}
