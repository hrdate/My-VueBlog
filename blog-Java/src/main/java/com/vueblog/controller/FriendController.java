package com.vueblog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueblog.annotation.OptLog;
import com.vueblog.common.lang.Result;
import com.vueblog.entity.Friend;
import com.vueblog.service.FriendService;
import com.vueblog.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static com.vueblog.common.constant.OptTypeConst.REMOVE;
import static com.vueblog.common.constant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
@Api(tags = "友链模块")
@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    FriendService friendService;

    @ApiOperation(value = "前台查询友链")
    @GetMapping("/friends")
    public Result list(){
        return Result.succ(friendService.list());
    }

    @ApiOperation(value = "后台查询友链")
    @GetMapping("/admin")
    public Result adminGetFrindLinkList(@RequestParam(defaultValue = "1") @RequestBody Integer currentPage,@RequestParam(defaultValue = "5") @RequestBody Integer pageSize){
        return Result.succ(friendService.adminGetFrindLinkList(currentPage,pageSize),"评论信息");
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增或修改友链")
    @RequiresAuthentication
    @RequiresRoles("admin")
    @PostMapping("/edit")
    public  Result adminEditFriendLink(@Validated @RequestBody Friend friend) throws ParseException {
        return Result.succ(friendService.adminEditFriendLink(friend));
    }

    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除友链")
    @RequiresAuthentication
    @RequiresRoles("admin")
    @DeleteMapping("/del/{linkId}")
    public Result adminDelFrindLink(@PathVariable(value = "linkId") Integer linkId) {
        return Result.succ(friendService.adminDelFrindLink(linkId));
    }

}
