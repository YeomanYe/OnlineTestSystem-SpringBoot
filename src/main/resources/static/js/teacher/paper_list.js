/**
 * Created by KINGBOOK on 2017/3/2.
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
            url: "paper/refreshPaper",
            dataSrc: ''
        },
        "columns": [
            {data: 'uuid'},
            {
                data: 'paperName',
                className: 'tb_paperName'
            },
            {data: 'paperType'},
            {data: 'ansTime'},
            {data: 'subjectCnt'},
            {data: 'updateWhenStr'},
        ],
        "columnDefs": [
            {
                "targets": [0],
                "orderable":false,
                "data": "uuid",
                "render": function (data, type, full) {
                    return '<input name="paperIds" type="checkbox" class="paperCheckbox" value="' + data + '"/>'
                }
            },
            {
                "targets": [6],
                "orderable":false,
                "data":"uuid",
                "render": function (data) {
                    debugger
                    return '<div class="fa fa-fw fa-file tb_paperInfo" onclick="paperInfoClick(\''+data+'\')" title="详情"></div>';
                }
            }
        ]
    };
    $('#paper1').DataTable($.extend(dtOption,dtTemplateOption));
    //给关闭按钮添加关闭事件
    //给关闭按钮添加关闭事件
    $(".clsBtn").click(closeDialogBtn);
    //绑定事件处理函数
    $("#addPaper").click(toggleTabs("paperAddTab", "添加试题", "paper/addPaperPage", null,
        function () {
            //清除添加表单
            resetAddPaperForm();
            //清除paperID值
            $("#paperId").val("");
        }));
    $("#updatePaper").click(function () {
        var checkedboxs = $(".paperCheckbox:checked");
        if (checkedboxs.length != 1) {
            openDialog("#infoDialog","<p>请选择一个试卷</p>",null,true);
        } else {
            //切换到添加试题标签
            toggleTabs("paperAddTab", "添加试卷", "paper/addPaperPage", null)();
            var paperId = checkedboxs.val();
            console.log(paperId);
            //添加paperId到隐藏表单中
            $("#paperId").val(paperId);
            //拼接试题选项
            $.ajax({
                type: "get",
                url: "paper/queryPaper4Update?paperId=" + paperId,
                context: $(this),
                beforeSend:function () {
                    startProgress();
                },
                success: function (data) {
                    endProgress();
                    var table = $("#subjectTable").DataTable();
                    //将试题ID放入全局数组中
                    subjectIds = data.subjectIds;
                    //将试卷中的试题的checkbox置为选中状态
                    table.rows().iterator('row', function (context, index) {
                        var $check = $($(this.row(index).node())[0]).find(":checkbox");
                        var len = subjectIds.length;
                        for (var i = 0; i < len; i++) {
                            if (subjectIds[i] == $check.val())
                                $check.prop({checked: true});
                        }
                    })
                    //装填试卷信息
                    var paper = data.paper;
                    $("#paperId").val(paper.uuid);
                    $("#paperName").val(paper.paperName);
                    $("#ansTime").val(paper.ansTime);
                    $("#paperScore").val(paper.paperScore);
                    $("#subjectCnt").val(paper.subjectCnt);
                    $("#paperDesc").val(paper.paperDesc);
                    $("#paperType").val(paper.paperType).trigger("change");
                },
                error:function () {
                    endProgress();
                }
            });
        }
    });
    $("#deletePaper").click(function () {
        var checkedboxs = $("#paper1 :checked");
        if (!checkedboxs.length) {
            openDialog("#infoDialog","<p>请选择一个以上的试卷</p>",null,true);
        } else {
            confirmDialog("<p>确定删除吗?</p>",function () {
                $.ajax({
                    url: "paper/deletePaper",
                    type: "get",
                    data: $("#paperListForm").serialize(),
                    context: $(this),
                    beforeSend:function () {
                        startProgress();
                    },
                    success: function (data) {
                        debugger;
                        endProgress();
                        if (data === true) {
                            openDialog("#successDialog","<p>删除成功</p>",null,true);
                            paperRefresh();
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
    $("#refreshPaper").click(paperRefresh);
    $("#chartPaper").click(function () {
        openDialog("#paperChartDialog", null,null,true);
    })
    //图表Dialog,点击按钮切换样式事件
    $(".blue-button-group button").click(setBtnStyle("btn-danger", "btn-default"));
    $(".green-button-group button").click(setBtnStyle("btn-success", "btn-default"));
    $("#launchStaPaper").click(staHandler("#paperChartCanvase", [{
            selector: "#staPaperUpdateWhen",
            url: "paper/queryForSta?type=updateWhen"
        }, {
            selector: "#staPaperSubjectCnt",
            url: "paper/queryForSta?type=subjectCnt"
        }, {
            selector: "#staPaperType",
            url: "paper/queryForSta?type=type"
        }, {
            selector: "#staPaperAnsTime",
            url: "paper/queryForSta?type=ansTime"
        }], ["#staPaperBar", "#staPaperLine", "#staPaperRadar", "#staPaperDoughnut"],
        ["更新时间", "试卷题量", "试卷类型", "答卷时间"]));
    //绑定第一个复选框为反选按钮
    $("#paper1 :checkbox:first").click(function (evt) {
        $("#paper1 :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
        })
    })
});

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
/**
 * 刷新试卷
 */
function paperRefresh() {
    $('#paper1').DataTable().ajax.reload();
}

function paperInfoClick(id) {
    openBox("#paperInfoBox", null, function () {
        $.ajax({
            url: "paper/queryPaperInfo?paperId="+id,
            type: "get",
            beforeSend:function () {
                startProgress();
            },
            success: function (data) {
                debugger;
                endProgress();
                //装载试卷类型信息
                var $paperInfo = $("#paperInfoBox").find(".box-body div:first");
                if (data) {
                    var paper = data.paper,
                        paperName = paper.paperName,
                        ansTime = paper.ansTime,
                        paperScore = paper.paperScore,
                        subjectCnt = paper.subjectCnt,
                        paperDesc = paper.paperDesc,
                        paperType = paper.paperType;
                    var content = "";
                    content += "<h3 class='text-center'>" + paperName + "</h3>";
                    content += "<p><span class='col-xs-3'>答卷时间:" + ansTime + "</span>" +
                        "<span class='paperInfoScore col-xs-3'>试卷总分:" + paperScore + "</span> " +
                        " <span class='paperInfoSubjectCnt col-xs-3'>试题数量:" + subjectCnt + "</span>" +
                        " <span class='col-xs-3'>试卷类型:" + paperType + "</span>" + "</p>";
                    content += "<p class='col-xs-12'>试卷描述:" + paperDesc + "</p>";
                    $paperInfo.html(content);

                    //加载前先清空数据
                    $("#paperInfoSubjectTable").DataTable().destroy();
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
                        "data": data.subjects,
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
                                    return '<div style="cursor: pointer;" class="glyphicon glyphicon-list-alt margin" onclick="subjectInfoShow(\'' + data + '\')" title="详情"></div>';
                                }
                            }
                        ]
                    };
                    $("#paperInfoSubjectTable").DataTable($.extend(dtPaperInfoOption, dtTemplateOption));
                }
            },
            error:function () {
                endProgress();
            }
        });
    },true);
}