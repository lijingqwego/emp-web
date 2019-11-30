package com.kaisn.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kaisn.dao.IDynamicQueryDao;
import com.kaisn.mysql.QueryParam;
import com.kaisn.mysql.UpdateParam;
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

    @Override
    public int push(String tableName,String values) {
        String[] valArray = values.split("\n");
        JSONArray objects = new JSONArray();
        for (int i = 0; i < valArray.length; i++) {
            JSONObject jsonObject = JSONObject.parseObject(valArray[i]);
            objects.add(jsonObject);
        }
        UpdateParam updateParam = new UpdateParam();
        updateParam.setTableName(tableName);
        updateParam.setValues(objects);
        int count = iDynamicQueryDao.insertForeach(updateParam);
        return count;
    }
}
