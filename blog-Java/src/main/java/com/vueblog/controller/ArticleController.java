package com.vueblog.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vueblog.common.lang.Result;
import com.vueblog.dto.ArticleLatestDTO;
import com.vueblog.dto.ArticleSearchDTO;
import com.vueblog.util.BeanCopyUtil;
import com.vueblog.vo.SearchVO;
import com.vueblog.entity.Article;
import com.vueblog.service.ArticleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
@Api(tags = "文章模块")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "前台首页分页查看文章列表")
    @RequestMapping(value = "/article/articles",method = RequestMethod.GET)
    public Result articleList(@RequestParam(defaultValue = "1") @RequestBody Integer currentPage,@RequestParam(defaultValue = "5") @RequestBody Integer pageSize){
//        return Result.succ("测试");
        return Result.succ(articleService.listArticles(currentPage,pageSize));
    }

    @ApiOperation(value = "后台分页查看文章列表")
    @GetMapping("/article/admin/articles")
    public Result adminArticleList(@RequestParam(defaultValue = "1",value = "currentPage") @RequestBody Integer currentPage,
                    @RequestParam(defaultValue = "5",value = "pageSize") @RequestBody Integer pageSize,
                   @RequestParam(defaultValue = "-1",value = "isDelete") @RequestBody Integer isDelete,
               @RequestParam(defaultValue = "-1",value = "tagId") @RequestBody Integer tagId){
        return Result.succ(articleService.adminArticleList(currentPage,pageSize,isDelete,tagId));
    }

    @ApiOperation(value = "查看文章归档")
    @GetMapping("/article/archives")
    public Result archivesList(@RequestParam(defaultValue = "1") @RequestBody Integer currentPage,@RequestParam(defaultValue = "5") @RequestBody Integer pageSize){
        return Result.succ(articleService.archivesList(currentPage,pageSize));
    }

    @ApiOperation(value = "获取回收站文章")
    @GetMapping("/article/isDelete")
    public Result getArticleDeleteList(@RequestParam(defaultValue = "1") @RequestBody Integer isDelete){
        return Result.succ(articleService.getArticleDeleteList(isDelete));
    }

    @ApiOperation(value = "根据id查看文章")
    @GetMapping("/article/{articleId}")
    public Result getArticleById(@PathVariable(name = "articleId")Long articleId){
        return Result.succ(articleService.getArticleById(articleId));
    }

    @ApiOperation(value = "获取最新文章推荐")
    @GetMapping("/article/latest")
    public Result getArticleLatest(){
        return Result.succ(articleService.getArticleLatest());
    }

    @ApiOperation(value = "新增或修改文章")
    @RequiresAuthentication
    @RequiresRoles("admin")
    @PostMapping("/article/edit")
    public Result editArticle(@Validated @RequestBody Article article) throws ParseException {
        return Result.succ(articleService.editArticle(article));
    }

    @ApiOperation(value = "根据id把文章移入回收站")
    @RequiresAuthentication
    @RequiresRoles("admin")
    @DeleteMapping("/article/del/{articleId}")
    public Result delectArticleById(@PathVariable(value = "articleId") Long articleId){
        return Result.succ(articleService.delectArticleById(articleId));
    }

    @ApiOperation(value = "根据标签id获取文章列表")
    @GetMapping("/article/tag/{tagId}")
    public Result getArticleByTagId(@PathVariable(name = "tagId") Long tagId){
        return Result.succ(articleService.getArticleByTagId(tagId));
    }

    @ApiOperation(value = "点赞文章或取消点赞")
    @RequiresAuthentication
    @GetMapping("/article/like/{articleId}")
    public Result editArticleLikeByArticleId(@PathVariable(value = "articleId")Long articleId){
        return Result.succ(articleService.editArticleLikeByArticleId(articleId));
    }

    @ApiOperation(value = "置顶文章或取消置顶")
    @PutMapping("/article/top/{articleId}")
    public Result editArticleIsTopByArticleId(@PathVariable(value = "articleId")Long articleId){
        return Result.succ(articleService.editArticleIsTopByArticleId(articleId));
    }

    /**
     * 搜索文章
     *
     * @param searchVo 条件
     * @return {@link Result<ArticleSearchDTO>} 文章列表
     */
    @ApiOperation(value = "搜索文章")
    @PostMapping("/article/search")
    public Result searchArticleByKeywords(@Validated @RequestBody SearchVO searchVo){
        System.out.println("keywords:"+ searchVo.getKeyWords());
        return Result.succ(articleService.searchArticleByKeywords(searchVo.getKeyWords()));
    }

    /**
     *
     * @return 获取文章浏览量最多的前五名
     */
    @ApiOperation(value = "获取文章流量排名表")
    @GetMapping("/article/admin/articleRankList")
    public Result getArticleRankList(){
        return Result.succ(articleService.getArticleRankList(),"成功获取文章浏览量排名");
    }


}
