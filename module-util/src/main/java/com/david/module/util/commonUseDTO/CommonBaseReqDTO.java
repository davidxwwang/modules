package com.david.module.util.commonUseDTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public abstract class CommonBaseReqDTO implements Serializable {

    @Getter
    @Setter
    private String userId;
}
