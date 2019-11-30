package com.kaisn.dao;

import com.kaisn.mysql.QueryParam;
import com.kaisn.mysql.UpdateParam;

import java.util.List;
import java.util.Map;

public interface IDynamicQueryDao {

    List<Map<String,Object>> querySql(QueryParam queryParam);

    int insertForeach(UpdateParam updateParam);
}
