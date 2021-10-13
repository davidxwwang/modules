package com.david.module.data.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.api.stream.StreamAddArgs;
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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.BaseStream;


/**
 *
 * 看一看 CrudRepository
 */

@Slf4j
@Component
public class StudentRepository{

//    @Autowired  这样会报错的
//    private RedisTemplate<String, Student> redisTemplate;

//    @Resource
//    private RedisTemplate<String, Student> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    @PostConstruct
    public void postConstruct(){
        log.info("");
    }

    public void save(Student student) {

//        redisTemplate.opsForValue()
//                .set(student.getId(), student);
        RBucket<Object> bucket = redissonClient.getBucket(student.getId());
        bucket.set(student);
    }

    public Student findById(String id) {
//        return redisTemplate.opsForValue()
//                .get(id);
        RBucket<Object> bucket = redissonClient.getBucket(id);
        Object o = bucket.get();
        return (Student) o;
    }

    public void doTestRedis(){

        // pipeline 与 事务 的差别


        // 批量提交 pipeline
        String setName = "myset";
        RBatch batch = redissonClient.createBatch();
        RSetAsync<Object> set = batch.getSet(setName);
        RFuture<Boolean> booleanRFuture = set.addAsync("david");

        RSetAsync<Object> set2 = batch.getSet(setName);
        RFuture<Boolean> booleanRFuture2 = set.addAsync("haohao");
        BatchResult<?> execute = batch.execute();
        List<?> responses = execute.getResponses();
//        RSet<Object> set1 = redissonClient.getSet(setName);
//                set1.toArray();
        System.out.print(responses.toString() + " ::: " );


        // 事务
        try {
            RTransaction transaction = redissonClient.createTransaction(TransactionOptions.defaults());
            RBucket<Integer> myInt = transaction.getBucket(setName);
            myInt.set(1);
            myInt.trySetAsync(2);

            transaction.getBucket("msg").set("______this is new String");
            transaction.commit();
        }catch (Exception ex){
            ex.printStackTrace();
        }


        // stream
//        RStream<Object, Object> mystream = redissonClient.getStream("mystream");
//
//        HashMap<String, Object> bizData = new HashMap<>();
//        bizData.put("msg", "hello haohao");
//        BaseStream
//        StreamAddArgs<Object, Object> msg = new BaseStreamAddArgs(bizData);
//        mystream.addAsync()

    }

}