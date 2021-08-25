package com.david.module.data.mysql.config;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * mybatis 插件
 */
@Intercepts({@Signature(
        type= Executor.class,
        method = "get", //
        args = {MappedStatement.class,Object.class})})
public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        invocation.proceed();
        return "zhsdf;";
    }

    @Override
    public Object plugin(Object target) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
