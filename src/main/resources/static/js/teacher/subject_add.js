$(function () {
    var subjectId = $("#subjectId").val();
    //初始化试题类型
    $.ajax({
        url: "baseData/queryByType?dataType=subjectType",
        type: "get",
        async: false,
        beforeSend:function () {
            startProgress();
        },
        success: function (datas) {
            endProgress();
            var len = datas.length;
            for (var i = 0; i < len; i++) {
                var $option = $("<option></option>").val(datas[i].uuid).text(datas[i].name);
                $("#subjectType").append($option);
            }
        },
        error:function () {
            endProgress();
        }
    });
    //如果存在ID说明是更新试题，并且没有选择试题类型
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
    $(".clsBtn").click(closeDialogBtn);
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
            beforeSend:function () {
                startProgress();
            },
            success: function (datas) {
                //显示添加成功提示
                endProgress();
                if(datas){
                    openDialog("#successDialog","<p>更改成功</p>",null,true);
                    $("input[name='subjectId']").val(datas.subjectId);
                    $(".subjectItemIds").each(function (index) {
                        $(this).val(datas.subjectItemIds[index])
                    })
                }else{
                    openDialog("#errorDialog","<p>更改失败，原因不明</p>",null,true);
                }
            },
            error:function () {
                endProgress();
                openDialog("#errorDialog","<p>更改失败，服务器端错误</p>",null,true);
            }
        });
    })
    $('button[type="reset"]').click(function(){
        confirmDialog("确定重置吗?",resetAddSubjectForm);
    });
    //打开上传图片会话框
    $("#uploadSubjectImg").click(function () {
        openBox("#subjectImgBox", null, function () {
            //将试题ID复制到subjectImgForm表单下
            var subjectId = $("#subjectId").val();
            debugger;
            $("#subjectImgForm input[name='subjectId']").val(subjectId);
            //清空原来的表格
            $("#subjectImgTable").DataTable().destroy();
            var dtOption = {
                "rowCallback": function (row, data, index) {
                    //单击选择元素
                    $(row).on("click",function () {
                        debugger;
                        if($(this).find(":checkbox").prop("checked")){
                            $(this).find(":checkbox").prop({checked:false});
                        }else{
                            $(this).find(":checkbox").prop({checked:true});
                        }
                    })
                },
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
            };
            $("#subjectImgTable").DataTable($.extend(dtOption,dtTemplateOption));

        });
    });
    //上传图片
    $("#uploadImgBtn").click(function () {
        debugger;
        var subjectId = $("#subjectImgForm input[name='subjectId']").val();
        //不存在ID则不能上传
        if (subjectId == "") {
            return;
        }
        console.log(subjectId);
        uploadFile("#subjectImgForm", "image/saveImage", refreshSubjectImgTable)();
    });
    $("#subjectImgFile").on('change', function () {
        selectImage(this,function (data) {
            hasImage = data;
        });
    });
    //预览试题
    $("#previewSubject").click(function () {
        var content = "",
            subjectId = $("#subjectId").val(),
            subjectName = "<p style='font-weight: bold;'>" + $("#subjectName").val() + "</p>",
            subjectParse = "解析: " + $("#subjectParse").val(),
            subjectItems = "",
            $answers = $("input[name='answers']"),
            answer = "答案: ",
            num = 0;
        $answers.each(function (index) {
            if ($(this).val() == "true") {
                answer += ALPHA_CONSTANT.charAt(index);
            }
            num++;
        });
        for (var i = 0; i < num; i++) {
            subjectItems += "<p>" + ALPHA_CONSTANT.charAt(i) + ". " + $("input[name='subjectItem" + i + "']").val() + "</p>";
        }
        content += subjectName;
        if (hasImage) {
            content += '<img class="img-responsive pad" src="' + hasImage + '">';
        }else if(subjectId){
            //如果存在试题ID，查询其相应的图片
            $.ajax({
                url:"image/queryImageBySubjectId?subjectId=" + subjectId,
                type:"get",
                async:false,
                success:function (datas) {
                    if(datas.length){
                        content += '<img class="img-responsive pad" src="' + datas[0].relPath + '">';
                    }
                }
            });
        }
        content += subjectItems;
        content += "<p style='font-weight: bold;'>" + answer + "</p>";
        content += "<p>" + subjectParse + "</p>";
        openDialog("#subjectPreviewDialog", content);

    })
    //初始化选择器二
    $("#subjectType").select2();
});
/**
 * 重置添加试题表单
 */
function resetAddSubjectForm() {
    debugger;
    $("#subjectName").val("");
    $("#subjectScore").val("");
    $("#subjectParse").val("");
    $("#subjectImgFile").val("");
    //清空试题选项值
    var len = $("#subjectItem input[type='hidden']").length;
    for(var i=0;i<len;i++){
        $("input[name='subjectItem"+i+"']").val("");
    }
    //清空图片缓存
    hasImage = null;
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
    confirmDialog("<p>确定移除图片吗?</p>",function () {
        $.ajax({
            url: "image/removeImage?imageId=" + uuid,
            type: "get",
            context: $(this),
            beforeSend:function () {
                startProgress();
            },
            success: function (data) {
                endProgress();
                if (data == true) {
                    openDialog("#successDialog", "<p>删除成功!</p>", null, true);
                    refreshSubjectImgTable();
                } else {
                    openDialog("#errorDialog", "<p>删除失败,原因未知</p>", null, true);
                }
            },
            error:function () {
                endProgress();
                openDialog("#errorDialog", "<p>删除失败,服务器端错误</p>", null, true);
            }
        });
    });
}

var hasImage = null; //判断是否有试题图片,有图片则存入图片数据
