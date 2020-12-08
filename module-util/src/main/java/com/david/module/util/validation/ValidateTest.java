package com.david.module.util.validation;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

//@Validated
public class ValidateTest {

    public static void main(String[] args) {

        ValidateTest validateTest = new ValidateTest();
        Item item = new Item();
        Integer a = validateTest.doValidate(item);
        Integer b = validateTest.doValidate2(null);

        Integer i = 3;
    }

    public Integer doValidate(Item item) {
        return 1;
    }

    @Validated
    public Integer doValidate2(@NotNull Integer item) {
        return 2;
    }

}
