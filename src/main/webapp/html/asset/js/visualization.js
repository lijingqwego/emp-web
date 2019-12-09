var nameList = []; //类别数组（实际用来盛放X轴坐标值）
var countList = []; //销量数组（实际用来盛放Y坐标值）

$(document).ready(function () {
    var chart = echarts.init(document.getElementById('chartmain'));
    initChart(chart);
});

function initChart(chart) {
    // 显示标题，图例和空的坐标轴
    chart.setOption({
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
    chart.showLoading(); //数据加载完之前先显示一段简单的loading动画
    var param = {};
    param.values = JSON.stringify(mysqlQueryParam);
    $.ajax({
        type : "post",
        async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url : "/emp-web/emp/query",
        data : param,
        dataType : "json",
        success : function(data) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (data.code == 0) {
                //请求成功时执行该函数内容，result即为服务器返回的json对象
                $.each(data.extend.list, function (index, item) {
                    nameList.push(item.time);//挨个取出类别并填入类别数组
                    countList.push(item.count);
                });
                chart.hideLoading(); //隐藏加载动画
                chart.setOption({ //加载数据图表
                    xAxis : {
                        data : nameList
                    },
                    series : [ {
                        // 根据名字对应到相应的系列
                        name : '总数',
                        data : countList
                    } ]
                });
            }else{
                alert("后台数据获取失败!");
            }
        },
        error : function(errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            chart.hideLoading();
        }
    });
}