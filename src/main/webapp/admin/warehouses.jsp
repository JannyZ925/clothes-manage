<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>仓库管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/main.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/warehouse.js"></script>
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
        <li><a href="${pageContext.request.contextPath}/product/search">货品信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/stock/searchCome">入库信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/stock/searchStockOut">出库信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/warehouse/ShowWarehouse">仓库管理</a></li>
    </ul>

    <div class="stock_manage">

        <h3 class="box-title">仓库信息</h3>
        <div class="box-body">
            <div class="add_and_search">

                <%--新增按钮：如果当前登录用户是管理员，页面展示新增按钮--%>
                <c:if test="${USER_SESSION.role =='ADMIN'}">
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="新建" data-toggle="modal"
                                        data-target="#addOrEditModal" onclick="resetWareFrom()"> 新增
                                </button>
                            </div>
                        </div>
                    </div>
                </c:if>
                <%--新增按钮 /--%>
            </div>

            <!-- 数据列表 -->
            <div class="table-box">
                <!-- 数据表格 -->
                <table id="dataList"
                       class="table table-bordered table-striped table-hover dataTable text-center stock_info">
                    <thead>
                    <tr>
                        <th class="sorting_asc">序号</th>
                        <th class="sorting">仓库名</th>
                        <th class="sorting">仓库容量</th>
                        <th class="text-center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageResult.rows}" var="warehouse" varStatus="status">
                        <tr>
                            <td>${requestScope.offset+status.index+1}</td>
                            <td> ${warehouse.name}</td>
                            <td> ${warehouse.capacity}</td>
                            <td class="text-center">
                                <c:if test="${USER_SESSION.role =='ADMIN'}">
                                    <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                            data-target="#addOrEditModal" onclick="findWareById(${warehouse.id})"> 编辑
                                    </button>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/product/findByWarehouse?id=${warehouse.id}&name=${warehouse.name}">
                                    <button type="button" class="btn bg-olive btn-xs" data-toggle="modal">
                                        查看仓库内货品
                                    </button>
                                </a>
                            </td>
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


        <!-- 添加和编辑仓库的模态窗口 -->
        <div class="modal fade" id="addOrEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 id="myModalLabel">仓库信息</h3>
                    </div>
                    <div class="modal-body">
                        <form id="addOrEditWarehouse">
                            <span><input type="hidden" id="wareid" name="id"></span>
                            <table id="warehouseList" class="table table-bordered table-striped" width="800px">
                                <%--货品的id,不展示在页面--%>
                                <tr>
                                    <td>仓库名</td>
                                    <td><input class="form-control" placeholder="仓库名称" name="name" id="warename"></td>
                                    <td>仓库容量</td>
                                    <td><input class="form-control" placeholder="仓库容量" name="capacity"
                                               id="warecapacity"></td>
                                </tr>
                            </table>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="ware" disabled
                                onclick="addOrEditWare()">保存
                        </button>
                        <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
                    </div>
                </div>
            </div>
        </div>
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