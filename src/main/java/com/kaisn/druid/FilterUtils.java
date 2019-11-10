package com.kaisn.druid;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FilterUtils {

    public static JSONObject switchFilter(String type,String dimension,String value){
        JSONObject field = null;
        switch (type){
            case "eq":
                field = FilterUtils.toSelectorFilter(dimension,value);
                break;
            case "bt":
                field = FilterUtils.toBoundFilter(dimension,value,true,"bt",false,false);
                break;
            case "in":
                JSONArray inValues = JSONArray.parseArray(value);
                field = FilterUtils.toInFilter(dimension,inValues);
                break;
            case "se":
                field = FilterUtils.toSelectorFilter(dimension,value);
                break;
            default:
                break;
        }
        return field;
    }

    public static JSONObject toSearchFilter(String dimension, String value){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dimension",dimension);
        jsonObject.put("type","search");
        JSONObject query = new JSONObject();
        query.put("type","insensitive_contains");
        query.put("value",value);
        jsonObject.put("query",query);
        return jsonObject;
    }

    public static JSONObject toRegexFilter(String dimension,String patternValue){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dimension",dimension);
        jsonObject.put("type","regex");
        jsonObject.put("pattern",patternValue);
        return jsonObject;
    }

    public static JSONObject toSelectorFilter(String dimension,String value){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dimension",dimension);
        jsonObject.put("type","selector");
        jsonObject.put("value",value);
        return jsonObject;
    }

    public static JSONObject toInFilter(String dimension,JSONArray values){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dimension",dimension);
        jsonObject.put("type","in");
        jsonObject.put("values",values);
        return jsonObject;
    }

    public static JSONObject toBoundFilter(String dimension,String values,boolean alphaNumric,
                                            String boundType, boolean lowerStrict,boolean upperStrict){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dimension",dimension);
        jsonObject.put("type","bound");
        if(alphaNumric){//数字
            jsonObject.put("alphaNumeric",true);
            if(lowerStrict){//true为大于；默认大于或者等于
                jsonObject.put("lowerStrict",true);
            }
            if(upperStrict){//true为小于；默认小于或者等于
                jsonObject.put("upperStrict",true);
            }
        }
        setBoundValues(jsonObject,values,boundType);
        return jsonObject;
    }

    private static void setBoundValues(JSONObject jsonObject,String values,String boundType){
        String[] boundValue = values.split(";");
        switch (boundType){
            case "lt"://大于
                jsonObject.put("lower",boundValue[0]);
                break;
            case "gt"://小于
                jsonObject.put("upper",boundValue[0]);
                break;
            case "bt"://在...之间
                jsonObject.put("lower",boundValue[0]);
                jsonObject.put("upper",boundValue[1]);
                break;
        }
    }

    public static JSONObject toLogicFilter(String logic,JSONArray fieldArray){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",logic);
        jsonObject.put("fields",fieldArray);
        return jsonObject;
    }
}
