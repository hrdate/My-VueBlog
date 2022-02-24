package com.vueblog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Transactional;

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
@TableName("m_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "userId")
    private Long userId;

    /**
     * 用户名
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 文章标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;


    /**
     * 文章内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "created")
//    @TableField(fill = FieldFill.INSERT)
    private Date created;

    /**
     * 文章状态
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 文章标签id
     */
    @TableField(value = "tagId")
    private long tagId;

    /**
     * 文章标签名
     */
    @TableField(exist = false)
    private String TagName;

    /**
     * 文章标签id
     */
    @TableField(value = "view_count")
    private long viewsCount;
    /**
     * 文章是否置顶
     */
    @TableField(value = "is_top")
    private int isTop;
    /**
     * 文章类型，原创、转载、翻译
     */
    @NotBlank(message ="文章类型不能为空")
    @TableField(value = "article_type")
    private String type;

    /**
     * 文章封面链接
     */
    @TableField(value = "article_cover")
    private String articleCover;


}
