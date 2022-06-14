package com.vueblog.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserRoleDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long roleId;
}
