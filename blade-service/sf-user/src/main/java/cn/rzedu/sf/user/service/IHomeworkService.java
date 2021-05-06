package cn.rzedu.sf.user.service;

import cn.rzedu.sf.user.entity.Homework;
import cn.rzedu.sf.user.vo.HomeworkVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 作业 服务类
 *
 * @author Blade
 * @since 2021-04-21
 */
public interface IHomeworkService extends IService<Homework> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param homework
	 * @return
	 */
	IPage<HomeworkVO> selectHomeworkPage(IPage<HomeworkVO> page, HomeworkVO homework);

}
