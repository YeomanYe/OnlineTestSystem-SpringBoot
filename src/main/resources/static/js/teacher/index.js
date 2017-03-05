$(function(){
    /*设置事件处理函数*/
   $("#query_subject").click(function () {
       $.get("subject/querySubject",function (data) {
            //设置内容头
           $(".content-header").html('' +
               '<h1>试题列表 ' +
               '<small>subject list</small> ' +
               '</h1> ' +
               '<ol class="breadcrumb"> ' +
               '<li><a href="#">' +
               '<i class="fa fa-dashboard"></i> 主目录</a></li> ' +
               '<li><a href="#">试题管理</a></li> ' +
               '<li class="active">试题查询</li> </ol>');
           //设置内容体
           $(".content").html(data);
       })
   });
    $("#add_subject").click(function(){
        $.ajax({
            url: "subject/addSubjectPage",
            type: "get",
            context: $(this),
            success:function (data) {
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
    });
});