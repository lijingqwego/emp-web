package com.kaisn.service;

import com.alibaba.fastjson.JSONObject;
import com.kaisn.mysql.QueryParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IDynamicQueryService {

    List<Map<String,Object>> querySql(QueryParam queryParam);

    int push(String tableName,String values);

    int push(String tableName, List<JSONObject> values);
}
