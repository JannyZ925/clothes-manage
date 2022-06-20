<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Janny.Zhang
  Date: 2022/6/5
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/user.css">
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

    <%--    <ul class="left_nav">--%>
    <%--        <li><a href="#">用户管理</a></li>--%>
    <%--    </ul>--%>

    <div class="user_manage">
        <h3>用户管理</h3>
        <div class="add_and_search">
            <button>新增</button>
            <form action="${pageContext.request.contextPath}/user/search" method="post">
                <c:if test="${msg != null}">
                    <span>${msg}</span>
                </c:if>
                用户名：<input type="text" name="name" value="${user.name}">
                <button type="submit">查询</button>
            </form>
        </div>

        <table class="user_info">
            <thead>
            <th>用户名</th>
            <th>密码</th>
            <th>角色</th>
            <th>操作</th>
            </thead>

            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.password}</td>
                    <td>${user.role}</td>
                    <td>
                        <button onclick="findUserByName('${user.name}')">编辑</button>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>


<div id="editUser">
<form>
    <h3>用户信息</h3>
    <table>
        <tr>
            <td>用户名：</td>
            <td><input type="text" readonly name="name" id="user_name"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="text" name="password" id="user_pw"></td>
        </tr>
        <tr>
            <td>用户角色：</td>
            <td>
                <select name="role" id="user_role">
                    <option value="SUPER ADMIN">SUPER ADMIN</option>
                    <option value="ADMIN">ADMIN</option>
                    <option value="USER">USER</option>
                </select>
            </td>
        </tr>
    </table>
</form>
    <div class="footer">
        <button>保存</button>
        <button onclick="closeFrame('editUser')">关闭</button>
    </div>
</div>

</body>

</html>
