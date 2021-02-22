package com.david.module.data.mysql.dao;

import com.david.module.data.mysql.GirlDO;
import com.david.module.data.mysql.mapper.GirlDOMapper;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class GirlDAO {

    @Resource
    private GirlDOMapper girlMapper;

    public GirlDO doTest(){
        GirlDO count = girlMapper.selectByPrimaryKey(2);
        log.info("count = {}", count);
        return count;
    }

    public Integer doInsert(GirlDO girlDO){
       int count = girlMapper.insertSelective(girlDO);
       log.info("count = {}", count);
       return count;
    }
}
