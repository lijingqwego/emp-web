package com.kaisn.dao;

import com.kaisn.mysql.InsertParam;
import com.kaisn.mysql.QueryParam;

import java.util.List;
import java.util.Map;

public interface IDynamicQueryDao {

    List<Map<String,Object>> querySql(QueryParam queryParam);

    void insertInfo(InsertParam updateParam);

    void updateInfoByID(Map<String,String> params);
}
