package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.CharacterStroke;
import cn.rzedu.sf.resource.vo.CharacterStrokeVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

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

	/**
	 * 所有笔画
	 * @return
	 */
	List<Map<String, Object>> findAll();

	/**
	 * 单个笔画
	 * @param name
	 * @return
	 */
	Map<String, Object> findByName(String name);
}
