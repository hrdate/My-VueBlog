package com.vueblog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Component
public class JavaMailUtils {
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")  //读取配置信息中的邮箱账号
    private String  fromEmail;
    // 单发
    public void sendOneEmail(String toEmail,String title,String context){
        SimpleMailMessage message =  new SimpleMailMessage();
        // 邮件标题
        message.setSubject(title);
        // 从哪里，发送
        message.setFrom(fromEmail);
        // 到哪里，可设置多个，实现群发
        message.setTo(toEmail);
        // 邮件时间
        message.setSentDate(new Date());
        // 邮件内容
        message.setText(context);
        try {
            javaMailSender.send(message);
//            log.info("邮件发送成功");
        } catch (Exception  e){
            e.printStackTrace();
//            log.error("邮件发送失败");
        }
    }
    // 发送附件
    public void sendFileEmail(String toEmail,String title,String context,String fileName,String filePath) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setSubject(title);
        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSentDate(new Date());
        helper.setText(context);
        helper.addAttachment(fileName,new File(filePath));
        javaMailSender.send(mimeMessage);
    }

    //产生随机验证码
    public String getRandCode(){
        int code = (int) (Math.random()*900000) + 100000;
        return new Integer(code).toString();
    }
}
