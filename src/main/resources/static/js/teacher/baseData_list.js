/**
 *
 */
$(function () {
    $('#baseData1').DataTable({
        "paging": true,
        "lengthChange": true,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": true,
        "ajax": {
            url: "baseData/refreshBaseData",
            dataSrc: ''
        },
        "columns": [
            {data: 'uuid'},
            {data: 'dataType'},
            {data: 'name'}
        ],
        "columnDefs": [
            {
                "targets": [0],
                "data": "uuid",
                "render": function (data, type, full) {
                    return '<input name="baseDataIds" type="checkbox" class="baseDataCheckbox" value="' + data + '"/>'
                }
            },
            {
                "targets": [1],
                "data": "dataType",
                "render": function (data, type, full) {
                    var str = "";
                    if (data == "subjectType") {
                        str = "试题类型";
                    } else if (data == "paperType") {
                        str = "试卷类型";
                    }
                    return str;
                }
            }
        ]
    });
    $("#refreshBaseData").click(refreshBaseData);
});
/**
 * 刷新基础数据表
 */
function refreshBaseData() {
    $('#baseData1').DataTable().ajax.reload();
}