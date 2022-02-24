package com.vueblog.controller;



import com.vueblog.annotation.OptLog;
import com.vueblog.common.lang.Result;
import com.vueblog.entity.Bloginfo;
import com.vueblog.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static com.vueblog.common.constant.OptTypeConst.REMOVE;
import static com.vueblog.common.constant.OptTypeConst.UPDATE;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-22
 */
@Api(tags = "博客信息模块")
@RestController
public class BloginfoController {

    @Autowired
    BloginfoService bloginfoService;


    @ApiOperation(value = "查看博客信息")
    @GetMapping("/bloginfo")
    public Result getBloginfo(){
        return Result.succ(bloginfoService.getBloginfo());
    }
    @GetMapping("/admin/bloginfo")
    public Result adminGetBloginfo(){
        return Result.succ(bloginfoService.adminGetBloginfo());
    }

    @ApiOperation(value = "后台查看博客配置信息")
    @GetMapping("/admin/blogInfo/config")
    public Result adminGetBlogInfoConfig(){
        return Result.succ(bloginfoService.adminGetBlogInfoConfig());
    }

    @OptLog(optType =  UPDATE)
    @ApiOperation(value = "后台修改博客信息")
    @RequiresAuthentication
    @RequiresRoles("admin")
    @PostMapping(value = "/admin/blogInfo/config/update")
    public Result adminUpdateWebsiteConfig(@RequestBody Bloginfo newBloginfo) throws ParseException {
        return Result.succ(bloginfoService.adminUpdateWebsiteConfig(newBloginfo));
    }


    /**
     * 上传访客信息
     *
     * @return {@link Result}
     */
    @PostMapping("/report")
    public Result report() {
        bloginfoService.report();
        return Result.succ(null);
    }
}
