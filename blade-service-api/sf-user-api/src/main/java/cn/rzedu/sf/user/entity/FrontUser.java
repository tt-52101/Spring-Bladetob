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
package cn.rzedu.sf.user.entity;

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
 * @since 2021-04-12
 */
@Data
@TableName("sf_front_user")
@ApiModel(value = "FrontUser对象", description = "FrontUser对象")
public class FrontUser implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 用户名,账号,注册码
     */
    @ApiModelProperty(value = "用户名,账号,注册码")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 用户类型id
     */
    @ApiModelProperty(value = "1=普及版，2=基础版，3=互动版")
    private int typeId;
    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private String typeName;
    /**
     * 省级区划编号
     */
    @ApiModelProperty(value = "省级区划编号")
    private String provinceCode;
    /**
     * 省级名称
     */
    @ApiModelProperty(value = "省级名称")
    private String provinceName;
    /**
     * 市级区划编号
     */
    @ApiModelProperty(value = "市级区划编号")
    private String cityCode;
    /**
     * 市级名称
     */
    @ApiModelProperty(value = "市级名称")
    private String cityName;
    /**
     * 区级区划编号
     */
    @ApiModelProperty(value = "区级区划编号")
    private String districtCode;
    /**
     * 区级名称
     */
    @ApiModelProperty(value = "区级名称")
    private String districtName;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String department;
    /**
     * 出版社名称,多个用逗号隔开
     */
    @ApiModelProperty(value = "出版社名称,多个用逗号隔开")
    private String publisherName;
    /**
     * 出版社id,多个用逗号隔开
     */
    @ApiModelProperty(value = "出版社id,多个用逗号隔开")
    private String publisherId;
    /**
     * 年级册次id,多个用逗号隔开
     */
    @ApiModelProperty(value = "年级册次id,多个用逗号隔开")
    private String gradeId;
    /**
     * 年级册次名字,多个用逗号隔开
     */
    @ApiModelProperty(value = "年级册次名字,多个用逗号隔开")
    private String gradeName;
    /**
     * 功能权限id,多个用逗号隔开
     */
    @ApiModelProperty(value = "年级册次id,多个用逗号隔开")
    private String functionId;
    /**
     * 功能权限名,多个用逗号隔开
     */
    @ApiModelProperty(value = "年级册次名字,多个用逗号隔开")
    private String functionName;
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
     * 状态
     */
    @ApiModelProperty(value = "修改时间")
    private Integer status;
    /**
     * 最后使用时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime lastUseTime;
    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Integer isDeleted;


}
