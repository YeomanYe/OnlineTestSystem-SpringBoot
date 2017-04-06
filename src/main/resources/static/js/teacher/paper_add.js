var subjectIds = [];

$(function () {
    var dtOption = {
        "rowCallback": function (row, data, index) {
            debugger;
            //双极显示试题信息
            $(row).dblclick(function () {
                var subjectId = $(this).find(":checkbox").val();
                subjectInfoShow(subjectId);
            });
        },
        "ajax": {
            url: "subject/refreshSubject",
            dataSrc: ''
        },
        "columns": [
            {
                data: 'uuid',
                orderable: false
            },
            {data: 'subjectType'},
            {
                data: 'subjectName',
                className: 'tb_subjectName'
            },
            {data: 'subjectScore'},
        ],
        "columnDefs": [
            {
                "targets": [0],
                "data": "uuid",
                "orderable":false,
                "render": function (data, type, full) {
                    return '<input name="subjectIds" onchange="checkboxChangeHandler(this)" type="checkbox" class="paperSubjectCheckbox" value="' + data + '"/>'
                }
            },
        ]
    };
    $('#subjectTable').DataTable($.extend(dtOption, dtTemplateOption));
    //初始化试卷类型
    $.ajax({
        url: "baseData/queryByType?dataType=paperType",
        type: "get",
        async: false,
        success: function (datas) {
            var len = datas.length;
            for (var i = 0; i < len; i++) {
                var $option = $("<option></option>").val(datas[i].uuid).text(datas[i].name);
                $("#paperType").append($option);
            }
        }
    });
    $(".clsBtn").click(closeDialogBtn);
    //初始化选择器二
    $("#paperType").select2();
    //绑定第一个复选框为反选按钮
    $("#paperFirstCheck").click(function (evt) {
        $("#subjectTable :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
            //触发属性改变事件
            $(this).change();
        })
    });
    $("#previewPaper").click(function () {
        openBox("#paperInfoBox", null, function () {
            //装载试卷类型信息
            var $paperInfo = $("#paperInfoBox").find(".box-body div:first");
            var paperName = $("#paperName").val(),
                ansTime = $("#ansTime").val(),
                paperScore = $("#paperScore").val(),
                subjectCnt = $("#subjectCnt").val(),
                paperDesc = $("#paperDesc").val(),
                paperType = $("#select2-paperType-container").text();
            var content = "";
            content += "<h3 class='text-center'>" + paperName + "</h3>";
            content += "<p><span class='col-xs-3'>答卷时间:" + ansTime + "</span>" +
                "<span class='paperInfoScore col-xs-3'>试卷总分:" + paperScore + "</span> " +
                " <span class='paperInfoSubjectCnt col-xs-3'>试题数量:" + subjectCnt + "</span>" +
                " <span class='col-xs-3'>试卷类型:" + paperType + "</span>" + "</p>";
            content += "<p class='col-xs-12'>试卷描述:" + paperDesc + "</p>";
            $paperInfo.html(content);
            //加载试题用的数据
            var allData = $("#subjectTable").DataTable().data(),
                loadData = [];
            for (var i = 0, len = subjectIds.length; i < len; i++) {
                for (var j = 0, len2 = allData.length; j < len2; j++) {
                    if (subjectIds[i] == allData[j].uuid) {
                        loadData.push(allData[j]);
                        break;
                    }
                }
            }
            //加载前先清空数据
            $("#paperInfoSubjectTable").DataTable().destroy();
            debugger;
            var dtPaperInfoOption = {
                "rowCallback": function (row, data, index) {
                    //双极显示试题信息
                    $(row).on("dblclick", function () {
                        var subjectId = $(this).find(":checkbox").val();
                        subjectInfoShow(subjectId);
                    });
                    //单击选择元素
                    $(row).on("click", function () {
                        debugger;
                        if ($(this).find(":checkbox").prop("checked")) {
                            $(this).find(":checkbox").prop({checked: false});
                        } else {
                            $(this).find(":checkbox").prop({checked: true});
                        }
                    })
                },
                "data": loadData,
                "columns": [
                    {data: 'uuid'},
                    {data: 'subjectType'},
                    {
                        data: 'subjectName',
                        className: 'tb_subjectName'
                    },
                    {
                        data: 'subjectScore',
                        className: 'subjectScore'
                    },
                ],
                "columnDefs": [
                    {
                        "targets": [0],
                        "data": "uuid",
                        "render": function (data, type, full) {
                            debugger
                            return '<input name="subjectIds" type="checkbox" class="paperSubjectCheckbox" value="' + data + '"/>'
                        }
                    },
                    {
                        "targets": [4],
                        "data": "uuid",
                        "render": function (data) {
                            return '<div style="cursor: pointer;" class="glyphicon glyphicon-list-alt margin" onclick="subjectInfoShow(\'' + data + '\')" title="详情"></div>'
                                + '<div style="cursor: pointer;" class="glyphicon glyphicon-remove margin" onclick="removeSubjectFromPaper(\'' + data + '\')" title="删除"></div>';
                        }
                    }
                ]
            };
            $("#paperInfoSubjectTable").DataTable($.extend(dtPaperInfoOption, dtTemplateOption));
        },true);
    });
    //提交按钮的事件
    $("#paperSubmit").click(function () {
        var data = "";
        data = $("#paperAddForm").serialize() + "&" + $.param({
                subjectIds: subjectIds, paperScore: $("#paperScore").val(), subjectCnt: $("#subjectCnt").val()
            }, true);
        $.ajax({
            url: "paper/mergePaper",
            data: data,
            type: "post",
            beforeSend: function () {
                startProgress();
            },
            success: function (data) {
                endProgress();
                if (data) {
                    openDialog("#successDialog", "<p>更改成功</p>", null, true);
                    $("#paperId").val(data);
                } else {
                    openDialog("#errorDialog", "<p>更改失败，原因不明</p>", null, true);
                }
            },
            error: function () {
                endProgress();
                openDialog("#errorDialog", "<p>更改失败，服务器端错误</p>", null, true);
            }
        })
    });
    $("#paperReset").click(function () {
        confirmDialog("<p>确定重置吗?</p>", resetAddPaperForm);
    });
});
/**
 * 试题勾选状态改变时，采取相应处理
 * @param that
 */
function checkboxChangeHandler(that) {
    debugger;
    var score = parseInt($("#paperScore").val()),
        cnt = parseInt($("#subjectCnt").val());
    if (that.checked) {
        subjectIds.push($(that).val());
        //修改分值，题量
        $("#paperScore").val(score + parseInt($(that).closest("tr").find("td:last").text()));
        $("#subjectCnt").val(++cnt);
    } else {
        var index = subjectIds.indexOf($(that).val());
        //修改分值，题量
        $("#paperScore").val(score - parseInt($(that).closest("tr").find("td:last").text()));
        $("#subjectCnt").val(--cnt);
        if (index != -1)
            subjectIds.splice(index, 1);
    }
}
/**
 * 将试题从试卷列表中删除
 * @param subjectId
 */
function removeSubjectFromPaper(subjectId) {
    //清除该行
    var table = $("#paperInfoSubjectTable").DataTable(),
        $checkbox = $("input[value=" + subjectId + "]"),
        tr = $checkbox.parents('tr');
    table.rows(tr).remove().draw();
    //将id相同的checkbox设置为未选中状态
    var $table1 = $("#subjectTable").DataTable(),
        $checkboxs = $table1.column(0).nodes().toJQuery().find(":checkbox");
    $checkboxs.each(function () {
        if ($(this).val() === $checkbox.val()) {
            $(this).prop({checked: false});
            return false;
        }
    });
    //修改表单中的分值，题量
    var score = parseInt($("#paperScore").val()),
        cnt = parseInt($("#subjectCnt").val()),
        curScore = score - tr.find(".subjectScore").text(),
        curCnt = --cnt;
    $("#paperScore").val(curScore);
    $("#subjectCnt").val(curCnt);
    $(".paperInfoSubjectCnt").text("试题数量:" + curCnt);
    $(".paperInfoScore").text("试卷总分:" + curScore);
    //去除subjectIds数组中的值
    var index = subjectIds.indexOf(subjectId);
    if (index != -1)
        subjectIds.splice(index, 1);
}
/**
 * 清空添加试卷表单
 */
function resetAddPaperForm() {
    $("#paperName").val("");
    $("#ansTime").val("");
    $("#paperScore").val("0");
    $("#subjectCnt").val("0");
    $("#paperDesc").val("");
    $("#subjectTable").DataTable().rows().invalidate().draw();
    $(":checked").prop({checked: false});
    subjectIds = [];
}