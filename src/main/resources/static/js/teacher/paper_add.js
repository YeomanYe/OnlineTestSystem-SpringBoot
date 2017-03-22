var subjectIds = [];

$(function () {
    $('#subjectTable').DataTable({
        "paging": true,
        "lengthChange": true,
        "searching": true,
        "ordering": false,
        "info": true,
        "autoWidth": true,
        "ajax": {
            url: "subject/refreshSubject",
            dataSrc: ''
        },
        "columns": [
            {data: 'uuid'},
            {data: 'subjectType'},
            {
                data: 'subjectName',
                className: 'tb_subjectName'
            },
            {data: 'subjectScore'},
        ],
        "columnDefs": [
            {
                "targets": [0],
                "data": "uuid",
                "render": function (data, type, full) {
                    debugger
                    return '<input name="subjectIds" onchange="checkboxChangeHandler(this)" type="checkbox" class="subjectCheckbox" value="' + data + '"/>'
                }
            },
        ]
    });
    //初始化试卷类型
    $.ajax({
        url:"baseData/queryByType?dataType=paperType",
        type:"get",
        async:false,
        success:function (datas) {
            var len = datas.length;
            for(var i=0;i<len;i++){
                var $option = $("<option></option>").val(datas[i].uuid).text(datas[i].name);
                $("#paperType").append($option);
            }
        }
    });
    //初始化选择器二
    $("#paperType").select2();
    //绑定第一个复选框为反选按钮
    $("#paperFirstCheck").click(function (evt) {
        $("#subjectTable :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
            //触发属性改变事件
            $(this).change();
        })
    });
    //提交按钮的事件
    $("#paperSubmit").click(function () {
        var data = "";
       data = $("#paperAddForm").serialize() + "&" + $.param({
               subjectIds:subjectIds,paperScore:$("#paperScore").val(),subjectCnt:$("#subjectCnt").val()},true);
        console.log(data);
        $.ajax({
            url:"paper/mergePaper",
            data:data,
            type:"post",
            success:function (data) {
                if(data) $("#paperId").val(data);
            }
        })
    })
    $("#paperReset").click(resetAddPaperForm);
});
/**
 * 试题勾选状态改变时，采取相应处理
 * @param that
 */
function checkboxChangeHandler(that) {
    debugger;
    var score = parseInt($("#paperScore").val()),
        cnt = parseInt($("#subjectCnt").val());
    if(that.checked){
        subjectIds.push($(that).val());
        //修改分值，题量
        $("#paperScore").val(score + parseInt($(that).closest("tr").find("td:last").text()));
        $("#subjectCnt").val(++cnt);
    }else{
        var index = subjectIds.indexOf($(that).val());
        //修改分值，题量
        $("#paperScore").val(score - parseInt($(that).closest("tr").find("td:last").text()));
        $("#subjectCnt").val(--cnt);
        if(index != -1)
            subjectIds.splice(index,1);
    }
}
/**
 * 清空添加试卷表单
 */
function resetAddPaperForm() {
    $("#paperName").val("");
    $("#ansTime").val("");
    $("#paperScore").val("0");
    $("#subjectCnt").val("0");
    $("#paperDesc").val("");
    $("#subjectTable").DataTable().rows().invalidate().draw();
    $(":checked").prop({checked:false});
    subjectIds = [];
}