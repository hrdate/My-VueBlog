package com.vueblog.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
public class RedisConfig  {


    /**
     * shiro redis缓存使用的模板
     * 实例化 RedisTemplate 对象
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //注入连接工厂
        redisTemplate.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 初始化参数和初始化工作
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * redis地址
     */
    @Value("${spring.redis.host:127.0.0.1}")
    private String host;

    /**
     * redis端口
     */
    @Value("${spring.redis.port:6379}")
    private Integer port;

    /**
     * redis密码
     */
    @Value("${spring.redis.password:hrdate}")
    private String password;

    /**
     * redis数据库默认使用db0
     */
    @Value("${spring.redis.database:0}")
    private Integer database;

    @Value("${spring.redis.timeout:5000}")
    private int timeout;


//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setPassword("hrdate");
//        return factory;
//    }

//    @Bean
//    public JedisPool jedisPool(){
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMinIdle(minIdle);
//        jedisPoolConfig.setMaxTotal(maxActive);
////        JedisPool jedisPool = new JedisPool(jedisPoolConfig ,host, port, timeout, password);
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig ,host, port, timeout);
//        log.info("JedisPool连接成功: host:" + host + "  prot:" + port + " password:" + password);
//        return jedisPool;
//    }



}
