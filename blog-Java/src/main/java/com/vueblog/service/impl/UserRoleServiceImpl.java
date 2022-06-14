package com.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.vueblog.common.lang.Result;
import com.vueblog.dto.UpdateUserRoleDTO;
import com.vueblog.entity.UserRole;
import com.vueblog.mapper.UserRoleMapper;
import com.vueblog.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ……hrdate……
 * @since 2022-02-23
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public Result updateUserRole(UpdateUserRoleDTO request) {
        LambdaUpdateWrapper<UserRole> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(UserRole::getUserId, request.getUserId())
                .eq(UserRole::getRoleId, request.getRoleId());
        boolean update = update(updateWrapper);
        return update ? Result.succ(null, "修改用户角色成功")
                : Result.fail(null, "修改用户角色失败");
    }
}
