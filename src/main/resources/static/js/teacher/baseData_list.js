/**
 *
 */
$(function () {
    var dtOption = {
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
            url: "baseData/refreshBaseData",
            dataSrc: ''
        },
        "columns": [
            {data: 'uuid'},
            {
                data: 'dataType',
                className: 'dataType'
            },
            {
                data: 'name',
                className: 'name'
            }
        ],
        "columnDefs": [
            {
                "targets": [0],
                "data": "uuid",
                "render": function (data, type, full) {
                    return '<input name="baseDataIds" type="checkbox" class="baseDataCheckbox" value="' + data + '"/>'
                }
            },
            {
                "targets": [1],
                "data": "dataType",
                "render": function (data, type, full) {
                    var str = "";
                    if (data == "subjectType") {
                        str = "试题类型";
                    } else if (data == "paperType") {
                        str = "试卷类型";
                    }
                    return str;
                }
            }
        ]
    };
    $('#baseData1').DataTable($.extend(dtOption,dtTemplateOption));
    //添加事件
    //给关闭按钮添加关闭事件
    //绑定第一个复选框为反选按钮
    $("#baseData1 :checkbox:first").click(function (evt) {
        $("#baseData1 :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
        })
    })
    $(".clsBtn").click(closeDialogBtn);
    $("#refreshBaseData").click(refreshBaseData);
    $("#addBaseData").click(function () {
        openBox("#baseDataAddBox", null, function () {
            //查询所有基础数据类型
            $.ajax({
                url: "baseData/queryType",
                type: "get",
                async: true,
                beforeSend:function () {
                    startProgress();
                },
                success: function (datas) {
                    endProgress();
                    var len = datas.length,
                        //用于存放select数据
                        selectData = [];
                    $.map(datas, function (data) {
                        selectData.push({
                            id: data.dataType,
                            text: data.typeName
                        });
                    })
                    //初始化select2表单
                    $("#baseDataType").select2({
                        data: selectData
                    });
                },
                error:function () {
                    endProgress();
                }
            });
        },true)
    });
    $("#deleteBaseData").click(function () {
        var checkedboxs = $("#baseData1 :checked");
        if (!checkedboxs.length) {
            openDialog("#infoDialog","<p>请选择一个以上的基础数据值</p>",null,true);
        } else {
            confirmDialog("<p>确定删除吗?</p>",function () {
                $.ajax({
                    url: "baseData/deleteBaseData",
                    type: "get",
                    data: $("#baseDataListForm").serialize(),
                    context: $(this),
                    beforeSend:function () {
                        startProgress();
                    },
                    success: function (data) {
                        endProgress();
                        if (data === true) {
                            //刷新
                            openDialog("#successDialog", "<p>删除成功</p>", null, true);
                            refreshBaseData();
                        } else {
                            openDialog("#errorDialog", "<p>删除失败，原因不明</p>", null, true);
                        }
                    },
                    error:function () {
                        endProgress();
                        openDialog("#errorDialog","<p>删除失败，服务器端错误</p>",null,true);
                    }
                });
            })
        }
    });
    $("#updateBaseData").click(function () {
        var $checkedboxs = $("#baseData1 :checked");
        if ($checkedboxs.length != 1) {
            openDialog("#infoDialog","<p>请选择一个基础数据</p>",null,true);
        } else {
            openBox("#baseDataAddBox", null, function () {
                //查询所有基础数据类型,并添加到下拉框中
                $.ajax({
                    url: "baseData/queryType",
                    type: "get",
                    async: true,
                    beforeSend:function () {
                        startProgress();
                    },
                    success: function (datas) {
                        endProgress();
                        var len = datas.length,
                            //用于存放select数据
                            selectData = [];
                        $.map(datas, function (data) {
                            selectData.push({
                                id: data.dataType,
                                text: data.typeName
                            });
                        })
                        //初始化select2表单
                        $("#baseDataType").select2({
                            data: selectData
                        });
                    },
                    error:function () {
                        endProgress();
                    }
                });

                var id = $checkedboxs.val();
                //添加id到隐藏表单中
                $("#baseDataId").val(id);
                var baseDataTypeName = $checkedboxs.closest("tr").find(".dataType").text(),
                    name = $checkedboxs.closest("tr").find(".name").text(),
                    baseDataType = null;
                switch (baseDataTypeName) {
                    case "试题类型":
                        baseDataType = "subjectType";
                        break;
                    case "试卷类型":
                        baseDataType = "paperType";
                        break;
                }
                $("#baseDataType").val(baseDataType).trigger("change");
                $("#baseDataName").val(name);
            },true);
        }
    });
    //点击按钮保存基础数据
    $("#submitDataBaseBtn").click(function () {
        $.ajax({
            type: "get",
            data: $("#baseDataForm").serialize(),
            url: "baseData/mergeBaseData",
            beforeSend:function () {
                startProgress();
            },
            success: function (data) {
                endProgress();
                //如果数据不为空，说明保存成功
                if (data) {
                    openDialog("#successDialog","<p>更改成功</p>",null,true);
                    $("#baseDataId").val(data);
                    refreshBaseData();
                }else{
                    openDialog("#errorDialog","<p>更改失败，原因不明</p>",null,true);
                }
            },
            error:function () {
                endProgress();
                openDialog("#errorDialog","<p>更改失败，服务器端错误</p>",null,true);
            }
        })
    })
});
/**
 * 刷新基础数据表
 */
function refreshBaseData() {
    $('#baseData1').DataTable().ajax.reload();
}