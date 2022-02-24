package com.vueblog.service;

import com.vueblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vueblog.vo.MailCodeVO;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
public interface UserService extends IService<User> {

    boolean registerCheck(String email,String code);

    boolean editPasswordChceck(String email,String code);

    String editUserInfo(User user);

    String mailSend(String email);

    String register (MailCodeVO mailCodeVO) throws ParseException;

    String editPassword(MailCodeVO mailCodeVO);
}
