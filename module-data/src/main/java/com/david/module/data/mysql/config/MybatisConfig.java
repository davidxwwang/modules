package com.david.module.data.mysql.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer(){
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(false);
                configuration.setUseGeneratedKeys(true);
             //   configuration.setProxyFactory();
                configuration.setObjectFactory(new DavidMybatisObjectFactory());
            }
        };
    }
}
