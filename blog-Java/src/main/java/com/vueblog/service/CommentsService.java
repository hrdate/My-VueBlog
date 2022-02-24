package com.vueblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vueblog.dto.CommentsAdminDTO;
import com.vueblog.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vueblog.vo.PageVO;

import java.text.ParseException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
public interface CommentsService extends IService<Comments> {

    IPage<Comments> getCommentsListByArticleId(Long articleId,Integer currentPage,Integer pageSize);

    IPage<Comments> getFriendLinkCommentsList(Integer currentPage,Integer pageSize);

    PageVO<CommentsAdminDTO> adminGetCommentsList(Integer currentPage, Integer pageSize, Integer isReview);

    String addComments(Comments comments) throws ParseException;

    String commentsReview(Long commentsId);

    IPage<?> getCommentsReplyList(Long commentId,Integer currentPage);

    String deleteComment(Long commentId);

    long commentsNumberByArticle(Long articleId);
}
