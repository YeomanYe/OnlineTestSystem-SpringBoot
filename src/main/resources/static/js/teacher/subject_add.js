$(function () {
    //如果subject不为空,说明是更新。查询subjectItem进行填充
    var subjectId = $("#subjectId").val();
    if (subjectId) {
        $.ajax({
            url: "subject/updateSubjectItem?subjectId=" + subjectId,
            type: "get",
            context: $(this),
            success:function (datas) {
                debugger
                var len = datas.length;
                for(var i=0;i<len;i++){
                    //复制一个表单
                    if(i){
                        $("#subjectItem").append($("#subjectItem .input-group:first").clone(true));
                        resetName();
                    }
                    var $subItem = $("input[name='subjectItem"+i+"']");
                    $subItem.val(datas[i].name);
                    $("input[name='subjectItemId"+i+"']").val(datas[i].uuid);
                    if(datas[i].answer){
                        $subItem.parent().find(".icheckbox_flat-green").addClass("checked")
                            .find(":checkbox").val("true");
                    }
                }
            }
        })
    }
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
        resetName();
    });
    $("button[type='submit']").click(function () {
        debugger
        $.ajax({
            url: "subject/addSubject?" + $("#subjectItemForm").serialize(),
            type: "get",
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
});

