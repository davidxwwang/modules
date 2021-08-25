package com.david.module.data.mysql.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.alibaba.druid.support.http.ResourceServlet.*;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    // 配置Druid监控
    // 1 配置Servlet，没有这个bean http://localhost:7001/druid就挂了
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        Map<String, String> map = new HashMap<>();

        // 在com.alibaba.druid.support.http.ResourceServlet中可以找到相关设置的key
        map.put(PARAM_NAME_USERNAME,"root");
        map.put(PARAM_NAME_PASSWORD,"438444w");
        map.put(PARAM_NAME_ALLOW,""); // 容许所有
        // map.put("deny","");
        bean.setInitParameters(map);
        return bean;
    }

    // 2 配置监控的filter,对应后台的 http://localhost:7001/druid/webapp.html,瑞
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean filterRegistrationBean =  new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());

        Map<String, String> map = new HashMap<>();
        map.put("exclusions","*.js,*.css");
        filterRegistrationBean.setInitParameters(map);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/"));
        return filterRegistrationBean;
    }

}
