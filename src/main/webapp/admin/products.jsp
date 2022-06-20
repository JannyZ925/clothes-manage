<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>货品管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/main.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
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

    <div class="product_manage">
        <c:if test="${model=='productManage'}">
            <h3 class="box-title">货品查询</h3>
        </c:if>
        <c:if test="${model=='add_item'}">
            <h3 class="box-title">入库明细添加</h3>
        </c:if>
        <c:if test="${model=='stock_out'}">
            <h3 class="box-title">添加出库明细</h3>
        </c:if>
        <c:if test="${model=='showWarePro'}">
            <h3 class="box-title">*${wareName}*货品信息</h3><br/><br/>
        </c:if>
        <div class="box-body">
            <c:if test="${model != 'showWarePro'}">
                <div class="add_and_search">
                        <%--新增按钮：如果当前登录用户是管理员，页面展示新增按钮--%>
                    <c:if test="${USER_SESSION.role =='ADMIN'}">
                        <div class="pull-left">
                            <div class="form-group form-inline">
                                <div class="btn-group">
                                    <c:if test="${USER_SESSION.role =='ADMIN'}">
                                        <button type="button" class="btn btn-default" title="新建" data-toggle="modal"
                                                data-target="#addOrEditModal" onclick="resetProFrom()"> 添加
                                        </button>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:if>
                        <%--新增按钮 /--%>
                    <!--工具栏 数据搜索 -->

                    <div class="box-tools pull-right">
                        <div class="has-feedback">
                            <c:if test="${model=='productManage'}">
                                <form action="${pageContext.request.contextPath}/product/search" method="post">
                                    货品货号：<input name="number" value="${search.number}" style="width: 150px">
                                    货品名称：<input name="name" value="${search.name}" style="width: 150px">
                                    货品色号：<input name="color" value="${search.color}" style="width: 150px">
                                    货品尺码：<input name="size" value="${search.size}" style="width: 150px">
                                    <input class="btn btn-default" type="submit" value="查询">
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:if>
            <!--工具栏 数据搜索 /-->
            <!-- 数据列表 -->
            <div class="table-box">
                <!-- 数据表格 -->
                <table id="dataList"
                       class="table table-bordered table-striped table-hover dataTable text-center product_info">
                    <thead>
                    <tr>
                        <th class="sorting_asc">货品货号</th>
                        <th class="sorting">货品名称</th>
                        <th class="sorting">货品色号</th>
                        <th class="sorting">货品尺寸</th>
                        <th class="sorting">货品库存</th>
                        <c:if test="${model != 'showWarePro'}">
                            <th class="text-center">操作</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageResult.rows}" var="product">
                        <tr>
                            <td>${product.number}</td>
                            <td>${product.name}</td>
                            <td>${product.color}</td>
                            <td>${product.size}</td>
                            <td>${product.stock}</td>
                            <c:if test="${model != 'showWarePro'}">
                                <td class="text-center">
                                    <c:if test="${USER_SESSION.role =='ADMIN'}">
                                        <c:if test="${model=='productManage'}">
                                            <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                                    data-target="#addOrEditModal"
                                                    onclick="findProductById(${product.id},'edit')"> 编辑
                                            </button>
                                            <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                                    data-target="#deleteModal"
                                                    onclick="deleteProductById(${product.id})">
                                                删除
                                            </button>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${model=='add_item'}">
                                        <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                                data-target="#addItemModal"
                                                onclick="findStock_itemById(${product.id},${stock_id})"> 入库
                                        </button>
                                    </c:if>
                                    <c:if test="${model=='stock_out'}">
                                        <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                                data-target="#addItemModal"
                                                onclick="findStockOutItem(${product.id},${stock_id})">
                                            出库
                                        </button>
                                    </c:if>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!--数据表格/-->
                <%--分页插件--%>
                <div id="pagination" class="pagination"></div>
            </div>
        </div>
        <!--数据列表/-->
    </div>
</div>


<!-- 添加和编辑货品的模态窗口 -->
<div class="modal fade" id="addOrEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myModalLabel">货品信息</h3>
            </div>
            <div class="modal-body">
                <form id="addOrEditProduct">
                    <span><input type="hidden" id="proid" name="id"></span>
                    <table id="productList" class="table table-bordered table-striped" width="800px">
                        <tr>
                            <td>货品货号</td>
                            <td><input class="form-control" placeholder="货品货号" name="number" id="pronumber"></td>
                            <td>货品名称</td>
                            <td><input class="form-control" placeholder="货品名称" name="name" id="proname"></td>
                        </tr>
                        <tr>
                            <td>色号</td>
                            <td><input class="form-control" placeholder="色号" name="color" id="procolor"></td>
                            <td>尺码</td>
                            <td><input class="form-control" placeholder="尺码" name="size" id="prosize"></td>
                        </tr>
                        <tr>
                            <td>库存</td>
                            <td><input class="form-control" placeholder="库存" name="stock" id="prostock"></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="pro"
                        onclick="addOrEditProduct()">保存
                </button>
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>

<%--输入出入库数量的模态窗口--%>
<div class="modal fade" id="addItemModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myAmount">出入库数量</h3>
            </div>
            <div class="modal-body">
                <form id="addOrEditStock_item">
                    <span><input type="hidden" id="itpro_id" name="product_id"></span>
                    <span><input type="hidden" id="itstock_id" name="stock_id"></span>
                    <table id="Item" class="table table-bordered table-striped" width="800px">
                        <tr>
                            <td>出入库数量</td>
                            <td><input class="form-control" placeholder="出入库数量" name="amount" id="itamount"></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <c:if test="${model=='add_item'}">
                    <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="ite"
                            onclick="addOrEditStock_item()">保存
                    </button>
                </c:if>

                <c:if test="${model=='stock_out'}">
                    <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="ite"
                            onclick="addStockOutItem()">保存
                    </button>
                </c:if>
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
    productVO.number = "${search.number}"
    productVO.name = "${search.name}"
    productVO.color = "${search.color}"
    productVO.size = "${search.size}"
    /*分页效果*/
    pagination(pageargs);
</script>
</html>