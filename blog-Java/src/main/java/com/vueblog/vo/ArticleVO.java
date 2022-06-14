package com.vueblog.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVO {

    /**
     * 文章id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 文章标签id
     */
    private Long tagId;

    /**
     * 文章标签名
     */
    private String TagName;

    /**
     * 文章标签id
     */
    private Long viewsCount;

    /**
     * 文章是否置顶
     */
    private Integer isTop;

    /**
     * 文章类型，原创、转载、翻译
     */
    private String type;

    /**
     * 文章封面链接
     */
    private String articleCover;

    /**
     * 文章点赞数量
     */
    private Long articleLike;
}
