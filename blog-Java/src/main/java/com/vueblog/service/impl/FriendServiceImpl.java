package com.vueblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueblog.entity.Friend;
import com.vueblog.mapper.FriendMapper;
import com.vueblog.service.FriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vueblog.util.DateUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    @Override
    public IPage<Friend> adminGetFrindLinkList(Integer currentPage, Integer pageSize) {
        if(currentPage == null || currentPage < 1) currentPage = 1;
        IPage<Friend> pageData;
        Page page = new Page(currentPage, pageSize);
        pageData = this.page(page, new LambdaQueryWrapper<Friend>().orderByDesc(Friend::getCreated));
        return pageData;
    }

    @Override
    public String adminEditFriendLink(Friend friend) throws ParseException {
        Friend temp;
        if(friend.getLinkId() != null){
            temp = this.getById(friend.getLinkId());
        }else {
            temp = new Friend();
            temp.setCreated(DateUtils.getCurrentTime());
        }
        BeanUtil.copyProperties(friend,temp,"created");
        this.saveOrUpdate(temp);
        return "友链操作成功";
    }

    @Override
    public String adminDelFrindLink(Integer linkId) {
        Friend temp = this.getById(linkId);
        Assert.notNull(temp,"友链已被删除");
        this.removeById(linkId);
        return "友链删除成功";
    }
}
