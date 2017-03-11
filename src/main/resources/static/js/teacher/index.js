$(function () {
    toggleTabs("subjectListTab", "试题列表", "subject/listSubjectPage")();
    /*设置事件处理函数*/
    $("#query_subject").click(toggleTabs("subjectListTab", "试题列表", "subject/listSubjectPage"));
    $("#add_subject").click(toggleTabs("subjectAddTab", "添加试题", "subject/addSubjectPage",null,
                    function(){
                        //清除添加表单
                        resetAddSubjectForm();
                        //清除subjectID值
                        $("#subjectId").val("");
                    }));
    $("#query_paper").click(toggleTabs("paperListTab", "试卷列表", "paper/listPaperPage"));
});
/**
 * 切换标签页
 * @param tabId 标签页ID
 * @param url 请求的URL
 * @param ajaxPos ajax的参数(默认get方式，无参数
 * @param tabName 标签名
 * @pram toggleCallback 切换标签后的回调函数
 */
function toggleTabs(tabId, tabName, url, ajaxPos,toggleCallback) {
    return function () {
        if (ajaxPos) {
            //如果存在数据,则添加数据,没有则添加空字符串
            var ajaxData = ajaxPos.data ? ajaxPos.data : "";
        }
        //取消标签页的激活状态
        $(".nav-tabs li.active").removeClass("active");
        $(".tab-pane.active").removeClass("active");
        if ($("#" + tabId).length) {
            $("#" + tabId).addClass("active");
            $("a[href='#" + tabId + "']").parent().addClass("active");
            //调用回调函数
            if(typeof toggleCallback === "function") toggleCallback();
        } else {
            $.ajax({
                url: url,
                type: "get",
                data: ajaxData,
                async: false,
                success: function (data) {
                    debugger;
                    if (!$(".content-wrapper .tab-content").length) {
                        $(".nav-tabs-custom").append('<ul class="nav nav-tabs"><li class="active"><a href="#' + tabId + '" data-toggle="tab">' + tabName + '</a></li></ul>');
                        $(".nav-tabs-custom").append('<div class="tab-content">' + data + '</div>');
                    } else {
                        $(".nav-tabs").append('<li class="active"><a href="#' + tabId + '" data-toggle="tab">' + tabName + '</a></li>');
                        $(".tab-content").append(data);
                    }
                    //调用回调函数
                    if(typeof toggleCallback === "function") toggleCallback();

                }
            })
        }
    };
}