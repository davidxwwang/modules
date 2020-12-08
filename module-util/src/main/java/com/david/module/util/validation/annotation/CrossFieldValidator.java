package com.david.module.util.validation.annotation;

import com.david.module.util.validation.group.Group1;
import com.david.module.util.validation.group.Group2;
import com.david.module.util.vendors.ObjectUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CrossFieldValidator implements ConstraintValidator<CrossFieldValid, Object> {

    /**
     * 验证注解的groups，以便对元素分组校验
     */
    Class<?>[] groups;
    /**
     * 属性名称
     */
    private String firstFieldName;

    /**
     * 属性名称
     */
    private String secondFieldName;


    @Override
    public void initialize(final CrossFieldValid constraintAnnotation) {

        groups = constraintAnnotation.groups();
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        // 对同一个元素，分组校验
        if (Arrays.asList(groups).contains(Group1.class)) {
            // todo 可以根据 value值，返回 true/false
            return true;
        } else if (Arrays.asList(groups).contains(Group2.class)) {
            // todo 可以根据 value值，返回 true/false
            return false;
        }

        try {
            final Object firstObj = ObjectUtil.getFieldValueByName(firstFieldName, value);
            final Object secondObj = ObjectUtil.getFieldValueByName(secondFieldName, value);
            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception ignore) {
            // ignore
        }
        return true;
    }

}
