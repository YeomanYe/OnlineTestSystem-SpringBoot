/**
 * Created by KINGBOOK on 2017/3/2.
 */
$(function () {
    var dtOption = {
        "rowCallback": function (row, data, index) {
            //双极显示试题信息
            $(row).on("dblclick", function () {
                var subjectId = $(this).find(":checkbox").val();
                subjectInfoShow(subjectId);
            });
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
            url: "subject/refreshSubject",
            dataSrc: ''
        },
        "columns": [
            {
                data: 'uuid'
            },
            {
                data: 'subjectName',
                className: 'tb_subjectName'
            },
            {data: 'subjectType'},
            {data: 'subjectScore'},
            {data: 'updateWhenStr'},
        ],
        "columnDefs": [
            {
                "targets": [0],
                "orderable":false,
                "data": "uuid",
                "render": function (data, type, full) {

                    return '<input name="subjectIds" type="checkbox" class="subjectCheckbox" value="' + data + '"/>'
                }
            },
            {
                "targets": [5],
                "orderable":false,
                "data": "uuid",
                "render": function (data) {
                    return '<div class="fa fa-fw fa-file tb_subjectInfo" onclick="subjectInfoShow(\'' + data + '\')" title="详情"></div>';
                }
            }
        ]
    };
    $('#subject1').DataTable($.extend(dtOption,dtTemplateOption));
    //绑定事件处理函数
    //给关闭按钮添加事件
    //给关闭按钮添加关闭事件
    $(".clsBtn").click(closeDialogBtn);
    $("#addSubject").click(toggleTabs("subjectAddTab", "添加试题", "subject/addSubjectPage", null,
        function () {
            //清除添加表单
            resetAddSubjectForm();
            //清除subjectID值
            $("#subjectId").val("");
        }));
    $("#updateSubject").click(function () {
        var checkedboxs = $(".subjectCheckbox:checked");
        if (checkedboxs.length != 1) {
            openDialog("#infoDialog", "<p>请选择一个试题</p>", null, true);
        } else {
            //切换到添加试题标签
            toggleTabs("subjectAddTab", "添加试题", "subject/addSubjectPage", null)();
            var subjectId = checkedboxs.val();
            //添加subjectId到隐藏表单中
            $("#subjectId").val(subjectId);
            //拼接试题选项
            $.ajax({
                type: "get",
                url: "subject/querySubject4Update?subjectId=" + subjectId,
                context: $(this),
                beforeSend: function () {
                    startProgress()
                },
                error: function () {
                    endProgress();
                },
                success: function (data) {
                    endProgress();
                    //清空所有选项
                    $("#subjectItem .input-group:not(:first)").remove();
                    var items = data.items,
                        subject = data.subject;
                    //装入试题信息到表单
                    $("#subjectId").val(subject.uuid);
                    $("#subjectName").val(subject.subjectName);
                    $("#subjectScore").val(subject.subjectScore);
                    $("#subjectParse").val(subject.subjectParse);
                    $("#subjectType").val(subject.subjectType).trigger("change");
                    //装入试题项信息到表单
                    var len = items.length;
                    for (var i = 0; i < len; i++) {
                        //复制一个表单
                        if (i) {
                            $("#subjectItem").append($("#subjectItem .input-group:first").clone(true));
                            resetName();
                        }
                        //除去第n个表单的选择状态,子元素从2开始
                        $("#subjectItem .input-group:nth-child(" + (i + 2) + ") .checked").removeClass("checked");
                        $("#subjectItem .input-group:nth-child(" + (i + 2) + ") :checkbox").val("false");
                        var $subItem = $("input[name='subjectItem" + i + "']");
                        $subItem.val(items[i].name);
                        $("input[name='subjectItemId" + i + "']").val(items[i].uuid);
                        if (items[i].answer) {
                            $subItem.parent().find(".icheckbox_flat-green").addClass("checked")
                                .find(":checkbox").val("true");
                        }
                    }
                }
            });
        }
    });
    $("#deleteSubject").click(function () {
        var checkedboxs = $(".subjectCheckbox:checked");
        if (!checkedboxs.length) {
            openDialog("#infoDialog", "<p>请选择一个试题</p>", null, true);
        } else {
            confirmDialog("<p>确认删除吗?</p>", function () {
                $.ajax({
                    url: "subject/deleteSubject",
                    type: "get",
                    data: $("#subjectListForm").serialize(),
                    context: $(this),
                    beforeSend: function () {
                        startProgress();
                    },
                    success: function (data) {
                        endProgress();
                        if (data === true) {
                            openDialog("#successDialog", "<p>删除成功</p>", null, true);
                            //刷新
                            subjectRefresh();
                        } else {
                            openDialog("#errorDialog", "<p>删除失败，未知错误</p>", null, true);
                        }
                    },
                    error: function () {
                        endProgress();
                        openDialog("#errorDialog", "<p>删除失败,请检查试卷中有无包含试题</p>", null, true);
                    }
                })
            });
        }
    });
    $("#refreshSubject").click(subjectRefresh);
    $("#chartSubject").click(function () {
        openDialog("#subjectChartDialog", null, null, true);
    });
    $("#uploadSubject").click(function () {
        openDialog("#subjectUploadDialog", null, null, true)
    });
    //绑定第一个复选框为反选按钮
    $("#subjectFirstCheck").click(function (evt) {
        $("#subject1 :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
        })
    });
    //图表Dialog,点击按钮切换样式事件
    $(".blue-button-group button").click(setBtnStyle("btn-danger", "btn-default"));
    $(".green-button-group button").click(setBtnStyle("btn-success", "btn-default"));
    $("#launchStaSubject").click(staHandler("#subjectChartCanvase", [{
            selector: "#staSubjectUpdateWhen",
            url: "subject/queryForSta?type=updateWhen"
        }, {
            selector: "#staSubjectUpdateBy",
            url: "subject/queryForSta?type=updateBy"
        }, {
            selector: "#staSubjectType",
            url: "subject/queryForSta?type=type"
        }, {
            selector: "#staSubjectScore",
            url: "subject/queryForSta?type=score"
        }], ["#staSubjectBar", "#staSubjectLine", "#staSubjectRadar", "#staSubjectDoughnut"],
        ["更新时间", "最后更新者", "类型", "分数"]));
    //上传文件触发ajax文件上传事件
    $("#uploadSubjectBtn").click(uploadFile("#subjectUploadForm", "subject/uploadExcel", subjectRefresh));
});


/**
 * 刷新试题
 */
function subjectRefresh() {
    $('#subject1').DataTable().ajax.reload();
}
/**
 * 将试题项输入框的name以及输入框的name重新编排
 */
function resetName() {
    $("#subjectItem input[type='text']").each(function (index) {
        $(this).attr({name: "subjectItem" + index});
    })
    $("input.subjectItemIds").each(function (index) {
        $(this).attr({name: "subjectItemId" + index});
    })
}

