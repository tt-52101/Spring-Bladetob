package cn.rzedu.sf.user.mapper;

import cn.rzedu.sf.user.entity.Homework;
import cn.rzedu.sf.user.vo.HomeworkVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 作业 Mapper 接口
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface HomeworkMapper extends BaseMapper<Homework> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param homework
	 * @return
	 */
	List<HomeworkVO> selectHomeworkPage(IPage page, HomeworkVO homework);

}
