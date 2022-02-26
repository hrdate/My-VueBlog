package com.vueblog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    /**
     * shiro redis缓存使用的模板
     * 实例化 RedisTemplate 对象
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //hash类型 key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash类型 value序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //注入连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPool,
                                                         RedisStandaloneConfiguration jedisConfig) {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisConfig);
        connectionFactory.setPoolConfig(jedisPool);
        return connectionFactory;
    }

    @Configuration
    public static class JedisConf {
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
        @Value("${spring.redis.password}")
        private String password;

        @Value("${spring.redis.database:0}")
        private Integer database;

        @Value("${spring.redis.jedis.pool.max-active:8}")
        private Integer maxActive;

        @Value("${spring.redis.jedis.pool.max-idle:8}")
        private Integer maxIdle;

        @Value("${spring.redis.jedis.pool.max-wait:-1}")
        private Long maxWait;

        @Value("${spring.redis.jedis.pool.min-idle:0}")
        private Integer minIdle;

        @Bean
        public JedisPoolConfig jedisPool() {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxWaitMillis(maxWait);
            jedisPoolConfig.setMaxTotal(maxActive);
            jedisPoolConfig.setMinIdle(minIdle);
            return jedisPoolConfig;
        }

        @Bean
        public RedisStandaloneConfiguration jedisConfig() {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            config.setHostName(host);
            config.setPort(port);
            config.setDatabase(database);
            config.setPassword(RedisPassword.of(password));
            return config;
        }
    }


}
