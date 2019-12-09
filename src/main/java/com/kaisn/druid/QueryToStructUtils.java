package com.kaisn.druid;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

public class QueryToStructUtils {

    public static String createGroupByJsonParam(QueryParam param) {
        String dataSource = param.getDataSource();
        String dimensions = param.getDimensions();
        if(StringUtils.isNotBlank(dimensions)){
            dimensions = dimensions+",";
        }
        String interval = param.getIntervals();
        String limit = String.valueOf(param.getLimit());
        String queryType = param.getQueryType();
        String json = ResourceReaderUtil.loadData("druid/template-"+queryType+".json");
        json = json.replace("%%dataSource%%", dataSource)
                .replace("%%dimensions%%", dimensions)
                .replace("%%intervals%%", interval)
                .replace("%%limit%%", limit)
                .replace("%%columns%%", dimensions);
        return json;
    }

    public static String createSelectJsonParam(QueryParam param) {
        String dataSource = param.getDataSource();
        String dimension = param.getDimensions();
        String filter = param.getFilter();
        String interval = param.getIntervals();
        String threshold = String.valueOf(param.getThreshold());
        String queryType = param.getQueryType();
        String json = ResourceReaderUtil.loadData("druid/template-"+queryType+".json");
        json = json.replace("%%dataSource%%", dataSource)
                .replace("%%dimensions%%", dimension)
                .replace("%%filter%%",filter)
                .replace("%%intervals%%", interval)
                .replace("%%threshold%%", threshold);
        return json;
    }

    public static String createTopNJsonParam(QueryParam param) {
        String dataSource = param.getDataSource();
        String granularity = param.getGranularity();
        String dimension = null;
        if(StringUtils.equals("day",granularity)){
            JSONObject jsonObject = toExtrDimension(param.getDimensions(), 10);
            dimension = JSONObject.toJSONString(jsonObject);
        } else if(StringUtils.equals("hour",granularity)){
            JSONObject jsonObject = toExtrDimension(param.getDimensions(), 13);
            dimension = JSONObject.toJSONString(jsonObject);
        }else{
            dimension = param.getDimensions();
        }
        String filter = param.getFilter();
        String interval = param.getIntervals();
        String threshold = String.valueOf(param.getThreshold());
        String queryType = param.getQueryType();
        String json = ResourceReaderUtil.loadData("druid/template-"+queryType+".json");
        json = json.replace("%%dataSource%%", dataSource)
                .replace("%%dimension%%", dimension)
                .replace("%%filter%%",filter)
                .replace("%%intervals%%", interval)
                .replace("%%threshold%%", threshold);
        return json;
    }

    private static JSONObject toExtrDimension(String dimension, int len){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","extraction");
        jsonObject.put("dimension",dimension);
        jsonObject.put("outputName",dimension);
        jsonObject.put("outputType","STRING");
        JSONObject extractionFn = new JSONObject();
        extractionFn.put("type","substring");
        extractionFn.put("index",0);
        extractionFn.put("length",len);
        jsonObject.put("extractionFn",extractionFn);
        return jsonObject;
    }
}
