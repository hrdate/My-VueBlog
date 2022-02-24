package com.vueblog.controller;

import com.vueblog.common.lang.Result;
import com.vueblog.entity.User;
import com.vueblog.mapper.UserMapper;
import com.vueblog.service.TestService;
import com.vueblog.service.UserService;
import com.vueblog.util.JavaMailUtils;
import com.vueblog.util.JwtUtils;
import com.vueblog.util.RedisUtils;
import com.vueblog.util.SerializeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试模块")
//@Controller
@RestController
public class TestController {
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

    @Autowired
    TestService testService;

    @ApiOperation(value = "测试1")
    @RequestMapping("/test1")
//    @ResponseBody
    public String test1(){
        return "测试1成功";
    }

    @GetMapping("/test/test1")
//    @ResponseBody
    public Result test2(){
        User user = new User().setUserName("test").setId(-1L);
        return Result.succ(user);
    }

    @GetMapping("/test/test2")
    public String test3(){
        return "QuAn_1.jpg";
    }

    @GetMapping("/test2")
    public String test4(){
        return "QuAn_1.jpg";
    }
}
