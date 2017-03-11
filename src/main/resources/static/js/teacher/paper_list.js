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
        "autoWidth": true
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
    $("#addPaper").click(toggleTabs("paperAddTab", "添加试题", "paper/addPaperPage",null,
        function(){
            //清除添加表单
            resetAddPaperForm();
            //清除paperID值
            $("#paperId").val("");
        }));
    $("#updatePaper").click(function () {
        var checkedboxs = $(".paperCheckbox:checked");
        if(checkedboxs.length != 1){

        }else{
            //切换到添加试题标签
            toggleTabs("paperAddTab", "添加试题", "paper/addPaperPage",null)();
            var paperId = checkedboxs.val();
            //添加paperId到隐藏表单中
            $("#paperId").val(paperId);
            //拼接试题选项
            $.ajax({
                type: "get",
                url: "paper/queryPaper4Update?paperId=" + paperId,
                context: $(this),
                success: function (data) {

                }
            });
        }
    })
    $("#deletePaper").click(function () {
        var checkedboxs = $("table :checked");
        if(!checkedboxs.length){

        }else{
            //选出不含表头的checkbox元素,后台删除成功时，前台也删除
            var $checkedRow = $("#paper1 :checked:not(.thCheckbox)").closest("tr");
            $.ajax({
                url: "paper/deletePaper",
                type: "get",
                data: $("#paperListForm").serialize(),
                context: $(this),
                success:function (data) {
                    debugger
                    if(data===true){
                        $checkedRow.remove();
                    }
                }
            })
        }
    })
    //绑定第一个复选框为反选按钮
    $("table :checkbox:first").click(function (evt) {
        $("table :checkbox:not(:first)").each(function () {
            if(this.checked){
                $(this).prop("checked",false);
            }else{
                $(this).prop("checked",true);
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
 * 将试题项输入框的name以及输入框的name重新编排
 */
function resetName() {
    $("#paperItem input[type='text']").each(function (index) {
        $(this).attr({name: "paperItem" + index});
    })
    $("input.paperItemIds").each(function (index) {
        $(this).attr({name: "paperItemId" + index});
    })
}