package com.kaisn.druid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.util.Map;

public class DruidTest {

	public static void main(String[] args) throws IOException {

		String values = ResourceReaderUtil.loadData("druid/query-values.json");
		QueryParam queryParam = QueryEntrance.createQueryParam(values, QueryContant.QUERY_TYPE_TOPN);
//		Map<String, Object> resultMap = QueryEntrance.selectTableData(queryParam);
		Map<String, Object> resultMap = QueryEntrance.selectCountData(queryParam);
		System.out.println(JSON.toJSONString(resultMap));

	}

}
