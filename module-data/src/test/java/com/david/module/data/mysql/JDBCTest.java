package com.david.module.data.mysql;

//
//import com.david.study.data.mysql.mapper.GirlDOMapper;
//
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.david.module.data.AutoIncTutorial;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 *  CREATE TABLE `girl` (
 *   `girlid` int(11) NOT NULL,
 *   `age` int(11) DEFAULT NULL,
 *   `cup_size` varchar(255) DEFAULT NULL,
 *   `email` varchar(255) DEFAULT NULL,
 *   `password` varchar(255) DEFAULT NULL,
 *   `user_name` varchar(255) DEFAULT NULL,
 *   PRIMARY KEY (`girlid`)
 * ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci |
 *
 *
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(JUnit4.class)
public class JDBCTest {

     final String url = "jdbc:mysql://localhost:3306/dbgirl?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";

    final String noSelectTableUrl = "jdbc:mysql://localhost:3307/?user=root&password=root";

    final String selectTableUrl = "jdbc:mysql://localhost:3307/david?user=root&password=root";

    // Logger log = LoggerFactory.getLogger(JDBCTest.class);

    /**
     * 测试下JDBC
     */
    @Test
    public void testJ(){
        final Properties properties = System.getProperties();
    //    System.out.println(System.getProperty("java.class.path"));//系统的classpaht路径

        Class mysqlClass = null;
        try {
            mysqlClass = Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
            //   log.error("");
        }

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs   = null;

        try{

            conn = DriverManager.getConnection(noSelectTableUrl);
            stmt = conn.createStatement();
//            stmt.executeUpdate("ALTER USER 'root'@'127.0.0.1' IDENTIFIED WITH mysql_native_password BY 'root';");
//            stmt.executeUpdate("FLUSH PRIVILEGES;");

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS david DEFAULT CHARSET utf8 COLLATE utf8_general_ci;");
            stmt.executeUpdate("USE david");

            stmt.executeUpdate("DROP TABLE IF EXISTS autoIncTutorial");
            stmt.executeUpdate(
                    "CREATE TABLE autoIncTutorial ("
                            + "priKey INT NOT NULL AUTO_INCREMENT, "
                            + "dataField VARCHAR(64), PRIMARY KEY (priKey))");

            stmt.executeUpdate(
                    "INSERT INTO autoIncTutorial (dataField) "
                            + "values ('Can I Get the Auto Increment Field?')");
            int autoIncKeyFromFunc = -1;
            rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            rs = stmt.executeQuery("SELECT * FROM autoIncTutorial");
            while (rs.next()){
                String dataField = rs.getString("dataField");
                System.out.print("输出  dataField = " + dataField);
            }

            if (rs.next()) {
                autoIncKeyFromFunc = rs.getInt(1);
            } else {
                // throw an exception from here
            }

            System.out.println("Key returned from " +
                    "'SELECT LAST_INSERT_ID()': " +
                    autoIncKeyFromFunc);

            System.out.print("");

//            conn = DriverManager.getConnection(url, "root", "root");
//            stmt = conn.createStatement();
//            rs = stmt.executeQuery("select * from girl");
//            while (rs.next()){
//                int id = rs.getInt("girlid");
//                System.out.print("");
//            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                }
                if (stmt != null){
                    stmt.close();
                }
                if (rs != null){
                    rs.close();
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    @Test
    public void testJDBCTemplate(){

        HikariConfig configuration = new HikariConfig();
        configuration.setJdbcUrl(selectTableUrl);
        configuration.setUsername("root");
        configuration.setPassword("root");
        DataSource source = new HikariDataSource(configuration);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(source);

        try {

            List<AutoIncTutorial> girlDTOS = jdbcTemplate.query("select * from autoIncTutorial", new ResultSetExtractor<List<AutoIncTutorial>>() {
                @Override
                public List<AutoIncTutorial> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    List<AutoIncTutorial> girls = new ArrayList<>();
                    while (rs.next()){
                        Integer id = rs.getInt("priKey");
                        String dataField = rs.getString("dataField");
                        AutoIncTutorial girlDTO = new AutoIncTutorial();
                        girlDTO.setPriKey(id);
                        girlDTO.setDataField(dataField);
                        girls.add(girlDTO);
                    }
                    return girls;
                }
            });
            System.out.print("\n" + "JdbcTemplate operation = " + girlDTOS.toString());
        }catch ( DataAccessException ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void testJDBCMybatis(){
        String resourcePath = "mybatis/mybatis-config.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resourcePath);

            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            System.out.print("\n" + "sqlSession  = " + sqlSession.toString());
//            GirlDOMapper girlMapper =  sqlSession.getMapper(GirlDOMapper.class);
//            // int count = girlMapper.getAll();

            sqlSession.close();
            sqlSession.commit();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("\n" + "IOException  = " + e.toString());
        }

    }



}

