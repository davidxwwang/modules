package com.david.module.data.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.nio.channels.Channel;

import static org.junit.jupiter.api.Assertions.*;
//@RunWith(JUnit4.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentRepositoryTest {

    @Resource
    StudentRepository studentRepository;

    @Test
    public void test(){
        studentRepository.save(null);

    }
}