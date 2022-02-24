package com.vueblog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
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
 * @since 2021-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * tag的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * tag的名字
     */
    @TableField(value = "tagName")
    private String tagName;

    /**
     * tag的创建时间
     */
//    @TableField(value = "created")
    @TableField(fill = FieldFill.INSERT)
    private Date created;


}
