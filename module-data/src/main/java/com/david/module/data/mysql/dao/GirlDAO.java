package com.david.module.data.mysql.dao;

import com.david.module.data.mysql.GirlDO;
import com.david.module.data.mysql.mapper.GirlDOMapper;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Slf4j
@Service
public class GirlDAO {

    @Resource
    PlatformTransactionManager transactionManager;

    @Resource
    private GirlDOMapper girlMapper;

    @Resource
    DataSource dataSource;

    public GirlDO getDataById(Integer id){
        return girlMapper.selectByPrimaryKey(id);
    }

    public Integer doInsert(GirlDO girlDO){
        log.info("数据源是 = {}", dataSource);
       int count = girlMapper.insertSelective(girlDO);
       log.info("count = {}", count);
       return count;
    }

    // 使用的是TransactionInterceptor 见TransactionAspectSupport~invokeWithinTransaction()
    @Transactional
    public void doTransactionInsert(GirlDO girlDO, Boolean triggerExp){
        girlMapper.insertSelective(girlDO);

        if (triggerExp){
            int x = 0;
            int y = 5/x;
        }
    }

    // 编程式事务
    public void doCodingTransactionInsert(GirlDO girlDO, Boolean triggerExp){

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setReadOnly(false);
        //隔离级别,-1表示使用数据库默认级别
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        TransactionStatus transaction = transactionManager.getTransaction(def);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                log.info("commited");
            }

            @Override
            public void afterCompletion(int status) {
                log.info("afterCompletion");
            }
        });
        try {
            int count = girlMapper.insertSelective(girlDO);
            if (triggerExp){
                int x = 0;
                int y = 5/x;
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error(e.getMessage());
        }
    }

}
