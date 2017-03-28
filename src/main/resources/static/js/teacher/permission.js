$(function () {
    //初始化用户表
    $('#userTable').DataTable({
        "paging": true,
        "pageingType": "input",
        "lengthChange": true,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": true,
        "ajax": {
            url: "permission/refreshUserList",
            dataSrc: ''
        },
        "columns": [
            {data: 'userName'},
            {data: 'userName'},
            {data: 'updateWhenStr'},
            {data: 'updateBy'},
        ],
        "columnDefs": [
            {
                "targets": [0],
                "data": "userName",
                "render": function (data) {

                    return '<input name="userIds" type="checkbox" class="userCheckbox" value="' + data + '"/>'
                }
            }
        ]
    });
    //初始化角色表
    $('#roleTable').DataTable({
        "paging": true,
        "pageingType": "input",
        "lengthChange": true,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": true,
        "ajax": {
            url: "permission/refreshRoleList",
            dataSrc: ''
        },
        "columns": [
            {data: 'uuid'},
            {
                data: 'name',
                className: 'roleName'
            },
            {data: 'updateWhenStr'},
            {data: 'updateBy'},
        ],
        "columnDefs": [
            {
                "targets": [0],
                "data": "uuid",
                "render": function (data, type, full) {

                    return '<input name="roleIds" type="checkbox" class="roleCheckbox" value="' + data + '"/>'
                }
            }
        ]
    });
    //绑定事件处理函数
    //给关闭按钮添加事件
    $(".clsBtn").click(function () {
        //获取需要关闭的modal的ID
        var modal = $(this).data("dismiss");
        $("#" + modal).hide();

    });
    /*
     用户相关操作
     */
    $("#addUser").click(function () {
        openDialog("#userAddDialog", null, function () {
            //将表单以及ID清空
            $("#userName").val("");
            $("#password").val("");
            $("#againPassword").val("");
        })
    });
    $("#updateUser").click(function () {
        var $checkedboxs = $(".userCheckbox:checked");
        if ($checkedboxs.length != 1) {

        } else {
            openDialog("#userAddDialog", null, function () {
                $.ajax({
                    type: "get",
                    url: "permission/queryUserForUpdate?userName=" + $checkedboxs.val(),
                    success: function (data) {
                        $("#userName").val(data.userName);
                        $("#password").val(data.pass);
                        $("#againPassword").val(data.pass);
                    }
                })
            });
        }
    });
    $("#deleteUser").click(function () {
        var $checkedboxs = $("table :checked");
        if (!$checkedboxs.length) {

        } else {
            $.ajax({
                url: "permission/deleteUser",
                type: "get",
                data: $("#userListForm").serialize(),
                context: $(this),
                success: function (data) {

                    if (data === true) {
                        //刷新
                        userRefresh();
                    }
                }
            })
        }
    });
    $("#refreshUser").click(userRefresh);
    $("#submitUserBtn").click(function () {
        $.ajax({
            type: "get",
            url: "permission/mergeUser",
            data: $("#userForm").serialize(),
            success: function (data) {

            }
        })
    })
    //角色相关操作
    $("#addRole").click(function () {
        openDialog("#roleAddDialog", null, function () {
            //将表单以及ID清空
            $("#roleId").val("");
            $("#roleName").val("");
        })
    });
    $("#updateRole").click(function () {
        var $checkedboxs = $(".roleCheckbox:checked");
        if ($checkedboxs.length != 1) {

        } else {
            openDialog("#roleAddDialog", null, function () {
                $("#roleId").val($checkedboxs.val());
                $("#roleName").val($checkedboxs.closest('tr').find('.roleName').text())
            });
        }
    });
    $("#deleteRole").click(function () {
        var $checkedboxs = $("table :checked");
        if (!$checkedboxs.length) {

        } else {
            $.ajax({
                url: "permission/deleteRole",
                type: "get",
                data: $("#roleListForm").serialize(),
                context: $(this),
                success: function (data) {

                    if (data === true) {
                        //刷新
                        roleRefresh();
                    }
                }
            })
        }
    });
    $("#refreshRole").click(roleRefresh);
    $("#submitRoleBtn").click(function () {
        $.ajax({
            type: "get",
            url: "permission/mergeRole",
            data: $("#roleForm").serialize(),
            success: function (data) {
                if (data) {
                    $("#roleId").val(data);
                    roleRefresh();
                }
            }
        })
    })
    //绑定第一个复选框为反选按钮
    $("#userTable :checkbox:first").click(function (evt) {
        $("#userTable :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
        })
    });
    $("#roleTable :checkbox:first").click(function (evt) {
        $("#roleTable :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
        })
    });
});

/**
 * 刷新用户表
 */
function userRefresh() {
    $('#userTable').DataTable().ajax.reload();
}

/**
 * 刷新角色表
 */
function roleRefresh() {
    $('#roleTable').DataTable().ajax.reload();
}