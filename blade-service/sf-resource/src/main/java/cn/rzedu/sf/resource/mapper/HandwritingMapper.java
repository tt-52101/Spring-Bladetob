/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.rzedu.sf.resource.mapper;

import cn.rzedu.sf.resource.entity.Handwriting;
import cn.rzedu.sf.resource.vo.HandwritingWordVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import java.time.LocalDateTime;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Blade
 * @since 2021-04-26
 */
public interface HandwritingMapper extends BaseMapper<Handwriting> {

	int generateHandwriting(String sentence, String standards, String banner, String font, String sign, int userId, String userName, LocalDateTime createDate);

	HandwritingWordVO selectHandwritingWord(String word,String font,String sourceAuthor,String sourceInscriptions);

	List<String> selectSourceAuthor();

	List<HandwritingWordVO> selectSourceInscriptions(String word,String font,String sourceAuthor);

}
