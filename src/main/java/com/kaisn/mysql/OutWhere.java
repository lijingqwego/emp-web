package com.kaisn.mysql;

import java.util.List;

public class OutWhere {

    private String logic;

    private List<InWhere> inWhereList;

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic.toUpperCase();
    }

    public List<InWhere> getInWhereList() {
        return inWhereList;
    }

    public void setInWhereList(List<InWhere> inWhereList) {
        this.inWhereList = inWhereList;
    }

}
