var resourcesCodes = [];
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

                    return '<input name="userNames" type="checkbox" class="userCheckbox" value="' + data + '"/>'
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
    //初始化权限树
    var $checkableTree;
    $.ajax({
        type:"get",
        url:"permission/queryPermissionTree",
        success:function (data) {
            //如果为空则返回
            if(!data) return;
            console.log(data);
            $checkableTree = $('#permissionTree').treeview({
                data: data,
                showIcon: false,
                showCheckbox: true,
                onNodeChecked: function (event, node) {
                    var parentNode = $checkableTree.treeview('getParent', node);
                    if(parentNode.nodes){
                        //递归选择
                        $checkableTree.treeview('checkNode',parentNode);
                    }
                    //添加资源代码到数组中
                    var value = node.value;
                    if(value){
                        resourcesCodes.push(node.value);
                    }
                },
                onNodeUnchecked: function (event, node) {
                    var childNodes = node.nodes;
                    if(childNodes){
                        //递归取消选择
                        for(var i=0,len=childNodes.length;i<len;i++){
                            $checkableTree.treeview('uncheckNode',[childNodes[i].nodeId]);
                        }
                    }
                    //删除资源代码数组中的值
                    var index = resourcesCodes.indexOf(node.value);
                    if(index != -1)
                        resourcesCodes.splice(index,1);
                }
            });
        }
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
    /*$("#updateUser").click(function () {
        var $checkedboxs = $(".userCheckbox:checked");
        if ($checkedboxs.length != 1) {

        } else {
            openDialog("#userAddDialog", null, function () {
                $("#userName").val($checkedboxs.closest("tr").find(".userName").text());
            });
        }
    });*/
    $("#deleteUser").click(function () {
        var $checkedboxs = $("table :checked");
        if (!$checkedboxs.length) {

        } else {
            $.ajax({
                url: "permission/deleteUsers",
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
            url: "permission/addUsers",
            data: $("#userForm").serialize(),
            success: function (data) {
                if(data==true){
                    userRefresh();
                }
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
    });
    $("#resourcesRelTree").click(function () {
        var $checkedboxs = $(".roleCheckbox:checked");
        if ($checkedboxs.length != 1) {

        } else {
            openDialog("#resourcesDialog",null,function () {
                $.ajax({
                    url:"permission/queryAuth?roleId="+$checkedboxs.val(),
                    type:"get",
                    success:function (data) {
                        $checkableTree.treeview('uncheckAll');
                        resourcesCodes = [];
                        if(!data.length) return;
                        for(var i=0,len=data.length;i<len;i++){
                            var checkableNodes = $checkableTree.treeview('search', [ data[i].text, {
                                ignoreCase: false,     // case insensitive
                                exactMatch: true,    // like or equals
                                revealResults: true,  // reveal matching nodes
                            }]);
                            $checkableTree.treeview('checkNode', [ checkableNodes, { silent: true}]);
                            resourcesCodes.push(data[i].resourcesId);
                        }
                        $checkableTree.treeview('clearSearch');
                    }
                })
            });
        }

    });
    $("#submitResourcesBtn").click(function () {
        var data = $.param({
            roleId: $(".roleCheckbox:checked").val(),
            resourcesIds:resourcesCodes
        },true);
        $.ajax({
            type:"get",
            url:"permission/addAuth",
            data:data,
            success:function (data) {
                if(data==true){

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