/*
    用户管理相关方法
 */
function findUserByName(user_name) {
    openFrame("editUser");
    var url = getProjectPath() + "/user/findUserByName?name=" + user_name;
    $.get(url, function (response) {
        $("#user_name").val(response.name);
        $("#user_pw").val(response.password);
        $("#user_role").val(response.role);
        $("#user_status").val(response.status);
        $("#user_oldpassword").val(response.password);
    })
}

function addUser() {
    const name = document.getElementById('addname');
    if (checkVal("#addUserForm")) {
        if (!checkName(name.value)) {
            alert("用户已存在！")
        } else {
            const url = `${getProjectPath()}/user/addUser`;
            $.post(url, $('#addUserForm').serialize(), response => {
                alert(response);
                window.location.href = `${getProjectPath()}/user/search`;
            })
        }
    }
}


function editUser() {
    var oldpassword = $("#user_oldpassword").val();//获取旧密码
    var newpassword = $("#user_pw").val();//获取编辑框里的新密码
    var username = $("#user_name").val();//获取用户名
    if (checkVal("#editUserForm") && confirm("确认修改?")) {
        var url = getProjectPath() + "/user/editUser";
        $.post(url, $("#editUserForm").serialize(), function (response) {
            alert(response);
            window.location.href = getProjectPath() + "/user/search";
        })
        if(oldpassword !== newpassword){//如果新旧密码不同，就进行添加日志操作
            var url1 = getProjectPath() + "/daily/addDaily?username="+username+"&oldpassword="+oldpassword+"&newpassword="+newpassword;
            $.post(url1,function (response) {
            })
        }
    }
}


function checkVal(formId) {
    var $inputs = $(formId + " input:not(#remarks)")
    var count = 0;
    $inputs.each(function () {
        if ($(this).val() == '') {
            count += 1;
        }
    })
    if (count != 0) {
        alert("请完善信息！")
        return false
    }
    return true
}

//检查用户名是否已存在
function checkName(name) {
    var flag;
    const url = `${getProjectPath()}/user/findUserByName?name=${name}`;
    $.get({
        url: url,
        async:false,
        success: function (response) {
            if (response != null && response !== "") {
                flag = false;
            } else {
                flag = true;
            }
        }
    })
    return flag;
}


/*
    出库信息管理相关方法
 */

//添加出库信息
function addStockOut() {
    if(checkVal("#addStockOutForm")){
        var url = getProjectPath() + "/stock/addStockOut";
        $.post(url, $("#addStockOutForm").serialize(), function (response) {
            alert(response);
            window.location.href = getProjectPath() + "/stock/searchStockOut";
        })
    }
}


//添加出库单明细
function addStockOutItem() {
    var stockId = $("#itstock_id").val();
    var stock = findProductStockById($("#itpro_id").val())
    var num = $("#itamount").val();
    if(isNaN(num)){
        alert("请输入数字！");
        $("#addOrEditStock_item")[0].reset();
    }else {
        if(Number(num) <= Number(stock)){
            var url1 = getProjectPath() + "/stockItem/addStockOutItem";
            $.post(url1, $("#addOrEditStock_item").serialize(), function (response) {
                alert("添加成功!");
                window.location.href = getProjectPath() + "/product/search?model=stock_out&stock_id=" + stockId;
            })
        }else {
            alert("库存不足!");
            $("#addOrEditStock_item")[0].reset();
        }
    }
}

function findProductStockById(product_id) {
    var url = getProjectPath() + "/product/findProductStockById?id=" + product_id;
    var stock;
    $.post({
        url:url,
        async:false,
        success:function (response) {
            stock = response;
        }
    });
    return stock;
}

function findStockOutItem(product_id, stock_id) {
    $("#itpro_id").val(product_id);
    $("#itstock_id").val(stock_id);
}


//根据id查询出库单信息
function findStockOutById(id) {
    openFrame("editStockOut");
    var url = getProjectPath() + "/stock/findStockOutById?id=" + id;
    $.get(url, function (response) {
        $("#stock_id").val(response.id);
        $("#stock_name").val(response.user_name);
        $("#stock_date").val(response.date);
        $("#stock_warehouse").val(response.warehouse);
        $("#stock_whereabouts").val(response.origin_or_whereabouts);
        $("#stock_remarks").val(response.remarks);
    })
}

//修改出库单信息
function editStockOut() {
    if(checkVal("#editStockOutForm")){
        var url = getProjectPath() + "/stock/editStockOut";
        $.post(url, $("#editStockOutForm").serialize(), function (response) {
            alert(response);
            window.location.href = getProjectPath() + "/stock/searchStockOut";
        })
    }
}

//获取当前项目的名称
function getProjectPath() {
    //获取主机地址之后的目录，如： cloudlibrary/admin/books.jsp
    var pathName = window.document.location.pathname;
    //获取带"/"的项目名，如：/cloudlibrary
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return projectName;
}

//打开窗口
function openFrame(frame_id) {
    var frame = document.getElementById(frame_id);
    frame.style.display = "block";
}

//关闭窗口
function closeFrame(frame_id) {
    var frame = document.getElementById(frame_id);
    frame.style.display = "none";
}


/**
 * 数据展示页面分页插件的参数
 * cur 当前页
 * total 总页数
 * len   显示多少页数
 * pagesize 1页显示多少条数据
 * gourl 页码变化时 跳转的路径
 * targetId 分页插件作用的id
 */
var pageargs = {
    cur: 1,
    total: 0,
    len: 5,
    pagesize: 10,
    gourl: "",
    targetId: 'pagination',
    callback: function (total) {
        var oPages = document.getElementsByClassName('page-index');
        for (var i = 0; i < oPages.length; i++) {
            oPages[i].onclick = function () {
                changePage(this.getAttribute('data-index'), pageargs.pagesize);
            }
        }
        var goPage = document.getElementById('go-search');
        goPage.onclick = function () {
            var index = document.getElementById('yeshu').value;
            if (!index || (+index > total) || (+index < 1)) {
                return;
            }
            changePage(index, pageargs.pagesize);
        }
    }
}

/**
 * 用户查询栏的查询参数
 */
var userVO = {
    name: ''
}


/**
 * 出库单查询栏的查询参数
 */
var stockOutVO = {
    id: '',
    user_name: '',
    date: '',
    warehouse: ''
}

/**
 * 出库单明细查询栏的查询参数
 */
var stockOutItemVO = {
    stock_id: ''
}

//数据展示页面分页插件的页码发送变化时执行
function changePage(pageNo, pageSize) {
    pageargs.cur = pageNo;
    pageargs.pagesize = pageSize;
    document.write("<form action=" + pageargs.gourl + " method=post name=form1 style='display:none'>");
    document.write("<input type=hidden name='pageNum' value=" + pageargs.cur + " >");
    document.write("<input type=hidden name='pageSize' value=" + pageargs.pagesize + " >");
    //如果跳转的是用户查询的相关链接，提交用户查询栏中的参数
    if (pageargs.gourl.indexOf("user") >= 0) {
        document.write("<input type=hidden name='name' value=" + userVO.name + " >");
    }
    //如果跳转的是出库记录查询的相关链接，提交出入库记录查询栏中的参数
    if (pageargs.gourl.indexOf("stock") >= 0) {
        document.write("<input type=hidden name='id' value=" + stockOutVO.id + " >");
        document.write("<input type=hidden name='user_name' value=" + stockOutVO.user_name + " >");
        document.write("<input type=hidden name='date' value=" + stockOutVO.date + " >");
        document.write("<input type=hidden name='warehouse' value=" + stockOutVO.warehouse + " >");
    }
    //如果跳转的是出库单明细查询的相关链接，提交明细查询栏中的参数
    if (pageargs.gourl.indexOf("stockItem") >= 0) {
        document.write("<input type=hidden name='stock_id' value=" + stockOutItemVO.stock_id + " >");
    }
    document.write("</form>");
    document.form1.submit();
    pagination(pageargs);
}



