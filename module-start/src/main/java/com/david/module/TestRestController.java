package com.david.module;

//import com.david.module.util.Person;

import com.david.module.data.mysql.GirlDO;
import com.david.module.data.mysql.dao.GirlDAO;
import com.david.module.data.redis.Student;
import com.david.module.data.redis.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.Properties;

@RestController
public class TestRestController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GirlDAO girlDAO;

    @RequestMapping("/hello")
    public String helloWord(){

        Student student = new Student();
        student.setGrade(1);
        student.setId("david_1");
        student.setName("david");

      //  studentRepository.save(student);
      //  Person person = new Person();
        return "hello world: david \n";
    }

    //  curl 127.0.0.1:8001/mysql
    @RequestMapping("/mysql")
    public GirlDO mysql(){


        GirlDO girlDO = new GirlDO();
        girlDO.setAge(20);
        girlDO.setEmail("xx");
        girlDO.setUserName("秋香");
        girlDO.setPassword("root");
        girlDO.setCupSize("cc");
       // girlDAO.doInsert(girlDO);
        return girlDAO.doTest();
    }

    @RequestMapping("/redis")
    public String redis(){

        Student student = new Student();
        student.setGrade(1);
        student.setId("david_1");
        student.setName("david");

        studentRepository.save(student);

        Student student1 = studentRepository.findById("david_1");

        //  Person person = new Person();
        return student1.toString();
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println(2);
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println(2);
    }
}
