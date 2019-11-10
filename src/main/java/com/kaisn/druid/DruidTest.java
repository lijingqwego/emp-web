package com.kaisn.druid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.util.Map;

public class DruidTest {

	public static void main(String[] args) throws IOException {

		String values = ResourceReaderUtil.loadData("druid/query-values.json");
		String dataSourceStruct = QueryToParamUtils.getDataSourceStruct(values);
		String dimensions = QueryToParamUtils.getDimenstionStruct(values);
		String filterStruct = QueryContant.QUERY_FILTER_KEY+ QueryToParamUtils.toFilterStruct(values);
		String intervalsStruct = QueryToParamUtils.toIntervalsStruct(values);
		int threshold = QueryToParamUtils.getlimitStruct(values);

		QueryParam queryParam = new QueryParam();
		queryParam.setDataSource(dataSourceStruct);
		JSONArray jsonArray = JSONArray.parseArray(dimensions);
		String firstDimension = jsonArray.getString(0);
		queryParam.setDimensions(firstDimension);
		queryParam.setFilter(filterStruct);
		queryParam.setIntervals(intervalsStruct);
		queryParam.setThreshold(threshold);
		queryParam.setQueryType(QueryContant.QUERY_TYPE_TOPN);
//		Map<String, Object> resultMap = QueryEntrance.selectTableData(queryParam);
		Map<String, Object> resultMap = QueryEntrance.selectCountData(queryParam);
		System.out.println(JSON.toJSONString(resultMap));

	}

}
