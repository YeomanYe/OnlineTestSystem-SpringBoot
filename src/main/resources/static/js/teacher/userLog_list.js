/**
 * Created by KINGBOOK on 2017/3/2.
 */
$(function () {
    $('#userLog1').DataTable({
        "paging": true,
        "lengthChange": true,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "ajax": {
            url: "userLog/refreshUserLog",
            dataSrc: ''
        },
        "columns": [
            {data: 'uuid'},
            {data: 'userName'},
            {data: 'ip'},
            {data: 'operation'},
            {data: 'timeStr'},
        ],
        "columnDefs": [
            {
                "targets": [0],
                "data": "uuid",
                "render": function (data, type, full) {
                    debugger;
                    return '<input name="userLogIds" type="checkbox" class="userLogCheckbox" value="' + data + '"/>'
                }
            }
        ]
    });
    //绑定事件处理函数
    //给关闭按钮添加关闭事件
    $(".clsBtn").click(closeDialogBtn);
    $("#deleteUserLog").click(function () {
        var checkedboxs = $("#userLog1 :checked");
        if (!checkedboxs.length) {

        } else {
            $.ajax({
                url: "userLog/deleteUserLog",
                type: "get",
                data: $("#userLogListForm").serialize(),
                context: $(this),
                success: function (data) {
                    debugger;
                    if (data === true) {
                        userLogRefresh();
                    }
                }
            })
        }
    });
    $("#refreshUserLog").click(userLogRefresh);
    $("#chartUserLog").click(function () {
        openDialog("#userLogChartDialog", null);
    })
    //图表Dialog,点击按钮切换样式事件
    $(".blue-button-group button").click(setBtnStyle("btn-danger", "btn-default"));
    $(".green-button-group button").click(setBtnStyle("btn-success", "btn-default"));
    $("#launchStaUserLog").click(staHandler("#userLogChartCanvase", [{
            selector: "#staUserLogUserName",
            url: "userLog/queryForSta?type=userName"
        }, {
            selector: "#staUserLogOperation",
            url: "userLog/queryForSta?type=operation"
        }, {
            selector: "#staUserLogDate",
            url: "userLog/queryForSta?type=date"
        }], ["#staUserLogBar", "#staUserLogLine", "#staUserLogRadar", "#staUserLogDoughnut"],
        ["用户名", "操作", "日期"]));
    //绑定第一个复选框为反选按钮
    $("#userLog1 :checkbox:first").click(function (evt) {
        $("#userLog1 :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
        })
    })
});
/**
 * 刷新日志
 */
function userLogRefresh() {
    $('#userLog1').DataTable().ajax.reload();
}

