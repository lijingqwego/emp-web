package com.kaisn.service.impl;

import com.kaisn.dao.IDynamicQueryDao;
import com.kaisn.mysql.QueryParam;
import com.kaisn.service.IDynamicQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DynamicQueryService implements IDynamicQueryService {

    @Autowired
    private IDynamicQueryDao iDynamicQueryDao;

    @Override
    public List<Map<String,Object>> querySql(QueryParam queryParam){
        return iDynamicQueryDao.querySql(queryParam);
    }
}
