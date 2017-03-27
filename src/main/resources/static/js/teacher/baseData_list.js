/**
 *
 */
$(function () {
    $('#baseData1').DataTable({
        "paging": true,
        "lengthChange": true,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": true,
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
    });
    //添加事件
    //给关闭按钮添加关闭事件
    $(".clsBtn").click(function () {
        //获取需要关闭的modal的ID
        var modal = $(this).data("dismiss");
        $("#" + modal).hide();
    });
    $("#refreshBaseData").click(refreshBaseData);
    $("#addBaseData").click(function () {
        openDialog("#baseDataAddDialog", null, function () {
            //查询所有基础数据类型
            $.ajax({
                url: "baseData/queryType",
                type: "get",
                async: true,
                success: function (datas) {
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
                }
            });
        })
    });
    $("#deleteBaseData").click(function () {
        var checkedboxs = $("#baseData1 :checked");
        if (!checkedboxs.length) {

        } else {
            $.ajax({
                url: "baseData/deleteBaseData",
                type: "get",
                data: $("#baseDataListForm").serialize(),
                context: $(this),
                success: function (data) {
                    if (data === true) {
                        //刷新
                        refreshBaseData();
                    }
                }
            })
        }
    });
    $("#updateBaseData").click(function () {
        var $checkedboxs = $("#baseData1 :checked");
        if ($checkedboxs.length != 1) {

        } else {
            openDialog("#baseDataAddDialog", null, function () {
                //查询所有基础数据类型,并添加到下拉框中
                $.ajax({
                    url: "baseData/queryType",
                    type: "get",
                    async: true,
                    success: function (datas) {
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
            });
        }
    });
    //点击按钮保存试题
    $("#submitDataBaseBtn").click(function () {
        $.ajax({
            type: "get",
            data: $("#baseDataForm").serialize(),
            url: "baseData/mergeBaseData",
            success: function (data) {
                //如果数据不为空，说明保存成功
                if (data) {
                    debugger;
                    $("#baseDataId").val(data);
                    refreshBaseData();
                }
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