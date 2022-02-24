package com.vueblog.service;

import com.vueblog.dto.BloginfoDTO;
import com.vueblog.entity.Bloginfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-22
 */
public interface BloginfoService extends IService<Bloginfo> {

    Bloginfo getBloginfo();

    BloginfoDTO adminGetBloginfo();

    String adminGetBlogInfoConfig();

    String adminUpdateWebsiteConfig(Bloginfo bloginfo) throws ParseException;

    Boolean articleCountChange(Integer changSize);

    Boolean userCountChange(Integer changSize);

    Boolean tagCountChange(Integer changSize);

    Boolean messageCountChange(Integer changSize);

    Boolean commentsCountChange(Integer changSize);

    void report();
}
