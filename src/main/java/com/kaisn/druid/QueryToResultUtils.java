package com.kaisn.druid;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class QueryToResultUtils {

    public static JSONArray getTableResult(String druidResult){
        JSONArray resultArray = new JSONArray();
        JSONArray jsonArray = JSONArray.parseArray(druidResult);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray events = result.getJSONArray("events");
            //JSONArray dimensions = jsonObject.getJSONArray("dimensions");
            for (int j = 0; j < events.size(); j++) {
                JSONObject object = events.getJSONObject(j);
                JSONObject event = object.getJSONObject("event");
                event.remove("timestamp");
                event.remove("count");
                resultArray.add(event);
            }
        }
        return resultArray;
    }

    public static JSONObject getPagingIdentifiers(String druidResult){
        JSONArray jsonArray = JSONArray.parseArray(druidResult);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        JSONObject result = jsonObject.getJSONObject("result");
        return result.getJSONObject("pagingIdentifiers");
    }

    public static int getTotalResult(String druidResult){
        int count = 0;
        JSONArray jsonArray = JSONArray.parseArray(druidResult);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject event = jsonObject.getJSONObject("event");
            count += event.getIntValue("count");
        }
        return count;
    }

    public static JSONArray getCountResult(String druidResult,String granularity){
        JSONArray countArray = new JSONArray();
        JSONArray jsonArray = JSONArray.parseArray(druidResult);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray resultArray = jsonObject.getJSONArray("result");
            JSONArray countList = new JSONArray();
            for (int j = 0; j < resultArray.size(); j++) {
                JSONObject result = resultArray.getJSONObject(i);
                JSONObject countObject = new JSONObject();
                for(Map.Entry<String,Object> entry : result.entrySet()){
                    if(StringUtils.equals("dateTime",entry.getKey()) && StringUtils.equals("hour",granularity)){
                        countObject.put("dateTime",entry.getValue()+":00");
                    }else{
                        countObject.put(entry.getKey(),entry.getValue());
                    }
                }
                countList.add(countObject);
            }
            countArray.addAll(countList);
        }
        return countArray;
    }

}
