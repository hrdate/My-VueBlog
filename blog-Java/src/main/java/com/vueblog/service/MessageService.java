package com.vueblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vueblog.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vueblog.dto.MessageDTO;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-10
 */
public interface MessageService extends IService<Message> {

    List<MessageDTO> messagesList();

    IPage<Message> adminMessagesList(Integer currentPage,Integer pageSize,Integer isReview);

    String addMessage(Message message) throws ParseException;

    String messageReviewById(Long messageId);

    String messageDeleteById(Long messageId);
}
