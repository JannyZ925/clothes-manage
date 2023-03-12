<%--
  Created by IntelliJ IDEA.
  User: Janny.Zhang
  Date: 2022/6/6
  Time: 8:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/main.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>

<body>
<nav id="top_bar_wrapper">
    <nav id="top_bar">
        <a href="#">服装库存管理系统</a>

        <div class="info">
            <h3>
                <i class="iconfont">&#xe62c;</i>
                <span>${USER_SESSION.name}</span>
            </h3>
            <h3>
                <a href="${pageContext.request.contextPath}/user/logout">注销</a>
            </h3>
        </div>
    </nav>
</nav>


<div class="main">
    <ul class="left_nav">
        <li><a href="${pageContext.request.contextPath}/product/search">货品信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/stock/searchCome">入库信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/stock/searchStockOut">出库信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/warehouse/ShowWarehouse">仓库管理</a></li>
    </ul>
</div>
</body>
</html>
