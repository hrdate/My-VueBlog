package com.vueblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vueblog.entity.Friend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
public interface FriendService extends IService<Friend> {

    IPage<Friend> adminGetFrindLinkList(Integer currentPage, Integer pageSize);

    String adminEditFriendLink(Friend friend) throws ParseException;

    String adminDelFrindLink(Integer linkId);

}
