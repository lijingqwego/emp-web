<%--
  Created by IntelliJ IDEA.
  User: lijjing
  Date: 2019/12/7
  Time: 上午9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
</head>
<body>
    <div class="main_content_horizontaladdCondition_content_height">
        <div class="main_content_symbol_position"><label class="fontStyle">组合条件</label>
            <lable class="horizontal_symbolfontStyle">:</lable>
        </div>
        <div class="conditionStyle" onclick="addConditionGroup(this);">
            <label class="addSym">+</label>
            <label class="addSymTxt">Condition Group</label>
        </div>
    </div>
    <div class="table_boxgroupTable groupTableEnStyle"></div>

    <div id="chartmain" style="width:600px; height: 400px;"></div>

    <script type="text/javascript" src="../statis/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="../statis/js/echarts.min.js"></script>
    <script type="text/javascript" src="asset/js/query.js"></script>
    <script type="text/javascript" src="asset/js/visualization.js"></script>
    <script type="text/javascript" src="asset/js/message_zh.js"></script>
    <script type="text/javascript" src="asset/js/index.js"></script>
    <script type="text/css" src="asset/css/index.css"></script>
</body>
</html>

