//package com.david.module;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import javax.annotation.PostConstruct;
//
//@Slf4j
//@Configuration
//public class RedisConfig {
//
//    @PostConstruct
//    public void postConstruct(){
//        log.info("");
//    }
//
//    @Bean
//   // @ConditionalOnMissingBean(name = "redisTemplate")
//    public RedisTemplate redisTemplate() {
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
//
//}
