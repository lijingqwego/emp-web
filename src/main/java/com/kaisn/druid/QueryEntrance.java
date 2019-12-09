package com.kaisn.druid;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QueryEntrance {

    public static Map<String, Object> queryDruid(String queryType,String values){
        Map<String, Object> resultMap = null;
        QueryParam queryParam = createQueryParam(values, queryType);
        if(StringUtils.equals(queryType,QueryContant.QUERY_TYPE_SELECT)){
            resultMap = selectTableData(queryParam);
        }else{
            resultMap = selectCountData(queryParam);
        }
        return resultMap;
    }

    private static Map<String,Object> selectTableData(QueryParam param){
        Map<String, Object> resultMap = new HashMap<>();
        //查询列表
        String selectJsonParam = QueryToStructUtils.createSelectJsonParam(param);
        String tableResult = postWithJson(QueryContant.QUERY_DRUID_URL, selectJsonParam);
        JSONArray tableReturnResult = QueryToResultUtils.getTableResult(tableResult);
        JSONObject pagingIdentifiers = QueryToResultUtils.getPagingIdentifiers(tableResult);
        resultMap.put("data",tableReturnResult);
        resultMap.put("pagingIdentifiers",pagingIdentifiers);
        //查询总数
        param.setDimensions(StringUtils.EMPTY);
        param.setQueryType(QueryContant.QUERY_TYPE_GROUPBY);
        String countQueryParam = QueryToStructUtils.createGroupByJsonParam(param);
        String countResult = postWithJson(QueryContant.QUERY_DRUID_URL, countQueryParam);
        int totalResult = QueryToResultUtils.getTotalResult(countResult);
        resultMap.put("total",totalResult);
        return resultMap;
    }

    private static Map<String,Object> selectCountData(QueryParam param){
        Map<String, Object> resultMap = new HashMap<>();
        String topNJsonParam = QueryToStructUtils.createTopNJsonParam(param);
        String result = postWithJson(QueryContant.QUERY_DRUID_URL, topNJsonParam);
        JSONArray countResult = QueryToResultUtils.getCountResult(result,param.getGranularity());
        resultMap.put("data",countResult);
        return resultMap;
    }

    private static QueryParam createQueryParam(String values,String queryType){

        String dataSourceStruct = QueryToParamUtils.getDataSourceStruct(values);
        String dimensions = QueryToParamUtils.getDimenstionStruct(values);
        String granularityStruct = QueryToParamUtils.getGranularityStruct(values);
        String filterStruct = QueryContant.QUERY_FILTER_KEY + QueryToParamUtils.toFilterStruct(values);
        String intervalsStruct = QueryToParamUtils.toIntervalsStruct(values);
        int threshold = QueryToParamUtils.getlimitStruct(values);

        QueryParam queryParam = new QueryParam();
        queryParam.setDataSource(dataSourceStruct);
        queryParam.setGranularity(granularityStruct);
        if(StringUtils.equals(queryType,QueryContant.QUERY_TYPE_TOPN)){
            JSONArray jsonArray = JSONArray.parseArray(dimensions);
            String firstDimension = jsonArray.getString(0);
            queryParam.setDimensions(firstDimension);
        }else{
            queryParam.setDimensions(dimensions);
        }
        queryParam.setFilter(filterStruct);
        queryParam.setIntervals(intervalsStruct);
        queryParam.setThreshold(threshold);
        queryParam.setQueryType(queryType);

        return queryParam;
    }

    private static String postWithJson(String url, String json) {
        String returnValue = "request failed.";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            StringEntity requestEntity = new StringEntity(json,"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);
            returnValue = httpClient.execute(httpPost,responseHandler);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }
}
