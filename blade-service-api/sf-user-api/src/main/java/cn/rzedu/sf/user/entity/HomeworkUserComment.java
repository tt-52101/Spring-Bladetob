package cn.rzedu.sf.user.entity;

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
 * 作业评价实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@TableName("sf_homework_user_comment")
@ApiModel(value = "HomeworkUserComment对象", description = "作业评价")
public class HomeworkUserComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 作业id
     */
    @ApiModelProperty(value = "作业id")
    private Integer homeworkId;
    /**
     * 用户id  sf_user.id
     */
    @ApiModelProperty(value = "用户id  sf_user.id")
    private Integer userId;
    /**
     * 类型  1=点赞 2=推荐 3=评论
     */
    @ApiModelProperty(value = "类型  1=点赞 2=推荐 3=评论")
    private Integer type;
    /**
     * 文字点评内容
     */
    @ApiModelProperty(value = "文字点评内容")
    private String content;
    /**
     * 批改图片
     */
    @ApiModelProperty(value = "批改图片")
    private String photos;
    /**
     * 语音点评内容
     */
    @ApiModelProperty(value = "语音点评内容")
    private String audios;
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
