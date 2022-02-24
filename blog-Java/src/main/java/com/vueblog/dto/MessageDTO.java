package com.vueblog.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 留言
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private Long id;

    private String userName;

    private String content;

    private String avatar;

    private int time;
}
