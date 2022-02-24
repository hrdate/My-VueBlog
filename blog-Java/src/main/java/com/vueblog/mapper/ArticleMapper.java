package com.vueblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vueblog.dto.ArticleHomeDTO;
import com.vueblog.dto.ArticleSearchDTO;
import com.vueblog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


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
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询首页文章
     *
     * @param currentPage 页码
     * @param pageSize    大小
     * @return 文章列表
     */
    List<ArticleHomeDTO> listArticles(@Param("currentPage") Integer currentPage, @Param("pageSize") Integer pageSize);

    List<ArticleSearchDTO> searchArticleByKeywords(@Param("keyWords")String keyWords);
}
