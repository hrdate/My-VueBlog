package com.vueblog.controller;



import com.vueblog.annotation.OptLog;
import com.vueblog.common.lang.Result;
import com.vueblog.entity.Comments;

import com.vueblog.service.CommentsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
 * @since 2021-12-06
 */
@Api(tags = "评论模块")
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    @ApiOperation(value = "查询文章评论")
    @GetMapping("/article/{articleId}")
    public Result getCommentsListByArticleId(@PathVariable(value = "articleId") Long articleId,
                    @RequestParam(defaultValue = "1")  Integer currentPage,
                               @RequestParam(defaultValue = "5")  Integer pageSize){
        return Result.succ(commentsService.getCommentsListByArticleId(articleId,currentPage,pageSize),"文章评论信息");
    }

    @ApiOperation(value = "查询友链评论")
    @GetMapping("/friendLink")
    public Result getFriendLinkCommentsList(@RequestParam(defaultValue = "1") @RequestBody Integer currentPage,
                       @RequestParam(defaultValue = "5") @RequestBody Integer pageSize){
        return Result.succ(commentsService.getFriendLinkCommentsList(currentPage,pageSize),"友链评论信息");
    }

    @ApiOperation(value = "后台查询评论")
    @GetMapping("/admin")
    public Result adminGetCommentsList(@RequestParam(defaultValue = "1",value = "currentPage") @RequestBody Integer currentPage,
                      @RequestParam(defaultValue = "5",value = "pageSize") @RequestBody Integer pageSize,
                      @RequestParam(defaultValue = "-1",value = "isReview") @RequestBody Integer isReview) {
        return Result.succ(commentsService.adminGetCommentsList(currentPage,pageSize,isReview),"评论信息");
    }

    @OptLog(optType =  SAVE)
    @ApiOperation(value = "新增评论")
    @RequiresAuthentication
    @PostMapping("/edit")
    public Result addComments(@Validated @RequestBody Comments comments) throws ParseException {
        return Result.succ(commentsService.addComments(comments));
    }

    @OptLog(optType =  UPDATE)
    @ApiOperation(value = "审核评论")
    @RequiresAuthentication
    @GetMapping("/review/{commentId}")
    public Result commentsReview(@PathVariable(value = "commentId")Long id){
        return Result.succ(commentsService.commentsReview(id));
    }

    @ApiOperation(value = "查询评论回复")
    @GetMapping("/reply/{commentId}")
    public Result getCommentsReplyList(@PathVariable(value = "commentId") Long id,
                   @RequestParam(defaultValue = "1",value = "currentPage") @RequestBody Integer currentPage){
        return Result.succ(commentsService.getCommentsReplyList(id,currentPage));
    }

    @OptLog(optType =  REMOVE)
    @ApiOperation(value = "删除评论")
    @RequiresAuthentication
    @DeleteMapping("/del/{commentId}")
    public Result deleteComment(@PathVariable(value = "commentId") Long id){
        return Result.succ(commentsService.deleteComment(id));
    }

}
