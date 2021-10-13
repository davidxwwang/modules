package com.david.module.data.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.net.Socket;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.Future;

@Slf4j
@Configuration
public class RedisConfiguration {

    @PostConstruct
    public void postConstruct(){
        log.info("postConstruct");
    }

//    @Bean("redisTemplate")
//    public RedisTemplate<String, Object> redisTemplate() {
//
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        return template;
//    }
//
//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConFactory
//                = new JedisConnectionFactory();
//        jedisConFactory.setHostName("localhost");
//        jedisConFactory.setPort(6380);
//        return jedisConFactory;
//    }

    @Bean
    public RedissonClient redissonClient(){

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6380");
                // use "rediss://" for SSL connection

        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

}
