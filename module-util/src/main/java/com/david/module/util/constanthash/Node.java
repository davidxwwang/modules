package com.david.module.util.constanthash;

import lombok.Data;

/**
 * 节点 可以认为是服务器
 */
@Data
public class Node {

    private String name;

    private String key;

    Object object;
}
