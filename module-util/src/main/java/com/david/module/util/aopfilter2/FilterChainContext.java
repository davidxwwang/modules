package com.david.module.util.aopfilter2;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class FilterChainContext {

    @Setter
    @Getter
    private Map<String, Object> contextMap = new HashMap<>();
}
