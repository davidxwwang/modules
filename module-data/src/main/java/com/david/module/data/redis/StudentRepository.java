package com.david.module.data.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;


/**
 *
 * 看一看 CrudRepository
 */

@Slf4j
@Component
public class StudentRepository{

//    @Autowired  这样会报错的
//    private RedisTemplate<String, Student> redisTemplate;

    @Resource
    private RedisTemplate<String, Student> redisTemplate;

    @PostConstruct
    public void postConstruct(){
        log.info("");
    }

    public void save(Student student) {

        System.out.println(System.getProperty("java.class.path"));//系统的classpaht路径
        System.out.println(System.getProperty("user.dir"));//用户的当前路径


        // todo 一定要搞明白
        String classPath = this.getClass().getClassLoader().getResource("").getPath();
        String resourcePath = classPath + "mybatis/mybatis-config.xml";
      //  String resourcePath = "/Users/david/Desktop/work/clients/module2/module-data/src/main/java/mybatis/mybatis-config.xml";
        try {

         //   InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/com/david/module/data/mysql/mybatis-config.xml");

       //     InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/mybatis/mybatis-config.xml");
           InputStream inputStream = Resources.getResourceAsStream(resourcePath);

            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            System.out.print("\n" + "sqlSession  = " + sqlSession.toString());
//            GirlDOMapper girlMapper =  sqlSession.getMapper(GirlDOMapper.class);
//            // int count = girlMapper.getAll();

            sqlSession.close();
            sqlSession.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("\n" + "IOException  = " + e.toString());
        }

    Class  c1 = RedisTemplate.class;
        Class  c2 = redisTemplate.getClass();

        redisTemplate.opsForValue()
                .set(student.getId(), student);
    }

    public Student findById(String id) {
        return redisTemplate.opsForValue()
                .get(id);
    }

}