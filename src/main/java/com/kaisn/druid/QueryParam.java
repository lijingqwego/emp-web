package com.kaisn.druid;

import com.alibaba.fastjson.JSONObject;

public class QueryParam {

    private String queryType;

    private String dataSource;

    private String descending;

    private String dimensions;

    private String filter;

    private String granularity;

    private String intervals;

    private int threshold;

    private int limit;

    private JSONObject pagingIdentifiers;

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDescending() {
        return descending;
    }

    public void setDescending(String descending) {
        this.descending = descending;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public String getIntervals() {
        return intervals;
    }

    public void setIntervals(String intervals) {
        this.intervals = intervals;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public JSONObject getPagingIdentifiers() {
        return pagingIdentifiers;
    }

    public void setPagingIdentifiers(JSONObject pagingIdentifiers) {
        this.pagingIdentifiers = pagingIdentifiers;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
