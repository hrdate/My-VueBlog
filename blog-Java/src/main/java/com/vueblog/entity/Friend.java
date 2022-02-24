package com.vueblog.entity;

import com.baomidou.mybatisplus.annotation.*;

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
 * @since 2021-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_friend")
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "linkId", type = IdType.AUTO)
    private Integer linkId;

    @NotBlank(message = "友链名称不能为空")
    @TableField("linkName")
    private String linkName;

    @NotBlank(message = "友链url不能为空")
    @TableField("linkUrl")
    private String linkUrl;

    @TableField("linkInfo")
    private String linkInfo;
    /**
     * 友链头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     *友链创建时间
     */
//    @TableField("created")
    @TableField(fill = FieldFill.INSERT)
    private Date created;
}
