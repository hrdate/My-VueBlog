package com.vueblog.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;

    private String email;

    private String password;
}
