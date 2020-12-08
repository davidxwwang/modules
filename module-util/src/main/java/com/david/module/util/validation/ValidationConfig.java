package com.david.module.util.validation;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@ComponentScan(value = "com.javastudy.vendor.validation")
public class ValidationConfig {

    /**
     * 方法级别的验证
     *
     * @return
     */

    @Bean
    public MethodValidationPostProcessor getMethodValidationPostProcessor() {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        // processor.setValidator(this.validator());
        return processor;
    }

//    @Bean
//    public LocalValidatorFactoryBean validator(){
//        return new LocalValidatorFactoryBean();
//    }
}
