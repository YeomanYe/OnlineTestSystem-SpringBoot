/**
 * Created by KINGBOOK on 2017/3/2.
 */
$(function () {
    $('#subject1').DataTable({
        "paging": true,
        "lengthChange": true,
        "searching": true,
        "ordering": true,
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
            {data: 'updateWhenStr'},
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
            {
                "targets": [5],
                "render": function () {
                    return '<div class="fa fa-fw fa-file tb_subjectInfo" onclick="subjectInfoClick()" title="详情"></div>';
                }
            }
        ]
    });
    //绑定事件处理函数
    //给关闭按钮添加事件
    $(".clsBtn").click(function () {
        //获取需要关闭的modal的ID
        var modal = $(this).data("dismiss");
        $("#" + modal).hide();

    })
    $("#addSubject").click(toggleTabs("subjectAddTab", "添加试题", "subject/addSubjectPage", null,
        function () {
            //清除添加表单
            resetAddSubjectForm();
            //清除subjectID值
            $("#subjectId").val("");
        }));
    $("#updateSubject").click(function () {
        var checkedboxs = $(".subjectCheckbox:checked");
        if (checkedboxs.length != 1) {

        } else {
            //切换到添加试题标签
            toggleTabs("subjectAddTab", "添加试题", "subject/addSubjectPage", null)();
            var subjectId = checkedboxs.val();
            //添加subjectId到隐藏表单中
            $("#subjectId").val(subjectId);
            //拼接试题选项
            $.ajax({
                type: "get",
                url: "subject/querySubject4Update?subjectId=" + subjectId,
                context: $(this),
                success: function (data) {
                    debugger;
                    //清空所有选项
                    $("#subjectItem .input-group:not(:first)").remove();
                    var items = data.items,
                        subject = data.subject;
                    //装入试题信息到表单
                    $("#subjectId").val(subject.uuid);
                    $("#subjectName").val(subject.subjectName);
                    $("#subjectScore").val(subject.subjectScore);
                    $("#subjectParse").val(subject.subjectParse);
                    $("#subjectType option").each(function (index) {
                        if (subject.subjectType == $(this).val()) {
                            $(this).prop('selected', true);
                        }
                    })
                    //装入试题项信息到表单
                    var len = items.length;
                    for (var i = 0; i < len; i++) {
                        //复制一个表单
                        if (i) {
                            $("#subjectItem").append($("#subjectItem .input-group:first").clone(true));
                            resetName();
                        }
                        //除去第n个表单的选择状态,子元素从2开始
                        $("#subjectItem .input-group:nth-child(" + (i + 2) + ") .checked").removeClass("checked");
                        $("#subjectItem .input-group:nth-child(" + (i + 2) + ") :checkbox").val("false");
                        var $subItem = $("input[name='subjectItem" + i + "']");
                        $subItem.val(items[i].name);
                        $("input[name='subjectItemId" + i + "']").val(items[i].uuid);
                        if (items[i].answer) {
                            $subItem.parent().find(".icheckbox_flat-green").addClass("checked")
                                .find(":checkbox").val("true");
                        }
                    }
                }
            });
        }
    });
    $("#deleteSubject").click(function () {
        var checkedboxs = $("table :checked");
        if (!checkedboxs.length) {

        } else {
            $.ajax({
                url: "subject/deleteSubject",
                type: "get",
                data: $("#subjectListForm").serialize(),
                context: $(this),
                success: function (data) {
                    debugger
                    if (data === true) {
                        //刷新
                        subjectRefresh();
                    }
                }
            })
        }
    });
    $("#refreshSubject").click(subjectRefresh);
    $("#chartSubject").click(function () {
        openDialog("#subjectChartDialog",null,showBar);
    })
    //绑定第一个复选框为反选按钮
    $("table :checkbox:first").click(function (evt) {
        $("table :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
        })
    });
    //设置图表Dialog,点击按钮切换样式事件
    $(".blue-button-group button").click(setBtnStyle("btn-danger", "btn-default"));
    $(".green-button-group button").click(setBtnStyle("btn-success", "btn-default"));
});
var ALPHA_CONSTANT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
/**
 * 点击显示试题信息
 必须在页面中绑定，因为使用的前端分页不会扫描出所有元素，造成除第一页的元素都没有事件
 */
function subjectInfoClick() {
    $(".tb_subjectInfo").click(function () {
        //获取subjectId
        var uuid = $(this).parent().siblings().children().filter(":checkbox").val();
        $.ajax({
            url: "subject/querySubjectInfo?uuid=" + uuid,
            type: "get",
            context: $(this),
            success: function (datas) {
                debugger
                var subjectNameText = $(this).parent().siblings().filter(".tb_subjectName").text();
                var answerStr = "答案: ";
                var subjectItem = "";
                for (var i = 0, len = datas.length; i < len; i++) {
                    subjectItem += "<p>" + ALPHA_CONSTANT.charAt(i) + ". " + datas[i].name + "</p>"
                    if (datas[i].answer) answerStr += ALPHA_CONSTANT.charAt(i);
                }
                var content = "<p id='dig_subjectName'>" + subjectNameText + "</p>"
                    + subjectItem + "<p>" + answerStr + "</p>";
                openDialog("#subjectInfoDialog", content);
            }
        });
    });
}
/**
 * 打开会话框并显示内容
 * @param digId
 * @param content
 * @param callback 回调函数
 */
function openDialog(digSelector, content, callback) {
    if (content) {
        var digBody = $(digSelector + " .modal-body");
        digBody.html("");
        digBody.append(content);
    }
    $(digSelector).css({"display": "block"});
    if (typeof callback === 'function') {
        callback();
    }
}
/**
 * 设置按钮组的按钮样式处理函数
 * @param goal 目标样式
 * @param origin 原样式
 */
function setBtnStyle(goal, origin) {
    return function () {
        debugger;
        //清除按钮的样式
        $(this).parent().children().filter("button").removeClass(goal).addClass(origin);
        //添加目标样式
        $(this).addClass(goal);
    };
}
/**
 * 刷新试题
 */
function subjectRefresh() {
    $('#subject1').DataTable().ajax.reload();
}
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
//展示柱状图
function showBar(){
    var ctx = $("#subjectChartCanvase")[0].getContext("2d");
    $.ajax({
        url: "subject/queryDateAndType",
        type: "get",
        context: $(this),
        success:function (datas) {
            var labelArr = [],
                dataArr = [];
            //将返回值封装为数组用于图表展示
            var len = datas.length;
            for(var i=0;i<len;i++){
                labelArr.push(datas[i].subjectType);
                dataArr.push(datas[i].cont);
            }
            // barData.labels = labelArr;
            debugger
            // barData.datasets[0].data = dataArr;
            var char = new Chart(ctx, {
                type: 'bar',
                data: barData
            });
        }
    })
}
var barData = {
    labels: ["January", "February", "March", "April", "May", "June", "July"],
    datasets: [
        {
            label: "试题统计",
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1,
            data: [65, 59, 80, 81, 56, 55, 40],
        }
    ]
};

var lineData = {
    labels: ["January", "February", "March", "April", "May", "June", "July"],
    datasets: [
        {
            label: "My First dataset",
            fill: false,
            lineTension: 0.1,
            backgroundColor: "rgba(75,192,192,0.4)",
            borderColor: "rgba(75,192,192,1)",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: "rgba(75,192,192,1)",
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "rgba(75,192,192,1)",
            pointHoverBorderColor: "rgba(220,220,220,1)",
            pointHoverBorderWidth: 2,
            pointRadius: 1,
            pointHitRadius: 10,
            data: [65, 59, 80, 81, 56, 55, 40],
            spanGaps: false,
        }
    ]
};

var redarData = {
    datasets: [{
        data: [
            11,
            16,
            7,
            3,
            14
        ],
        backgroundColor: [
            "#FF6384",
            "#4BC0C0",
            "#FFCE56",
            "#E7E9ED",
            "#36A2EB"
        ],
        label: 'My dataset' // for legend
    }],
    labels: [
        "Red",
        "Green",
        "Yellow",
        "Grey",
        "Blue"
    ]
};

var doughnutData = {
    labels: [
        "Red",
        "Blue",
        "Yellow"
    ],
    datasets: [
        {
            data: [300, 50, 100],
            backgroundColor: [
                "#FF6384",
                "#36A2EB",
                "#FFCE56"
            ],
            hoverBackgroundColor: [
                "#FF6384",
                "#36A2EB",
                "#FFCE56"
            ]
        }]
};