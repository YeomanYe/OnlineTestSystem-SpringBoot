var ALPHA_CONSTANT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
$(function () {
    //显示时间
    setInterval(systemTime("#showTime"), 1000);
    // toggleTabs("subjectListTab", "试题列表", "subject/listSubjectPage")();
    /*设置事件处理函数*/
    $("#query_subject").click(toggleTabs("subjectListTab", "试题列表", "subject/listSubjectPage"));
    $("#add_subject").click(toggleTabs("subjectAddTab", "添加试题", "subject/addSubjectPage", null,
        function () {
            //清除添加表单
            resetAddSubjectForm();
            //清除subjectID值
            $("#subjectId").val("");
        }));
    $("#query_paper").click(toggleTabs("paperListTab", "试卷列表", "paper/listPaperPage"));
    $("#query_basedata").click(toggleTabs("baseDataListTab", "基础数据", "baseData/listBaseDataPage"));
    $("#add_paper").click(toggleTabs("paperAddTab", "添加试卷", "paper/addPaperPage", null,
        function () {
            //清除添加表单
            resetAddPaperForm();
            //清除paperID值
            $("#paperId").val("");
        }));
    $("#query_userLog").click(toggleTabs("userLogListTab", "日志管理", "userLog/listUserLogPage"));
    $("#query_permission").click(toggleTabs("permissionListTab", "权限管理", "permission/listPermissionPage"))
});
/**
 * 切换标签页
 * @param tabId 标签页ID
 * @param url 请求的URL
 * @param ajaxPos ajax的参数(默认get方式，无参数
 * @param tabName 标签名
 * @pram toggleCallback 切换标签后的回调函数
 */
function toggleTabs(tabId, tabName, url, ajaxPos, toggleCallback) {
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
            if (typeof toggleCallback === "function") toggleCallback();
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
                    if (typeof toggleCallback === "function") toggleCallback();

                }
            })
        }
    };
}
/**
 * 重置添加试卷表单
 */
function resetAddPaperForm() {
    $("#paperName").val("");
    $("#ansTime").val("");
    $("#paperScore").val("0");
    $("#subjectCnt").val("0");
    $("#paperDesc").val("");
    $("#subjectTable").DataTable().rows().invalidate().draw();
    $(":checked").prop({checked: false});
    subjectIds = [];
}

/**
 * 统计处理函数
 * @param urlAndSta {selector,url}
 * @param staTypes 统计类型按钮的选择器数组,顺序为bar,line,radar,doughnut
 * @param legends 标题数组或者字符串[]/string
 * @param canSelector canvas选择器
 * @returns {Function}
 */
function staHandler(canSelector, urlAndSta, staTypes, legends) {
    var chart = null;
    var staTemplateData = [
        {
            labels: [],
            datasets: [
                {
                    label: "",
                    backgroundColor: [],
                    borderColor: [],
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
    return function () {
        var ctx = $(canSelector)[0].getContext("2d");
        //类型字符串数组
        var staTypeArr = ['bar', 'line', 'radar', 'doughnut'];
        //类型名
        var type = "";
        //循环遍历，有对应的类的按钮既是被选择的按钮
        var len = staTypes.length;
        var url = "";
        var data = null;
        //必须先获取数据data
        for (var i = 0; i < len; i++) {
            if ($(staTypes[i]).hasClass("btn-success") && i != 1) {
                //i不等于1时生成与图表对应的颜色数
                var boderColorArr = [],
                    bgColorArr = [];
                for (var j = 0, len2 = staTemplateData[0].datasets[0].data.length; j < len2; j++) {
                    var color = 'rgba(' + randInt(255) + ', ' + randInt(150) + ', ' + randInt(50);
                    bgColorArr.push(color + ', 0.2)');
                    boderColorArr.push(color + ', 1)');
                }
                data = staTemplateData[0];
                data.datasets[0].backgroundColor = bgColorArr;
                data.datasets[0].borderColor = boderColorArr;
                type = staTypeArr[i];
            } else if ($(staTypes[i]).hasClass("btn-success")) {
                data = staTemplateData[1];
                type = staTypeArr[i];
            }
        }
        len = urlAndSta.length;
        for (i = 0; i < len; i++) {
            if ($(urlAndSta[i].selector).hasClass("btn-danger")) {
                url = urlAndSta[i].url;
                //加入标题
                if (legends instanceof Array) {
                    data.datasets[0].label = legends[i];
                } else {
                    data.datasets[0].label = legends;
                }
            }
        }
        $.ajax({
            url: url,
            type: "get",
            context: $(this),
            success: function (datas) {

                var labelArr = [],
                    dataArr = [];
                //将返回值封装为数组用于图表展示
                var len = datas.length;
                for (var i = 0; i < len; i++) {
                    //x轴
                    labelArr.push(datas[i].name);
                    //y轴
                    dataArr.push(datas[i].cont);
                }
                data.labels = labelArr;
                data.datasets[0].data = dataArr;
                //清除上一种显示状态
                if (chart) chart.destroy();
                chart = new Chart(ctx, {
                    type: type,
                    data: data
                });
            }
        })
    }
}
/**
 * 打开会话框并显示内容
 * @param digSelector 会话框选择器
 * @param content 内容，为空则不添加
 * @param callback 回调函数
 * @param isCenter 是否居中
 */
function openDialog(digSelector, content, callback, isCenter) {
    if (content) {
        var digBody = $(digSelector + " .modal-body");
        digBody.html("");
        digBody.append(content);
    }
    //模态窗口垂直居中设置
    if (isCenter) {
        var $modal = $(digSelector);
        $modal.on('shown.bs.modal', function () {
            var $this = $(this);
            var $modal_dialog = $this.find('.modal-dialog');
            var m_top = ( $(document).height() - $modal_dialog.height() ) / 2;
            $modal_dialog.css({'margin': m_top + 'px auto'});
        });
    }
    $(digSelector).modal({
        keyboard: true,
        show: true
    });
    if (typeof callback === 'function') {
        callback();
    }
}
/**
 * 确认会话框
 * @param content 显示内容
 * @param handler 点击确认的处理函数
 */
function confirmDialog(content, handler) {
    var digBody = $("#confirmDialog .modal-body");
    digBody.html("");
    digBody.append(content);
    var $modal = $("#confirmDialog");
    $modal.on('shown.bs.modal', function () {
        var $this = $(this);
        var $modal_dialog = $this.find('.modal-dialog');
        var m_top = ( $(window).height() - $modal_dialog.height() ) / 2;
        $modal_dialog.css({'margin': m_top + 'px auto'});
    });
    $modal.modal("show");
    if (typeof handler === "function") {
        $("#confirmDialog .okBtn")[0].onclick = function () {
            handler();
            $("#confirmDialog").modal("hide");
        }
    }
}
/**
 * 关闭会话框按钮
 */
function closeDialogBtn() {
    //获取需要关闭的modal的ID
    var modal = $(this).data("dismiss");
    $("#" + modal).modal("hide");
}
/**
 * 随机整数，高位为空时从零到低位
 * @param low 低位
 * @param high 高位
 */
function randInt(low, high) {
    var ret = 0;
    if (high) {
        ret = parseInt(Math.random() * (high - low) + low);
    } else {
        ret = parseInt(Math.random() * low);
    }
    return ret;
}

/**
 * 文件上传函数生成器
 * @param form
 * @param url
 * @param call 成功时的回调函数
 * @returns {Function}
 */
function uploadFile(form, url, call) {
    return function () {
        $(form).ajaxSubmit({
            type: "POST",
            url: url,
            beforeSend: function () {

            },
            success: function (data) {
                if (data === true) {
                    openDialog("#successDialog", "<p>上传成功!</p>", null, true);
                    if (typeof call == "function") call();
                }
                else {
                    openDialog("#errorDialog", "<p>上传失败,原因未知</p>", null, true);
                }
            },
            error: function () {
                openDialog("#errorDialog", "<p>上传失败,服务器端错误</p>", null, true);
            }
        });
    }
}
/**
 * 启动进度条
 */
var progressTimeout; //进度条计时器标志位
var curProgress = 0;
function startProgress() {
    $("#progressPanel").show();
    //第一次启动时，设置进度条位置
    if (!curProgress) {
        $("#progressPanel").css({
            position: "absolute",
            top: ($(window).height() - $("#progress-bar").height()) / 2,
            left: ($(window).width() - $("#progress-bar").width()) / 2
        });
    }

    if (curProgress >= 100) {
        curProgress = 100;
    }
    $("#progress-bar").css("width", curProgress + "%").text("进度:" + curProgress + "%");
    curProgress += 10;
    progressTimeout = setTimeout(startProgress, 50);
}
/**
 * 关闭进度条
 */
function endProgress() {
    $("#progressPanel").hide();
    $("#progress-bar").css("width", "0%").text("进度:0%");
    curProgress = 0;
    clearTimeout(progressTimeout);
}

/**
 * 设置按钮组的按钮样式处理函数
 * @param goal 目标样式
 * @param origin 原样式
 */
function setBtnStyle(goal, origin) {
    return function () {
        //清除按钮的样式
        $(this).parent().children().filter("button").removeClass(goal).addClass(origin);
        //添加目标样式
        $(this).addClass(goal);
    };
}

/**
 * 显示时间,显示时间的块需要设置为相对定位
 * @param selector jQuery选择器
 */
function systemTime(selector) {
    return function () {
        var myDate = new Date();
        var year = myDate.getFullYear();
        var month = myDate.getMonth() + 1;
        var date = myDate.getDate();
        var day = myDate.getDay();
        var hours = myDate.getHours();
        var minutes = myDate.getMinutes();
        var seconds = myDate.getSeconds();
        switch (day) {
            case 0 :
                day = "日";
                break;
            case 1 :
                day = "一";
                break;
            case 2 :
                day = "二";
                break;
            case 3 :
                day = "三";
                break;
            case 4 :
                day = "四";
                break;
            case 5 :
                day = "五";
                break;
            case 6 :
                day = "六";
                break;
            default :
                brak;
        }
        switch (minutes) {
            case 0 :
                minutes = "00";
                break;
            case 1 :
                minutes = "01";
                break;
            case 2 :
                minutes = "02";
                break;
            case 3 :
                minutes = "03";
                break;
            case 4 :
                minutes = "04";
                break;
            case 5 :
                minutes = "05";
                break;
            case 6 :
                minutes = "06";
                break;
            case 7 :
                minutes = "07";
                break;
            case 8 :
                minutes = "08";
                break;
            case 9 :
                minutes = "09";
                break;
        }
        switch (seconds) {
            case 0 :
                seconds = "00";
                break;
            case 1 :
                seconds = "01";
                break;
            case 2 :
                seconds = "02";
                break;
            case 3 :
                seconds = "03";
                break;
            case 4 :
                seconds = "04";
                break;
            case 5 :
                seconds = "05";
                break;
            case 6 :
                seconds = "06";
                break;
            case 7 :
                seconds = "07";
                break;
            case 8 :
                seconds = "08";
                break;
            case 9 :
                seconds = "09";
                break;
        }
        var fullTime = year + "-" + month + "-" + date + " 周" + day + " " + hours + ":" + minutes + ":" + seconds;
        $(selector).html(fullTime);
    }
}