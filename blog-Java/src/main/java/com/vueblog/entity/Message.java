package com.vueblog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("userId")
    private Long userId;

    @TableField("userName")
    private String userName;

    @NotBlank(message = "留言内容不能为空")
    @TableField("content")
    private String content;
    /**
     * 留言创建时间
     */
//    @TableField("created")
    @TableField(fill = FieldFill.INSERT)
    private Date created;


    @TableField("avatar")
    private String avatar;
    /**
     * 弹幕速度
     */
    @TableField("time")
    private int time;
    /**
     * 是否已审核
     */
    @TableField("is_review")
    private int isReview;

    /**
     * 用户ip
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 用户ip来源
     */
    @TableField("ip_source")
    private String ipSource;
}
