package com.vueblog.service;

import com.vueblog.common.lang.Result;
import com.vueblog.dto.UpdateUserRoleDTO;
import com.vueblog.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ……hrdate……
 * @since 2022-02-23
 */
public interface UserRoleService extends IService<UserRole> {

    Result updateUserRole(UpdateUserRoleDTO request);
}
