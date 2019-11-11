package com.kaisn.druid;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kaisn.utils.StringUtils;

public class FilterUtils {

    private static final String FILTER_TYPE_SELECTOR = "selector";

    private static final String FILTER_TYPE_BOUND = "bound";

    private static final String FILTER_TYPE_IN = "in";

    private static final String FILTER_TYPE_SEARCH = "search";

    private static final String FILTER_TYPE_REGEX = "regex";

    private static final String SEARCH_TYPE_INSENSITIVE_CONTAINS = "insensitive_contains";

    private static final String SEARCH_TYPE_FRAGMENT = "fragment";

    private static final String SEARCH_TYPE_CONTAINS = "contains";

    private static final String SEARCH_TYPE_REGEX = "regex";

    public static JSONObject switchFilter(String type,String dimension,String value){
        JSONObject field = null;
        switch (type){
            case "eq":
                field = FilterUtils.toSelectorFilter(dimension,value);
                break;
            case "bt":
                field = FilterUtils.toBoundFilter(dimension,value,true,"bt",false,false);
                break;
            case "lt":
                field = FilterUtils.toBoundFilter(dimension,value,true,"lt",false,false);
                break;
            case "gt":
                field = FilterUtils.toBoundFilter(dimension,value,true,"gt",false,false);
                break;
            case "in":
                JSONArray inValues = JSONArray.parseArray(value);
                field = FilterUtils.toInFilter(dimension,inValues);
                break;
            case "sic":
                field = FilterUtils.toSearchFilter(dimension,value,SEARCH_TYPE_INSENSITIVE_CONTAINS);
                break;
            case "sft":
                field = FilterUtils.toSearchFilter(dimension,value,SEARCH_TYPE_FRAGMENT);
                break;
            default:
                break;
        }
        return field;
    }

    public static JSONObject toSearchFilter(String dimension, String value,String searchType){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dimension",dimension);
        jsonObject.put("type",FILTER_TYPE_SEARCH);
        JSONObject query = new JSONObject();
        query.put("type",searchType);
        if(StringUtils.equals(searchType,SEARCH_TYPE_FRAGMENT)){
            query.put("values",value.split(";"));
            query.put("case_sensitive",true);
        }else{
            query.put("value",value);
        }
        jsonObject.put("query",query);
        return jsonObject;
    }

    public static JSONObject toRegexFilter(String dimension,String patternValue){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dimension",dimension);
        jsonObject.put("type",FILTER_TYPE_REGEX);
        jsonObject.put("pattern",patternValue);
        return jsonObject;
    }

    public static JSONObject toSelectorFilter(String dimension,String value){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dimension",dimension);
        jsonObject.put("type",FILTER_TYPE_SELECTOR);
        jsonObject.put("value",value);
        return jsonObject;
    }

    public static JSONObject toInFilter(String dimension,JSONArray values){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dimension",dimension);
        jsonObject.put("type",FILTER_TYPE_IN);
        jsonObject.put("values",values);
        return jsonObject;
    }

    public static JSONObject toBoundFilter(String dimension,String values,boolean alphaNumric,
                                            String boundType, boolean lowerStrict,boolean upperStrict){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dimension",dimension);
        jsonObject.put("type",FILTER_TYPE_BOUND);
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
