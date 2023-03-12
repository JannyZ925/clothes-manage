<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>入库管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/main.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/mypro.js"></script>
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
        <!-- .box-body -->
        <h3 class="box-title">入库信息</h3>
        <div class="box-body">
            <div class="add_and_search">

                <%--新增按钮：如果当前登录用户是管理员，页面展示新增按钮--%>
                <c:if test="${USER_SESSION.role =='ADMIN'}">
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="新建" data-toggle="modal"
                                        data-target="#addOrEditModal" onclick="resetComeStoFrom()"> 新增
                                </button>
                            </div>
                        </div>
                    </div>
                </c:if>
                <%--新增按钮 /--%>
                <!--工具栏 数据搜索 -->
                <div class="box-tools pull-right">
                    <div class="has-feedback">
                        <form action="${pageContext.request.contextPath}/stock/searchCome" method="post">
                            经办人：<input name="user_name" value="${search.user_name}" style="width: 150px">
                            所入仓库：<select name="warehouse" style="width: 150px">
                            <option></option>
                            <c:forEach items="${warehouseList}" var="warehouse" >
                                <option value="${warehouse.id}" ${search.warehouse eq warehouse.id ? "selected" : ""}>
                                        ${warehouse.name}
                                </option>
                            </c:forEach>
                        </select>
                            单据编号：<input name="id" value="${search.id}" style="width: 150px">
                            入库日期：<input type="date" name="date" value="${search.strDate}" style="width: 150px">
                            <input class="btn btn-default" type="submit" value="查询" style="width: 150px">
                        </form>
                    </div>
                </div>
            </div>
            <!--工具栏 数据搜索 /-->
            <!-- 数据列表 -->
            <div class="table-box">
                <!-- 数据表格 -->
                <table id="dataList"
                       class="table table-bordered table-striped table-hover dataTable text-center stock_info">
                    <thead>
                    <tr>
                        <th class="sorting_asc">序号</th>
                        <th class="sorting">单据编号</th>
                        <th class="sorting">所入仓库</th>
                        <th class="sorting">入库日期</th>
                        <th class="sorting">经办人</th>
                        <th class="sorting">来源</th>
                        <th class="sorting">备注</th>
                        <th class="text-center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageResult.rows}" var="stock" varStatus="status">
                        <tr>
                            <td>${requestScope.offset+status.index+1}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/stockItem/searchStock_item?stock_id=${stock.id}">${stock.id}</a>
                            </td>
                            <td>${stock.wareName}</td>
                            <td>${stock.strDate}</td>
                            <td>${stock.user_name}</td>
                            <td>${stock.origin_or_whereabouts}</td>
                            <td>${stock.remarks}</td>
                            <td class="text-center">
                                <c:if test="${USER_SESSION.role =='ADMIN'}">
                                    <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                            data-target="#EditModal" onclick="findComeStockById(${stock.id})"> 编辑
                                    </button>
                                </c:if>
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
    </div>
</div>

<!-- 添加入库单的模态窗口 -->
<div class="modal fade" id="addOrEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myModalLabel">入库单信息</h3>
            </div>
            <div class="modal-body">
                <form id="addOrEditStock">
                    <table id="comeStockList" class="table table-bordered table-striped" width="800px">
                        <tr>
                            <td>经办人</td>
                            <td><input class="form-control" placeholder="经办人" name="user_name" id="csuser_name"></td>
                            <td>来源</td>
                            <td><input class="form-control" placeholder="来源" name="origin_or_whereabouts" id="csorigin_or_whereabouts"></td>
                        </tr>
                        <tr>
                            <td>入库仓库</td>
                            <td>
                                <select name="warehouse" id="cswarehouse" placeholder="所入仓库">
                                    <c:forEach items="${warehouseList}" var="warehouse" >
                                        <option value="${warehouse.id}">${warehouse.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>入库日期</td>
                            <td><input type="date" class="form-control" placeholder="入库日期" name="date" id="csdate"></td>
                        </tr>
                        <tr>
                            <td>备注<br/></td>
                            <td><input class="form-control" placeholder="备注" name="remarks" id="csremarks"></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="cos" onclick="addStockIn()">保存
                </button>
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改入库单的模态窗口 -->
<div class="modal fade" id="EditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myLabel">入库单信息</h3>
            </div>
            <div class="modal-body">
                <form id="EditStock">
                    <span><input type="hidden" id="edcsstatus" name="status" value="1"></span>
                    <table id="comeEdStockList" class="table table-bordered table-striped" width="800px">
                        <%--货品的单据编号,不展示在页面--%>
                        <tr>
                            <td>单据编号</td>
                            <td><input readonly class="form-control" placeholder="单据编号" id="edcsid" name="id" ></td>
                            <td>经办人</td>
                            <td><input readonly class="form-control" placeholder="经办人" name="user_name" id="edcsuser_name"></td>

                        </tr>
                        <tr>
                            <td>来源</td>
                            <td><input class="form-control" placeholder="来源" name="origin_or_whereabouts" id="edcsorigin_or_whereabouts"></td>
                            <td>入库仓库</td>
                            <td><select name="warehouse" id="edcswarehouse" placeholder="所入仓库">
                                <c:forEach items="${warehouseList}" var="warehouse" varStatus="status">
                                    <option value="${warehouse.id}">${warehouse.name}</option>
                                </c:forEach>
                            </select></td>

                        </tr>
                        <tr>
                            <td>入库时间<br/></td>
                            <td><input readonly class="form-control" placeholder="入库时间" name="date" id="edcsdate"></td>
                            <td>备注<br/></td>
                            <td><input class="form-control" placeholder="备注" name="remarks" id="edcsremarks"></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="edcos" onclick="editStockIn()">保存
                </button>
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
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
    stockVO.id = "${search.id}"
    stockVO.user_name = "${search.user_name}"
    stockVO.date = "${search.date}"
    stockVO.warehouse = "${search.warehouse}"
    /*分页效果*/
    pagination(pageargs);
</script>
</html>