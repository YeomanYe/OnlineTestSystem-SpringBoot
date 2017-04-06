var resourcesCodes = [];
var usersRole = [];
$(function () {
    var userDtOption = {
        "rowCallback": function (row, data, index) {
            //单击选择元素
            $(row).on("click",function () {
                debugger;
                if($(this).find(":checkbox").prop("checked")){
                    $(this).find(":checkbox").prop({checked:false});
                }else{
                    $(this).find(":checkbox").prop({checked:true});
                }
            })
        },
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
    };
    //初始化用户表
    $('#userTable').DataTable($.extend(userDtOption,dtTemplateOption));
    //初始化角色表
    var roleDtOption = {
        "rowCallback": function (row, data, index) {
            //单击选择元素
            $(row).on("click",function () {
                debugger;
                if($(this).find(":checkbox").prop("checked")){
                    $(this).find(":checkbox").prop({checked:false});
                }else{
                    $(this).find(":checkbox").prop({checked:true});
                }
            })
        },
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
    };
    $('#roleTable').DataTable($.extend(roleDtOption,dtTemplateOption));
    //初始化权限树
    var $checkablePermissionTree;
    $.ajax({
        type:"get",
        url:"permission/queryPermissionTree",
        success:function (data) {
            //如果为空则返回
            if(!data) return;
            console.log(data);
            $checkablePermissionTree = $('#permissionTree').treeview({
                data: data,
                showIcon: false,
                collapseIcon:"glyphicon glyphicon-folder-open",
                expandIcon:"glyphicon glyphicon-folder-close",
                emptyIcon:"glyphicon glyphicon-tower",
                showCheckbox: true,
                onNodeChecked: function (event, node) {
                    var parentNode = $checkablePermissionTree.treeview('getParent', node);
                    if(parentNode.nodes){
                        //递归选择
                        $checkablePermissionTree.treeview('checkNode',parentNode);
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
                            $checkablePermissionTree.treeview('uncheckNode',[childNodes[i].nodeId]);
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
    $(".clsBtn").click(closeDialogBtn);
    /*
     用户相关操作
     */
    $("#addUser").click(function () {
        openBox("#userAddBox",null,function () {
            //将表单以及ID清空
            $("#userName").val("");
            $("#password").val("");
            $("#againPassword").val("");
        });
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
        var $checkedboxs = $(".userCheckbox:checked");
        if (!$checkedboxs.length) {
            openDialog("#infoDialog","<p>请选择一个以上的用户</p>",null,true);
        } else {
            confirmDialog("<p>确定删除吗?</p>",function () {
                $.ajax({
                    url: "permission/deleteUsers",
                    type: "get",
                    data: $("#userListForm").serialize(),
                    context: $(this),
                    beforeSend:function () {
                        startProgress();
                    },
                    success: function (data) {
                        endProgress();
                        if (data === true) {
                            openDialog("#successDialog","<p>删除成功</p>",null,true);
                            //刷新
                            userRefresh();
                        }else{
                            openDialog("#errorDialog","<p>删除失败，原因不明</p>",null,true);
                        }
                    },
                    error:function () {
                        endProgress();
                        openDialog("#errorDialog","<p>删除失败，服务器端错误</p>",null,true);
                    }
                });
            });
        }
    });
    $("#refreshUser").click(userRefresh);
    $("#submitUserBtn").click(function () {
        $.ajax({
            type: "get",
            url: "permission/addUsers",
            data: $("#userForm").serialize(),
            beforeSend:function () {
                startProgress();
            },
            success: function (data) {
                endProgress();
                if(data==true){
                    openDialog("#successDialog","<p>更改成功</p>",null,true);
                    userRefresh();
                }else{
                    openDialog("#errorDialog","<p>未知错误，请重试!</p>",null,true);
                }
            },
            error:function () {
                openDialog("#errorDialog","<p>操作失败，服务器端错误!</p>",null,true);
                endProgress();
            }
        })
    });
    $("#roleTreeBtn").click(function () {
        var $checkedboxs = $(".userCheckbox:checked");
        if ($checkedboxs.length != 1) {
            openDialog("#infoDialog","<p>请选择一个用户</p>",null,true);
        } else {
            //初始角色树
            var $checkableRoleTree;
            $.ajax({
                type:"get",
                async:false,
                url:"permission/queryRoleTree",
                success:function (data) {
                    //如果为空则返回
                    if(!data) return;
                    console.log(data);
                    $checkableRoleTree = $('#roleTree').treeview({
                        data: data,
                        showIcon: false,
                        collapseIcon:"glyphicon glyphicon-folder-open",
                        expandIcon:"glyphicon glyphicon-folder-close",
                        emptyIcon:"glyphicon glyphicon-tower",
                        showCheckbox: true,
                        onNodeChecked: function (event, node) {
                            //添加角色ID到数组中
                            var value = node.value;
                            if(value){
                                usersRole.push(node.value);
                            }
                        },
                        onNodeUnchecked: function (event, node) {
                            //删除用户角色数组中的值
                            var index = usersRole.indexOf(node.value);
                            if(index != -1)
                                usersRole.splice(index,1);
                        }
                    });
                }
            });
            openBox("#roleTreeBox",null,function(){
                $.ajax({
                    url:"permission/queryRelRole?userId="+$checkedboxs.val(),
                    type:"get",
                    beforeSend:function () {
                        startProgress();
                    },
                    success:function (data) {
                        endProgress();
                        $checkableRoleTree.treeview('uncheckAll');
                        usersRole = [];
                        if(!data.length) return;
                        for(var i=0,len=data.length;i<len;i++){
                            var checkableNodes = $checkableRoleTree.treeview('search', [ data[i].text, {
                                ignoreCase: false,     // case insensitive
                                exactMatch: true,    // like or equals
                                revealResults: true,  // reveal matching nodes
                            }]);
                            $checkableRoleTree.treeview('checkNode', [ checkableNodes, { silent: true}]);
                            usersRole.push(data[i].roleId);
                        }
                        $checkableRoleTree.treeview('clearSearch');
                    },
                    error:function () {
                        endProgress();
                    }
                })
            });
        }
    });
    $("#submitRoleTreeBtn").click(function () {
        var data = $.param({
            userId: $(".userCheckbox:checked").val(),
            roleIds:usersRole
        },true);
        $.ajax({
            type:"get",
            url:"permission/addUserRoleRel",
            data:data,
            beforeSend:function () {
                startProgress();
            },
            success:function (data) {
                endProgress();
                if(data==true){
                    openDialog("#successDialog","<p>更改成功</p>",null,true);
                }else{
                    openDialog("#errorDialog","<p>未知错误，请重试!</p>",null,true);
                }
            },
            error:function () {
                openDialog("#errorDialog","<p>操作失败，服务器端错误!</p>",null,true);
                endProgress();
            }
        })
    });
    //角色相关操作
    $("#addRole").click(function () {
        openBox("#roleAddBox",null,function () {
            //将表单以及ID清空
            $("#roleId").val("");
            $("#roleName").val("");
        });
    });
    $("#updateRole").click(function () {
        var $checkedboxs = $(".roleCheckbox:checked");
        if ($checkedboxs.length != 1) {
            openDialog("#infoDialog","<p>请选择一个角色</p>",null,true);
        } else {
            openBox("#roleAddBox",null,function () {
                $("#roleId").val($checkedboxs.val());
                $("#roleName").val($checkedboxs.closest('tr').find('.roleName').text())
            });
        }
    });
    $("#deleteRole").click(function () {
        var $checkedboxs = $(".roleCheckbox:checked");
        if (!$checkedboxs.length) {
            openDialog("#infoDialog","<p>请选择一个以上的角色</p>",null,true);
        } else {
            confirmDialog("<p>确定删除吗?</p>",function () {
                $.ajax({
                    url: "permission/deleteRole",
                    type: "get",
                    data: $("#roleListForm").serialize(),
                    context: $(this),
                    beforeSend:function () {
                        startProgress();
                    },
                    success: function (data) {
                        endProgress();
                        if (data === true) {
                            openDialog("#successDialog","<p>删除成功</p>",null,true);
                            //刷新
                            roleRefresh();
                        }else{
                            openDialog("#errorDialog","<p>删除失败，原因不明</p>",null,true);
                        }
                    },
                    error:function () {
                        endProgress();
                        openDialog("#errorDialog","<p>删除失败，服务器端错误</p>",null,true);
                    }
                })
            });
        }
    });
    $("#refreshRole").click(roleRefresh);
    $("#submitRoleBtn").click(function () {
        $.ajax({
            type: "get",
            url: "permission/mergeRole",
            data: $("#roleForm").serialize(),
            beforeSend:function () {
                startProgress();
            },
            success: function (data) {
                endProgress();
                if (data) {
                    openDialog("#successDialog","<p>更改成功</p>",null,true);
                    $("#roleId").val(data);
                    roleRefresh();
                }else{
                    openDialog("#errorDialog","<p>未知错误，请重试!</p>",null,true);
                }
            },
            error:function () {
                openDialog("#errorDialog","<p>操作失败，服务器端错误!</p>",null,true);
                endProgress();
            }
        })
    });
    $("#resourcesRelTreeBtn").click(function () {
        var $checkedboxs = $(".roleCheckbox:checked");
        if ($checkedboxs.length != 1) {
            openDialog("#infoDialog","<p>请选择一个角色</p>",null,true);
        } else {
            openBox("#resourcesTreeBox",null,function () {
                $.ajax({
                    url:"permission/queryAuth?roleId="+$checkedboxs.val(),
                    type:"get",
                    beforeSend:function () {
                        startProgress();
                    },
                    success:function (data) {
                        endProgress();
                        $checkablePermissionTree.treeview('uncheckAll');
                        resourcesCodes = [];
                        if(!data.length) {
                            return;
                        }
                        for(var i=0,len=data.length;i<len;i++){
                            var checkableNodes = $checkablePermissionTree.treeview('search', [ data[i].text, {
                                ignoreCase: false,     // case insensitive
                                exactMatch: true,    // like or equals
                                revealResults: true,  // reveal matching nodes
                            }]);
                            $checkablePermissionTree.treeview('checkNode', [ checkableNodes, { silent: true}]);
                            resourcesCodes.push(data[i].resourcesId);
                        }
                        $checkablePermissionTree.treeview('clearSearch');
                    },
                    error:function () {
                        endProgress();
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
            beforeSend:function () {
                startProgress();
            },
            success:function (data) {
                endProgress();
                if(data==true){
                    openDialog("#successDialog","<p>更改成功</p>",null,true);
                }else{
                    openDialog("#errorDialog","<p>未知错误，请重试!</p>",null,true);
                }
            },
            error:function () {
                openDialog("#errorDialog","<p>操作失败，服务器端错误!</p>",null,true);
                endProgress();
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