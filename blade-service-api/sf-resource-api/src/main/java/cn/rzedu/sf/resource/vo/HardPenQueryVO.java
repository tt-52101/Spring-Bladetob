package cn.rzedu.sf.resource.vo;

import cn.rzedu.sf.resource.entity.HardPenQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "HardPenQueryVO对象", description = "硬笔查询")
public class HardPenQueryVO extends HardPenQuery {
    private static final long serialVersionUID = 1L;


}
