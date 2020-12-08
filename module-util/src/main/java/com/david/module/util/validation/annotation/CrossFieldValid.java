package com.david.module.util.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CrossFieldValidator.class)
@Documented

/**
 * 用于校验对象中多个属性，比如 开始时间要小于结束时间，否则错误
 */
public @interface CrossFieldValid {

    String message() default "{constraints.fieldmatch}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 第一个属性名称
     */
    String first();

    /**
     * 第二个属性名称
     */
    String second();


    /**
     * @see CrossFieldValid
     */
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CrossFieldValid[] value();
    }
}
