package com.david.module;

//import com.david.module.util.Person;

import com.david.module.data.mysql.GirlDO;
import com.david.module.data.mysql.dao.GirlDAO;
import com.david.module.data.mysql.mapper.GirlDOMapper;
import com.david.module.data.redis.Student;
import com.david.module.data.redis.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
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

    //  curl 127.0.0.1:8100/mysql/insert
    @RequestMapping("/mysql/insert")
    public Integer insertMysql(){
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        try {
            Class<?> aClass = Class.forName("com.david.module.TestRestController");
            Class.forName("x");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        System.out.println(System.getProperty("java.class.path"));//系统的classpaht路径
        System.out.println(System.getProperty("user.dir"));//用户的当前路径

        GirlDO girlDO = new GirlDO();
        girlDO.setAge(20);
        girlDO.setEmail("xx");
        girlDO.setUserName("秋香");
        girlDO.setPassword("root");
        girlDO.setCupSize("cc");
        return girlDAO.doInsert(girlDO);
    }

    @RequestMapping("/mysql/get")
    public GirlDO getMysql(){
        return girlDAO.getDataById(1);
    }

    //  curl 127.0.0.1:8001/mysql/trx/true
    @RequestMapping("/mysql/trx/{triggerExp}")
    public void mysqlTrx(@PathVariable("triggerExp") Boolean triggerExp){
        log.info("triggerExp \n");
        GirlDO girlDO = new GirlDO();
        girlDO.setAge(20);
        girlDO.setEmail("xx");
        girlDO.setUserName("秋香");
        girlDO.setPassword("root");
        girlDO.setCupSize("cc");

        try {
            girlDAO.doCodingTransactionInsert(girlDO, triggerExp);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //  curl 127.0.0.1:8001/mysql/annotationtrx/true
    @RequestMapping("/mysql/annotationtrx/{triggerExp}")
    public void mysqlAnnotationTrx(@PathVariable("triggerExp") Boolean triggerExp){
        log.info("triggerExp \n");
        GirlDO girlDO = new GirlDO();
        girlDO.setAge(20);
        girlDO.setEmail("xx");
        girlDO.setUserName("秋香");
        girlDO.setPassword("root");
        girlDO.setCupSize("cc");

        try {
            girlDAO.doTransactionInsert(girlDO, triggerExp);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //  curl 127.0.0.1:8001/redis
    @RequestMapping("/redis")
    public String redis(){

        Student student = new Student();
        student.setGrade(1);
        student.setId("david_1");
        student.setName("david");

        studentRepository.save(student);

        Student student1 = studentRepository.findById("david_1");
        Future<?> submit = Executors.newCachedThreadPool().submit(new Runnable() {
            @Override
            public void run() {

            }
        });


        //  Person person = new Person();
        return student1.toString();
    }
    // curl 127.0.0.1:8100/classpath
    @RequestMapping("/classpath")
    public void printClasspath(){
        String bootStrapLoadingPath = System.getProperty("sun.boot.class.path");
        List<String> bootStrapLoadingPathList = Arrays.asList(bootStrapLoadingPath.split(":"));

        String extLoadingPath = System.getProperty("java.ext.dirs");
        List<String> extLoadingPathList = Arrays.asList(extLoadingPath.split(":"));

        String appLoadingPath = System.getProperty("java.class.path");
        List<String> appLoadingPathList = Arrays.asList(appLoadingPath.split(":"));

        log.info("" );
    }

    //  curl 127.0.0.1:8001/dotestredis
    @RequestMapping("/dotestredis")
    public void dotestredis(){
        studentRepository.doTestRedis();
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
