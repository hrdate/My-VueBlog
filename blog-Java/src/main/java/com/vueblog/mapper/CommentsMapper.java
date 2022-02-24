package com.vueblog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.vueblog.dto.CommentsAdminDTO;
import com.vueblog.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
@Mapper
@Repository
public interface CommentsMapper extends BaseMapper<Comments> {


//    IPage<Comments> userSelectPage(IPage<Comments> page,@Param("articleId")Integer articleId,@Param(Constants.WRAPPER) QueryWrapper<Comments> queryWrapper);

    List<CommentsAdminDTO> adminGetCommentsList(@Param("currentPage") Integer currentPage, @Param("pageSize") Integer pageSize,@Param("isReview")Integer isReview);
}
