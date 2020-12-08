package com.david.module.util.validation;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated  // 打开校验开关 要加上这个 、，下面的@NotNull 才起作用
public interface Item2 {

    String setName(@NotNull(message = "姓名不能为空") String name, @Min(value = 0, message = "最小为0") Long id);


    Long multiple(@Min(value = 0, message = "最小为0") Long id);/**/

    // public void setItem(@NotNull(message = "姓名不能为空") Item item);
}
