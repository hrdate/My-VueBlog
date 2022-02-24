package com.vueblog.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 个人介绍
     */
    private String introduce;

    /**
     * 个人网站网址
     */
    private String webSite;
}
