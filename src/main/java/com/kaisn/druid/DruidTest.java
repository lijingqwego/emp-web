package com.kaisn.druid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kaisn.utils.StringUtils;

import java.io.IOException;
import java.util.Map;

public class DruidTest {

	public static void main(String[] args) throws IOException {

		String queryType = QueryContant.QUERY_TYPE_TOPN;

		Map<String, Object> resultMap = queryDruid(queryType);

		System.out.println(JSON.toJSONString(resultMap));
	}

	private static Map<String, Object> queryDruid(String queryType){

		Map<String, Object> resultMap = null;

		String values = ResourceReaderUtil.loadData("druid/query-values.json");

		QueryParam queryParam = QueryEntrance.createQueryParam(values, queryType);

		if(StringUtils.equals(queryType,QueryContant.QUERY_TYPE_SELECT)){
			resultMap = QueryEntrance.selectTableData(queryParam);
		}else{
			resultMap = QueryEntrance.selectCountData(queryParam);
		}
		return resultMap;
	}

}
