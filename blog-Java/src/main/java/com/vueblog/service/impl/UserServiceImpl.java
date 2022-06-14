package com.vueblog.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import com.vueblog.common.lang.Result;
import com.vueblog.entity.User;
import com.vueblog.entity.UserRole;
import com.vueblog.kafka.MQSender;
import com.vueblog.mapper.UserMapper;
import com.vueblog.service.RoleService;
import com.vueblog.service.UserRoleService;
import com.vueblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vueblog.util.*;
import com.vueblog.vo.MailCodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import static com.vueblog.common.constant.RedisPrefixConst.CODE_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Lazy
    @Autowired
    RedisUtils redisUtils;

    @Lazy
    @Autowired
    SerializeUtils serializeUtils;

    @Lazy
    @Autowired
    JavaMailUtils javaMailUtils;

    @Lazy
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

    @Autowired
    MQSender mqSender;

    public boolean registerCheck(String email,String code){
        String oldCode =(String) redisUtils.get(CODE_KEY + email);
        Assert.notNull(oldCode,"验证码过期，请重新获取。");
        User temp = null;
        try{
            temp = userMapper.getByEmail(email);
        }catch (Exception e){
        }
        Assert.isNull(temp,"该邮箱已被注册");
        Assert.isTrue(oldCode.equals(code),"验证码错误，请重试！");
        return true;
    }

    @Override
    public boolean editPasswordChceck(String email, String code) {
        String oldCode =(String) redisUtils.get(CODE_KEY + email);
        Assert.notNull(oldCode,"验证码过期，请重新获取。");
        Assert.isTrue(oldCode.equals(code),"验证码错误，请重试！");
        return true;
    }

    @Override
    public String editUserInfo(User user) {
        User temp = userMapper.getByEmail(user.getEmail());
        Assert.notNull(temp,"该用户不存在");
        userMapper.updateUserInfo(user.getEmail(),user.getUserName(),user.getWebSite(),user.getIntroduce());
        return "用户:"+user.getEmail()+"修改成功";
    }

    @Override
    public String mailSend(String email) {
        mqSender.sendMessage("用户注册eamil-send", email);
        return "验证码已发送到您的邮箱，请稍后。";
    }

    @Override
    public String register(MailCodeVO mailCodeVO) throws ParseException {
        //注册
        this.registerCheck(mailCodeVO.getEmail(),mailCodeVO.getCode()); //验证注册邮箱+验证码有效性
        User temp = new User();
        temp.setEmail(mailCodeVO.getEmail());
        temp.setPassword(SecureUtil.md5(mailCodeVO.getPassword()));
        temp.setUserName("用户"+(int)(Math.random()*100000+10000));
        temp.setStatus(0);
        temp.setCreated(DateUtils.getCurrentTime());
        temp.setLastLogin(DateUtils.getCurrentTime());
        save(temp);
        // 在用户-角色表userRole中添加游客角色的记录
        UserRole userRole = new UserRole();
        userRole.setUserId(temp.getId());
        userRole.setRoleId(2L);
        userRoleService.save(userRole);
        return "邮箱："+temp.getEmail()+" 的新用户创建成功！";
    }

    @Override
    public String editPassword(MailCodeVO mailCodeVO) {
        this.editPasswordChceck(mailCodeVO.getEmail(), mailCodeVO.getCode());
        User temp = userMapper.getByEmail(mailCodeVO.getEmail());
        Assert.notNull(temp,"该用户不存在");
        temp.setPassword(SecureUtil.md5(mailCodeVO.getPassword()));
        this.saveOrUpdate(temp);
        return "用户:"+mailCodeVO.getEmail()+"修改成功";
    }

}
