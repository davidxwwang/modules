package com.david.module.util.validation;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ToString
@Validated
public class Item implements Serializable {

    @Getter
    @Setter
    @NotNull(message = "id 不能为null")
    @Min(value = 0, message = "最小不能为0")
    private Integer id;

    @Getter
    @Setter
    @NotNull(message = "name 不能为空")
    @NotBlank(message = "name 不能为空")
    private String name;
}
