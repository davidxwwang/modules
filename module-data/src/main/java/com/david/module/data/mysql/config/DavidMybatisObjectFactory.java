package com.david.module.data.mysql.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

@Slf4j
public class DavidMybatisObjectFactory extends DefaultObjectFactory implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("mybatis java生成类 = {}", this.getClass());
    }

    @Override
    public <T> T create(Class<T> type) {
        log.info("ccc");
        return super.create(type);
    }

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        log.info("ccc");
        return super.create(type, constructorArgTypes, constructorArgs);
    }
}
