<%--
    模块名称：ZB，拆零组装，设置，zccl新增页面
    描述：
    作者： 叶成潇
    创建日期：2013-08
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!--在此导入你所需要的Java类-->
<%@ page import="com.pub.init.SysConstance" %>
<!--在此声明你所需的TagLib-->
<%@ taglib uri="/struts-tags" prefix="s" %>
<%--放置你的Java代码--%>
<%
    String baseWebPath = (String) SysConstance.getParameterSettings().get("baseWebPath");
    String path = request.getContextPath();
    String REQUIRED_INPUT = (String) SysConstance.getParameterSettings().get("requiredInput");
    request.setAttribute("decorator", "none");
    //强制浏览器不缓存本页面内容
    response.setHeader("Cache-Control", "no-cache");//HTTP 1.1
    response.setHeader("Pragma", "no-cache");//HTTP 1.0
    //阻止浏览器直接从代理服务器取得本页面的内容
    response.setDateHeader("Expires", 0);
    final String MODULE_NAME = "拆零组装_设置";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><%=MODULE_NAME%>_新增</title>
<base target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>
<!--在此包含你所需要的JS文件-->
<script type="text/javascript" src="<%=baseWebPath%>/js/forminit.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/js/util.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/js/open_dialog.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/js/operationSubmit.js"></script>
<script type="text/javascript" src="<%=baseWebPath %>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
$(function () {
    $("#mclh,#mclhMC").attr("readonly", "readonly").addClass("readonly");
    $("#0 th").attr("nowrap", "nowrap");
});

//功能描述： 新增
//参数说明：无
//返回值：无
function f_addconfirm() {
    if (checkAdd()) {
        if (confirm("确定要新建吗?")) {
            processDojoDate();
            disableAllSubmit();
            abledAllText();
            form1.action = "<%=baseWebPath%>/zcclddml/addZcclAction.action";
            form1.submit();
        }
    }
}

//功能描述： 新增前检查必填项
//参数说明：无
//返回值：无
function checkAdd() {
    var zclh = getTableCell2(document.getElementById("data_ddnr_list")).join("|");
    var mclh = $("#mclh").val();
    if (null == mclh || "" == mclh) {
        alert("请选择总成号");
        return;
    }
    if (zclh == "") {
        alert("请选择零件号");
        return false;
    }
    $("#rowflag_str").val(zclh);
    return true;
}

//功能描述： 保存前，获取每行数据,并且装订.从0开始计数
//参数说明：无
//返回值：无
function getTableCell2(obj) {
    var _arrCellOnes = [];
    var _oTBody = obj.TBodies ? obj.TBodies : obj;
    var _oTRows = _oTBody.rows;
    for (i = 2; i < _oTRows.length; i++) {
        if (_oTRows[i].cells[1]) {
            _arrCellOnes.push(_oTRows[i].cells[1].innerHTML + "#" + _oTRows[i].cells[2].innerHTML + "#" + _oTRows[i].cells[3].firstChild.value + "#"
                    + _oTRows[i].cells[4].innerHTML + "#" + _oTRows[i].cells[5].innerHTML + "#");
        }
    }
    return _arrCellOnes;
}

//功能描述： 选择mclh
//参数说明：param 判断是不是mclh
//返回值：无
function choose_mclh(param) {
    <%--//调用clk--%>
    <%--var ts = f_view_choose_clk_dialog('<%=baseWebPath%>');--%>
    <%--if (ts != null && param == "mclh") {--%>
    <%--$("#mclh").val(ts[0]);--%>
    <%--$("#mclhMC").val(ts[1]);--%>
    <%--}--%>
    var winParam = "";
    var ts = window.showModalDialog(encodeURI("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/pjbfddml/turnChooseBjkcPageAction.action&time=" + new Date()), winParam, "dialogWidth:830px;dialogHeight:410px;center:yes;help:no;status:no;resizable:no");
    //多行数据被返回，每行数据用|，分隔。
    if (ts == undefined || ts == "") {
        return;
    }
    var ts_tmp = ts.split("|");
    for (var i = 0; i < 1; i++) {
        var tmpRecord = ts_tmp[i].split("#")
        $("#mclh").val(tmpRecord[0]);
        $("#mclhMC").val(tmpRecord[1]);
    }
}

//功能描述：获取表格内容
//参数说明：obj
//返回值：无
function getTableCell(obj) {
    var _arrCellOnes = [];
    var _oTBody = obj.TBodies ? obj.TBodies : obj;
    var _oTRows = _oTBody.rows;
    for (i = 1; i < _oTRows.length; i++) {
        if (_oTRows[i].cells[1]) {
            _arrCellOnes.push(_oTRows[i].cells[1].innerHTML);
        }
    }
    return _arrCellOnes;
}

//功能描述：选择clh
//参数说明：无
//返回值：无
function choose_clh() {
    //需要判断新增的内容是否已经增加过。增加过了就需要去掉。
    var tmp_gn = [];
    var tmp_gn2 = [];
    var tmp_gn_array = getTableCell(document.getElementById("data_ddnr_list"));

    if (null != tmp_gn_array && tmp_gn_array.length > 0) {
        for (var i = 0; i < tmp_gn_array.length; i++) {
            tmp_gn[tmp_gn_array[i]] = true;
        }
    }
    <%--调用徐芳的clk   var ts = window.showModalDialog(encodeURI("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/zcclddml/turnChooseClhPageAction.action&time=" + new Date()), window, "dialogWidth:580px;dialogHeight:500px;center:yes;help:no;status:no;resizable:no");--%>
    var winParam = "";
    var ts = window.showModalDialog(encodeURI("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/pjbfddml/turnChooseBjkcPageAction.action&time=" + new Date()), winParam, "dialogWidth:830px;dialogHeight:410px;center:yes;help:no;status:no;resizable:no");
    //多行数据被返回，每行数据用|，分隔。
    if (ts == undefined || ts == "") {
        return;
    }
    var ts_tmp = ts.split("|");//addTr中，还要继续split()，以便取出用#分隔的数据。用#分隔的数据为每行中<td></td>间需要显示的数据

    for (var i = 0; i < ts_tmp.length; i++) {
        var tmpRecord = ts_tmp[i].split("#")
        if (!tmp_gn[ tmpRecord[0] ]) {
            tmp_gn2.push(ts_tmp[i]);
        }
    }

    addTr(tmp_gn2, "data_ddnr_list");
}
//功能描述：新增行。model为需要添加行的table的id
//参数说明：无
//返回值：无
function addTr(recordArray, model) {
    var tr_id = $("#" + model + ">tbody>tr:last").attr("id");
    tr_id++;
    var html;
    for (var i = 0; i < recordArray.length; i++) {
        html = "";
        if (tr_id % 2 == 0)
            html += "<tr id='" + tr_id + "' class='odd'>";
        else
            html += "<tr id='" + tr_id + "' class='enen'>";

        arr = String(recordArray[i]).split("#");

        html += "<td align='center' >" + tr_id + "</td>";
        html += "<td align='left' width='100px'>" + formatNullToString(arr[0]) + "</td>";
        html += "<td align='left' width='100px'>" + formatNullToString(arr[1]) + "</td>";
        html += "<td align='right'><input type='text' value='1' max='10' style='width:98%;text-align:right;' onblur='f_inputCheck(this)'  /></td>";
        html += "<td align='center' width='100px'>" + formatNullToString(arr[2]) + "</td>";
        html += "<td align='right' width='100px'>" + formatNullToString(arr[3]) + "</td>";
        html += "<td style='word-break:break-all' align='center'> <a href='#' onclick='del(this, \"xsddnrList_arr\", \"data_ddnr_list\",\"" + formatNullToString(arr[0]) + "\")'>删除</a></td>";
        html += "</tr>";

        $("#" + model).append(html);
        tr_id++;
    }
}

//功能描述：删除行
//参数说明：obj, hiddenName, dataId, data
//返回值：无
function del(obj, hiddenName, dataId, data) {//存放数据隐藏域数组名，colNames “hiddenName”中每行列名
    $(obj).parent().parent().remove();
    //删除行后，表格每行id和序号需要重新计算
    $("#" + dataId + ">tbody>tr").each(
            function (n) {
                if (n != 0) {
                    $(this).attr("id", n);
                    $(this).next().children().filter(":first").text(n);
                    if (n % 2 == 0)
                        $(this).attr("class", 'odd');
                    else
                        $(this).attr("class", 'enen');
                }
            });
}

//功能描述：数据校验，输入必须为正整数。
//参数说明：obj
//返回值：无
function f_inputCheck(obj) {
    var cls = obj.value;//材料数。
    var reg = new RegExp("^[1-9][0-9]*$");//正则表达式，正整数
    var regExec = reg.exec(cls);

    if (null != cls && cls != regExec) {
        alert("请输入大于零的整数！");
        //obj.focus();
        obj.value = 1;
        return;
    }

    return true;
}

//功能描述：把null 值转换成 空值
//参数说明：str
//返回值：无
function formatNullToString(str) {
    if (str == null) {
        return "";
    } else {
        return str;
    }
}
</script>
</head>
<body>
<!-- 导航条 Start -->
<div id="navigate_container" style="width:100%;height: 25px;" class="MenuBar">
    <table style="width: 100%;" cellspacing="0">
        <tr style="height:25px">
            <td>
                <img src="<%=baseWebPath%>/images/navigation_left.gif" alt=""/>
                <span class="left3px">您的位置：<%=MODULE_NAME%>_新增</span>
            </td>
            <td class="bodyleft">
                <input type="button" class="button" value="保存" onclick="f_addconfirm()"/>
                <input type="button" class="button" value="返回" onclick="window.close()"/>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条 End -->
<%--要新增的数据 Start--%>
<s:form name="form1" action="" method="post">
    <s:token/>
    <s:hidden id="rowflag_str" name="rowflag_str" value=""/>

    <fieldset id="FIELDSET8" style="width:100%; margin-left: 3px; margin-top: 5px;">
        <legend>总成号信息</legend>
        <table align="center">
            <tr>
                <td align="right">总成号</td>
                <td>
                    <s:textfield id="mclh" name="zccl.zcclPK.mclh" ondblclick="choose_mclh('mclh');"
                                 title="双击选择总成号"/><%=REQUIRED_INPUT%>
                </td>
                <td align="right">总成号名称</td>
                <td>
                    <s:textfield id="mclhMC" name="zccl.mclmc"/>
                </td>
            </tr>
        </table>
    </fieldset>
    <fieldset id="FIELDSET8" style="width:100%; margin-left: 3px; margin-top: 5px;">
        <legend>零件号信息</legend>
        <div style="width:100%;height:auto;border: 0px solid red;">
            <table id="data_ddnr_list" class="data" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr>
                    <td align="right" colspan="7">
                        <input id="button" type="button" size="100px" value="选择零件号" onclick="choose_clh()"/>
                    </td>
                </tr>
                <tr id="0">
                    <th align="center" style="width:40px;">行号</th>
                    <th align="center" style="width:100px;">零件号</th>
                    <th align="center" style="width:150px;">零件号名称</th>
                    <th align="center" style="width:100px;">零件号数量</th>
                    <th align="center" style="width:50px;">单位</th>
                    <th align="center" style="width:60px;">单价</th>
                    <th align="center" style="width:60px;">操作</th>
                </tr>
            </table>
        </div>
    </fieldset>
</s:form>
<%--要新增的数据 End--%>
</body>
</html>

