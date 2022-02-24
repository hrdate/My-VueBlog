package com.vueblog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_comments")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "articleId")
    private Long articleId;

    @TableField(value = "userId")
    private long userId;

    /**
     * 评论
     */
    @NotBlank(message = "评论内容不能为空")
    @TableField(value = "content")
    private String content;

    /**
     * 留言时间
     */
//    @TableField(value = "created")
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    /**
     * 留言状态
     */
    @TableField(value = "is_review")
    private Integer isReview;
    /**
     * 父评论id
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 父评论的用户id
     */
    @TableField(value = "reply_userId")
    private Integer replyUserId;

    /**
     * 评论下的留言个数
     */
    @TableField(exist = false)
    private Integer replyCount;
    /**
     * 评论下的留言
     */
    @TableField(exist = false)
    private List<Comments> replyDTOList;
}
