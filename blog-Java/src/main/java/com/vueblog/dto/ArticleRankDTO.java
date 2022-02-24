package com.vueblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRankDTO {

    /**
     * 标题
     */
    private String title;

    /**
     * 浏览量
     */
    private long viewsCount;


}

