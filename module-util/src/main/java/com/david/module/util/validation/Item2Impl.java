package com.david.module.util.validation;

import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Service(value = "item2david")
public class Item2Impl implements Item2 {

    @Override
    public String setName(@NotNull(message = "姓名不能为空") String name, @Min(value = 0, message = "最小为0") Long id) {
        return name + String.valueOf(id);
    }

    @Override
    public Long multiple(@Min(value = 0, message = "最小为0") Long id) {
        return id * 2L;
    }
}
