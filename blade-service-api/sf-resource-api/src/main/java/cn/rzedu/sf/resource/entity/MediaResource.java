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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2021-03-15
 */
@Data
@TableName("sf_media_resource")
@ApiModel(value = "MediaResource对象", description = "MediaResource对象")
public class MediaResource implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;
    /**
     * 资源学科  71=软笔书法 72=硬笔书法
     */
    @ApiModelProperty(value = "资源学科  71=软笔书法 72=硬笔书法")
    private Integer subject;
    /**
     * 分类 多分类用逗号隔开
     */
    @ApiModelProperty(value = "分类 多分类用逗号隔开")
    private Integer sortId;

  /**
   * 标签
   */
    @ApiModelProperty(value = "标签")
    private String tags;

  /**
   * 文件类型  video，audio，image，text
     */
    @ApiModelProperty(value = "文件类型  video，audio，image，text")
    private String objectType;
  /**
   * 文件类型  video，audio，image，text
   */
    @ApiModelProperty(value = "文件后缀  MP4，ppt，pdf，mp3,word,image")
    private String suffix;

    /**
     * 文件对应的uuid
     */
    @ApiModelProperty(value = "文件对应的uuid")
    private String uuid;
    /**
     * 封面图url
     */
    @ApiModelProperty(value = "封面图对应的url")
    private String coverImgUrl;

    /**
     * 点击量
     */
    @ApiModelProperty(value = "点击量")
    private Integer hits;

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
    /**
     * 资源类型 1=授课教案 2=创作文案 3=国学视频 4=知识宝库 5=名师微课 
     */
    @ApiModelProperty(value = "资源类型 1=授课教案 2=创作文案 3=国学视频 4=知识宝库 5=名师微课 ")
    private Integer mediaType;


}
