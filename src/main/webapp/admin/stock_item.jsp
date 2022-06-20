<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Janny.Zhang
  Date: 2022/6/12
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>出库单明细</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/main.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
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

    <div class="stock_manage">
        <h3>出库单明细</h3>
        <div class="add_and_search">
            <c:if test="${USER_SESSION.role=='ADMIN'}">
                <a href="${pageContext.request.contextPath}/product/search?model=stock_out&stock_id=${stock_id}">
                    <button>新增</button>
                </a>
            </c:if>
            <span>单号：${stock_id}</span>
        </div>

        <table class="stock_info">
            <thead>
            <th>货号</th>
            <th>货品名</th>
            <th>颜色</th>
            <th>尺码</th>
            <th>库存</th>
            <th>出库数量</th>
            </thead>

            <tbody>
            <c:forEach items="${pageResult.rows}" var="stock_item">
                <tr>
                    <td>${stock_item.product.number}</td>
                    <td>${stock_item.product.name}</td>
                    <td>${stock_item.product.color}</td>
                    <td>${stock_item.product.size}</td>
                    <td>${stock_item.product.stock}</td>
                    <td>${stock_item.amount}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div id="pagination" class="pagination"></div>
    </div>
</div>

</body>

<script>
    /*分页插件展示的总页数*/
    pageargs.total = Math.ceil(${pageResult.total}/pageargs.pagesize);
    /*分页插件当前的页码*/
    pageargs.cur = ${pageNum}
    /*分页插件页码变化时将跳转到的服务器端的路径*/
    pageargs.gourl = "${gourl}"
    /*保存搜索框中的搜索条件，页码变化时携带之前的搜索条件*/
    stockOutItemVO.stock_id = "${stock_id}"
    /*分页效果*/
    pagination(pageargs);
</script>
</html>
