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
    $("#addSubject").click(function () {
        $.ajax({
            url: "subject/addSubjectPage",
            type: "get",
            context: $(this),
            success: function (data) {
                //设置内容头
                $(".content-header").html('' +
                    '<h1>添加试题 ' +
                    '<small>subject add</small> ' +
                    '</h1> ' +
                    '<ol class="breadcrumb"> ' +
                    '<li><a href="#">' +
                    '<i class="fa fa-dashboard"></i> 主目录</a></li> ' +
                    '<li><a href="#">试题管理</a></li> ' +
                    '<li class="active">试题添加</li> </ol>');
                //设置内容体
                $(".content").html(data);
            }
        });
    })
    $("#updateSubject").click(function () {
        var checkedboxs = $(".subjectCheckbox:checked");
        if(checkedboxs.length != 1){

        }else{
            $.ajax({
                url: "subject/updateSubjectPage?subjectId=" + $(".subjectCheckbox:checked").val(),
                type: "get",
                context: $(this),
                success: function (data) {
                    //设置内容头
                    $(".content-header").html('' +
                        '<h1>添加试题 ' +
                        '<small>subject add</small> ' +
                        '</h1> ' +
                        '<ol class="breadcrumb"> ' +
                        '<li><a href="#">' +
                        '<i class="fa fa-dashboard"></i> 主目录</a></li> ' +
                        '<li><a href="#">试题管理</a></li> ' +
                        '<li class="active">试题添加</li> </ol>');
                    //设置内容体
                    $(".content").html(data);
                }
            })
        }
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
                $("#dig_subjectName").parent().append("<p>" + answerStr + "</p>")
                $("#subjectInfoDialog").css({"display": "block"})

            }
        });
    });
}