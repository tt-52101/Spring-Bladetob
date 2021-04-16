package cn.rzedu.sf.resource.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 汉字资源业务封装类
 * @author Yjs
 * @since 2021-04-14
 */
@Data
@ApiModel(value = "汉字资源业务封装类", description = "汉字资源 ，汉字的（视频）资源 ，一个汉字会有多个资源记录")
public class CharacterResourceBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 对应的字典汉字记录 character.id
     */
    @NotNull(message = "汉字Id不能为空")
    @ApiModelProperty(value = "对应的字典汉字记录 character.id", required = true)
    private Integer characterId;

    /**
     * 资源学科  71=软笔书法 72=硬笔书法
     */
    @NotNull(message = "资源学科不能为空")
    @ApiModelProperty(value = "资源学科  71=软笔书法 72=硬笔书法", required = true)
    private Integer subject;

    /**
     * 字体名
     */
    @NotNull(message = "字体不能为空")
    @ApiModelProperty(value = "字体名", required = true)
    private String font;

    @NotNull(message = "文件数据集不能为空")
    @ApiModelProperty(value = "文件数据集", required = true)
    List<FileData> fileDatas;
}

