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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/user.css">
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
        <li><a href="${pageContext.request.contextPath}/user/search">用户管理</a></li>
        <li><a href="${pageContext.request.contextPath}/daily/findAll">查看日志</a></li>
    </ul>

    <div class="user_manage">
        <h3>用户管理</h3>
        <div class="add_and_search">
            <button onclick="openFrame('addUser')">新增</button>
            <form action="${pageContext.request.contextPath}/user/search" method="post">
                用户名：<input type="text" name="name" value="${search.name}">
                <button type="submit">查询</button>
            </form>
        </div>

        <table class="user_info">
            <thead>
            <th>用户名</th>
            <th>密码</th>
            <th>角色</th>
            <th>在职状态</th>
            <th>操作</th>
            </thead>

            <tbody>
            <c:forEach items="${pageResult.rows}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.password}</td>
                    <td>${user.role}</td>
                    <td>${user.status}</td>
                    <td>
                        <c:if test="${user.status == '在职'}">
                            <button onclick="findUserByName('${user.name}')">编辑</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div id="pagination" class="pagination"></div>
    </div>
</div>


<div id="addUser">
    <form id="addUserForm">
        <h3>添加用户</h3>
        <table>
            <tr>
                <td>用户名：</td>
                <td><input type="text" name="name" id="addname"></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input type="text" name="password" id="addpw"></td>
            </tr>
            <tr>
                <td>用户角色：</td>
                <td>
                    <select name="role" id="addrole">
                        <option value="USER">其他部门用户</option>
                        <option value="ADMIN">普通管理员</option>
                        <option value="SUPER ADMIN">系统管理员</option>
                    </select>
                </td>
            </tr>
            <input type="hidden" name="status" id="addstatus" value="在职">
        </table>
    </form>
    <div class="footer">
        <button onclick="addUser()">保存</button>
        <button onclick="closeFrame('addUser')">关闭</button>
    </div>
</div>

<div id="editUser">
    <form id="editUserForm">
        <h3>用户信息</h3>
        <span><input type="hidden" id="user_oldpassword" name="oldpassword"></span>
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
                        <option value="USER">其他部门用户</option>
                        <option value="ADMIN">普通管理员</option>
                        <option value="SUPER ADMIN">系统管理员</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>在职状态：</td>
                <td>
                    <select name="status" id="user_status">
                        <option value="在职">在职</option>
                        <option value="离职">离职</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
    <div class="footer">
        <button onclick="editUser()">保存</button>
        <button onclick="closeFrame('editUser')">关闭</button>
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
    userVO.name = "${search.name}"
    /*分页效果*/
    pagination(pageargs);
</script>

</html>
