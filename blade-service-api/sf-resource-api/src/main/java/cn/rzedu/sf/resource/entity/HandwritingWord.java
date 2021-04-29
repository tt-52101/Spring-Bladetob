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
package cn.rzedu.sf.resource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Blade
 * @since 2021-04-26
 */
@Data
@TableName("sf_handwriting_word")
@ApiModel(value = "HandwritingWord对象", description = "HandwritingWord对象")
public class HandwritingWord implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 单字
     */
    @ApiModelProperty(value = "单字")
    private String word;
    /**
     * 单字图片的uuid
     */
    @ApiModelProperty(value = "单字图片的uuid")
    private String uuid;
    /**
     * 字体名称: 楷,草,隶,篆,行
     */
    @ApiModelProperty(value = "字体名称: 楷,草,隶,篆,行")
    private String font;
    /**
     * 所属书法作者
     */
    @ApiModelProperty(value = "所属书法作者")
    private String sourceAuthor;
    /**
     * 所属碑帖分类
     */
    @ApiModelProperty(value = "所属碑帖分类")
    private String sourceInscriptions;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyDate;
    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Integer isDeleted;


}
