package com.vueblog.controller;



import com.vueblog.annotation.OptLog;
import com.vueblog.common.lang.Result;

import com.vueblog.entity.Message;
import com.vueblog.service.MessageService;
import com.vueblog.util.BeanCopyUtil;

import com.vueblog.dto.MessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static com.vueblog.common.constant.OptTypeConst.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-10
 */
@Api(tags = "留言模块")
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @ApiOperation(value = "前台查看留言列表")
    @GetMapping("/messages")
    public Result messagesList(){
        return Result.succ(BeanCopyUtil.copyList(messageService.messagesList(), MessageDTO.class));
    }

    @ApiOperation(value = "后台分页查看留言列表")
    @GetMapping("/admin")
    public Result adminMessagesList(@RequestParam(defaultValue = "1") @RequestBody Integer currentPage,
            @RequestParam(defaultValue = "5") @RequestBody Integer pageSize,
            @RequestParam(defaultValue = "-1",value = "isReview") @RequestBody Integer isReview){
        return Result.succ(messageService.adminMessagesList(currentPage,pageSize,isReview));
    }

    @OptLog(optType =  SAVE)
    @ApiOperation(value = "添加留言")
    @RequiresAuthentication
    @RequiresRoles("admin")
    @PostMapping("/edit")
    public Result addMessage(@Validated @RequestBody Message message) throws ParseException {
        return Result.succ(messageService.addMessage(message));
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "审核留言")
    @RequiresAuthentication
    @GetMapping("/review/{messageId}")
    public Result messageReviewById(@PathVariable(value = "messageId")Long id){
        return Result.succ(messageService.messageReviewById(id));
    }

    @OptLog(optType =  REMOVE)
    @ApiOperation(value = "删除留言")
    @RequiresAuthentication
    @RequiresRoles("admin")
    @DeleteMapping("/del/{id}")
    public Result messageDeleteById(@PathVariable(value = "id")Long id){
        return Result.succ(messageService.messageDeleteById(id));
    }

}
