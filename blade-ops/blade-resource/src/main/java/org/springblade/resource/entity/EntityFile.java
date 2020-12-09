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
package org.springblade.resource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 书法上传文件记录表实体类
 *
 * @author Blade
 * @since 2020-08-10
 */
@Data
@TableName("sf_entity_file")
@ApiModel(value = "EntityFile对象", description = "书法上传文件记录表")
public class EntityFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 文件md5码
     */
    @ApiModelProperty(value = "文件md5码")
    private String md5Code;
    /**
     * 做关联的uuid
     */
    @ApiModelProperty(value = "做关联的uuid")
    private String uuid;
    /**
     * 文件缩略图地址
     */
    @ApiModelProperty(value = "文件缩略图地址")
    private String thumbnailUrl;
    /**
     * 文件存放路径地址
     */
    @ApiModelProperty(value = "文件存放路径地址")
    private String url;
    /**
     * 上传后修改生成的文件名
     */
    @ApiModelProperty(value = "上传后修改生成的文件名")
    private String fileName;
    /**
     * 上传时的真实文件名
     */
    @ApiModelProperty(value = "上传时的真实文件名")
    private String realFileName;
    /**
     * 文件后缀
     */
    @ApiModelProperty(value = "文件后缀")
    private String suffix;
    /**
     * 文件大小 单位mb
     */
    @ApiModelProperty(value = "文件大小")
    private Double size;
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


}
