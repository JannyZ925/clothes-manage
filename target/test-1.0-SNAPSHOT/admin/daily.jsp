<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>查看日志</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/user.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>

<body class="hold-transition skin-red sidebar-mini">
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
        <li><a href="${pageContext.request.contextPath}/user/search">用户管理</a></li>
        <li><a href="${pageContext.request.contextPath}/daily/findAll">查看日志</a></li>
    </ul>

    <div class="user_manage">
        <h3 class="box-title">日志信息</h3>

        <!-- 数据列表 -->
        <div class="table-box">
            <!-- 数据表格 -->
            <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center user_info">
                <thead>
                <tr>
                    <th class="sorting_asc">序号</th>
                    <th class="sorting">用户名</th>
                    <th class="sorting">原始密码</th>
                    <th class="sorting">新密码</th>
                    <th class="sorting">修改日期</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pageResult.rows}" var="daily" varStatus="status">
                    <tr>
                        <td>${requestScope.offset+status.index+1}</td>
                        <td> ${daily.username}</td>
                        <td> ${daily.oldpassword}</td>
                        <td> ${daily.newpassword}</td>
                        <td> ${daily.date}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!--数据表格/-->
            <%--分页插件--%>
            <div id="pagination" class="pagination"></div>
        </div>
        <!--数据列表/-->
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
    /*分页效果*/
    pagination(pageargs);


</script>

</html>