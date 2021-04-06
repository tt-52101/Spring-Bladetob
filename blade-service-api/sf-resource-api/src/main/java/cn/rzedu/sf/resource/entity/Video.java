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

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Blade
 * @since 2021-01-15
 */
@Data
@TableName("sf_video")
@ApiModel(value = "Video对象", description = "Video对象")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ?????????
     */
    @ApiModelProperty(value = "?????????")
    @TableField("CNID")
  private BigDecimal cnid;
    /**
     * ????ID
     */
    @ApiModelProperty(value = "????ID")
    @TableField("CNGUID")
  private String cnguid;
    /**
     * ????????
     */
    @ApiModelProperty(value = "????????")
    @TableField("CNVIDEOGUID")
  private String cnvideoguid;
    /**
     * ????????
     */
    @ApiModelProperty(value = "????????")
    @TableField("VIDEONAME")
  private String videoname;
    /**
     * ????????
     */
    @ApiModelProperty(value = "????????")
    @TableField("HZNAME")
  private String hzname;
    /**
     * ??????
     */
    @ApiModelProperty(value = "??????")
    @TableField("CNSOPERATOR")
  private String cnsoperator;
    /**
     * ????????
     */
    @ApiModelProperty(value = "????????")
    @TableField("CNDTOPERATE")
  private String cndtoperate;
    /**
     * ????????
     */
    @ApiModelProperty(value = "????????")
    @TableField("CNSPINYIN")
  private String cnspinyin;
    /**
     * ????????
     */
    @ApiModelProperty(value = "????????")
    @TableField("CNTYPE")
  private String cntype;
    /**
     * ????????
     */
    @ApiModelProperty(value = "????????")
    @TableField("CNSCLASSHOUR")
  private String cnsclasshour;
    /**
     * ????????
     */
    @ApiModelProperty(value = "????????")
    @TableField("CNSVERSION")
  private String cnsversion;
    /**
     * ????????
     */
    @ApiModelProperty(value = "????????")
    @TableField("CNNJCODE")
  private String cnnjcode;
    /**
     * ??????????
     */
    @ApiModelProperty(value = "??????????")
    @TableField("ISBD")
  private String isbd;
  @TableField("CONTENT")
  private String content;
  @TableField("SZUINT")
  private String szuint;
  @TableField("SZUINTNR")
  private String szuintnr;
  @TableField("CONTENTTEXT")
  private String contenttext;
    /**
     * 视频格式
     */
    @ApiModelProperty(value = "视频格式")
    @TableField("VIDEOGESHI")
  private String videogeshi;


}
