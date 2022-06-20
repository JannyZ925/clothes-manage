//重置仓库添加和编辑窗口中输入框的内容
function resetWareFrom() {
    $("#ware").attr("disabled",false)
    var $vals=$("#addOrEditWarehouse input");
    $vals.each(function(){
        $(this).attr("style","").val("")
    });
}

//重置仓库添加和编辑窗口中输入框的样式
function resetWareStyle() {
    $("#ware").attr("disabled",false)
    var $vals=$("#addOrEditWarehouse input");
    $vals.each(function(){
        $(this).attr("style","")
    });
}

//查询id对应仓库信息，并将仓库信息回显到编辑的窗口中
function findWareById(id) {
    resetWareStyle()
    //将单据编号和货品id回显到窗口中
    var url = getProjectPath()+"/warehouse/findById?id=" + id;
    $.get(url, function (response) {
        //将获取的入库单信息回显到编辑的窗口中
        $("#wareid").val(response.data.id);
        $("#warename").val(response.data.name);
        $("#warecapacity").val(response.data.capacity);
    })
}

//点击添加或编辑的窗口的确定按钮时，提交仓库信息
function addOrEditWare() {
    //获取表单中id的内容
    var wareid = $("#wareid").val();
    //如果表单中有wareid的内容，说明本次为编辑操作
    if (wareid!=null&&wareid!=='') {
        window.alert("修改成功")
        var url = getProjectPath()+"/warehouse/editWarehouse";
        //$("#addOrEditWarehouse")是新增表单的id
        $.post(url, $("#addOrEditWarehouse").serialize(), function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath()+"/warehouse/ShowWarehouse";
            }
        })
    }
    //如果表单中没有入库单id，说明本次为添加操作
    else {

        var url = getProjectPath()+"/warehouse/addWarehouse";
        $.post(url, $("#addOrEditWarehouse").serialize(), function (response) {
            alert(response.message)
            window.alert("添加成功")
            if (response.success == true) {
                window.location.href = getProjectPath()+"/warehouse/ShowWarehouse";
            }
        })
    }
}
//获取当前项目的名称
function getProjectPath() {
    //获取主机地址之后的目录，如： cloudlibrary/admin/books.jsp
    var pathName = window.document.location.pathname;
    //获取带"/"的项目名，如：/cloudlibrary
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return  projectName;
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
    pagesize:10,
    gourl:"",
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

//数据展示页面分页插件的页码发送变化时执行
function changePage(pageNo,pageSize) {
    pageargs.cur=pageNo;
    pageargs.pagesize=pageSize;
    document.write("<form action="+pageargs.gourl +" method=post name=form1 style='display:none'>");
    document.write("<input type=hidden name='pageNum' value="+pageargs.cur+" >");
    document.write("<input type=hidden name='pageSize' value="+pageargs.pagesize+" >");
    //如果跳转的是货品信息查询的相关链接，提交货品查询栏中的参数
    if(pageargs.gourl.indexOf("product")>=0){
        document.write("<input type=hidden name='number' value="+productVO.number+" >");
        document.write("<input type=hidden name='name' value="+productVO.name+" >");
        document.write("<input type=hidden name='color' value="+productVO.color+" >");
        document.write("<input type=hidden name='size' value="+productVO.size+" >");
    }
    //如果跳转的是出入库记录查询的相关链接，提交出入库记录查询栏中的参数
    if(pageargs.gourl.indexOf("stock")>=0){
        document.write("<input type=hidden name='id' value="+stockVO.id+" >");
        document.write("<input type=hidden name='user_name' value="+stockVO.user_name+" >");
        document.write("<input type=hidden name='date' value="+stockVO.date+" >");
        document.write("<input type=hidden name='warehouse' value="+stockVO.warehouse+" >");
    }
    //如果跳转的是出入库记录查询的相关链接，提交出入库记录查询栏中的参数
    if(pageargs.gourl.indexOf("stock_item")>=0){
        document.write("<input type=hidden name='stock_id' value="+stock_itemVO.stock_id+" >");
    }
    if(pageargs.gourl.indexOf("warehouse")>=0){

    }
    document.write("</form>");
    document.form1.submit();
    pagination(pageargs);
}
