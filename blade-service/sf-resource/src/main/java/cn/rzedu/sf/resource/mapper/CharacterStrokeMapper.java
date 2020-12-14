package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.CharacterStroke;
import cn.rzedu.sf.resource.vo.CharacterStrokeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 汉字笔画 Mapper 接口
 *
 * @author Blade
 * @since 2020-12-10
 */
public interface CharacterStrokeMapper extends BaseMapper<CharacterStroke> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param characterStroke
	 * @return
	 */
	List<CharacterStrokeVO> selectCharacterStrokePage(IPage page, CharacterStrokeVO characterStroke);

}
