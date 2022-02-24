package com.vueblog.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueblog.dto.MessageDTO;
import com.vueblog.entity.Message;
import com.vueblog.mapper.MessageMapper;
import com.vueblog.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vueblog.util.DateUtils;
import com.vueblog.util.IpUtils;
import com.vueblog.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

import static com.vueblog.common.constant.SystemConst.MESSAGE_IS_REVIEW;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-10
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {


    @Autowired
    MessageMapper messageMapper;

    @Resource
    private HttpServletRequest request;


    @Override
    public List<MessageDTO> messagesList() {
        List<MessageDTO> messageDTOList = messageMapper.userGetMssagesList();
        return messageDTOList;
    }

    @Override
    public IPage<Message> adminMessagesList(Integer currentPage, Integer pageSize, Integer isReview) {
        if(currentPage == null || currentPage < 1) currentPage = 1;
        IPage<Message>pageData;
        Page page = new Page(currentPage, pageSize);
        pageData = this.page(page,new LambdaQueryWrapper<Message>()
                .eq(isReview != -1 ,Message::getIsReview,MESSAGE_IS_REVIEW).orderByDesc(Message::getCreated));
        return pageData;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addMessage(Message message) throws ParseException {
        //处理用户ip和来源
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        message.setIpAddress(ipAddress);
        message.setIpSource(ipSource);
        //设置留言创建时间
        message.setCreated(DateUtils.getCurrentTime());
        message.setUserId(ShiroUtil.getProfile().getId().longValue());
        this.saveOrUpdate(message);
        return "添加留言成功";
    }

    @Override
    public String messageReviewById(Long id) {
        this.update(new LambdaUpdateWrapper<Message>()
                .set(Message::getIsReview, MESSAGE_IS_REVIEW).eq(Message::getId, id));
        return "留言:"+id+"通过审核";
    }

    @Override
    public String messageDeleteById(Long id) {
        Message temp = this.getById(id);
        Assert.notNull(temp,"留言:"+id+"已被删除");
        this.removeById(id);
        return "留言:"+id+"删除成功";
    }
}
