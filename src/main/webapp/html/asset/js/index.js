var tableNum = new Date().getTime();
var conditionArr = [];
var filterFieldList = [];//Filter Field list option in the combined condition table
var ralationShipNameArr = [{"name": "AND", "value": "and"}, {"name": 'OR', "value": "or"}];//0is and,1is or
var operationList = [];
var complexDataStructureObject = {
    values:{
        essence:{
            conditionArray:[]
        }
    }
}
/***Symbolic option for string type*/
var stringOperationList = [
    {
        operationKey: 1,
        operationName: CHRMsg.Equal
    },
    {
        operationKey: 2,
        operationName: CHRMsg.Notequalto
    },
    {
        operationKey: 3,
        operationName: CHRMsg.Include
    }
];
/***Symbolic option for number type*/
var numberOperationList = [
    {
        operationKey: 1,
        operationName: CHRMsg.Equal
    },
    {
        operationKey: 12,
        operationName: CHRMsg.MoreThan
    },
    {
        operationKey: 11,
        operationName: CHRMsg.LessThan
    },
    {
        operationKey: 2,
        operationName: CHRMsg.Notequalto
    }
];
/***Symbolic option for date type*/
var dateOperationList = [
    {
        operationKey: 12,
        operationName: CHRMsg.MoreThan
    },
    {
        operationKey: 11,
        operationName: CHRMsg.LessThan
    }
];
/***init*@returns*/
$(document).ready(function () {
    initConditionTable();
});

/***init condition table by neType or chrType or moveToRight or moveToLeft*@returns*/
function initConditionTable() {
    conditionArr = [];
    $(".main_content.groupTable").html("");
    $(".main_content.groupTable").append(initAddTable(tableNum));
    $("#group_condition_box_" + tableNum + "").remove();
    var obj = {"id": tableNum, "op": "and", "condition": []};
    conditionArr.push(obj);
    complexDataStructureObject.values.essence.conditionArray = conditionArr;
}

/***Get selected select options*@returns*/
function getFilterFiledList() {
    var FilterFiledArr = [];
    var getConditionArr = complexDataStructureObject.values.essence.condition;
    for (var i = 0; i < getConditionArr.length; i++) {
        var getConditionObj = getConditionArr[i];
        for (var keyCondition in getConditionObj) {
            var innerConditionArr = getConditionObj[keyCondition];
            for (var j = 0; j < innerConditionArr.length; j++) {
                if (innerConditionArr[j]["isSelect"] == true) {
                    var obj = {
                        cn_Name: innerConditionArr[j]["cn_Name"],
                        dimension: innerConditionArr[j]["dimension"],
                        direction: innerConditionArr[j]["direction"],
                        en_Name: innerConditionArr[j]["en_Name"],
                        setType: innerConditionArr[j]["type"],
                        isSelect: true,
                    };
                    FilterFiledArr.push(obj);
                }
            }
        }
    };
    FilterFiledArr = arrayUnique2(FilterFiledArr, "dimension");
    return FilterFiledArr;
}

/***Initialize the combined condition table to empty*@param tableNum*@returns*/
function initAddTable(tableNum) {
    var strTable = '';
    strTable += '<div class="group_condition_boxclearfix"data-num="' + tableNum + '"id="group_condition_box_' + tableNum + '">';
    strTable += '<div class="group_condition_lebel_boxfl"><span>Group RelationShip</span></div>';
    strTable += '<div class="select-box select-box-groupCondition pr fl">';
    strTable += '<select size="1"class="groupRelationShip"onchange="groupRelationShipChange(this);"data-tablenum="' + tableNum + '">';
    for (var i = 0; i < ralationShipNameArr.length; i++) {
        strTable += '<option value="' + ralationShipNameArr[i]["value"] + '">' + ralationShipNameArr[i]["name"] + '</option>';
    }
    strTable += '</select>';
    strTable += '</div>';
    strTable += '<div class="group_condition_img_box"data-num="' + tableNum + '"id="deleteGroupTable_' + tableNum + '"onclick="deleteGroupTable(this);">';
    strTable += '<img src="../assets/images/rules_delete.png"alt="">';
    strTable += '</div>';
    strTable += '</div>';
    strTable += '<table id="tableId_' + tableNum + '"data-tablenum=' + tableNum + '>';
    strTable += '<thead>';
    strTable += '<tr>';
    strTable += '<td><div class="checkbox"><input autocomplete="off"type="checkbox"class="input_check"><label id="checkboxAll_' + tableNum + '"onclick="checkboxAll(this);"></label></div></td>';
    strTable += '<td>Relationship</td>';
    strTable += '<td>Filter Filed</td>';
    strTable += '<td>Operation</td>';
    strTable += '<td>Value</td>';
    strTable += '<td>';
    strTable += '<div class="icon_btn_box">';
    strTable += '<a href="javascript:void(0)"class="deleteBtn"onclick="deleteCheckedRows(this);"><img src="../assets/images/rules_delete.png"></a>';
    strTable += '<a href="javascript:void(0)"class="addBtn"onclick="addRow(this);"><img src="../assets/images/rules_add.png"></a>';
    strTable += '</div>';
    strTable += '</td>';
    strTable += '</tr>';
    strTable += '</thead>';
    strTable += '<tbody><tr class="odd"><td colspan="6"class="dataTables_empty">' + CHRMsg.DataIsNotAvailable + '</td></tr></tbody>';
    strTable += '</table>';
    return strTable;
}

/***Click on the combination condition to add a form*@param getDataRowNum*@returns*/
function setTables(getDataRowNum) {
    if (filterFieldList[0]["setType"] == "string") {
        operationList = stringOperationList;
    } else if (filterFieldList[0]["setType"] == "number") {
        operationList = numberOperationList;
    } else if (filterFieldList[0]["setType"] == "datetime") {
        operationList = dateOperationList;
    }
    if (filterFieldList.length == 0) {
        warnings(CHRMsg.NoFilteredFieldsAvailable);
        return;
    }
    var str = '';
    str += '<tr rowId=' + getDataRowNum + '>';
    /***box*/str += '<td><div class="checkbox"><input autocomplete="off"type="checkbox"class="input_check"><label onclick="checkboxSingle(this);"></label></div></td>';
    /***Relationship drop down*/str += '<td>';
    str += '<div>';
    str += '<select class="andOrClass"id="andOr_' + getDataRowNum + '"onchange="groupRowAndOrChange(this);">';
    for (var i = 0; i < ralationShipNameArr.length; i++) {
        str += '<option value="' + ralationShipNameArr[i]["value"] + '">' + ralationShipNameArr[i]["name"] + '</option>';
    }
    str += '</select>';
    str += '</div>';
    str += '</td>';
    /***Filter Filed drop down*/str += '<td>';
    str += '<div>';
    str += '<select class="filterFieldListClass"id="filterFieldList_' + getDataRowNum + '"onchange="groupRowFilterFieldChange(this);">';
    for (var i = 0; i < filterFieldList.length; i++) {
        str += '<option value="' + filterFieldList[i]["dimension"] + '"data-type="' + filterFieldList[i]["setType"] + '">' + filterFieldList[i]["dimension"] + '</option>';
    }
    str += '</div>';
    str += '</td>';
    /***greater than less than the symbol pull down*/str += '<td>';
    str += '<div>';
    str += '<select class="symbolListClass"id="symbolList_' + getDataRowNum + '"onchange="groupRowSymbolChange(this);">';
    for (var i = 0; i < operationList.length; i++) {
        str += '<option value="' + operationList[i]["operationKey"] + '">' + operationList[i]["operationName"] + '</option>';
    }
    str += '</div>';
    str += '</td>';
    /***Value text box or date box*/str += '<td>';
    str += '<div>';
    if (filterFieldList[0]["setType"] == "string" || filterFieldList[0]["setType"] == "number") {
        str += '<input class="filterValue_input"type="text"id="filterValueInput_' + getDataRowNum + '"onkeyup="onKeyUpChangeValue(this);"placeholder="' + CHRMsg.Enter + '"autocomplete="off">';
        str += '<input class="filter_form_datetime"style="display:none;"readonly type="text"id="filterFormDatetime_' + getDataRowNum + '"placeholder="' + CHRMsg.Enter + '"autocomplete="off">';
    }
    ;
    if (filterFieldList[0]["setType"] == "datetime") {
        str += '<input class="filterValue_input"style="display:none;"type="text"id="filterValueInput_' + getDataRowNum + '"onkeyup="onKeyUpChangeValue(this);"placeholder="' + CHRMsg.Enter + '"autocomplete="off">';
        str += '<input class="filter_form_datetime"readonly type="text"id="filterFormDatetime_' + getDataRowNum + '"placeholder="' + CHRMsg.Enter + '"autocomplete="off">';
    }
    str += '</div>';
    str += '</td>';
    str += '<td><a href="javascript:void(0)"class="deleteBtn"onclick="deleteRow(this);"><img src="../assets/images/rules_delete.png"></a></td>';
    str += '</tr>';
    return str;
}


/***The outermost layer increases the combination condition,and can only add up to5large combination conditions.*@param el*@returns*/
function addConditionGroup(el) {
    tableNum++;
    var obj = {"id": tableNum, "op": "and", "condition": []};
    if (conditionArr.length < 5) {
        $(".main_content.groupTable").append(initAddTable(tableNum));
        select2AddGroupEvent(tableNum);
        conditionArr.push(obj);
        complexDataStructureObject.values.essence.conditionArray = conditionArr;
    } else {
        warnings(CHRMsg.MaximumOfFiveCombinedConditions);
    }
}

/***Initialize select2 drop-down*@param tableNum*@returns*/
function select2AddGroupEvent(tableNum) {
    $('.groupRelationShip').select2({placeholder: CHRMsg.PleaseSelect, minimumResultsForSearch: -1});
}

/***Outer AND OR switching method for combined conditions*@param_this*@returns*/
function groupRelationShipChange(_this) {
    var thisBtn = _this;
    var getDataTableNum = $(thisBtn).attr("data-tablenum");
    var getChangeValue = $(thisBtn).find("option:selected").val();
    /***How many tables are there in the outer layer combination of the traversal combination condition*/
    for (var i = 0; i < conditionArr.length; i++) {
        if (conditionArr[i]["id"] == getDataTableNum) {
            conditionArr[i]["op"] = getChangeValue;
        }
    }
    ;complexDataStructureObject.values.essence.conditionArray = conditionArr
}

/***Combine conditions to add row events to a single table--initialization options*@param el*@returns*/
function addRow(el) {
    if (filterFieldList.length == 0) {
        warnings(CHRMsg.NoFilteredFieldsAvailable);
        return;
    }
    var getDataTableNum = $(el).closest("table").attr("data-tablenum");
    var timeStamp = new Date().getTime() + '';
    var getDataRowNum = timeStamp.substring(timeStamp.length - 8);
    getDataRowNum++;
    for (var i = 0; i < conditionArr.length; i++) {
        if (conditionArr[i]["id"] == getDataTableNum) {
            var conditionInnerArr = conditionArr[i]["condition"];
            select2AddGroupRowEvent(getDataTableNum, getDataRowNum, conditionInnerArr);
            //init event
        }
    }
    initGroupFirstOpToNull();
}

/***Initialize the first and or empty*@returns*/
function initGroupFirstOpToNull() {
    conditionArr = complexDataStructureObject.values.essence.conditionArray;
    $(".groupTable table").each(function (i) {
        $(".groupTable").find("table").eq(i).find("tbody tr").find("td").eq(1).find("select").remove();
        $(".groupTable").find("table").eq(i).find("tbody tr").find("td").eq(1).find(".select2").remove();
    });
    if (conditionArr.length != 0) {
        $.each(conditionArr, function (i, item) {
            conditionArr[0].op = "";
            if (item.condition.length != 0) {
                item.condition[0].op = "";
            }
        });
    }
}

/***Combine conditions to add row events to a single table--initialization options*@param getDataTableNum*@param getDataRowNum*@param conditionInnerArr*@returns*/
function select2AddGroupRowEvent(getDataTableNum, getDataRowNum, conditionInnerArr) {
    /***If you add the first one,first clear the contents of tbody*/
    if (conditionInnerArr.length == 0) {
        $("#tableId_" + getDataTableNum + "").find("tbody").html("");
    }
    /***Can only add up to10inline combinations*/
    if (conditionInnerArr.length < 10) {
        $("#tableId_" + getDataTableNum + "").find("tbody").append(setTables(getDataRowNum));
    } else {
        warnings(CHRMsg.MaximumOfTenCombinedConditions);
        return;
    }
    /***Combined condition--and or*/
    var andOrKey = $('#andOr_' + getDataRowNum + '').find("option:selected").val();
    /***Combined condition--Relationship*/
    $('.andOrClass').select2({
        placeholder: CHRMsg.PleaseSelect,
        minimumResultsForSearch: -1
    });
    /***Combined condition---Filter Filed*/
    var filterFieldKey = $('#filterFieldList_' + getDataRowNum + '').find("option:selected").val();
    var filterFieldType = $('#filterFieldList_' + getDataRowNum + '').find("option:selected").attr("data-type");
    $('.filterFieldListClass').select2({
        placeholder: CHRMsg.PleaseSelect,
        minimumResultsForSearch: -1
    }).on("select2:open", function (e) {
        /***Open the select drop-down box to get the selected value*add title*/
        $("#select2-filterFieldList_" + getDataRowNum + "-results").on("mouseover", "li", function () {
            $(this).attr("title", $(this).text());
        });
    });
    /***Combined condition---Operation*/
    var symbolKey = $('#symbolList_' + getDataRowNum + '').find("option:selected").val();
    $('.symbolListClass').select2({
        placeholder: CHRMsg.PleaseSelect,
        minimumResultsForSearch: -1
    }).on("select2:open", function (e) {
        /***Open the select drop-down box to get the selected value*add title*/
        $("#select2-symbolList_" + getDataRowNum + "-results").on("mouseover", "li", function () {
            $(this).attr("title", $(this).text());
        });
    });
    ;var obj = {
        "id": getDataRowNum,
        "op": andOrKey,
        "dimension": filterFieldKey,
        "setType": filterFieldType,
        "rule": symbolKey,
        "value": ""
    };
    conditionInnerArr.push(obj);
    initTableDateTime();
}


/***Initialize the time plugin in the table*@returns*/
function initTableDateTime() {
    $(".main_content.groupTable.filter_form_datetime").datetimepicker({
        language: timeLanage,
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        minView: 0,
        maxview: 1,
        forceParse: 0,
        format: 'yyyy-mm-ddhh:ii:ss'
    }).on('changeDate', function () {
        var thisBtn = this;
        var getDataTableNum = $(thisBtn).closest("table").attr("data-tablenum");
        var getDataTableRowNum = $(thisBtn).closest("tr").attr("rowid");
        var getChangeValue = $("#filterFormDatetime_" + getDataTableRowNum + "").val();
        /***How many tables are there in the outer layer combination of the traversal combination condition*/
        for (var i = 0; i < conditionArr.length; i++) {
            if (conditionArr[i]["id"] == getDataTableNum) {
                var conditionInnerArr = conditionArr[i]["condition"];
                /***Traversing the row elements in the table*@param_this*@returns*/
                for (var j = 0; j < conditionInnerArr.length; j++) {
                    if (conditionInnerArr[j]['id'] == getDataTableRowNum) {
                        conditionInnerArr[j].value = getChangeValue;
                    }
                }
            }
        };
        complexDataStructureObject.values.essence.conditionArray = conditionArr
    });
}

/***Inner layer AND OR switching method for combined conditions*@param_this*@returns*/
function groupRowAndOrChange(_this) {
    var thisBtn = _this;
    commonChange(thisBtn, "op");
}

/***Shared code used for line switching within combined conditions*@param thisBtn*@param conditionInnerArrKey*@returns*/
function commonChange(thisBtn, conditionInnerArrKey) {
    var getDataTableNum = $(thisBtn).closest("table").attr("data-tablenum");
    var getDataTableRowNum = $(thisBtn).closest("tr").attr("rowid");
    var getChangeValue = '';
    if (conditionInnerArrKey == "op" || conditionInnerArrKey == "rule") {
        getChangeValue = $(thisBtn).find("option:selected").val();
    }
    ;
    if (conditionInnerArrKey == "value") {
        getChangeValue = $("#filterValueInput_" + getDataTableRowNum + "").val();
    };
    /***How many tables are there in the outer layer combination of the traversal combination condition*/
    var conditionInnerArr = [];
    for (var i = 0; i < conditionArr.length; i++) {
        if (conditionArr[i]["id"] == getDataTableNum) {
            conditionInnerArr = conditionArr[i]["condition"];
        }
    };
    /***(Performance optimization)If the following code is placed in the above for loop,it will affect the performance,and the website may crash directly when rendering.*Minimize the number of nested layers to improve page performance*/
    for (var j = 0; j < conditionInnerArr.length; j++) {
        //Traversing the row elements in the table
        if (conditionInnerArr[j]['id'] == getDataTableRowNum) {
            conditionInnerArr[j][conditionInnerArrKey] = getChangeValue;
            if (conditionInnerArrKey == "dimension") {
                /***In the case of editing,because the data selected by the pull-down is an onchange event,and the field drop-down box and value are bound to the input box and date madness,editing the specified drop-down selection will cause the data to be emptied.*The data structure will also change,so re-assign the field field and value to the structure.*/
                var getType = $(thisBtn).find("option:selected").attr("data-type");
                conditionInnerArr[j]["setType"] = getType;
                conditionInnerArr[j]["dimension"] = $(thisBtn).closest("tr").find("td").eq(2).find(".filterFieldListClass").find("option:selected").val();
                /***Pass stringOperationList,operationList,operationList to operationList according to the type of field*Make sure to switch the field for operation drop-down according to the type reset drop-down option*/
                if (getType == "string") {
                    conditionInnerArr[j].value = $(thisBtn).closest("tr").find("td").eq(4).find(".filterValue_input").val();
                    operationList = stringOperationList;
                } else if (getType == "number") {
                    conditionInnerArr[j].value = $(thisBtn).closest("tr").find("td").eq(4).find(".filterValue_input").val();
                    operationList = numberOperationList;
                } else if (getType == "datetime") {
                    initDateTime();
                    //Initialize event option
                    conditionInnerArr[j].value = $(thisBtn).closest("tr").find("td").eq(4).find(".filter_form_datetime").val();
                    operationList = dateOperationList;
                }
                /***The following is a reset of the symbol drop-down selection,because onchange will trigger the onchange by default.When the field is selected by default,the corresponding operation should be selected according to the type of field.*/
                $("#symbolList_" + getDataTableRowNum + "").html("");
                var str = '';
                for (var i = 0; i < operationList.length; i++) {
                    str += '<option value="' + operationList[i]["operationKey"] + '">' + operationList[i]["operationName"] + '</option>';
                }
                $("#symbolList_" + getDataTableRowNum + "").append(str);
                $("#symbolList_" + getDataTableRowNum + "").val(conditionInnerArr[j]["rule"]).trigger("change");
                var newoperation = $(thisBtn).closest("tr").find("td").eq(3).find(".symbolListClass").find("option:selected").val();
                /***When switching Field,if the symbol drop-down does not have the symbol value corresponding to the database return field,the first one is selected by default,and the first parameter value is returned to the structure again.*/
                if (newoperation == undefined || newoperation == "undefined") {
                    $("#symbolList_" + getDataTableRowNum + ":first").prop('selected', 'selected');
                    $("#symbolList_" + getDataTableRowNum + "").find('option').eq(0).attr("selected", "selected");
                    newoperation = $(thisBtn).closest("tr").find("td").eq(3).find(".symbolListClass").find("option:selected").val();
                }
                conditionInnerArr[j].rule = newoperation;
            };
        }
    };
    complexDataStructureObject.values.essence.conditionArray = conditionArr;
}


/***Inner layer Filter Filed switching method for combined conditions*@param_this*@returns*/
function groupRowFilterFieldChange(_this) {
    var thisBtn = _this;
    var filterFiled = $(thisBtn).find("option:selected").attr("data-type");
    commonChange(thisBtn, "dimension");
    var getDataTableRowNum = $(thisBtn).closest("tr").attr("rowid");
    var getChangeValue = $(thisBtn).find("option:selected").val();
    if (filterFiled == "datetime") {
        $("#filterValueInput_" + getDataTableRowNum + "").hide();
        $("#filterFormDatetime_" + getDataTableRowNum + "").show();
        initTableDateTime();
    } else {
        $("#filterFormDatetime_" + getDataTableRowNum + "").hide();
        $("#filterValueInput_" + getDataTableRowNum + "").show();
    }
}

/***Inner layer symbol switching method of combined condition*@param_this*@returns*/
function groupRowSymbolChange(_this) {
    var thisBtn = _this;
    commonChange(thisBtn, "rule");
}

/***If the type of the Filter Filed field is a text box,the keyboard event of the text box*@param_this*@returns*/function onKeyUpChangeValue(_this) {
    var thisBtn = _this;
    commonChange(thisBtn, "value");
}

/***Delete the combined condition in the line*@param_this*@returns*/
function deleteRow(_this) {
    var thisBtn = _this;
    var getDataTableNum = $(thisBtn).closest("table").attr("data-tablenum");
    var getDataTableRowNum = $(thisBtn).closest("tr").attr("rowid");
    /***How many tables are there in the outer layer combination of the traversal combination condition*/
    for (var i = 0; i < conditionArr.length; i++) {
        if (conditionArr[i]["id"] == getDataTableNum) {
            var conditionInnerArr = conditionArr[i]["condition"];
            /***Traversing the row elements in the table*/
            for (var j = 0; j < conditionInnerArr.length; j++) {
                if (conditionInnerArr[j]['id'] == getDataTableRowNum) {
                    conditionInnerArr.splice(j, 1);
                    $(thisBtn).closest("tr").remove();
                    /***Add an empty dom element if the deletion is empty*/
                    if (conditionInnerArr.length == 0) {
                        var str = '';
                        str += '<tr class="odd"><td colspan="6"class="dataTables_empty">' + CHRMsg.DataIsNotAvailable + '</td></tr>';
                        $("#tableId_" + getDataTableNum + "").find("tbody").html("");
                        $("#tableId_" + getDataTableNum + "").find("tbody").append(str);
                    }
                }
            }
        }
    }
    ;complexDataStructureObject.values.essence.conditionArray = conditionArr;
    initGroupFirstOpToNull();
}

/***Delete rows in bulk*@param_this*@returns*/
function deleteCheckedRows(_this) {
    var thisBtn = _this;
    var getDataTableNum = $(thisBtn).closest("table").attr("data-tablenum");
    var groupRowDeleteArr = [];
    var getTableTr = $("#tableId_" + getDataTableNum + "").find("tbody tr");
    for (var i = 0; i < getTableTr.length; i++) {
        if ($(getTableTr).eq(i).find("td").eq(0).find("label").hasClass("selectCheckBox")) {
            var deleteIdArr = $(getTableTr).eq(i).attr("rowid");
            groupRowDeleteArr.push(deleteIdArr);
            $(getTableTr).eq(i).remove();
            //delete
        }
    };
    /***How many tables are there in the outer layer combination of the traversal combination condition*/
    for (var i = 0; i < conditionArr.length; i++) {
        if (conditionArr[i]["id"] == getDataTableNum) {
            var conditionInnerArr = conditionArr[i]["condition"];
            /***How many tables are there in the outer layer combination of the traversal combination condition*/
            for (var j = 0; j < conditionInnerArr.length; j++) {
                for (var t = 0; t < groupRowDeleteArr.length; t++) {
                    if (conditionInnerArr[j]['id'] == groupRowDeleteArr[t]) {
                        conditionInnerArr.splice(j, 1);
                        /***Add an empty dom element if the deletion is empty*/
                        if (conditionInnerArr.length == 0) {
                            var str = '';
                            str += '<tr class="odd"><td colspan="6"class="dataTables_empty">' + CHRMsg.DataIsNotAvailable + '</td></tr>';
                            $("#tableId_" + getDataTableNum + "").find("tbody").html("");
                            $("#tableId_" + getDataTableNum + "").find("tbody").append(str);
                        }
                    }
                }
            }
        }
    };
    $("#checkboxAll_" + getDataTableNum + "").removeClass("selectCheckBox");
    complexDataStructureObject.values.essence.conditionArray = conditionArr;
    initGroupFirstOpToNull();
}

/***Delete row*@param_this*@returns*/
function deleteGroupTable(_this) {
    var thisBtn = _this;
    var getTableDeleteNum = $(thisBtn).attr("data-num");
    for (var i = 0; i < conditionArr.length; i++) {
        if (conditionArr[i]["id"] == getTableDeleteNum) {
            conditionArr.splice(i, 1);
            $(thisBtn).closest(".group_condition_box").remove();
            $("#tableId_" + getTableDeleteNum + "").remove();
        }
    }
    ;complexDataStructureObject.values.essence.conditionArray = conditionArr;
};

/***Select all box---Select all--Cancel all selection*@param_this*@returns*/
function checkboxAll(_this) {
    var thisBtn = _this;
    if ($(thisBtn).hasClass("selectCheckBox")) {
        $(thisBtn).removeClass("selectCheckBox");
        $(thisBtn).closest("table").find("tbody tr").each(function (i) {
            $(thisBtn).closest("table").find("tbody tr").eq(i).find("td").eq(0).find("label").removeClass("selectCheckBox");
        })
    } else {
        $(thisBtn).addClass("selectCheckBox");
        $(thisBtn).closest("table").find("tbody tr").each(function (i) {
            $(thisBtn).closest("table").find("tbody tr").eq(i).find("td").eq(0).find("label").addClass("selectCheckBox");
        })
    }
}

/***Radio box---check---uncheck*@param_this*@returns*/
function checkboxSingle(_this) {
    var thisBtn = _this;
    var getDataTableNum = $(thisBtn).closest("table").attr("data-tablenum");
    if ($(thisBtn).hasClass("selectCheckBox")) {
        $(thisBtn).removeClass("selectCheckBox");
    } else {
        $(thisBtn).addClass("selectCheckBox");
    }
}

/***Click CHR type,NE type,move to the right,move to the left to perform condition group table reset initialization status*@returns*/
function conditionalStructureChange() {
    filterFieldList = getFilterFiledList();
    initConditionTable();
}


/***render condition group*@returns*/
//Rendering of combined conditions
function rendConditionGroup() {
    filterFieldList = getFilterFiledList();
    conditionArr = complexDataStructureObject.values.essence.conditionArray;
    $(".main_content.table_box.groupTable").html("");
    for (var i = 0; i < conditionArr.length; i++) {
        $(".main_content.table_box.groupTable").append(initAddTable(conditionArr[i]["id"]));
        $("#group_condition_box_" + conditionArr[0]["id"] + "").remove();
        $("#group_condition_box_" + conditionArr[i]["id"] + "").find(".groupRelationShip").val(conditionArr[i]["op"]).trigger("change");
        select2AddGroupEvent(conditionArr[i]["id"]);
        var conditionInnerArr = conditionArr[i]["condition"];
        $("#tableId_" + conditionArr[i]["id"] + "").find("tbody").html("");
        for (var j = 0; j < conditionInnerArr.length; j++) {
            var getDataRowNum = conditionInnerArr[j]["id"];
            if (conditionInnerArr.length <= 10) {
                $("#tableId_" + conditionArr[i]["id"] + "").find("tbody").append(setTables(getDataRowNum));
                //Combined condition--Relationship
                $('.andOrClass').select2({placeholder: CHRMsg.PleaseSelect, minimumResultsForSearch: -1});
                $("#andOr_" + getDataRowNum + "").val(conditionInnerArr[j]["op"]).trigger("change");
                if (conditionInnerArr[j]["setType"] == "string") {
                    $("#filterValueInput_" + getDataRowNum + "").val(conditionInnerArr[j]["value"]);
                } else if (conditionInnerArr[j]["setType"] == "number") {
                    $("#filterValueInput_" + getDataRowNum + "").val(conditionInnerArr[j]["value"]);
                } else if (conditionInnerArr[j]["setType"] == "datetime") {
                    $("#filterFormDatetime_" + getDataRowNum + "").val(conditionInnerArr[j]["value"]);
                }
                $('.filterFieldListClass').select2({
                    placeholder: CHRMsg.PleaseSelect,
                    minimumResultsForSearch: -1
                }).on("select2:open", function (e) {
                    $(".select2-results__options").on("mouseover", "li", function () {
                        $(this).attr("title", $(this).text());
                    });
                });
                $("#filterFieldList_" + getDataRowNum + "").val(conditionInnerArr[j]["dimension"]).trigger("change");
                $('.symbolListClass').select2({
                    placeholder: CHRMsg.PleaseSelect,
                    minimumResultsForSearch: -1
                }).on("select2:open", function (e) {
                    $(".select2-results__options").on("mouseover", "li", function () {
                        $(this).attr("title", $(this).text());
                    });
                })
                $("#symbolList_" + getDataRowNum + "").val(conditionInnerArr[j]["rule"]).trigger("change");
            } else {
                warnings(CHRMsg.MaximumOfTenCombinedConditions);
                return;
            }
        }
    }
    ;
    for (var i = 0; i < conditionArr.length; i++) {
        if (conditionArr[i]["condition"].length == 0) {
            var str = '';
            str += '<tr class="odd"><td colspan="6"class="dataTables_empty">' + CHRMsg.DataIsNotAvailable + '</td></tr>';
            $("#tableId_" + conditionArr[i]["id"] + "").find("tbody").html("");
            $("#tableId_" + conditionArr[i]["id"] + "").find("tbody").append(str);
        }
    }
    ;initGroupFirstOpToNull();
}

/***Array object deduplication according to a certain attribute*Array object format*Convert object elements to strings for comparison*@param obj*@param keys*@returns*/
function arrayUnique2(arr, key) {
    var hash = {};
    returnarr.reduce(function (item, next) {
        hash[next[key]] ? '' : hash[next[key]] = true && item.push(next);
        return item;
    }, []);
}
