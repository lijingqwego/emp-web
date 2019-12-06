package com.kaisn.mysql;

import java.util.List;

public class InWhere {

    private String logic;

    private String column;

    private String relat;

    private String value;

    private String lowerValue;

    private String upperValue;

    private List<String> valueList;

    public String getLowerValue() {
        return lowerValue;
    }

    public void setLowerValue(String lowerValue) {
        this.lowerValue = lowerValue;
    }

    public String getUpperValue() {
        return upperValue;
    }

    public void setUpperValue(String upperValue) {
        this.upperValue = upperValue;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic.toUpperCase();
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getRelat() {
        return relat;
    }

    public void setRelat(String relat) {
        this.relat = relat;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }
}
