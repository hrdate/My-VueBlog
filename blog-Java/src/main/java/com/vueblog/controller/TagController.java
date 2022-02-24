package com.vueblog.controller;


import cn.hutool.core.lang.Assert;
import com.vueblog.annotation.OptLog;
import com.vueblog.common.lang.Result;
import com.vueblog.entity.Tag;
import com.vueblog.mapper.TagMapper;
import com.vueblog.service.TagService;
import com.vueblog.util.DateUtils;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static com.vueblog.common.constant.OptTypeConst.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-08
 */
@Api(tags = "标签模块")
@RestController
public class TagController {

    @Autowired
    TagService tagService;



    @GetMapping("/tag/tags")
    public Result tags(){
        return Result.succ(tagService.list(),"查询全部标签信息");
    }


    @OptLog(optType =  REMOVE)
    @RequiresAuthentication
    @RequiresRoles("admin")
    @DeleteMapping("/tag/del")
    public Result tagDel(@RequestParam(name = "tagId",required = true)Integer tagId){
        return Result.succ(tagService.removeById(tagId),"标签删除操作成功");
    }

    @OptLog(optType =  SAVE_OR_UPDATE)
    @RequiresAuthentication
    @RequiresRoles("admin")
    @PostMapping("/tag/edit")
    public Result tagEdit(@Validated @RequestBody Tag tag) throws ParseException {
        if(tag.getId() == null){
            tag.setCreated(DateUtils.getCurrentTime());
        }
        return Result.succ(tagService.saveOrUpdate(tag),"标签 \""+tag.getTagName()+ "\" 操作成功");
    }

}
