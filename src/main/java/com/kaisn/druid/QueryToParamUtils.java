package com.kaisn.druid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class QueryToParamUtils {

    public static String getDataSourceStruct(String values){

        JSONObject valuesObject = JSONObject.parseObject(values);

        String dataSource = valuesObject.getString("dataSource");

        return dataSource;
    }

    public static String getDimenstionStruct(String values){

        JSONObject valuesObject = JSONObject.parseObject(values);

        JSONArray dimensions = valuesObject.getJSONArray("dimensions");

        return JSON.toJSONString(dimensions);
    }

    public static String getGranularityStruct(String values){

        String granularity = "all";

        JSONObject valuesObject = JSONObject.parseObject(values);

        granularity = valuesObject.getString("granularity");

        return granularity;
    }

    public static int getlimitStruct(String values){

        JSONObject valuesObject = JSONObject.parseObject(values);

        int limit = valuesObject.getIntValue("limit");

        return limit;
    }

    public static String toIntervalsStruct(String values){
        String formatDate = null;
        try {
            SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            JSONObject valuesObject = JSONObject.parseObject(values);
            JSONObject baseFilter = valuesObject.getJSONObject("baseFilter");
            JSONObject dateTimeObject = baseFilter.getJSONObject("dateTime");
            String startTime = dateTimeObject.getString("startTime");
            String endTime = dateTimeObject.getString("endTime");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startTime);
            Date endtDate = dateFormat.parse(endTime);
            String formatStartDate = timestampFormat.format(startDate);
            String formatEndDate = timestampFormat.format(endtDate);
            formatDate = formatStartDate+"/"+formatEndDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatDate;
    }

    public static String toFilterStruct(String values){
        JSONArray filterArray = new JSONArray();

        JSONObject valuesObject = JSONObject.parseObject(values);

        JSONObject baseFilter = valuesObject.getJSONObject("baseFilter");
        JSONArray baseFilterArray = toBaseFilter(baseFilter);
        filterArray.addAll(baseFilterArray);

        JSONArray conditionFilter = valuesObject.getJSONArray("conditionFilter");
        JSONArray toConditionArray = toConditionArray(conditionFilter);
        filterArray.addAll(toConditionArray);

        JSONObject logicFilter = FilterUtils.toLogicFilter("and", filterArray);
        return JSON.toJSONString(logicFilter);
    }

    private static JSONArray toConditionArray(JSONArray conditionArray) {
        JSONArray jsonArray = new JSONArray();
        Collections.reverse(conditionArray);
        String typeTemp = "and";
        for (int i = 0; i < conditionArray.size(); i++) {
            JSONObject jsonObject = conditionArray.getJSONObject(i);
            String type = jsonObject.getString("logic");
            if("no".equals(type)){
                type = typeTemp;
            }else{
                typeTemp = type;
            }
            JSONArray condition = jsonObject.getJSONArray("condition");
            JSONArray conditionFields = toConditionFilter(condition);
            JSONObject logicFilter = FilterUtils.toLogicFilter(type, conditionFields);
            jsonArray.add(logicFilter);
        }
        return jsonArray;
    }

    private static JSONArray toConditionFilter(JSONArray condition) {

        JSONObject tempObject = new JSONObject();
        Collections.reverse(condition);
        String typeTemp = "and";
        for (int i = 0; i < condition.size(); i++) {
            JSONObject jsonObject = condition.getJSONObject(i);
            String dimension = jsonObject.getString("dimension");
            String logic = jsonObject.getString("logic");
            if("no".equals(logic)){
                logic = typeTemp;
            }else{
                typeTemp = logic;
            }
            String relat = jsonObject.getString("relat");
            String value = jsonObject.getString("value");
            JSONObject field = FilterUtils.switchFilter(relat, dimension, value);
            if(tempObject.containsKey(logic)){
                tempObject.getJSONArray(logic).add(field);
            }else{
                JSONArray tempArray = new JSONArray();
                tempArray.add(field);
                tempObject.put(logic, tempArray);
            }
        }

        JSONArray fields = new JSONArray();
        for (String key : tempObject.keySet()){
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = tempObject.getJSONArray(key);
            jsonObject.put("type",key);
            jsonObject.put("fields",jsonArray);
            fields.add(jsonObject);
        }
        return fields;
    }

    private static JSONArray toBaseFilter(JSONObject baseFilter) {
        JSONArray fieldArray = new JSONArray();
        JSONArray chrTypeValues = baseFilter.getJSONArray("CHR Type");
        JSONArray neTypeValues = baseFilter.getJSONArray("neType");
        JSONArray neNameValues = baseFilter.getJSONArray("neName");

        JSONObject dateTimeObject = baseFilter.getJSONObject("dateTime");
        String startTime = dateTimeObject.getString("startTime");
        String endTime = dateTimeObject.getString("endTime");

        if(CollectionUtils.isNotEmpty(chrTypeValues)){
            JSONObject chrType = FilterUtils.toInFilter("CHR Type", chrTypeValues);
            fieldArray.add(chrType);
        }
        if(CollectionUtils.isNotEmpty(neTypeValues)){
            JSONObject neType = FilterUtils.toInFilter("neType", neTypeValues);
            fieldArray.add(neType);
        }
        if(CollectionUtils.isNotEmpty(neNameValues)){
            JSONObject neName = FilterUtils.toInFilter("neName", neNameValues);
            fieldArray.add(neName);
        }
        JSONObject dateTime = FilterUtils.toBoundFilter("dateTime", startTime + ";" + endTime,
                false, FilterUtils.RELAT_BT, false, false);

        fieldArray.add(dateTime);

        return fieldArray;
    }

}
