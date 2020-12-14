package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.CharacterStroke;
import cn.rzedu.sf.resource.vo.CharacterStrokeVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 汉字笔画 服务类
 *
 * @author Blade
 * @since 2020-12-10
 */
public interface ICharacterStrokeService extends IService<CharacterStroke> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param characterStroke
	 * @return
	 */
	IPage<CharacterStrokeVO> selectCharacterStrokePage(IPage<CharacterStrokeVO> page,
                                                       CharacterStrokeVO characterStroke);

}
