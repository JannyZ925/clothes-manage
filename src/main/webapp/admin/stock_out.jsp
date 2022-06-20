<%--
  Created by IntelliJ IDEA.
  User: Janny.Zhang
  Date: 2022/6/10
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>出库信息管理</title>
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
        <h3>出库信息管理</h3>
        <div class="add_and_search">
            <c:if test="${USER_SESSION.role=='ADMIN'}">
                <button onclick="openFrame('addStockOut')">新增</button>
            </c:if>
            <form action="${pageContext.request.contextPath}/stock/searchStockOut" method="get">
                单据编号：<input type="text" name="id" value="${search.id}">
                经办人：<input type="text" name="user_name" value="${search.user_name}">
                日期：<input type="date" name="date" value="${search.strDate}">
                仓库：<select name="warehouse" id="warehouse">
                <option></option>
                <c:forEach items="${warehouseList}" var="warehouse">
                    <option value="${warehouse.id}" ${search.warehouse eq warehouse.id ? "selected" : ""}>
                            ${warehouse.name}
                    </option>
                </c:forEach>
            </select>
                <button type="submit">查询</button>
            </form>

        </div>

        <table class="stock_info">
            <thead>
            <th>单据编号</th>
            <th>经办人</th>
            <th>日期</th>
            <th>仓库</th>
            <th>备注</th>
            <th>去向</th>
            <c:if test="${USER_SESSION.role=='ADMIN'}">
                <th>操作</th>
            </c:if>
            </thead>

            <tbody>
            <c:forEach items="${pageResult.rows}" var="stock">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/stockItem/findStockItemsByStockId?stock_id=${stock.id}">${stock.id}</a>
                    </td>
                    <td>${stock.user_name}</td>
                    <td>${stock.strDate}</td>
                    <td>${stock.wareName}</td>
                    <td>${stock.remarks}</td>
                    <td>${stock.origin_or_whereabouts}</td>
                    <c:if test="${USER_SESSION.role=='ADMIN'}">
                        <td>
                            <button onclick="findStockOutById(${stock.id})">编辑</button>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>

            </tbody>
        </table>
        <div id="pagination" class="pagination"></div>
    </div>
</div>

<div id="addStockOut">
    <form id="addStockOutForm">
        <h3>添加出库单</h3>
        <table>
            <tr>
                <td>经办人：</td>
                <td><input type="text" name="user_name" value="${USER_SESSION.name}" readonly></td>
            </tr>
            <tr>
                <td>日期：</td>
                <td><input type="date" name="date"></td>
            </tr>
            <tr>
                <td>所属仓库：</td>
                <td>
                    <select name="warehouse">
                        <c:forEach items="${warehouseList}" var="warehouse">
                            <option value="${warehouse.id}">${warehouse.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>去向：</td>
                <td><input type="text" name="origin_or_whereabouts"></td>
            </tr>
            <tr>
                <td>备注：</td>
                <td><input type="text" name="remarks" id="remarks"></td>
            </tr>

        </table>
    </form>
    <div class="footer">
        <button onclick="addStockOut()">保存</button>
        <button onclick="closeFrame('addStockOut')">关闭</button>
    </div>
</div>

<div id="editStockOut">
    <form id="editStockOutForm">
        <h3>出库单信息</h3>
        <table>
            <tr>
                <td>单据编号：</td>
                <td><input type="text" name="id" id="stock_id" readonly></td>
            </tr>
            <tr>
                <td>经办人：</td>
                <td><input type="text" name="user_name" id="stock_name"></td>
            </tr>
            <tr>
                <td>出库日期：</td>
                <td><input type="date" name="date" id="stock_date"></td>
            </tr>
            <tr>
                <td>所属仓库：</td>
                <td>
                    <select name="warehouse">
                        <c:forEach items="${warehouseList}" var="warehouse">
                            <option value="${warehouse.id}">${warehouse.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>去向：</td>
                <td><input type="text" name="origin_or_whereabouts" id="stock_whereabouts"></td>
            </tr>
            <tr>
                <td>备注：</td>
                <td><input type="text" name="remarks" id="stock_remarks"></td>
            </tr>
        </table>
    </form>
    <div class="footer">
        <button onclick="editStockOut()">保存</button>
        <button onclick="closeFrame('editStockOut')">关闭</button>
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
    stockOutVO.id = "${search.id}"
    stockOutVO.user_name = "${search.user_name}"
    stockOutVO.date = "${search.date}"
    stockOutVO.warehouse = "${search.warehouse}"
    /*分页效果*/
    pagination(pageargs);
</script>

</html>
