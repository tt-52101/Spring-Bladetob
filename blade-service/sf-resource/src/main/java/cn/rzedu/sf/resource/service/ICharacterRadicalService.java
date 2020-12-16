package cn.rzedu.sf.resource.service;

import cn.rzedu.sf.resource.entity.CharacterRadical;
import cn.rzedu.sf.resource.vo.CharacterRadicalVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 汉字偏旁 服务类
 *
 * @author Blade
 * @since 2020-12-10
 */
public interface ICharacterRadicalService extends IService<CharacterRadical> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param characterRadical
	 * @return
	 */
	IPage<CharacterRadicalVO> selectCharacterRadicalPage(IPage<CharacterRadicalVO> page,
                                                         CharacterRadicalVO characterRadical);

	/**
	 * 所有偏旁
	 * @return
	 */
	List<Map<String, Object>> findAll();

	/**
	 * 单个偏旁
	 * @param name
	 * @return
	 */
	Map<String, Object> findByName(String name);
}
