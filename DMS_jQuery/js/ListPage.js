var moduleName = "";

$(function () {
            $("#data_list tr:gt(0)").attr("nowrap", "nowrap");
            //当tr上有单双击事件时，checkbox取消冒泡
            $(":checkbox").click(function (event) {
                event.stopPropagation();
            });

            //功能描述：转换状态
            var ztTd = $("td[name='formatZt']");
            ztTd.each(function () {
                var zt = $(this).text();
                //alert(zt);
                if (zt == 1) {
                    $(this).text("已确认");
                } else if (zt == 0) {
                    $(this).text("未确认");
                }
            });
});

//功能描述： 打开新增对话框
//参数说明：无
//返回值：无
function f_add_dialog() {
    var ts = window.showModalDialog("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/" + moduleName + "/turnAddPageAction.action&time=" + new Date(), window, "dialogWidth:600px;dialogHeight:550px;center:yes;help:no;status:no;resizable:no;scroll:no;");
    return ts;
}

//功能描述：查询
//参数说明：无
//返回值：无
function f_query() {
    //对所有dojo日期处理
    //处理所有日期输入域，日期输入域的值格式化后赋给对应的日期隐藏域。要求：dojo日期域的name值等于id值，等于对应日期隐藏的name值。并且给dojo日期域加class=dateCSS
    processDojoDate();

    disableAllSubmit();
    $("#form1 input[name='turnPage.needDispPage']").attr("value", "1");
    $("#form1 input[name='turnPage.totalLinesNum']").attr("value", "0");
    $("#form1 input[name='turnPage.queryFlag']").attr("value", "new");

    form1.action = "<%=baseWebPath%>/" + moduleName + "/queryListAction.action";
    form1.submit();
}

//功能描述：双击打开修改页面/详情页面
//参数说明：PK
//返回值：无
function turn_modify_view_dialog(mclh) {
    var needDispPage = document.getElementsByName("turnPage.needDispPage")[0].value
    var ts = window.showModalDialog(encodeURI("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/" + moduleName + "/turnModifyViewPageAction.action&zcclddml.zyh=" + escape(zyh) + "&turnPage.needDispPage=" + (needDispPage) + "&time=" + new Date()), window, "dialogWidth:880px;dialogHeight:500px;center:yes;help:no;status:no;resizable:no");

    if (ts != null) {
        form1.queryOrder.value = ts[0];
        $("input[name='turnPage.needDispPage']").attr("value", ts[1]);
        $("input[name='turnPage.queryFlag']").attr("value", "new");

        disableAllSubmit();
        form1.action = "<%=baseWebPath%>/" + moduleName + "/queryListAction.action";
        form1.submit();
    }
}

//功能描述：删除
//参数说明：无
//返回值：无
function f_delete() {
    if (document.all.rowflag == null) {
        alert("没有记录供选择!");
        return;
    }
    if (inspectBox(document.all.rowflag) != "") {
        if (confirm("您确定删除？")) {
            document.getElementsByName("rowflag_str")[0].value = inspectBox(document.all.rowflag);
            form2.action = "<%=baseWebPath%>/" + moduleName + "/deleteAction.action";
            disableAllSubmit();
            form2.submit();
        }
        else
            return;
    } else {
        alert("请选择记录!");
        return;
    }
}

//功能描述：点标题排序
//参数说明：orderField 排序的字段名
//返回值：无
function f_sort(orderField) {
    if (!(orderField == form1.orderField.value && form1.orderTrend.value == "asc")) {
        form1.orderTrend.value = "asc";
    } else {
        form1.orderTrend.value = "desc";
    }
    form1.orderField.value = orderField;
    document.getElementsByName("turnPage.queryFlag")[0].value = "normal";
    if ($("#data_list tr").length == 1) {//标题排序。若列表只有标题，点击标题排序失效。
        return false;
    } else {
        form1.queryOrder.value = "back";
        document.getElementsByName("turnPage.needDispPage")[0].value = "1";
        disableAllSubmit();
        form1.action = "<%=baseWebPath%>/" + moduleName + "/queryListAction.action";
        form1.submit();
    }
    document.getElementsByName("turnPage.needDispPage")[0].value = "1";
    disableAllSubmit();
    form1.action = "<%=baseWebPath%>/" + moduleName + "/queryListAction.action";
    form1.submit();
}

//功能描述：实现翻页
//参数说明：currentDispPage 当前翻转页，mode 翻转动作
//返回值：无
//其实这个方法只要个模块名就行了，没必要重复写。
function turnPage(currentDispPage, mode) {
    var currentPageNum = parseInt(currentDispPage);
    var totalPages = <s:property default="0" value="%{turnPage.totalPages}" />;
    var nextPageNum;

    if (mode == "choosePage") {
        //输入整数判断
        var ret = trim($("#pageindex").val());

        if (ret == "") {
            return false;
        }
        if (isNaN(ret)) {
            alert("输入数据不合法,只能输入数字!");
            return false;
        }

        if (ret >= totalPages) {
            ret = totalPages;
        } else if (ret <= 0) {
            ret = 1;
        }
        nextPageNum = ret;
    } else {
        nextPageNum = getNextPageNum(currentDispPage, mode, totalPages);
    }

    if (nextPageNum == null) {
        return;
    }

    var obj_queryFlag = document.getElementsByName("turnPage.queryFlag")[0];
    var pagesPerQuery = <s:property default="10" value="%{turnPage.pagesPerQuery}"/>;

    //判断是否需要重新查询，这里的重新查询只是取另一个缓冲段的数据，而不做总数的重新统计。
    if (Math.ceil(currentPageNum / pagesPerQuery) != Math.ceil(nextPageNum / pagesPerQuery)) {
        obj_queryFlag.value = "normal";
    } else {
        obj_queryFlag.value = "old";
    }

    var obj_needDispPage = document.getElementsByName("turnPage.needDispPage")[0];
    obj_needDispPage.value = nextPageNum;
    form1.action = "<%=baseWebPath%>/" + moduleName + "/queryListAction.action";
    disableAllSubmit();
    form1.submit();
}

//功能描述：导入。导入完成后，自动查询。
//参数说明：无
//返回值：无
function f_import() {
    var ts = window.showModalDialog(encodeURI("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/excel/turnAddPageAction.action&model=" + moduleName + "&time=" + new Date()), window, "dialogWidth:620px;dialogHeight:350px;center:yes;help:no;status:no;resizable:no");

    if (ts != null) {
        form1.queryOrder.value = ts[0];
        document.getElementsByName("turnPage.needDispPage")[0].value = ts[1];
        document.getElementsByName("turnPage.queryFlag")[0].value = "new";
        disableAllSubmit();
        form1.action = "<%=baseWebPath%>/" + moduleName + "/queryListAction.action";
        form1.submit();
    }
}

//功能描述：数据导出
//参数说明：obj
//返回值：无
function f_export(obj) {
    form1.queryOrder.value = "back";
    $("#form1 input[name='turnPage.needDispPage']").attr("value", "1");
    $("#form1 input[name='turnPage.totalLinesNum']").attr("value", "0");
    $("#form1 input[name='turnPage.queryFlag']").attr("value", "new");
    form1.action = "<%=baseWebPath%>/excel/exportAction.action?model=" + moduleName + "";
    form1.submit();
}


