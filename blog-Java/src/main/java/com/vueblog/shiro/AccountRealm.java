package com.vueblog.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.vueblog.entity.User;
import com.vueblog.service.UserService;
import com.vueblog.util.JwtUtils;
import com.vueblog.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountRealm extends AuthorizingRealm  {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        //为了让realm支持jwt的凭证校验
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        权限校验 ,获取登录认证用户信息
        AccountProfile user  = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        List<String> roles = new ArrayList<>();
        if(user.getId().equals(1L)){
            roles.add("admin");
        }
        //构建资源校验
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roles);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //实现登录认证校验的逻辑
        JwtToken jwtToken = (JwtToken) token;
        //把AuthenticationToken转化成JwtToken，再从JwtToken中获取用户信息
        //Claims会把用户的id放在字符串SUBJECT中
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        User user = userService.getById(Long.valueOf(userId));
        //登录失败，抛出的异常在loginFailure中，故可以在JwtFilter拦截器中重写loginFailure
        if (user == null) {
            throw new UnknownAccountException("账户不存在");
        }

        if (user.getStatus() == -1) {
            throw new LockedAccountException("账户已被锁定");
        }
        //封装一个不含敏感信息的身份信息类，传入shiro内处理
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);

        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    }
}
