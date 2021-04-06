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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 字体基本信息表实体类
 *
 * @author Blade
 * @since 2021-01-14
 */
@Data
@TableName("setword")
@ApiModel(value = "Word对象", description = "字体基本信息表")
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId("CNID")
  private BigDecimal cnid;
  @TableField("CNGUID")
  private String cnguid;
    /**
     * 汉字名称
     */
    @ApiModelProperty(value = "汉字名称")
    @TableField("CNSFONTNAME")
  private String cnsfontname;
    /**
     * 简体字
     */
    @ApiModelProperty(value = "简体字")
    @TableField("CNSSIMPLIFIED")
  private String cnssimplified;
    /**
     * 汉字部首
     */
    @ApiModelProperty(value = "汉字部首")
    @TableField("CNSRADICAL")
  private String cnsradical;
    /**
     * 汉字笔画
     */
    @ApiModelProperty(value = "汉字笔画")
    @TableField("CNNSTROKE")
  private String cnnstroke;
    /**
     * 汉字结构
     */
    @ApiModelProperty(value = "汉字结构")
    @TableField("CNSSTRUCTURE")
  private String cnsstructure;
    /**
     * 是否可用
     */
    @ApiModelProperty(value = "是否可用")
    @TableField("CNSNOTE")
  private String cnsnote;
    /**
     * 汉字拼音
     */
    @ApiModelProperty(value = "汉字拼音")
    @TableField("CNSPINYINZM")
  private String cnspinyinzm;
    /**
     * 汉字释义
     */
    @ApiModelProperty(value = "汉字释义")
    @TableField("CNSEXPLANATION")
  private String cnsexplanation;
    /**
     * 所在单元
     */
    @ApiModelProperty(value = "所在单元")
    @TableField("CNSEVOLUTION")
  private String cnsevolution;
    /**
     * 所在课时
     */
    @ApiModelProperty(value = "所在课时")
    @TableField("CNSCLASSHOUR")
  private String cnsclasshour;
    /**
     * 出版书号
     */
    @ApiModelProperty(value = "出版书号")
    @TableField("CNSVERSION")
  private String cnsversion;
    /**
     * 字形特征
     */
    @ApiModelProperty(value = "字形特征")
    @TableField("CNZXTZ")
  private String cnzxtz;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField("CNSMENDER")
  private String cnsmender;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @TableField("CNDTMODIFY")
  private String cndtmodify;
    /**
     * 所在的年级
     */
    @ApiModelProperty(value = "所在的年级")
    @TableField("CNNJCODE")
  private String cnnjcode;
    /**
     * 拼音字母
     */
    @ApiModelProperty(value = "拼音字母")
    @TableField("CNSPINYINZW")
  private String cnspinyinzw;
    /**
     * 六书编号
     */
    @ApiModelProperty(value = "六书编号")
    @TableField("CNGUIDLS")
  private String cnguidls;
    /**
     * 汉字字体
     */
    @ApiModelProperty(value = "汉字字体")
    @TableField("HZZT")
  private String hzzt;
    /**
     * 汉字释义
     */
    @ApiModelProperty(value = "汉字释义")
    @TableField("CNSEXPTEXT")
  private String cnsexptext;


}
