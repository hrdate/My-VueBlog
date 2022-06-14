package com.vueblog.controller;


import com.vueblog.common.lang.Result;
import com.vueblog.dto.UpdateUserRoleDTO;
import com.vueblog.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ……hrdate……
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;
    @PostMapping("update")
    public Result updateUserRole(@RequestBody @Valid UpdateUserRoleDTO request) {
        return userRoleService.updateUserRole(request);

    }
}
