package cn.rzedu.sf.user.vo;


import cn.rzedu.sf.user.entity.FrontUser;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;


/**
 * 视图实体类
 *
 * @author Blade
 * @since 2021-04-12
 */
@Data
@ApiModel(value = "PublisherVO对象", description = "PublisherVO对象")
public class PublisherVO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "出版社id")
    private Integer id;

    /**
     * name
     */
    @ApiModelProperty(value = "出版社名")
    private String name;
    /**
     * subject
     */
    @ApiModelProperty(value = "72 = 硬笔,71 = 软笔")
    private Integer subject;

}
