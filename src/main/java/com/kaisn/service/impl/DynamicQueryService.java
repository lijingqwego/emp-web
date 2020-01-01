package com.kaisn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kaisn.dao.IDynamicQueryDao;
import com.kaisn.mysql.InsertParam;
import com.kaisn.mysql.QueryParam;
import com.kaisn.service.IDynamicQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Override
    public int push(String tableName,String values) {
        String[] valArray = values.split("\n");
        int count = 0;
        for (int i = 0; i < valArray.length; i++) {
            InsertParam insertParam = new InsertParam();
            insertParam.setTableName(tableName);
            Map<String,String> map = JSONObject.parseObject(valArray[i], Map.class);
            insertParam.setParams(map);
            iDynamicQueryDao.insertInfo(insertParam);
            count++;
        }
        return count;
    }

    @Override
    public int push(String tableName, List<JSONObject> values) {
        int count = 0;
        Map<String, String> param = new HashMap<String, String>();
        param.put("tableName",tableName);
        iDynamicQueryDao.deleteInfo(param);
        for(JSONObject jsonObject : values){
            InsertParam insertParam = new InsertParam();
            insertParam.setTableName(tableName);
            Map<String,String> map = JSONObject.parseObject(jsonObject.toJSONString(), Map.class);
            insertParam.setParams(map);
            iDynamicQueryDao.insertInfo(insertParam);
            count++;
        }
        return count;
    }
}
