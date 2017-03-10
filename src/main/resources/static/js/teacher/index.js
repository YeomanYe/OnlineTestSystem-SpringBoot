$(function () {
    /*设置事件处理函数*/
    $("#query_subject").click(function () {
        if ($("#subjectListTab").length) {
            $("#subjectListTab").addClass("active");
            $("a[href='#subjectListTab']").parent().addClass("active");
        } else {
            $.ajax({
                url: "subject/querySubject",
                type: "get",
                success: function (data) {
                    $(".nav .nav-tabs").append('<li class="active"><a href="#subjectListTab" data-toggle="tab">试题列表</a></li>');
                    $(".tab-content").append(data);
                }
            })
        }

    });
    $("#add_subject").click(function () {
        if ($("#subjectAddTab").length) {
            $("#subjectAddTab").addClass("active");
            $("a[href='#subjectAddTab']").parent().addClass("active");
        } else {
            $.ajax({
                url: "subject/addSubjectPage",
                type: "get",
                success: function (data) {
                    $(".nav-tabs-custom").append(data);
                }
            });
        }
    });
});