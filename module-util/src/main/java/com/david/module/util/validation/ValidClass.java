package com.david.module.util.validation;

import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;

@Validated
public class ValidClass {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    String testName(@NotNull(message = "string not null") String name) {
        Method[] methods = this.getClass().getDeclaredMethods();

        logger.info(methods + name);
        return methods + name;
    }

    String testName3(@NotNull String name) {
        Method[] methods = this.getClass().getMethods();
        logger.info(methods + name);
        return methods + name;
    }
}
