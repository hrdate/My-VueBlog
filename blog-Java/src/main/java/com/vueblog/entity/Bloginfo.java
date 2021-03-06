package com.vueblog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_bloginfo")
public class Bloginfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 网站浏览量
     */
    @TableField("views_count")
    private Long viewsCount;



    @TableField("config")
    private String config;

//    @TableField(fill = FieldFill.INSERT)
    @TableField("create_time")
    private Date websiteCreateTime;

    @TableField("update_time")
    private Date websiteUpdateTime;

}
