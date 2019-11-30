package com.kaisn.mysql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class QueryToStructUtils {

    public static QueryParam createQueryParam(String values){
        QueryParam queryParam = new QueryParam();
        JSONObject jsonObject = JSONObject.parseObject(values);
        //table
        String dataSource = jsonObject.getString("dataSource");
        queryParam.setTableName(dataSource);
        //column
        JSONArray dimensions = jsonObject.getJSONArray("dimensions");
        List<String> columnList = JSONObject.parseArray(dimensions.toJSONString(), String.class);
        queryParam.setColumnList(columnList);
        //where
        JSONArray conditionFilter = jsonObject.getJSONArray("conditionFilter");
        List<OutWhere> outWhereList = new ArrayList<>();
        for (int i = 0; i < conditionFilter.size(); i++) {
            OutWhere outWhere = new OutWhere();
            JSONObject outConditionArray = conditionFilter.getJSONObject(i);
            String outLogic = outConditionArray.getString("logic");
            outLogic = StringUtils.equals("no",outLogic)? StringUtils.EMPTY:outLogic;
            JSONArray inConditionArray = outConditionArray.getJSONArray("condition");
            List<InWhere> inWhereList = new ArrayList<>();
            for (int j = 0; j < inConditionArray.size(); j++) {
                InWhere inWhere = new InWhere();
                JSONObject condition = inConditionArray.getJSONObject(j);
                String dimension = condition.getString("dimension");
                String logic = condition.getString("logic");
                logic = StringUtils.equals("no",logic)? StringUtils.EMPTY:logic;
                String relat = condition.getString("relat");
                String value = condition.getString("value");
                inWhere.setColumn(dimension);
                inWhere.setLogic(logic);
                inWhere.setRelat(relat);
                inWhere.setValue(value);
                inWhereList.add(inWhere);
            }
            outWhere.setLogic(outLogic);
            outWhere.setInWhereList(inWhereList);
            outWhereList.add(outWhere);
        }
        queryParam.setOutWhereList(outWhereList);
        //group by
        List<GroupBy> groupByList = new ArrayList<>();
        JSONArray groupArray = jsonObject.getJSONArray("groupArray");
        for (int i = 0; i < groupArray.size(); i++) {
            GroupBy groupBy = new GroupBy();
            JSONObject groupObject = groupArray.getJSONObject(i);
            String dimension = groupObject.getString("dimension");
            String direction = groupObject.getString("direction");
            groupBy.setColumn(dimension);
            groupBy.setSort(direction);
            groupByList.add(groupBy);
        }
        queryParam.setGroupByList(groupByList);
        String limit = jsonObject.getString("limit");
        queryParam.setRows(Integer.parseInt(limit));
        return queryParam;
    }
}
