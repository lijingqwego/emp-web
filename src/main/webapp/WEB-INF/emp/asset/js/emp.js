layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function(){
    var laydate = layui.laydate //日期
        ,laypage = layui.laypage //分页
        ,layer = layui.layer //弹层
        ,table = layui.table //表格
        ,element = layui.element //元素操作

    //向世界问个好
    //layer.msg('Hello World');

    //监听Tab切换
    element.on('tab(demo)', function(data){
        layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
            tips: 1
        });
    });

    //执行一个 table 实例
    table.render({
        elem: '#demo'
        ,height: 420
        ,url: '/emp-web/emp/list' //数据接口
        ,method:'post'
        ,parseData: function(res) { //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                //"count": res.total, //解析数据长度
                "data": res.extend.list //解析数据列表
            }
        }
        ,title: '员工表'
        ,page: true //开启分页
        ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        ,cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'empId', title: 'ID', width:80, sort: true}
            ,{field: 'empName', title: '用户名', width:80}
            ,{field: 'gender', title: '性别', width:80}
            ,{field: 'birth', title: '出生日期', width: 150, sort: true}
            ,{field: 'email', title: '邮箱', width: 180}
            ,{field: 'address', title: '家庭住址', width:180}
            ,{field: 'descText', title: '描述', width: 200}
            ,{fixed: 'right', width: 165, align:'center', toolbar: '#barDemo'}
        ]]
    });

    //监听头工具栏事件
    table.on('toolbar(test)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        switch(obj.event){
            case 'add':
                addItem();
                //layer.msg('添加');
                break;
            case 'update':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else if(data.length > 1){
                    layer.msg('只能同时编辑一个');
                } else {
                    //layer.alert('编辑 [id]：'+ checkStatus.data[0].id);
                }
                break;
            case 'delete':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    layer.msg('删除');
                }
                break;
        };
    });

    layui.use('laydate', function(){
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#birth' //指定元素
        });
    });

    //监听行工具事件
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail'){
            layer.msg('查看操作');
        } else if(layEvent === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
                delItem(data.empId);
            });
        } else if(layEvent === 'edit'){
            //layer.msg('编辑操作');
            itemInfo(data);
        }
    });

    //分页
    laypage.render({
        elem: 'pageDemo' //分页容器的id
        ,count: 100 //总页数
        ,skin: '#1E9FFF' //自定义选中色值
        //,skip: true //开启跳页
        ,jump: function(obj, first){
            if(!first){
                layer.msg('第'+ obj.curr +'页', {offset: 'b'});
            }
        }
    });
});

function addItem() {
    layer.open({
        type: 2,
        area: ['600px', '400px'],
        shadeClose: false, //点击遮罩关闭
        content: "/emp-web/page/addEmpItem",
        btn:['提交','取消'],
        yes:function(index,layero){
            var body = top.layer.getChildFrame('body',index);
            $.ajax({
                url:"/emp-web/emp/add",
                type:'post',
                data:{
                    'empName':body.find("#empName").val(),
                    'gender':body.find("input[type='radio']:checked").val(),
                    'birth':body.find("#birth").val(),
                    'email':body.find("#email").val(),
                    'address':body.find("#address").val(),
                    'descText':body.find("#descText").val()
                },
                dataType:'json',
                success:function(data){
                    if (data.code == 0) {
                        layer.msg(data.msg,{
                            icon:1,
                            time:1000
                        },function(){
                            parent.layer.close(index)
                        });
                    } else if (data.code == 500) {
                        layer.msg(data.msg,{
                            icon:2,
                            time:1000
                        },function(){});
                    }
                }
            });
        }
    });
}

function delItem(id){
    var param={};
    param.empId=id;
    $.ajax({
        url: "/emp-web/emp/del",
        async:false,
        type:"POST",
        data:param,
        success:function(res){
            console.log(res);
        }
    });
}

function itemInfo(data) {

    layer.open({
        type: 2,    //iframe层
        area: ['600px', '400px'],
        fix: false, //不固定
        btn: ['确认', '取消'],//弹出层按钮
        maxmin: true,
        shadeClose: true,
        shade: 0.4,
        title: "修改员工信息",
        offset: '50px',
        content: "/emp-web/page/addEmpItem",
        success: function (layero, index) {//弹出层打开后的回调函数
            var body = layer.getChildFrame('body', index);//获取弹出层的dom元素
            result = JSON.stringify(data);
            result = $.parseJSON(result);
            $.each(result, function (item) {
                if (item == 'empId'){
                    body.find('#empId').val(result[item]);//给弹出层页面赋值，id为对应弹出层表单id
                } else if (item == 'empName') {
                    body.find('#empName').val(result[item]);//这里是为动态select赋值，在弹出层创建隐藏元素
                } else if (item == 'gender') {
                    body.find("input[name='gender']").each(function(index, element) {
                        //console.log($(this).val()+"<=====>"+result[item])
                        if($(this).val()==result[item]){
                            //$(this).prop("checked",true);
                            //console.log("33333")
                            body.find($(this)).prop("checked",true);
                        }
                    });
                } else if (item == 'birth') {
                    body.find('#birth').val(result[item]);
                }else if (item == 'email') {
                    body.find('#email').val(result[item]);
                }else if (item == 'address') {
                    body.find('#address').val(result[item]);
                }else if (item == 'descTest') {
                    body.find('#descText').val(result[item]);
                }
            });
        },
        yes:function(index,layero){
            var body = top.layer.getChildFrame('body',index);
            $.ajax({
                url:"/emp-web/emp/upd",
                type:'post',
                data:{
                    'empId':body.find("#empId").val(),
                    'empName':body.find("#empName").val(),
                    'gender':body.find("input[type='radio']:checked").val(),
                    'birth':body.find("#birth").val(),
                    'email':body.find("#email").val(),
                    'address':body.find("#address").val(),
                    'descText':body.find("#descText").val()
                },
                dataType:'json',
                success:function(data){
                    if (data.code == 0) {
                        layer.msg(data.msg,{
                            icon:1,
                            time:1000
                        },function(){
                            parent.layer.close(index)
                        });
                    } else if (data.code == 500) {
                        layer.msg(data.msg,{
                            icon:2,
                            time:1000
                        },function(){});
                    }
                }
            });
            return false;
        }
    });
}
