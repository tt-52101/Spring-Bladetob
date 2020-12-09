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
import cn.rzedu.sf.resource.entity.Textbook;
import cn.rzedu.sf.resource.vo.TextbookVO;

/**
 * 书法教材表
 * 1.系统使用的书法教材
 * 2.属性与普教资源平台的教材类似
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-08-05
 */
public class TextbookWrapper extends BaseEntityWrapper<Textbook, TextbookVO> {

    public static TextbookWrapper build() {
        return new TextbookWrapper();
    }

    @Override
    public TextbookVO entityVO(Textbook textbook) {
        TextbookVO textbookVO = BeanUtil.copy(textbook, TextbookVO.class);

        return textbookVO;
    }

}
