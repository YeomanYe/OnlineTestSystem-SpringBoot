var ALPHA_CONSTANT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
$(function () {
    toggleTabs("subjectListTab", "试题列表", "subject/listSubjectPage")();
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
    $("#query_userLog").click(toggleTabs("userLogListTab","日志管理","userLog/listUserLogPage"))
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
    $(":checked").prop({checked:false});
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
function staHandler(canSelector,urlAndSta,staTypes,legends){
    var chart = null;
    var staTemplateData = [
        {
            labels: [],
            datasets: [
                {
                    label: "",
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
        var len = staTypes.length;
        var url = "";
        var data = null;
        //必须先获取数据data
        for(var i=0;i<len;i++){
            if($(staTypes[i]).hasClass("btn-success") && i!=1){
                //i不等于1时生成与图表对应的颜色数
                var boderColorArr = [],
                    bgColorArr = [];
                for(var j=0,len2=staTemplateData[0].datasets[0].data.length;j<len2;j++){
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
        len = urlAndSta.length;
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