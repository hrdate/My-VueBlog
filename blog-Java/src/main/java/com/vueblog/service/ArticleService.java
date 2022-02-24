package com.vueblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vueblog.dto.*;
import com.vueblog.entity.Article;
import com.vueblog.vo.ArchiveVO;
import com.vueblog.vo.ArticleVO;
import com.vueblog.vo.PageVO;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
public interface ArticleService extends IService<Article> {

    /**
     * 查询首页文章
     *
     * @return 文章列表
     */
    List<ArticleHomeDTO> listArticles(Integer currentPage, Integer pageSize);

    PageVO<ArticleVO> articleList(Integer currentPage, Integer pageSize);

    /**
     *
     * @param currentPage
     * @param pageSize
     * @param isDelete
     * @param tagId
     * @return 管理员获取文章列表
     */
    PageVO<ArticleAdminDTO> adminArticleList(Integer currentPage, Integer pageSize, Integer isDelete, Integer tagId);

    /**
     *
     * @param currentPage
     * @param pageSize
     * @return 获取文章的归档
     */
    PageVO<ArchiveVO> archivesList(Integer currentPage, Integer pageSize);

    /**
     *
     * @param isDelete
     * @return 获取回收站内的文章
     */
    List<Article> getArticleDeleteList(Integer isDelete);

    /**
     *
     * @param id
     * @return 根据id获取具体文章内容
     */
    ArticleVO getArticleById(Long id);

    /**
     *
     * @return 获取最新的4个文章
     */
    List<ArticleLatestDTO> getArticleLatest();

    /**
     *
     * @param article
     * @return 新增 或 修改 文章
     * @throws ParseException
     */
    String editArticle(Article article) throws ParseException;

    /**
     *
     * @param articleId
     * @return 根据文章id删除文章
     */
    String delectArticleById(Long articleId);

    /**
     *
     * @param tagId
     * @return 根据标签id获取文章列表
     */
    List<ArticlePreviewDTO> getArticleByTagId(Long tagId);
    /**
     * 点赞文章
     * 借鉴-风神版本
     * @param articleId 文章id
     */
    void saveArticleLike(Long articleId);

    /**
     *
     * @param articleId
     * @return 根据文章id，修改当前用户，是否点赞文章的状态
     */
    String editArticleLikeByArticleId(Long articleId);

    /**
     *
     * @param articleId
     * @return 修改文章置顶状态
     */
    String editArticleIsTopByArticleId(Long articleId);

    List<ArticleSearchDTO> searchArticleByKeywords(String keyWords);

    /**
     *
     * @return 获取文章浏览量最多的前五名
     */
    List<ArticleRankDTO>getArticleRankList();

    /**
     * 同步文章浏览量缓存到数据库
     */
    void saveArticle();
}
