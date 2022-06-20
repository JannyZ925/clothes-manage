<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login/login.css">
</head>

<body>
<form id="loginFrame" action="${pageContext.request.contextPath}/user/login" method="post">

    <h2>登录</h2>
    <%--  提示信息 --%>
    <c:if test="${msg!=null}">
        <span>${msg}</span>
    </c:if>
    <div>
        <i>用户名：</i><input type="text" name="name" placeholder="请输入用户名">
    </div>
    <div>
        <i>密&nbsp;&nbsp;码：</i><input type="password" name="password" placeholder="请输入密码">
    </div>
    <button type="submit"/>登录
</form>
</body>

</html>