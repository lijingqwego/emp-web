package com.kaisn.mysql;

import java.util.List;

public class QueryParam {

    private String tableName;

    private List<String> columnList;

    private List<OutWhere> outWhereList;

    private List<GroupBy> groupByList;

    private String granularity;

    private int offset;

    private int rows;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<String> columnList) {
        this.columnList = columnList;
    }

    public List<OutWhere> getOutWhereList() {
        return outWhereList;
    }

    public void setOutWhereList(List<OutWhere> outWhereList) {
        this.outWhereList = outWhereList;
    }

    public List<GroupBy> getGroupByList() {
        return groupByList;
    }

    public void setGroupByList(List<GroupBy> groupByList) {
        this.groupByList = groupByList;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
