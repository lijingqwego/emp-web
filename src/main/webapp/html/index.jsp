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

    <script type="text/javascript" src="asset/js/query.js"></script>
    <script type="text/javascript" src="../statis/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="../statis/js/echarts.min.js"></script>
    <script>
        var myChart = echarts.init(document.getElementById('chartmain'));
        // 显示标题，图例和空的坐标轴
        myChart.setOption({
            title : {
                text : 'CHR统计'
            },
            tooltip : {
                trigger: 'axis', //坐标轴触发提示框，多用于柱状、折线图中
            },
            legend : {
                data : [ '总数' ]
            },
            toolbox: {
                feature: {
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            xAxis : {
                type : 'category',
                data : []
            },
            yAxis : {},
            series : [ {
                name : '销量',
                type : 'bar',
                data : []
            } ]
        });

        myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画

        var names = [];     //类别数组（实际用来盛放X轴坐标值）
        var nums = [];       //销量数组（实际用来盛放Y坐标值）

        var param = {};
        param.values=JSON.stringify(mysqlQueryParam);
        $.ajax({
            type : "post",
            async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url : "${pageContext.request.contextPath }/emp/query",
            data : param,
            dataType : "json",
            success : function(data) {
                //请求成功时执行该函数内容，result即为服务器返回的json对象
                if (data.code == 0) {
                    //请求成功时执行该函数内容，result即为服务器返回的json对象
                    $.each(data.extend.list, function (index, item) {
                        names.push(item.ne_name);//挨个取出类别并填入类别数组
                        nums.push(item.count);
                    });
                    myChart.hideLoading(); //隐藏加载动画
                    myChart.setOption({ //加载数据图表
                        xAxis : {
                            data : names
                        },
                        series : [ {
                            // 根据名字对应到相应的系列
                            name : '总数',
                            data : nums
                        } ]
                    });
                }else{
                    alert("后台数据获取失败!");
                }
            },
            error : function(errorMsg) {
                //请求失败时执行该函数
                alert("图表请求数据失败!");
                myChart.hideLoading();
            }
        });
    </script>


    <script type="text/javascript" src="asset/js/message_zh.js"></script>
    <script type="text/javascript" src="asset/js/index.js"></script>
    <script type="text/css" src="asset/css/index.css"></script>
</body>
</html>

