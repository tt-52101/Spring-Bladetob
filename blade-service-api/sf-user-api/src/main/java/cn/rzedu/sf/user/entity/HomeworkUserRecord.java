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
 * 学生作业记录实体类
 *
 * @author Blade
 * @since 2021-04-21
 */
@Data
@TableName("sf_homework_user_record")
@ApiModel(value = "HomeworkUserRecord对象", description = "学生作业记录")
public class HomeworkUserRecord implements Serializable {

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
     * 学生id
     */
    @ApiModelProperty(value = "学生id")
    private Integer studentId;
    /**
     * 是否已阅  1=已阅  0=未阅
     */
    @ApiModelProperty(value = "是否已阅  1=已阅  0=未阅")
    private Integer readStatus;
    /**
     * 作业内容
     */
    @ApiModelProperty(value = "作业内容")
    private String content;
    /**
     * 作业图片
     */
    @ApiModelProperty(value = "作业图片")
    private String photos;
    /**
     * 作业音频
     */
    @ApiModelProperty(value = "作业音频")
    private String audios;
    /**
     * 是否点赞
     */
    @ApiModelProperty(value = "是否点赞")
    private Integer isLike;
    /**
     * 是否推荐
     */
    @ApiModelProperty(value = "是否推荐")
    private Integer isRecommend;
    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    private Integer likeCount;
    /**
     * 推荐数
     */
    @ApiModelProperty(value = "推荐数")
    private Integer recommendCount;
    /**
     * 评论数
     */
    @ApiModelProperty(value = "评论数")
    private Integer commentCount;
    /**
     * 状态  0=未提交，1=已提交，2=已批改
     */
    @ApiModelProperty(value = "状态  0=未提交，1=已提交，2=已批改")
    private Integer status;
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
