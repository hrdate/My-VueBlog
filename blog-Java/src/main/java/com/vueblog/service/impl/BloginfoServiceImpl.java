package com.vueblog.service.impl;

import com.vueblog.dto.BloginfoDTO;
import com.vueblog.entity.Bloginfo;
import com.vueblog.mapper.ArticleMapper;
import com.vueblog.mapper.BloginfoMapper;
import com.vueblog.mapper.UserMapper;
import com.vueblog.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vueblog.util.BeanCopyUtil;
import com.vueblog.util.DateUtils;
import com.vueblog.util.IpUtils;
import com.vueblog.util.RedisUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

import static com.vueblog.common.constant.CommonConst.*;
import static com.vueblog.common.constant.RedisPrefixConst.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-22
 */
@Service
public class BloginfoServiceImpl extends ServiceImpl<BloginfoMapper, Bloginfo> implements BloginfoService {

    @Autowired
    RedisUtils redisUtils;

    @Resource
    private HttpServletRequest request;

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private MessageService messageService;


    @Override
    public Bloginfo getBloginfo() {
        Bloginfo bloginfo = this.baseMapper.selectById(1);
        Long viewsCount = Long.valueOf((Integer) redisUtils.get(BLOG_VIEWS_COUNT));
        if(viewsCount == null){
            viewsCount = bloginfo.getViewsCount();
            redisUtils.set(BLOG_VIEWS_COUNT,viewsCount + 1);
        }
        redisUtils.increment(BLOG_VIEWS_COUNT,1);
        return bloginfo;
    }

    @Override
    public BloginfoDTO adminGetBloginfo() {
        Bloginfo bloginfo = this.baseMapper.selectById(1);
        Long viewsCount = Long.valueOf((Integer) redisUtils.get(BLOG_VIEWS_COUNT));
        if(viewsCount == null){
            viewsCount = bloginfo.getViewsCount();
            redisUtils.set(BLOG_VIEWS_COUNT,viewsCount);
        }
        bloginfo.setViewsCount(viewsCount);
        BloginfoDTO bloginfoDTO = BeanCopyUtil.copyObject(bloginfo,BloginfoDTO.class);
        bloginfoDTO.setArticleCount((long) articleService.count());
        bloginfoDTO.setCommentsCount((long) commentsService.count());
        bloginfoDTO.setMessageCount((long) messageService.count());
        bloginfoDTO.setUserCount((long) userService.count());
        return bloginfoDTO;
    }

    @Override
    public String adminGetBlogInfoConfig() {
        return this.getById(1).getConfig();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String adminUpdateWebsiteConfig(Bloginfo newBloginfo) throws ParseException {
        Bloginfo bloginfo = this.getById(1);
        bloginfo.setViewsCount((Long) redisUtils.get(BLOG_VIEWS_COUNT));
        bloginfo.setConfig(newBloginfo.getConfig());
        bloginfo.setWebsiteUpdateTime(DateUtils.getCurrentTime());
        this.saveOrUpdate(bloginfo);
        redisUtils.del(BLOG_VIEWS_COUNT);
        return "修改网站信息成功";
    }

    @Override
    public Boolean articleCountChange(Integer changSize) {
        Bloginfo bloginfo = this.getById(1);
        bloginfo.setArticleCount(bloginfo.getArticleCount() + changSize);
        this.saveOrUpdate(bloginfo);
        return true;
    }

    @Override
    public Boolean userCountChange(Integer changSize) {
        Bloginfo bloginfo = this.getById(1);
        bloginfo.setUserCount(bloginfo.getUserCount() + changSize);
        this.saveOrUpdate(bloginfo);
        return true;
    }

    @Override
    public Boolean tagCountChange(Integer changSize) {
        Bloginfo bloginfo = this.getById(1);
        bloginfo.setTagCount(bloginfo.getTagCount() + changSize);
        this.saveOrUpdate(bloginfo);
        return true;
    }

    @Override
    public Boolean messageCountChange(Integer changSize) {
        Bloginfo bloginfo = this.getById(1);
        bloginfo.setMessageCount(bloginfo.getMessageCount() + changSize);
        this.saveOrUpdate(bloginfo);
        return true;
    }

    @Override
    public Boolean commentsCountChange(Integer changSize) {
        Bloginfo bloginfo = this.getById(1);
        bloginfo.setCommentsCount(bloginfo.getCommentsCount() + changSize);
        this.saveOrUpdate(bloginfo);
        return true;
    }

    @Override
    public void report() {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!redisUtils.sHasKey(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtils.getIpSource(ipAddress);
            if (ipSource != null) {
                ipSource = ipSource.substring(0, 2)
                        .replaceAll(PROVINCE, "")
                        .replaceAll(CITY, "");
                redisUtils.hincr(VISITOR_AREA, ipSource, 1.0);
            } else {
                redisUtils.hincr(VISITOR_AREA, UNKNOWN, 1.0);
            }
            // 访问量+1
            redisUtils.increment(BLOG_VIEWS_COUNT, 1);
            // 保存唯一标识
            redisUtils.sSet(UNIQUE_VISITOR, md5);
        }
    }
}
