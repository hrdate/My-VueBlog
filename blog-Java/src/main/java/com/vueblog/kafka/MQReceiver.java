package com.vueblog.kafka;

import com.vueblog.util.JavaMailUtils;
import com.vueblog.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.vueblog.common.constant.RedisPrefixConst.CODE_KEY;

@Slf4j
@Component
public class MQReceiver {

    @Autowired
    private JavaMailUtils javaMailUtils;

    @Autowired
    private RedisUtils redisUtils;

    @KafkaListener(id = "defaultConsumer", groupId = "defaultConsumerGroup"
            , topics = {"vueBlogTopic"})
    public void onMessage(ConsumerRecord<String, Object> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        log.info("简单消费：topic:" + record.topic() + " - partition:" + record.partition()
                + " - key:" + record.key() + " - value:" + record.value());
        //邮箱注册,验证码15分钟有效
        String email = record.value().toString();
        String code = javaMailUtils.getRandCode();
        redisUtils.set(CODE_KEY + email, code,15 * 60);
        String context = "您的验证码为: "+code+" 有效期为15分钟，请不要告诉他人噢(⊙o⊙)";
        javaMailUtils.sendOneEmail(email,"博客注册验证码",context);
    }

}
