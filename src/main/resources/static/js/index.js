$(function(){
    /*设置事件*/
   $("#query_subject").click(function () {
       $.get("subject/querySubject",function (data) {
            //设置内容头
           $(".content-header").html('' +
               '<h1>Data Tables ' +
               '<small>advanced tables</small> ' +
               '</h1> ' +
               '<ol class="breadcrumb"> ' +
               '<li><a href="#">' +
               '<i class="fa fa-dashboard"></i> 主目录</a></li> ' +
               '<li><a href="#">试题管理</a></li> ' +
               '<li class="active">试题查询</li> </ol>');
           //设置内容体
           $(".content").html(data);
       })
   })
});