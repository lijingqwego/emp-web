package com.kaisn.dao;

import com.alibaba.fastjson.JSON;
import com.kaisn.mysql.QueryParam;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DynamicQueryDaoTest{

    SqlSession sqlSession;

    @Test
    public void testQuerySql() throws IOException {

        InputStream in = Resources.getResourceAsStream("config/spring-context.xml");
        SqlSessionFactoryBuilder builder =new SqlSessionFactoryBuilder();

        SqlSessionFactory factory = builder.build(in);
        sqlSession = factory.openSession();
        IDynamicQueryDao iDynamicQueryDao = sqlSession.getMapper(IDynamicQueryDao.class);
        QueryParam queryParam = new QueryParam();
        List<String> columnList = new ArrayList<>();
        columnList.add("emp_name");
        queryParam.setColumnList(columnList);
        queryParam.setTableName("t_employee");
        List<Map<String, Object>> maps = iDynamicQueryDao.querySql(queryParam);
        System.out.println(JSON.toJSONString(maps));
    }
}