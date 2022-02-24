package com.vueblog.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vueblog.common.lang.Result;
import com.vueblog.util.RedisUtils;
import com.vueblog.vo.LoginVO;
import com.vueblog.entity.User;
import com.vueblog.service.UserService;
import com.vueblog.util.JwtUtils;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static com.vueblog.common.constant.RedisPrefixConst.ARTICLE_USER_LIKE;

@Api(tags = "用户登录模块")
@RestController
public class AccountController {

    @Lazy
    @Autowired
    UserService userService;

    @Lazy
    @Autowired
    JwtUtils jwtUtils;

    @Lazy
    @Autowired
    RedisUtils redisUtils;

    @PostMapping("/user/login")
    public Result userLogin(@Validated @RequestBody LoginVO loginVo, HttpServletResponse response) {

        User user = userService.getOne(new QueryWrapper<User>().eq("userName", loginVo.getUserName()));
        if(null == user){
            user = userService.getOne(new QueryWrapper<User>().eq("email", loginVo.getEmail()));
        }
        Assert.notNull(user, "用户不存在");

        if(!user.getPassword().equals(SecureUtil.md5(loginVo.getPassword()))){
            return Result.fail("密码不正确");
        }


        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
        redisUtils.sSet(ARTICLE_USER_LIKE + user.getId(),new Integer(0));
        Set<Object> articleLikeSet = (Set<Object>) redisUtils.sGet(ARTICLE_USER_LIKE + user.getId());

        return Result.succ(MapUtil.builder()
                .put("userId", user.getId())
                .put("userName", user.getUserName())
                .put("avatar", user.getAvatar())
                .put("introduce",user.getIntroduce())
                .put("email", user.getEmail())
                .put("webSite",user.getWebSite())
                .put("articleLikeSet",articleLikeSet)
                .map()
        );
    }



    @RequiresAuthentication
    @GetMapping("/user/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ("用户退出成功");
    }

}
