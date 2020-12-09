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
import cn.rzedu.sf.resource.entity.TextbookLesson;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;

/**
 * 教材课程
 * 1.教材textbook表包含的课程
 * 2.部分课程（硬笔）包含二维码
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-08-05
 */
public class TextbookLessonWrapper extends BaseEntityWrapper<TextbookLesson, TextbookLessonVO> {

    public static TextbookLessonWrapper build() {
        return new TextbookLessonWrapper();
    }

    @Override
    public TextbookLessonVO entityVO(TextbookLesson textbookLesson) {
        TextbookLessonVO textbookLessonVO = BeanUtil.copy(textbookLesson, TextbookLessonVO.class);

        return textbookLessonVO;
    }

}
