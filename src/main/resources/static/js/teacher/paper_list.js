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
        "autoWidth": true,
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

    /*
     $(".clsBtn").click(function () {
     //获取需要关闭的modal的ID
     var modal = $(this).data("dismiss");
     $("#" + modal).hide();

     })
     */
    //绑定事件处理函数
    //给关闭按钮添加事件
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
                        for(var i=0;i<len;i++){
                            if(subjectIds[i] == $check.val())
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
var ALPHA_CONSTANT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
/**
 * 点击显示试题信息
 必须在页面中绑定，因为使用的前端分页不会扫描出所有元素，造成除第一页的元素都没有事件
 */
function paperInfoClick() {
    $(".tb_paperInfo").click(function () {
        //获取paperId
        var uuid = $(this).parent().siblings().children().filter(":checkbox").val();
        $.ajax({
            url: "paper/queryPaperInfo?uuid=" + uuid,
            type: "get",
            context: $(this),
            success: function (datas) {
                var paperNameText = $(this).parent().siblings().filter(".tb_paperName").text();
                var digBody = $("#dig_paperName").parent();
                digBody.html("");
                digBody.append($("<p id='dig_paperName'>" + paperNameText + "</p>"));
                var answerStr = "答案: ";
                for (var i = 0, len = datas.length; i < len; i++) {
                    $("#dig_paperName").parent().append($("<p>" + ALPHA_CONSTANT.charAt(i) + ". " + datas[i].name + "</p>"));
                    if (datas[i].answer) answerStr += ALPHA_CONSTANT.charAt(i);
                }
                $("#dig_paperName").parent().append("<p>" + answerStr + "</p>")
                $("#paperInfoDialog").css({"display": "block"})

            }
        });
    });
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
/**
 * 刷新试卷
 */
function paperRefresh() {
    $('#paper1').DataTable().ajax.reload();
}