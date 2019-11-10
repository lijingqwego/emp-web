package com.kaisn.druid;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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
        JSONObject pagingIdentifiers = result.getJSONObject("pagingIdentifiers");
        return pagingIdentifiers;
    }

    public static int getTotalResult(String druidResult){
        JSONArray jsonArray = JSONArray.parseArray(druidResult);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        JSONObject event = jsonObject.getJSONObject("event");
        int count = event.getIntValue("count");
        return count;
    }

    public static JSONArray getCountResult(String druidResult){
        JSONArray resultArray = new JSONArray();
        JSONArray jsonArray = JSONArray.parseArray(druidResult);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray result = jsonObject.getJSONArray("result");
            resultArray.addAll(result);
        }
        return resultArray;
    }
}
