package com.vueblog.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloginfoDTO {

    /**
     *
     */
    private Long id;

    /**
     * 网站浏览量
     */
    private Long viewsCount;

    /**
     * 用户数量
     */
    private Long userCount;

    /**
     * 文章数量
     */
    private Long articleCount;

    /**
     * 标签数量
     */
    private Long tagCount;

    /**
     * 留言数量
     */
    private Long messageCount;

    /**
     * 评论数量
     */
    private Long commentsCount;

    /**
     * 配置字符串
     */
    private String config;

    private Date websiteCreateTime;

    private Date websiteUpdateTime;
}
