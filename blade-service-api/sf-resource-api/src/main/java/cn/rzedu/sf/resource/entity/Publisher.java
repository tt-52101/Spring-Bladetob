package cn.rzedu.sf.resource.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * 出版社实体类
 *
 * @author Blade
 * @since 2020-12-15
 */
@Data
@TableName("sf_publisher")
@ApiModel(value = "Publisher对象", description = "出版社")
public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 出版社名称
     */
    @ApiModelProperty(value = "出版社名称")
    private String name;
    /**
     * 学科   71=软笔书法 72=硬笔书法
     */
    @ApiModelProperty(value = "学科   71=软笔书法 72=硬笔书法")
    private Integer subject;
    /**
     * 商标
     */
    @ApiModelProperty(value = "商标")
    private String logo;
    /**
     * 封面图像
     */
    @ApiModelProperty(value = "封面图像")
    private String coverImage;
    /**
     * 缩略图
     */
    @ApiModelProperty(value = "缩略图")
    private String thumb;
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
