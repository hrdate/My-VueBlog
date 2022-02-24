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
 * @since 2021-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "userName")
    private String userName;

    /**
     * 用户头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 用户邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @TableField(value = "email")
    private String email;

    /**
     * 用户密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 用户创建时间
     */
//    @TableField(value = "created")
    @TableField(fill = FieldFill.INSERT)
    private Date created;

    /**
     * 用户最近登录时间
     */
    @TableField(value = "lastLogin")
    private Date lastLogin;


    @TableField(value = "introduce")
    private String introduce;

    @TableField(value = "webSite")
    private String webSite;
}
