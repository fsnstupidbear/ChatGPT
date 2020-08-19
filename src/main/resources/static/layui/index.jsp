<%@page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="./layui/css/layui.css">
    <script src="../resources"></script>
</head>
<body>
<script>
    //一般直接写在一个js文件中
    layui.use(['layer', 'form'], function(){
        var layer = layui.layer
            ,form = layui.form;

        layer.msg('Hello World');
    });
</script>
<a href="mainPage.jsp" class="layui-btn">点击进入列表</a>
</body>
</html>
