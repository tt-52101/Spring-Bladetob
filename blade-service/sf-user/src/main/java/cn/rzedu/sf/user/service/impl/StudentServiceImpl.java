package cn.rzedu.sf.user.service.impl;

import cn.rzedu.sf.user.entity.Student;
import cn.rzedu.sf.user.vo.StudentVO;
import cn.rzedu.sf.user.mapper.StudentMapper;
import cn.rzedu.sf.user.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

	@Override
	public IPage<StudentVO> selectStudentPage(IPage<StudentVO> page, StudentVO student) {
		return page.setRecords(baseMapper.selectStudentPage(page, student));
	}

}
