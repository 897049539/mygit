<%--
  Created by IntelliJ IDEA.
  User: 89704
  Date: 2018/9/7
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>啊哦 出错了</title>
</head>
<body>
        <h1>您的访问出错了，${EX.msg}</h1>
        <span id="warn">${EX.warn}</span>
</body>
<script>
    window.onload=function () {
        var counter = 3;
        window.setInterval(function () {
            counter--;
            if(counter==0){
                window.location.href="${EX.url}";
                window.clearInterval();
            }
            document.getElementById("warn").innerHTML=counter+"秒后页面跳转";
        },1000);
    }

</script>
</html>
