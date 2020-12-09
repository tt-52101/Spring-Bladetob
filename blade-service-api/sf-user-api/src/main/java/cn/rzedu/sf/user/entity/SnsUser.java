package cn.rzedu.sf.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 少年说--用户报名信息实体类
 *
 * @author Blade
 * @since 2020-11-11
 */
@Data
@ApiModel(value = "SnsUser对象", description = "少年说--用户报名信息")
public class SnsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 微信openId
     */
    @ApiModelProperty(value = "微信openId")
    private String openId;
    /**
     * 微信头像地址
     */
    @ApiModelProperty(value = "微信头像地址")
    private String icon;
    /**
     * 微信昵称
     */
    @ApiModelProperty(value = "微信昵称")
    private String nickname;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 学校
     */
    @ApiModelProperty(value = "学校")
    private String school;
    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birthday;
    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String mobile;
    /**
     * 参赛组别  1=幼儿组  2=少儿A组  3=少儿B组  4=少儿C组
     */
    @ApiModelProperty(value = "参赛组别  1=幼儿组  2=少儿A组  3=少儿B组  4=少儿C组   " +
            "幼儿组 ：2013年9月1日-2016年8月31日出生。少儿A组：2011年9月1日-2013年8月31日出生。" +
            "少儿B组：2009年9月1日-2011年8月31日出生。少儿C组：2007年9月1日-2009年8月31日出生")
    private Integer category;
    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private String province;
    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String city;
    /**
     * 区/县
     */
    @ApiModelProperty(value = "区/县")
    private String district;
    /**
     * 省市区
     */
    @ApiModelProperty(value = "省市区")
    private String location;
    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String address;
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
    @TableLogic
    @ApiModelProperty(value = "删除标识")
    private Integer isDeleted;


}
