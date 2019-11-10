package com.kaisn.druid;

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
        String dimension = param.getDimensions();
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
}
