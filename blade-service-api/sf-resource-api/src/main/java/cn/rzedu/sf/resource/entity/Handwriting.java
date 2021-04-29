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
@TableName("sf_handwriting")
@ApiModel(value = "Handwriting对象", description = "Handwriting对象")
public class Handwriting implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 临摹的完整正文
     */
    @ApiModelProperty(value = "临摹的完整正文")
    private String sentence;
    /**
     * 规格
     */
    @ApiModelProperty(value = "规格")
    private String standards;
    /**
     * 幅式: 斗方,横幅,条幅,中堂,扇面
     */
    @ApiModelProperty(value = "幅式: 斗方,横幅,条幅,中堂,扇面")
    private String banner;
    /**
     * 字体名称: 楷,草,隶,篆,行
     */
    @ApiModelProperty(value = "字体名称: 楷,草,隶,篆,行")
    private String font;
    /**
     * 落款
     */
    @ApiModelProperty(value = "落款")
    private String sign;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    /**
     * 用户账号名
     */
    @ApiModelProperty(value = "用户账号名")
    private String userName;
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
