package com.kaisn.mysql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kaisn.druid.FilterUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryToStructUtils {

    public static final String RELAT_EQ = "eq";

    public static final String RELAT_LT = "lt";

    public static final String RELAT_GT = "gt";

    public static final String RELAT_EL = "el";

    public static final String RELAT_EG = "eg";

    public static final String RELAT_BT = "bt";

    public static final String RELAT_IN = "in";

    public static final String RELAT_SE = "se";

    public static final Map<String,String> MQL_RELAT_MAP = new HashMap<String,String>();

    static {
        MQL_RELAT_MAP.put(RELAT_EQ,"=");
        MQL_RELAT_MAP.put(RELAT_LT,">");
        MQL_RELAT_MAP.put(RELAT_GT,"<");
        MQL_RELAT_MAP.put(RELAT_EL,">=");
        MQL_RELAT_MAP.put(RELAT_EG,"<=");
        MQL_RELAT_MAP.put(RELAT_BT,"between");
        MQL_RELAT_MAP.put(RELAT_IN,"in");
        MQL_RELAT_MAP.put(RELAT_SE,"like");
    }

    public static QueryParam createQueryParam(String values){
        QueryParam queryParam = new QueryParam();
        JSONObject jsonObject = JSONObject.parseObject(values);
        //table
        String dataSource = jsonObject.getString("dataSource");
        queryParam.setTableName(dataSource);
        //column
        JSONArray dimensions = jsonObject.getJSONArray("dimensions");
        List<String> columnList = JSONObject.parseArray(dimensions.toJSONString(), String.class);
        queryParam.setColumnList(columnList);
        //where
        List<OutWhere> outWhereList = new ArrayList<>();
        JSONObject baseFilter = jsonObject.getJSONObject("baseFilter");
        if(baseFilter != null && !baseFilter.isEmpty()){
            OutWhere baseWhere = toBaseFilter(baseFilter);
            outWhereList.add(baseWhere);
        }
        JSONArray conditionFilter = jsonObject.getJSONArray("conditionFilter");
        if(CollectionUtils.isNotEmpty(conditionFilter)){
            List<OutWhere> conditionWhere = toConditionFilter(conditionFilter);
            outWhereList.addAll(conditionWhere);
        }
        queryParam.setOutWhereList(outWhereList);
        //group by
        JSONArray groupArray = jsonObject.getJSONArray("groupArray");
        List<GroupBy> groupByList = toGroupBy(groupArray);
        queryParam.setGroupByList(groupByList);
        String limit = jsonObject.getString("limit");
        queryParam.setRows(Integer.parseInt(limit));
        return queryParam;
    }

    private static OutWhere toBaseFilter(JSONObject baseFilter) {
        OutWhere outWhere = new OutWhere();
        JSONArray chrTypeValues = baseFilter.getJSONArray("CHR Type");
        JSONArray neTypeValues = baseFilter.getJSONArray("neType");
        JSONArray neNameValues = baseFilter.getJSONArray("neName");
        List<InWhere> inWhereList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(chrTypeValues)){
            InWhere chrType = toWhere(StringUtils.EMPTY,"chr_type", chrTypeValues);
            inWhereList.add(chrType);
        }
        if(CollectionUtils.isNotEmpty(neTypeValues)){
            InWhere neType = toWhere("and","ne_type", neTypeValues);
            inWhereList.add(neType);

        }
        if(CollectionUtils.isNotEmpty(neNameValues)){
            InWhere neType = toWhere("and","ne_name", neNameValues);
            inWhereList.add(neType);
        }
        JSONObject dateTimeObject = baseFilter.getJSONObject("dateTime");
        if(dateTimeObject != null && !dateTimeObject.isEmpty()){
            String startTime = dateTimeObject.getString("startTime");
            String endTime = dateTimeObject.getString("endTime");
            InWhere dateTime = toWhere("and", "date_time", RELAT_BT, startTime + ";" + endTime);
            inWhereList.add(dateTime);
        }
        outWhere.setLogic("and");
        outWhere.setInWhereList(inWhereList);
        return outWhere;
    }

    private static List<OutWhere> toConditionFilter(JSONArray conditionFilter) {
        List<OutWhere> outWhereList = new ArrayList<>();
        for (int i = 0; i < conditionFilter.size(); i++) {
            OutWhere outWhere = new OutWhere();
            JSONObject outConditionArray = conditionFilter.getJSONObject(i);
            String outLogic = outConditionArray.getString("logic");
            outLogic = StringUtils.equals("no",outLogic)? StringUtils.EMPTY:outLogic;
            JSONArray inConditionArray = outConditionArray.getJSONArray("condition");
            List<InWhere> inWhereList = new ArrayList<>();
            for (int j = 0; j < inConditionArray.size(); j++) {
                JSONObject condition = inConditionArray.getJSONObject(j);
                String dimension = condition.getString("dimension");
                String logic = condition.getString("logic");
                logic = StringUtils.equals("no",logic)? StringUtils.EMPTY:logic;
                String relat = condition.getString("relat");
                String value = condition.getString("value");
                InWhere inWhere = toWhere(logic,dimension,relat,value);
                inWhereList.add(inWhere);
            }
            outWhere.setLogic(outLogic);
            outWhere.setInWhereList(inWhereList);
            outWhereList.add(outWhere);
        }
        return outWhereList;
    }

    private static List<GroupBy> toGroupBy(JSONArray groupArray) {
        List<GroupBy> groupByList = new ArrayList<>();
        for (int i = 0; i < groupArray.size(); i++) {
            GroupBy groupBy = new GroupBy();
            JSONObject groupObject = groupArray.getJSONObject(i);
            String dimension = groupObject.getString("dimension");
            String direction = groupObject.getString("direction");
            groupBy.setColumn(dimension);
            groupBy.setSort(direction);
            groupByList.add(groupBy);
        }
        return groupByList;
    }

    private static InWhere toWhere(String logic, String dimension, String relat, String value) {
        InWhere inWhere = new InWhere();
        inWhere.setLogic(logic);
        inWhere.setColumn(dimension);
        inWhere.setRelat(MQL_RELAT_MAP.get(relat));
        if(StringUtils.equals(FilterUtils.RELAT_BT,relat)){
            String[] values = value.split(";");
            inWhere.setLowerValue(values[0]);
            inWhere.setUpperValue(values[1]);
        }else if(StringUtils.equals(FilterUtils.RELAT_IN,relat)){
            List<String> valueList = JSONArray.parseArray(value, String.class);
            inWhere.setValueList(valueList);
        }else{
            inWhere.setValue(value);
        }
        return inWhere;
    }

    private static InWhere toWhere(String logic,String dimension,JSONArray valueArray) {
        InWhere inWhere = new InWhere();
        inWhere.setLogic(logic);
        inWhere.setColumn(dimension);
        inWhere.setRelat(RELAT_IN);
        List<String> valueList = JSONArray.parseArray(valueArray.toJSONString(), String.class);
        inWhere.setValueList(valueList);
        return inWhere;
    }
}
