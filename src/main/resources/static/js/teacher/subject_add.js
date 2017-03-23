$(function () {
    var subjectId = $("#subjectId").val();
    //初始化试题类型
    $.ajax({
        url: "baseData/queryByType?dataType=subjectType",
        type: "get",
        async: false,
        success: function (datas) {
            var len = datas.length;
            for (var i = 0; i < len; i++) {
                var $option = $("<option></option>").val(datas[i].uuid).text(datas[i].name);
                $("#subjectType").append($option);
            }
        }
    });
    //如果存在ID说明是更新试题，并且没有选择试题类型
    //初始化选择器二
    $("#subjectType").select2();
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

    //给关闭按钮添加关闭事件
    $(".clsBtn").click(function () {
        //获取需要关闭的modal的ID
        var modal = $(this).data("dismiss");
        $("#" + modal).hide();
    });
    /**
     * 绑定事件处理函数
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
    $("#subjectSubmit").click(function () {
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
    //打开上传图片会话框
    $("#uploadSubjectImg").click(function () {
        openDialog("#subjectImgDialog", null, function () {
            //将试题ID复制到subjectImgForm表单下
            var subjectId = $("#subjectId").val();
            $("#subjectImgForm input[name='subjectId']").val(subjectId);
            //清空原来的表格
            $("#subjectImgTable").DataTable().destroy();

            $("#subjectImgTable").DataTable({
                "paging": false,
                "lengthChange": true,
                "searching": false,
                "ordering": false,
                "info": true,
                "autoWidth": true,
                "ajax": {
                    url: "image/queryImageBySubjectId?subjectId=" + subjectId,
                    dataSrc: ''
                },
                "columns": [
                    {data: 'fileType'},
                    {data: 'formerName'},
                    {data: 'fileSize'},
                    {data: 'updateWhenStr'},
                ],
                "columnDefs": [
                    {
                        "targets": [4],
                        "data": "uuid",
                        "render": function (data) {
                            return '<a class="glyphicon glyphicon-download margin" href="image/downloadImage?imageId=' + data + '" title="下载"></a>'
                                + '<div style="cursor: pointer;" class="glyphicon glyphicon-remove-circle margin" onclick="removeImage(\'' + data + '\')" title="删除"></div>';
                        }
                    }
                ]
            });

        });
    });
    //上传图片
    $("#uploadImgBtn").click(function () {
        var subjectId = $("#subjectImgForm input[name='subjectId']").val();
        //不存在ID则不能上传
        if (subjectId == "") {
            return;
        }
        console.log(subjectId);
        uploadFile("#subjectImgForm", "image/saveImage",refreshSubjectImgTable)();
    })
});
/**
 * 重置添加表单
 */
function resetAddSubjectForm() {
    debugger;
    $("textarea").val("");
    $("input[type='text']").val("");
}
/**
 * 刷新试题图片表格
 */
function refreshSubjectImgTable() {
    $("#subjectImgTable").DataTable().ajax.reload();
}
/**
 * 移除图片
 * @param uuid
 */
function removeImage(uuid) {
    $.ajax({
        url: "image/removeImage?imageId="+uuid,
        type: "get",
        context: $(this),
        success: function (data) {
            if(data==true){
                console.log("success");
                refreshSubjectImgTable();
            }else{
                console.log("error");
            }
        }
    });
}