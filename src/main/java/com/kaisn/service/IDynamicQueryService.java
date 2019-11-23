package com.kaisn.service;

import com.kaisn.mysql.QueryParam;

import java.util.List;
import java.util.Map;

public interface IDynamicQueryService {

    List<Map<String,Object>> querySql(QueryParam queryParam);
}
