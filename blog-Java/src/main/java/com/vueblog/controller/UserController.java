package com.vueblog.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import com.vueblog.annotation.OptLog;
import com.vueblog.common.lang.Result;
import com.vueblog.entity.User;
import com.vueblog.mapper.UserMapper;
import com.vueblog.service.UserService;
import com.vueblog.util.*;
import com.vueblog.vo.MailCodeVO;
import com.vueblog.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

import static com.vueblog.common.constant.RedisPrefixConst.CODE_KEY;
import static com.vueblog.common.constant.OptTypeConst.*;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
@Api(tags = "用户账号模块")
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    SerializeUtils serializeUtils;

    @Autowired
    JavaMailUtils javaMailUtils;

    @Autowired
    JwtUtils jwtUtils;

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/information")
    public Result information(@RequestParam(value = "id",required = false,defaultValue = "1")Long userId) {
        return Result.succ(BeanCopyUtil.copyObject(userService.getById(userId), UserVO.class));
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户信息")
    @RequiresAuthentication
    @PostMapping("/editInfo")
    public  Result editUserInfo(@Validated @RequestBody User user){
        return Result.succ(userService.editUserInfo(user));
    }

    @ApiOperation(value = "发送邮箱验证码")
    @GetMapping("/register/emailSend")
    public Result mailSend(@RequestParam(value = "email")String email){

        return Result.succ(userService.mailSend(email));
    }

    @OptLog(optType = SAVE)
    @ApiOperation(value = "注册验证")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/register/check")
    public Result register(@Validated @RequestBody MailCodeVO mailCodeVO) throws ParseException {
        return Result.succ(userService.register(mailCodeVO),"验证码正确");
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改密码验证")
    @PutMapping("/editPassword")
    public  Result editPassword(@Validated @RequestBody MailCodeVO mailCodeVO){
        return Result.succ(userService.editPassword(mailCodeVO),"验证码正确");
    }

}
