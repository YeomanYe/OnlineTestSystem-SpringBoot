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
            {
                data: 'subjectName',
                className: 'tb_subjectName'
            },
            {data: 'subjectType'},
            {data: 'subjectScore'},
        ],
        "columnDefs": [
            {
                "targets": [0],
                "data": "uuid",
                "render": function (data, type, full) {
                    debugger
                    return '<input name="subjectIds" type="checkbox" class="subjectCheckbox" value="' + data + '"/>'
                }
            },
            /*{
                "targets": [4],
                "render": function () {
                    return '<div class="fa fa-fw fa-file tb_subjectInfo" onclick="subjectInfoClick()" title="详情"></div>';
                }
            }*/
        ]
    });
    //初始化选择器二
    $(".select2").select2();
    //绑定第一个复选框为反选按钮
    $("#paperFirstCheck").click(function (evt) {
        $("table :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
        })
    });
});
