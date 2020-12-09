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
package cn.rzedu.sf.resource.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.vo.CharacterVO;

/**
 * 汉字表
 * 对应字典里面的汉字
 * 一个汉字对应唯一一个记录，同时包含该汉字的软笔和硬笔的资源总属性
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-08-05
 */
public class CharacterWrapper extends BaseEntityWrapper<Character, CharacterVO> {

    public static CharacterWrapper build() {
        return new CharacterWrapper();
    }

    @Override
    public CharacterVO entityVO(Character character) {
        CharacterVO characterVO = BeanUtil.copy(character, CharacterVO.class);

        return characterVO;
    }

}
