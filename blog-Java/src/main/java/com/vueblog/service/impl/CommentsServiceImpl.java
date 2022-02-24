package com.vueblog.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueblog.dto.CommentsAdminDTO;
import com.vueblog.entity.Comments;
import com.vueblog.mapper.CommentsMapper;
import com.vueblog.service.ArticleService;
import com.vueblog.service.CommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vueblog.service.UserService;
import com.vueblog.util.*;
import com.vueblog.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

import static com.vueblog.common.constant.SystemConst.COMMENTS_IS_REVIEW;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {


    @Autowired
    CommentsMapper commentsMapper;

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;



    @Autowired
    RedisUtils redisUtils;

    @Autowired
    SerializeUtils serializeUtils;

    @Override
    public IPage<Comments> getCommentsListByArticleId(Long articleId, Integer currentPage, Integer pageSize) {
        if(currentPage == null || currentPage < 1) currentPage = 1;
        IPage<Comments> pageData;
        Page page = new Page(currentPage, pageSize);
        pageData = this.page(page,new LambdaQueryWrapper<Comments>()
                .eq(Comments::getArticleId,articleId).eq(Comments::getIsReview,COMMENTS_IS_REVIEW)
                .isNull(Comments::getParentId).orderByDesc(Comments::getCreated));
        List<Comments> records = pageData.getRecords();
        records.forEach(item -> item.setReplyCount(this.count(new QueryWrapper<Comments>().eq("parent_id",item.getId()))));
        pageData.setRecords(records);
        return pageData;
    }

    @Override
    public IPage<Comments> getFriendLinkCommentsList(Integer currentPage, Integer pageSize) {
        IPage<Comments> pageData;
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, pageSize);
        pageData = this.page(page,new LambdaQueryWrapper<Comments>()
                .eq(Comments::getIsReview,COMMENTS_IS_REVIEW).orderByDesc(Comments::getCreated));
        List<Comments> records = pageData.getRecords();
        records.forEach(item -> item.setReplyCount(this.count(new LambdaQueryWrapper<Comments>().eq(Comments::getParentId,item.getId()))));
        pageData.setRecords(records);
        return pageData;
    }

    @Override
    public PageVO<CommentsAdminDTO> adminGetCommentsList(Integer currentPage, Integer pageSize, Integer isReview) {
        if(currentPage == null || currentPage < 1) currentPage = 1;
        int count = this.count(new LambdaQueryWrapper<Comments>().eq(isReview != -1,Comments::getIsReview,isReview));
        return new PageVO<CommentsAdminDTO>(commentsMapper.adminGetCommentsList((currentPage - 1) * pageSize, pageSize,isReview),count);
    }

    @Override
    public String addComments(Comments comments) throws ParseException {
        comments.setIsReview(0);
        comments.setCreated(DateUtils.getCurrentTime());
        comments.setUserId(ShiroUtil.getProfile().getId().longValue());
        this.saveOrUpdate(comments);
        return "留言新增操作成功";
    }

    @Override
    public String commentsReview(Long commentsId) {
        //        UpdateWrapper updateWrapper = new UpdateWrapper();
//        updateWrapper.set("is_review",1);
//        updateWrapper.eq("id",id);
//        commentsService.update(updateWrapper);
        this.update(new LambdaUpdateWrapper<Comments>().eq(Comments::getIsReview,COMMENTS_IS_REVIEW)
                .eq(Comments::getId,commentsId));
        return "评论:"+commentsId+"通过审核";
    }

    @Override
    public IPage<?> getCommentsReplyList(Long commentId,Integer currentPage) {
        if(currentPage == null || currentPage < 1) currentPage = 1;
        IPage<Comments> pageData;
        Page page = new Page(currentPage, 5);
        pageData = this.page(page, new LambdaQueryWrapper<Comments>().eq(Comments::getParentId,commentId)
                .eq(Comments::getIsReview,COMMENTS_IS_REVIEW).orderByDesc(Comments::getCreated));
        return pageData;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteComment(Long commentId) {
        Comments temp = this.getById(commentId);
        Assert.notNull(temp,"评论:"+commentId+"已被删除");
        this.removeById(commentId);
        return "评论:"+commentId+"删除成功";
    }

    @Override
    public long commentsNumberByArticle(Long articleId) {
        return this.count(new LambdaQueryWrapper<Comments>().eq(Comments::getArticleId, articleId));
    }
}
