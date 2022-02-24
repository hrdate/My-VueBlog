package com.vueblog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class CommentsAdminDTO implements Serializable {

    private Long id;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 游客名
     */
    private String userName;

    /**
     *
     */
    private String replyUserName;

    /**
     * 评论
     */
    private String content;

    /**
     * 留言时间
     */
    private Date created;

    /**
     * 留言状态
     */
    private Integer isReview;

}
