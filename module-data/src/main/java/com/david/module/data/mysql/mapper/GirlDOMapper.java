package com.david.module.data.mysql.mapper;

import com.david.module.data.mysql.GirlDO;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

// 如果有标记@MapperScan，就不要在这里写@Mapper了
@Mapper
public interface GirlDOMapper {

    int deleteByPrimaryKey(Integer girlid);

    int insert(GirlDO record);

    int insertSelective(GirlDO record);

    GirlDO selectByPrimaryKey(Integer girlid);

    int updateByPrimaryKeySelective(GirlDO record);

    int updateByPrimaryKey(GirlDO record);
}