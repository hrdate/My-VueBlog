package com.vueblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueblog.dto.*;
import com.vueblog.entity.Article;
import com.vueblog.mapper.ArticleMapper;
import com.vueblog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vueblog.service.CommentsService;
import com.vueblog.service.RedisService;
import com.vueblog.util.BeanCopyUtil;
import com.vueblog.util.DateUtils;
import com.vueblog.util.RedisUtils;
import com.vueblog.util.ShiroUtil;
import com.vueblog.vo.ArchiveVO;
import com.vueblog.vo.ArticleVO;
import com.vueblog.vo.PageVO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.*;

import static com.vueblog.common.constant.CommonConst.ARTICLE_SET;
import static com.vueblog.common.constant.RedisPrefixConst.*;
import static com.vueblog.common.constant.SystemConst.ARTICLE_NOT_DELECT;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService, InitializingBean {

    @Autowired
    private ArticleMapper articleMapper;


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisService redisService;

    @Lazy
    @Autowired
    private CommentsService commentsService;

    @Autowired
    private HttpSession session;

    @Override
    public List<ArticleHomeDTO> listArticles(Integer currentPage, Integer pageSize) {
        return articleMapper.listArticles(currentPage - 1, pageSize);
    }



    @Override
    public PageVO<ArticleVO> articleList(Integer currentPage, Integer pageSize) {
        IPage<Article> pageData;
        if(currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        Page page = new Page(currentPage, pageSize);
        pageData = articleMapper.selectPage(page, new LambdaQueryWrapper<Article>().orderByDesc(Article::getIsTop).orderByDesc(Article::getCreated));
        List<ArticleVO> articleVOList = BeanCopyUtil.copyList(pageData.getRecords(), ArticleVO.class);
        articleVOList.forEach(item ->{
            item.setArticleLike((redisUtils.hHasKey(ARTICLE_LIKE_COUNT, item.getId().toString()))
                    ? Long.valueOf((Integer)redisUtils.hget(ARTICLE_LIKE_COUNT, item.getId().toString())) : 0);
            item.setViewsCount( redisUtils.hHasKey(ARTICLE_VIEWS_COUNT , String.valueOf(item.getId()))
                    ? Long.valueOf((Integer)redisUtils.hget(ARTICLE_VIEWS_COUNT , String.valueOf(item.getId()))) : item.getViewsCount() );
        });
        return new PageVO<ArticleVO>(articleVOList, pageData.getTotal());
    }

    @Override
    public PageVO<ArticleAdminDTO> adminArticleList(Integer currentPage, Integer pageSize, Integer isDelete, Integer tagId) {
        if(currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        IPage<Article> pageData = articleMapper.selectPage(new Page(currentPage, pageSize) ,new LambdaQueryWrapper<Article>().eq(tagId != -1,Article::getTagId,tagId)
                .eq(isDelete != -1,Article::getIsDelete, isDelete).orderByDesc(Article::getIsTop).orderByAsc(Article::getCreated));
        List<ArticleAdminDTO> articleAdminDTOList = BeanCopyUtil.copyList(pageData.getRecords(), ArticleAdminDTO.class);
        articleAdminDTOList.forEach(item -> {
            item.setCommentNum(commentsService.commentsNumberByArticle(item.getId()));

            item.setArticleLike((redisUtils.hHasKey(ARTICLE_LIKE_COUNT, item.getId().toString()))
                    ? Long.valueOf((Integer)redisService.hGet(ARTICLE_LIKE_COUNT, item.getId().toString())) : 0);

            item.setViewsCount( redisUtils.hHasKey(ARTICLE_VIEWS_COUNT , String.valueOf(item.getId()))
                    ? Long.valueOf((Integer)redisUtils.hget(ARTICLE_VIEWS_COUNT , String.valueOf(item.getId()))) : item.getViewsCount() );
        });
        return new PageVO<ArticleAdminDTO>(articleAdminDTOList,pageData.getTotal());
    }

    @Override
    public PageVO<ArchiveVO> archivesList(Integer currentPage, Integer pageSize) {
        if(currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        IPage pageData = articleMapper.selectPage(new Page(currentPage, pageSize) ,new LambdaQueryWrapper<Article>()
                .select(Article::getId,Article::getTitle,Article::getCreated)
                .eq(Article::getIsDelete,ARTICLE_NOT_DELECT ).orderByDesc(Article::getIsTop).orderByAsc(Article::getCreated));

        List<ArchiveVO> archiveVOList = BeanCopyUtil.copyList(pageData.getRecords(), ArchiveVO.class);
        return new PageVO<ArchiveVO>(archiveVOList,pageData.getTotal());
    }

    @Override
    public List<Article> getArticleDeleteList(Integer isDelete) {
        return articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete,isDelete).orderByDesc(Article::getCreated));
    }

    @Override
    public ArticleVO getArticleById(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        Assert.notNull(article, "该博客已被删除");
        Long articleViewsCount = article.getViewsCount();
        if(redisUtils.hHasKey(ARTICLE_VIEWS_COUNT , articleId.toString())){
            articleViewsCount =  (Long) redisUtils.hget(ARTICLE_VIEWS_COUNT, articleId.toString());
        }
        articleViewsCount++;
        article.setViewsCount(articleViewsCount);
        // 更新缓存中文章浏览量+1
        redisUtils.hset(ARTICLE_VIEWS_COUNT, articleId.toString(), articleViewsCount);
        this.updateArticleViewsCount(articleId);

        // 转化成vo
        ArticleVO articleVO = BeanCopyUtil.copyObject(article,ArticleVO.class);
        articleVO.setArticleLike((redisUtils.hHasKey(ARTICLE_LIKE_COUNT, articleId.toString()))
                ? (Long) redisUtils.hget(ARTICLE_LIKE_COUNT, articleId.toString()) : 0);
        return  articleVO;
    }

    /**
     * 更新文章浏览量
     *
     * @param articleId 文章id
     */
    @Async
    public void updateArticleViewsCount(Long articleId) {
        // 通过session判断是否第一次访问，增加浏览量
        Set<Long> articleSet = (Set<Long>) Optional.ofNullable(session.getAttribute(ARTICLE_SET)).orElse(new HashSet<>());
        if (!articleSet.contains(articleId)) {
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            // 浏览量+1
//            redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
            redisUtils.hincr(ARTICLE_VIEWS_COUNT , String.valueOf(articleId),1.0);
        }
    }

    /**
     *
     * @return 获取最新的4个文章
     */
    @Override
    public List<ArticleLatestDTO> getArticleLatest() {
        List<Article> articleList = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getCreated, Article::getTitle, Article::getArticleCover)
                .orderByDesc(Article::getCreated).last("limit 4"));
         return BeanCopyUtil.copyList(articleList,ArticleLatestDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String editArticle(Article article) throws ParseException {
        Article temp;
        if(article.getId() != null){
            temp = articleMapper.selectById(article.getId());
//          //只能编辑自己的文章
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(),"没有权限编辑");
        }else {
            temp = new Article();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(DateUtils.getCurrentTime());
            temp.setViewsCount(0L);
            //更新网站信息
        }
        BeanUtil.copyProperties(article,temp,"viewsCount","created","articleLike");
        this.saveOrUpdate(temp);
        return "文章修改成功";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String delectArticleById(Long articleId) {
        Article temp = this.getById(articleId);
        Assert.notNull(temp,"文章已被删除");
        this.update(new LambdaUpdateWrapper<Article>()
                .eq(Article::getId,articleId).set(Article::getIsDelete,1));
        //更新网站信息
        return "文章ID:"+articleId+"删除成功";
    }

    @Override
    public List<ArticlePreviewDTO> getArticleByTagId(Long tagId) {
        List<Article> tagArticle = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getId,Article::getTitle,Article::getArticleCover,Article::getTagId,Article::getType,Article::getCreated)
                .eq(Article::getTagId,tagId));
        Assert.isTrue(!tagArticle.isEmpty(), "当前该标签没有文章");
        return BeanCopyUtil.copyList(tagArticle, ArticlePreviewDTO.class);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveArticleLike(Long articleId) {
        // 判断是否点赞
        String articleLikeKey = ARTICLE_USER_LIKE + ShiroUtil.getProfile().getId().longValue();
        if (redisService.sIsMember(articleLikeKey, articleId)) {
            // 点过赞则删除文章id
            redisService.sRemove(articleLikeKey, articleId);
            // 文章点赞量-1
            redisService.hDecr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        } else {
            // 未点赞则增加文章id
            redisService.sAdd(articleLikeKey, articleId);
            // 文章点赞量+1
            redisService.hIncr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String editArticleLikeByArticleId(Long articleId) {
        int like_or_not = 1;
        String articleLikeKey = ARTICLE_USER_LIKE + ShiroUtil.getProfile().getId().longValue();
        if(redisUtils.sHasKey(articleLikeKey ,articleId)) {
            //已点过赞
            redisUtils.setRemove(articleLikeKey ,articleId);
            // 文章点赞量-1
            redisUtils.hdecr(ARTICLE_LIKE_COUNT, articleId.toString(), 1.0);
            like_or_not = -1;
        }else {
            //未点过赞
            redisUtils.sSet(articleLikeKey,articleId);
            // 文章点赞量+1
            redisUtils.hincr(ARTICLE_LIKE_COUNT, articleId.toString(), 1.0);
        }
        return "文章:"+ articleId + (like_or_not == 1 ? "点赞":"取消点赞");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String editArticleIsTopByArticleId(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        int isTop = article.getIsTop() ^ 1;
        article.setIsTop(isTop);
        this.saveOrUpdate(article);
        return "文章:"+ articleId + (isTop == 1?"置顶":"取消置顶");
    }

    @Override
    public List<ArticleSearchDTO> searchArticleByKeywords(String keyWords) {
        return articleMapper.searchArticleByKeywords(keyWords);
    }

    @Override
    public List<ArticleRankDTO> getArticleRankList() {
        List<Article> articleList = this.list(new LambdaQueryWrapper<Article>()
                .select(Article::getTitle, Article::getViewsCount).orderByDesc(Article::getViewsCount).last("limit 5"));
        return BeanCopyUtil.copyList(articleList, ArticleRankDTO.class);
    }

    @Override
    @Scheduled(cron = " 0 0 0 * * ?", zone = "Asia/Shanghai")
    public void saveArticle() {
        Map<String, Object> map = redisService.hGetAll(ARTICLE_LIKE_COUNT);
        map.forEach((key, value) -> {
            int articleId = Integer.parseInt(key);
            Integer viewScount = (Integer) value;
            this.update(new LambdaUpdateWrapper<Article>()
                    .eq(Article::getId,articleId).set(Article::getViewsCount,viewScount));
        });
        log.debug("文章浏览量同步");
    }

    @Override
    public void afterPropertiesSet() {
        List<Article> articleList = this.list();
        for(Article article : articleList) {
            redisService.hSet(ARTICLE_LIKE_COUNT,article.getId().toString() , article.getViewsCount().toString());
        }
    }
}
