<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/7
  Time: 13:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>新增员工信息</title>
    <link rel="stylesheet" href="../asset/css/layui.css">
    <script src="../asset/layui.js"></script>
    <script src="../asset/jquery-3.4.1.min.js"></script>
    <script src="../asset/js/emp.js"></script>
</head>
<body>
<form class="layui-form" action="">
    <input type="hidden" name="empId" id="empId" />
    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-block">
            <input type="text" name="empName" id="empName" maxlength="30" required  lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">性别</label>
        <div class="layui-input-block">
            <input type="radio" name="gender" value="男" title="男">
            <input type="radio" name="gender" value="女" title="女" checked>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">出生日期</label>
        <div class="layui-input-block">
            <input type="text" name="birth" id="birth" maxlength="30" required  lay-verify="date" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-block">
            <input type="email" name="email" id="email" maxlength="30" required  lay-verify="email" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">家庭住址</label>
        <div class="layui-input-block">
            <select name="address" id="address" lay-verify="required">
                <option value=""></option>
                <option value="北京">北京</option>
                <option value="上海">上海</option>
                <option value="广州">广州</option>
                <option value="深圳">深圳</option>
                <option value="杭州">杭州</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea name="descText" id="descText" placeholder="请输入内容" class="layui-textarea"></textarea>
        </div>
    </div>
</form>
</body>
</html>
