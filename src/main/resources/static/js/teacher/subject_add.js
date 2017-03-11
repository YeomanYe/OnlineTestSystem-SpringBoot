$(function () {
    var subjectId = $("#subjectId").val();
    //初始化试题类型
    $.ajax({
        url:"baseData/queryByType?dataType=subjectType",
        type:"get",
        async:false,
        success:function (datas) {
            var len = datas.length;
            for(var i=0;i<len;i++){
                var $option = $("<option></option>").val(datas[i].uuid).text(datas[i].name);
                $("#subjectType").append($option);
            }
        }
    });
    //如果存在ID说明是更新试题，并且没有选择试题类型
    //查询试题对应的类型
    $("select[name='subjectType']");
    //初始化选择器二
    $(".select2").select2();
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

    /**
     * 事件处理函数
     */
    $("input[name='answers']").click(function (event) {
        event.stopPropagation();
        event.preventDefault();
        var $parent = $(this).parent();
        if ($parent.hasClass("checked")) {
            $parent.removeClass("checked");
            $(this).val("false");
        } else {
            $parent.addClass("checked");
            $(this).filter(":checkbox").val("true");
        }
    })
    $(".removeItem").click(function () {
        //至少存在一个选项
        if ($("#subjectItem").children().length > 2) {
            $(this).parent().remove();
            resetName();
        }
        ;
    });
    $(".addItem").click(function () {
        //复制一个输入框到表单中
        $("#subjectItem").append($(this).parent().clone(true));
        //去除ID和值
        $("#subjectItem").find(".input-group:last").find("input[type='text']").val("");
        resetName();
    });
    $("button[type='submit']").click(function () {
        debugger
        $.ajax({
            url: "subject/mergeSubject",
            type: "post",
            data: $("#subjectItemForm").serialize(),
            context: $(this),
            success: function (datas) {
                //显示添加成功提示
                debugger
                $("input[name='subjectId']").val(datas.subjectId);
                $(".subjectItemIds").each(function (index) {
                    $(this).val(datas.subjectItemIds[index])
                })
            }
        });
    })
    $('button[type="reset"]').click(resetAddSubjectForm);
});

function resetAddSubjectForm() {
    debugger;
    $("textarea").val("");
    $("input[type='text']").val("");
}