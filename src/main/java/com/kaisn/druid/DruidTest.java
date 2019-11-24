package com.kaisn.druid;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Map;

public class DruidTest {

	public static void main(String[] args) throws IOException {

		String queryType = QueryContant.QUERY_TYPE_TOPN;

		String values = ResourceReaderUtil.loadData("druid/query-values.json");

		Map<String, Object> resultMap = QueryEntrance.queryDruid(queryType,values);

		System.out.println(JSON.toJSONString(resultMap));
	}



}
