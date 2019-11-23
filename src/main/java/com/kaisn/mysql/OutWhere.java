package com.kaisn.mysql;

import java.util.List;

public class OutWhere {

    private String logic;

    private InWhere inWhere;

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public InWhere getInWhere() {
        return inWhere;
    }

    public void setInWhere(InWhere inWhere) {
        this.inWhere = inWhere;
    }

    class InWhere {
        private String logic;

        private String column;

        private String relat;

        private String value;

        private List<String> valueList;

        public String getLogic() {
            return logic;
        }

        public void setLogic(String logic) {
            this.logic = logic;
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
}
