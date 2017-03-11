/**
 * Created by KINGBOOK on 2017/3/2.
 */
$(function () {
    $('#subject1').DataTable({
        "paging": true,
        "lengthChange": true,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": true
    });
    //绑定事件处理函数
    //给关闭按钮添加事件
    $(".clsBtn").click(function () {
        //获取需要关闭的modal的ID
        var modal = $(this).data("dismiss");
        $("#" + modal).hide();

    })
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
                success: function (data) {
                    debugger;
                    //清空所有选项
                    $("#subjectItem .input-group:not(:first)").remove();
                    var items = data.items,
                        subject = data.subject;
                    //装入试题信息到表单
                    $("#subjectId").val(subject.uuid);
                    $("#subjectName").val(subject.subjectName);
                    $("#subjectScore").val(subject.subjectScore);
                    $("#subjectParse").val(subject.subjectParse);
                    $("#subjectType option").each(function (index) {
                        if (subject.subjectType == $(this).val()) {
                            $(this).prop('selected', true);
                        }
                    })
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
    })
    $("#deleteSubject").click(function () {
        var checkedboxs = $("table :checked");
        if (!checkedboxs.length) {

        } else {
            //选出不含表头的checkbox元素,后台删除成功时，前台也删除
            var $checkedRow = $(":checked:not(.thCheckbox)").closest("tr");
            $.ajax({
                url: "subject/deleteSubject",
                type: "get",
                data: $("#subjectListForm").serialize(),
                context: $(this),
                success: function (data) {
                    debugger
                    if (data === true) {
                        $checkedRow.remove();
                    }
                }
            })
        }
    })
    //绑定第一个复选框为反选按钮
    $("table :checkbox:first").click(function (evt) {
        $("table :checkbox:not(:first)").each(function () {
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
function subjectInfoClick() {
    $(".tb_subjectInfo").click(function () {
        //获取subjectId
        var uuid = $(this).parent().siblings().children().filter(":checkbox").val();
        $.ajax({
            url: "subject/querySubjectInfo?uuid=" + uuid,
            type: "get",
            context: $(this),
            success: function (datas) {
                var subjectNameText = $(this).parent().siblings().filter(".tb_subjectName").text();
                var digBody = $("#dig_subjectName").parent();
                digBody.html("");
                digBody.append($("<p id='dig_subjectName'>" + subjectNameText + "</p>"));
                var answerStr = "答案: ";
                for (var i = 0, len = datas.length; i < len; i++) {
                    $("#dig_subjectName").parent().append($("<p>" + ALPHA_CONSTANT.charAt(i) + ". " + datas[i].name + "</p>"));
                    if (datas[i].answer) answerStr += ALPHA_CONSTANT.charAt(i);
                }
                $("#dig_subjectName").parent().append("<p>" + answerStr + "</p>");
                // $("#dig_subjectName").parent().append("<p>" + subjectParse + "</p>");
                $("#subjectInfoDialog").css({"display": "block"})

            }
        });
    });
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