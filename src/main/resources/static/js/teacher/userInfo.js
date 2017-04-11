/**
 * Created by KINGBOOK on 2017/3/2.
 */
$(function () {
    var dtOption = {
        "rowCallback": function (row, data, index) {
            //单击选择元素
            $(row).on("click",function () {
                if($(this).find(":checkbox").prop("checked")){
                    $(this).find(":checkbox").prop({checked:false});
                }else{
                    $(this).find(":checkbox").prop({checked:true});
                }
            })
        },
        "ajax": {
            url: "userInfo/refreshUserInfo",
            dataSrc: ''
        },
        "columns": [
            {data: 'userName'},
            {data: 'userName'},
            {data: 'nickName'},
            {data: 'sex'},
            {data: 'timeStr'},
            {data: 'job'},
            {data: 'phone'},
            {data: 'qq'},
            {data: 'email'},
            {data: 'profile'},
        ],
        "columnDefs": [
            {
                "targets": [0],
                "data": "userName",
                "render": function (data, type, full) {

                    return '<input name="username" type="checkbox" class="userInfoCheckbox" value="' + data + '"/>'
                }
            }
        ]
    };
    $('#userInfo1').DataTable($.extend(dtOption,dtTemplateOption));
    //给关闭按钮添加关闭事件
    //给关闭按钮添加关闭事件
    $(".clsBtn").click(closeDialogBtn);
    //绑定事件处理函数
    $("#refreshUserInfo").click(userInfoRefresh);
    $("#chartUserInfo").click(function () {
        openDialog("#userInfoChartDialog", null,null,true);
    })
    //图表Dialog,点击按钮切换样式事件
    $(".blue-button-group button").click(setBtnStyle("btn-danger", "btn-default"));
    $(".green-button-group button").click(setBtnStyle("btn-success", "btn-default"));
    $("#launchStaUserInfo").click(staHandler("#userInfoChartCanvase", [{
            selector: "#staUserInfoSex",
            url: "userInfo/queryForSta?type=sex"
        }, {
            selector: "#staUserInfoJob",
            url: "userInfo/queryForSta?type=job"
        }, {
            selector: "#staUserInfoAge",
            url: "userInfo/queryForSta?type=age"
        }], ["#staUserInfoBar", "#staUserInfoLine", "#staUserInfoRadar", "#staUserInfoDoughnut"],
        ["用户性别", "用户职业", "用户年龄"]));
    //绑定第一个复选框为反选按钮
    $("#userInfo1 :checkbox:first").click(function (evt) {
        $("#userInfo1 :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
        })
    })
});
/**
 * 刷新UserInfo
 */
function userInfoRefresh() {
    $('#userInfo1').DataTable().ajax.reload();
}

