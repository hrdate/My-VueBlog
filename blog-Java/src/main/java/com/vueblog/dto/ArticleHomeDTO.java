package com.vueblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleHomeDTO {

    /**
     * 文章id
     */
    private Long id;


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
    private long tagId;

    /**
     * 文章标签名
     */
    private String TagName;


    /**
     * 文章是否置顶
     */
    private int isTop;

    /**
     * 文章类型，原创、转载、翻译
     */
    private String type;

    /**
     * 文章封面链接
     */
    private String articleCover;
}
