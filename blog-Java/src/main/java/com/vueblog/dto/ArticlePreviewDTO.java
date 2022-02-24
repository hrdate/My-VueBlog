package com.vueblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


/**
 * 预览文章
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePreviewDTO {

    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String title;

    /**
     * 发表时间
     */
    private Date created;

    /**
     * 文章分类id
     */
    private long tagId;

    /**
     * 文章分类名
     */
    private String tagName;

    /**
     * 文章类型
     */
    private String type;

}
