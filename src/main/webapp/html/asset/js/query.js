var mysqlQueryParam = {
    "dataSource":"ims_chr",
    "dimensions":["count","date_time"],
    "granularity": "day",
    "baseFilter":{
        "CHR Type":["36","37","38","39","53"],
        "neType":["CSCF","PSBC","MTELAS"],
        "neName":["NJPSBC11BHW","NJMTELAS11BHW","NJCSCF13BHW"],
        "dateTime":{"startTime":"2019-10-31 00:00:00","endTime":"2019-12-07 23:00:00"}
    },
    "conditionFilter":[
        {
            "logic":"and",
            "condition": [
                {
                    "dimension": "ne_name",
                    "type": "string",
                    "logic": "no",
                    "relat": "se",
                    "value":"BHW"
                },
                {
                    "dimension": "ne_type",
                    "type": "string",
                    "logic": "and",
                    "relat": "in",
                    "value":"[\"PSBC\",\"CSCF\",\"MTELAS\"]"
                }
            ]
        },
        {
            "logic":"and",
            "condition": [
                {
                    "dimension": "ne_type",
                    "type": "string",
                    "logic": "no",
                    "relat": "se",
                    "value":"MTELAS"
                },
                {
                    "dimension": "sip_status_code",
                    "type": "string",
                    "logic": "or",
                    "relat": "el",
                    "value":"500"
                }
            ]
        }
    ],
    "groupArray":[
        {
            "dimension": "date_time",
            "direction": "desc"
        }
    ],
    "limit":10
};

var druidQueryParam = {
    "dataSource":"IMS_CHR",
    "dimensions":["dateTime"],
    "granularity": "hour",
    "baseFilter":{
        "CHR Type":["36","37","38","39","53"],
        "neType":["CSCF","PSBC"],
        "neName":["NJPSBC12BHW","NJCSCF12BHW","NJCSCF10BHW"],
        "dateTime":{"startTime":"2019-11-31 00:00:00","endTime":"2019-12-07 00:00:00"}
    },
    "conditionFilter":[
        {
            "logic":"no",
            "condition": [
                {
                    "dimension": "neName",
                    "type": "string",
                    "logic": "no",
                    "relat": "se",
                    "value":"BHW"
                },
                {
                    "dimension": "Callee IMPU",
                    "type": "string",
                    "logic": "and",
                    "relat": "se",
                    "value":"152"
                }
            ]
        },
        {
            "logic":"and",
            "condition": [
                {
                    "dimension": "CHT Type",
                    "type": "string",
                    "logic": "no",
                    "relat": "in",
                    "value":"[\"37\",\"38\",\"39\"]"
                },
                {
                    "dimension": "Sip Status Code",
                    "type": "string",
                    "logic": "or",
                    "relat": "lt",
                    "value":"500"
                },
                {
                    "dimension": "Internal Error Code",
                    "type": "string",
                    "logic": "and",
                    "relat": "lt",
                    "value":"100005"
                }
            ]
        },
        {
            "logic":"or",
            "condition": [
                {
                    "dimension": "neId",
                    "type": "string",
                    "logic": "no",
                    "relat": "bt",
                    "value":"000001;000999"
                }
            ]
        }
    ],
    "groupArray":[
        {
            "dimension": "dateTime",
            "direction": "asc"
        }
    ],
    "limit":100
};

