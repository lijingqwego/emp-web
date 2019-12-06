package com.kaisn.druid;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FilterUtils {

    private static final String FILTER_TYPE_SELECTOR = "selector";

    private static final String FILTER_TYPE_BOUND = "bound";

    private static final String FILTER_TYPE_IN = "in";

    private static final String FILTER_TYPE_SEARCH = "search";

    private static final String FILTER_TYPE_REGEX = "regex";

    private static final String SEARCH_TYPE_INSENSITIVE_CONTAINS = "insensitive_contains";

    public static final String RELAT_EQ = "eq";

    public static final String RELAT_LT = "lt";

    public static final String RELAT_GT = "gt";

    public static final String RELAT_EL = "el";

    public static final String RELAT_EG = "eg";

    public static final String RELAT_BT = "bt";

    public static final String RELAT_IN = "in";

    public static final String RELAT_SE = "se";

    public static JSONObject switchFilter(String type,String dimension,String value){
        JSONObject field = null;
        switch (type){
            case RELAT_EQ:
                field = toSelectorFilter(dimension,value);
                break;
            case RELAT_BT:
                field = toBoundFilter(dimension,value,true,RELAT_BT,false,false);
                break;
            case RELAT_LT:
                field = toBoundFilter(dimension,value,true,RELAT_LT,false,false);
                break;
            case RELAT_GT:
                field = toBoundFilter(dimension,value,true,RELAT_GT,false,false);
                break;
            case RELAT_EL:
                field = toBoundFilter(dimension,value,true,RELAT_EL,true,false);
                break;
            case RELAT_EG:
                field = toBoundFilter(dimension,value,true,RELAT_EG,true,true);
                break;
            case RELAT_IN:
                JSONArray inValues = JSONArray.parseArray(value);
                field = toInFilter(dimension,inValues);
                break;
            case RELAT_SE:
                field = toSearchFilter(dimension,value,SEARCH_TYPE_INSENSITIVE_CONTAINS);
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
        query.put("value",value);
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
            case RELAT_LT://大于
            case RELAT_EL://大于或等于
                jsonObject.put("lower",boundValue[0]);
                break;
            case RELAT_GT://小于
            case RELAT_EG://小于或等于
                jsonObject.put("upper",boundValue[0]);
                break;
            case RELAT_BT://在...之间
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
