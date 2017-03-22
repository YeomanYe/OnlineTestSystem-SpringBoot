/**
 * Created by KINGBOOK on 2017/3/2.
 */
$(function () {
    $('#subject1').DataTable({
        "paging": true,
        "pageingType":"input",
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
                    ;
                    //清空所有选项
                    $("#subjectItem .input-group:not(:first)").remove();
                    var items = data.items,
                        subject = data.subject;
                    //装入试题信息到表单
                    $("#subjectId").val(subject.uuid);
                    $("#subjectName").val(subject.subjectName);
                    $("#subjectScore").val(subject.subjectScore);
                    $("#subjectParse").val(subject.subjectParse);
                    $("#subjectType").val(subject.subjectType).trigger("change");
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
        openDialog("#subjectChartDialog",null);
    });
    $("#uploadSubject").click(function () {
        openDialog("#subjectUploadDialog",null)
    });
    //绑定第一个复选框为反选按钮
    $("#subjectFirstCheck").click(function (evt) {
        $("#subject1 :checkbox:not(:first)").each(function () {
            if (this.checked) {
                $(this).prop("checked", false);
            } else {
                $(this).prop("checked", true);
            }
        })
    });
    //图表Dialog,点击按钮切换样式事件
    $(".blue-button-group button").click(setBtnStyle("btn-danger", "btn-default"));
    $(".green-button-group button").click(setBtnStyle("btn-success", "btn-default"));
    $("#launchStaSubject").click(staHandler("#subjectChartCanvase",[{
        selector:"#staSubjectUpdateWhen",
        url:"subject/queryUpdateWhenForSta"
    },{
        selector:"#staSubjectUpdateBy",
        url:"subject/queryUpdateByForSta"
    },{
        selector:"#staSubjectType",
        url:"subject/queryTypeForSta"
    },{
        selector:"#staSubjectScore",
        url:"subject/queryScoreForSta"
    }],["#staSubjectBar","#staSubjectLine","#staSubjectRadar","#staSubjectDoughnut"],
    ["更新时间","最后更新者","类型","分数"]))
    //上传文件触发ajax文件上传事件
    $("#uploadSubjectBtn").click(uploadFile("#subjectUploadForm","subject/uploadExcel",subjectRefresh));
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
        ;
        //清除按钮的样式
        $(this).parent().children().filter("button").removeClass(goal).addClass(origin);
        //添加目标样式
        $(this).addClass(goal);
    };
}
/**
 * 文件上传函数生成器
 * @param form
 * @param url
 * @param call 成功时的回调函数
 * @returns {Function}
 */
function uploadFile(form,url,call) {
    return function(){
        $(form).ajaxSubmit({
            type: "POST",
            url:url,
            success: function(data){
                if(data === true){
                    alert("success");
                    if(typeof call == "function") call();
                }
                else{
                    alert("error");
                }
            }
        });
    }
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

/**
 * 统计处理函数
 * @param urlAndSta {selector,url}
 * @param staTypes 统计类型按钮的选择器数组,顺序为bar,line,radar,doughnut
 * @param legends 标题数组或者字符串[]/string
 * @param canSelector canvas选择器
 * @returns {Function}
 */
function staHandler(canSelector,urlAndSta,staTypes,legends){
    var chart = null;
    var staTemplateData = [
        {
            labels: [],
            datasets: [
                {
                    label: "试题统计",
                    backgroundColor: [

                    ],
                    borderColor: [

                    ],
                    borderWidth: 1,
                    data: []
                }
            ]
        },
        {
            labels: [],
            datasets: [
                {
                    label: "",
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
                    data: [],
                    spanGaps: false
                }
            ]
        },
    ];
    return function(){
        var ctx = $(canSelector)[0].getContext("2d");
        //类型字符串数组
        var staTypeArr = ['bar','line','radar','doughnut'];
        //类型名
        var type = "";
        //循环遍历，有对应的类的按钮既是被选择的按钮
        var len = urlAndSta.length;
        var url = "";
        var data = null;
        //必须先获取数据data
        for(var i=0;i<len;i++){
            if($(staTypes[i]).hasClass("btn-success") && i!=1){
                //i不等于1时生成与图表对应的颜色数
                var boderColorArr = [],
                    bgColorArr = [];
                for(var j=0;j<len;j++){
                    var color = 'rgba('+randInt(255)+', '+randInt(150)+', '+randInt(50);
                    bgColorArr.push(color + ', 0.2)');
                    boderColorArr.push(color + ', 1)');
                }
                data = staTemplateData[0];
                data.datasets[0].backgroundColor = bgColorArr;
                data.datasets[0].borderColor = boderColorArr;
                type = staTypeArr[i];
            }else if($(staTypes[i]).hasClass("btn-success")){
                data = staTemplateData[1];
                type = staTypeArr[i];
            }
        }
        for(i=0;i<len;i++){
            if($(urlAndSta[i].selector).hasClass("btn-danger")){
                url = urlAndSta[i].url;
                //加入标题
                if( legends instanceof Array){
                    data.datasets[0].label = legends[i];
                }else{
                    data.datasets[0].label = legends;
                }
            }
        }
        $.ajax({
            url: url,
            type: "get",
            context: $(this),
            success:function (datas) {

                var labelArr = [],
                    dataArr = [];
                //将返回值封装为数组用于图表展示
                var len = datas.length;
                for(var i=0;i<len;i++){
                    //x轴
                    labelArr.push(datas[i].name);
                    //y轴
                    dataArr.push(datas[i].cont);
                }
                data.labels = labelArr;
                data.datasets[0].data = dataArr;
                //清除上一种显示状态
                if(chart) chart.destroy();
                chart = new Chart(ctx, {
                    type: type,
                    data: data
                });
            }
        })
    }
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
            barData.labels = labelArr;
            barData.datasets[0].data = dataArr;
            var char = new Chart(ctx, {
                type: 'bar',
                data: barData
            });
        }
    })
}
/**
 * 随机整数，高位为空时从零到低位
 * @param low 低位
 * @param high 高位
 */
function randInt(low,high) {
    var ret = 0;
    if(high){
        ret = parseInt(Math.random() * (high - low) + low);
    }else{
        ret = parseInt(Math.random() * low);
    }
    return ret;
}