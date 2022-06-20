
function findUserByName(user_name) {
    openFrame("editUser");
    var url = getProjectPath() + "/user/findUserByName?name=" + user_name;
    $.get(url, function (response) {
        $("#user_name").val(response.name);
        $("#user_pw").val(response.password);
        $("#user_role").val(response.role);
    })
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


