/**
 * Created by KINGBOOK on 2017/3/2.
 */
$(function () {
    $('#paper1').DataTable({
        "paging": true,
        "lengthChange": true,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
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
                "data": "uuid",
                "render": function (data, type, full) {

                    return '<input name="paperIds" type="checkbox" class="paperCheckbox" value="' + data + '"/>'
                }
            },
            {
                "targets": [6],
                "render": function () {
                    return '<div class="fa fa-fw fa-file tb_paperInfo" onclick="paperInfoClick()" title="详情"></div>';
                }
            }
        ]
    });
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
                success: function (data) {
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
                }
            });
        }
    });
    $("#deletePaper").click(function () {
        var checkedboxs = $("#paper1 :checked");
        if (!checkedboxs.length) {

        } else {
            $.ajax({
                url: "paper/deletePaper",
                type: "get",
                data: $("#paperListForm").serialize(),
                context: $(this),
                success: function (data) {
                    debugger;
                    if (data === true) {
                        paperRefresh();
                    }
                }
            })
        }
    });
    $("#refreshPaper").click(paperRefresh);
    $("#chartPaper").click(function () {
        openDialog("#paperChartDialog", null);
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

