package com.kaisn.mysql;

import com.alibaba.fastjson.JSONArray;

public class UpdateParam {

    private String tableName;

    private JSONArray values;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public JSONArray getValues() {
        return values;
    }

    public void setValues(JSONArray values) {
        this.values = values;
    }
}
