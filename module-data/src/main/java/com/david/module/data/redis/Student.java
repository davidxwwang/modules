package com.david.module.data.redis;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {

    private String id;
    private String name;
    private int grade;
}
