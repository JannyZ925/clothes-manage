//重置货品添加和编辑窗口中输入框的内容
function resetProFrom() {
    $("#pro").attr("disabled",false)
    var $vals=$("#addOrEditProduct input");
    $vals.each(function(){
        $(this).attr("style","").val("")
    });
}

//重置货品添加和编辑窗口中输入框的样式
function resetProStyle() {
    $("#pro").attr("disabled",false)
    var $vals=$("#addOrEditProduct input");
    $vals.each(function(){
        $(this).attr("style","")
    });
}

//重置入库添加和编辑窗口中输入框的内容
function resetComeStoFrom() {
    $("#cos").attr("disabled",false)
    var $vals=$("#addOrEditStock input");
    $vals.each(function(){
        $(this).attr("style","").val("")
    });
}

//重置入库添加和编辑窗口中输入框的样式
function resetComeStoStyle() {
    $("#cos").attr("disabled",false)
    var $vals=$("#EditStock input");
    $vals.each(function(){
        $(this).attr("style","")
    });
}


//重置入库添加和编辑窗口中输入框的样式
function resetStock_itemStyle() {
    $("#ite").attr("disabled",false)
    var $vals=$("#addOrEditStock_item input");
    $vals.each(function(){
        $(this).attr("style","")
    });
}

//查询id对应货品信息，并将货品信息回显到编辑的窗口中
function findProductById(id,doname) {
    resetProStyle()
    var url = getProjectPath()+"/product/findById?id=" + id;
    $.get(url, function (response) {
        //如果是编辑图书，将获取的图书信息回显到编辑的窗口中
        if(doname=='edit'){
            $("#proid").val(response.data.id);
            $("#pronumber").val(response.data.number);
            $("#proname").val(response.data.name);
            $("#procolor").val(response.data.color);
            $("#prosize").val(response.data.size);
            $("#prostock").val(response.data.stock);
        }
    })
}

//查询id对应入库单信息，并将入库单信息回显到编辑的窗口中
function findComeStockById(id) {
    resetComeStoStyle()
    var url = getProjectPath()+"/stock/findById?id=" + id;
    $.get(url, function (response) {
        //将获取的入库单信息回显到编辑的窗口中
            $("#edcsid").val(response.data.id);
            $("#edcsuser_name").val(response.data.user_name);
            $("#edcswarehouse").val(response.data.warehouse);
            $("#edcsdate").val(response.data.date);
            $("#edcsorigin_or_whereabouts").val(response.data.origin_or_whereabouts);
            $("#edcsremarks").val(response.data.remarks);
            $("#edcsstatus").val(response.data.status);
    })
}

//查询id对应入库单信息，并将入库单信息回显到编辑的窗口中
function findStock_itemById(product_id,stock_id) {
    resetStock_itemStyle()
        //将单据编号和货品id回显到窗口中
        $("#itpro_id").val(product_id);
        $("#itstock_id").val(stock_id);
}

//查询id对应货品信息，并进行删除操作
function deleteProductById(id) {
    //如果是删除货品，将货品删除后返回货品查询界面
    if(window.confirm("请确认是否删除")){
        var url = getProjectPath()+"/product/deleteProduct?id="+id;
        $.post(url, function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath()+"/product/search";
            }
        })
    }
}

//点击添加或编辑的窗口的确定按钮时，提交货品信息
function addOrEditProduct() {
    //判断表单是否为空
    if(checkPro("#addOrEditProduct")){
        //获取表单中id的内容
        var proid = $("#proid").val();
        //如果表单中有proid的内容，说明本次为编辑操作
        if (proid > 0) {
            var url = getProjectPath()+"/product/editProduct";
            //$("#addOrEditProduct")是新增表单的id
            $.post(url, $("#addOrEditProduct").serialize(), function (response) {
                alert(response.message)
                if (response.success == true) {
                    window.location.href = getProjectPath()+"/product/search";
                }
            })
        }
        //如果表单中没有货品id，说明本次为添加操作
        else {
            var url = getProjectPath()+"/product/addProduct";
            $.post(url, $("#addOrEditProduct").serialize(), function (response) {
                alert(response.message)
                if (response.success == true) {
                    window.location.href = getProjectPath()+"/product/search";
                }
            })
        }
    }
}

//检查货品添加或编辑是否为空
function checkPro(formId){
    var $inputs = $(formId + " input:not(#proid)")//只有货品id可以为空
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

//入库添加
function addStockIn(){
    if(checkStockAdd("#addOrEditStock"))
        var url = getProjectPath()+"/stock/addStockIn";
    $.post(url, $("#addOrEditStock").serialize(), function (response) {
        if (response.success == true) {
            window.location.href = getProjectPath()+"/stock/searchCome";
        }
    })
}
//入库编辑
function editStockIn(){
    if(checkStockEdit("#EditStock"))
        var url = getProjectPath()+"/stock/editStock";
    //$("#addOrEditProduct")是新增表单的id
    $.post(url, $("#EditStock").serialize(), function (response) {
        if (response.success == true) {
            window.location.href = getProjectPath()+"/stock/searchCome";
        }
    })
}

//判断添加入库窗口是否为空
function checkStockAdd(formId){
    var $inputs = $(formId + " input:not(#csremarks)")//只有备注可以为空
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

//判断添加入库窗口是否为空
function checkStockEdit(formId){
    var $inputs = $(formId + " input:not(#edcsremarks)")//只有备注可以为空
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


//提交出入库数量
function addOrEditStock_item() {
//获取表单中id的内容
    var stock_id = $("#itstock_id").val();
    var product_id = $("#itpro_id").val();
    var x=document.getElementById("itamount").value;
    if(isNaN(x))//如果x不是数字就执行这个if语句，如果是数字就不执行这个if语句
    {
        window.alert("请输入数字！");//弹框
    }
    else{
        if(x==null||x===''){//判断输入是否为空
            window.alert("数量不能为空！");//弹框
        }
        else{
            var url1 = getProjectPath()+"/product/findById?id=" + product_id;
            var previous=0
            $.get(url1, function (response) {
                previous.val(response.data.stock);
            })
            if(x+previous>3000){//判断输入的数量加上已有的数量是否已经超过内存
                window.alert("库存已满！");//弹框
            }
            else{//数量条件全部达标，进行添加明细操作
                var url = getProjectPath()+"/stock_item/addStock_item";
                //$("#addOrEditProduct")是新增表单的id
                $.post(url, $("#addOrEditStock_item").serialize(), function (response) {
                    alert(response.message)
                    window.alert("添加成功")
                    if (response.success == true) {
                        window.location.href = getProjectPath()+"/stock_item/searchStock_item?stock_id="+stock_id;
                    }
                })
            }
        }
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
/**
 *货品查询栏的查询参数
 * number 货品货号
 * name 货品名称
 * color 色号
 * size 尺码
 */
var productVO={
    number:'',
    name:'',
    color:'',
    size:''
}
/**
 *出库单查询栏的查询参数
 * id 出库单据
 * user_name 经办人
 * warehouse 入库仓库
 * date 入库日期
 * origin_or_whereabouts 来源
 * remarks 备注
 */
var stockVO={
    id:'',
    user_name:'',
    warehouse:'',
    date:'',
    origin_or_whereabouts:'',
    remarks:''
}
/**
 *单据明细查询栏的查询参数
 * stock_id 单据编号
 * origin_or_whereabouts 来源
 */
var stock_itemVO={
    stock_id:''
    //origin_or_whereabouts:''
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
    document.write("</form>");
    document.form1.submit();
    pagination(pageargs);
}



