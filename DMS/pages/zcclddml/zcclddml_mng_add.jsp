<%--
	模块名称：ZB，拆零组装，新增页面
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
    final String MODULE_NAME = "拆零组装_新增";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base target="_self">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>
    <title><%=MODULE_NAME%>
    </title>
    <!--在此包含你所需要的JS文件-->
    <script type="text/javascript" src="<%=baseWebPath%>/js/forminit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/util.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/operationSubmit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath %>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#table_add td:even").attr("nowrap", "nowrap");
            $("#clh,#clhMC,#ckh,#ckjc,#zt,#czymc").attr("readonly", "readonly").addClass("readonly");
        });
        //功能描述：新增_保存
        //参数说明：无
        //返回值：无
        function f_add_save() {
            if (checkAdd()) {
                if (confirm("你确定要保存吗?")) {
                    //processDojoDate();
                    disableAllSubmit();
                    abledAllText();
                    form1.action = "<%=baseWebPath%>/zcclddml/addAction.action";
                    form1.submit();
                }
            }
        }

        //功能描述：新增前检查必填项
        //参数说明：无
        //返回值：无
        function checkAdd() {
            var ckh = $("#ckh").val();
            if (null == ckh || "" == ckh) {
                alert("请选择仓库号");
                return;
            }
            var clh = $("#clh").val();
            if (null == clh || "" == clh) {
                alert("请选择总成件号");
                return false;
            }
            var cls = $("#cls").val();
            if (null == cls || "" == cls) {
                alert("请输入数量");
                return false;
            }
            var reg = new RegExp("^[1-9][0-9]*$");//正则表达式，正整数
            var regExec = reg.exec(cls);
            if (cls != regExec) {
                alert("请输入大于零的整数！");
                $("#cls").val("1");
                return;
            }
            return true;
        }
        //功能描述： 选择仓库号、仓库简称
        //参数说明：无
        //返回值：无
        function choose_ck() {
            var ts = window.showModalDialog("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/pjbfddml/dispatchCkhCkjcAction.action&time=" + new Date(), window, "dialogWidth:525px;dialogHeight:495px;center:yes;help:no;status:no;resizable:no");
            if (null != ts || undefined != ts) {
                $("#ckh").val(ts[0]);
                $("#ckjc").val(ts[1]);
            }
        }
        //功能描述： 选择总件成号、总成件名称
        //参数说明：无
        //返回值：无
        function choose_clh() {
            var ts = window.showModalDialog("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/zcclddml/turnSettingsPageAction.action&otherCall=zcclddmlAdd&time=" + new Date(), window, "dialogWidth:525px;dialogHeight:495px;center:yes;help:no;status:no;resizable:no");

            var zcl1 = [];//拆封后还有#分隔
            var zcl2 = [];
            var html = "";
            var trId = 1

            if (null != ts || undefined != ts) {
                $("#clh").val(ts[0]);
                $("#clhMC").val(ts[1]);
                //alert(ts[2]);//拼装后的zcl数据
                zcl1 = String(ts[2]).split("|");
                //再次选择时，需要把以前的数据清掉
                if ($("#data_ddnr_list tr:gt(0)") != null) {
                    $("#data_ddnr_list tr:gt(0)").remove();
                }
                for (var i = 0; i < zcl1.length - 1; i++) {
                    zcl2 = String(zcl1[i]).split("#");//035-1701016#带垫圈螺栓#个#2.75#1|
                    html = "<tr>";
                    html += "<td align='center' >" + trId + "</td>";
                    html += "<td align='left' width='100px'>" + zcl2[0] + "</td>";
                    html += "<td align='left' width='100px'>" + zcl2[1] + "</td>";
                    html += "<td align='left' width='100px'>" + zcl2[4] + "</td>";
                    html += "<td align='left' width='100px'>" + zcl2[2] + "</td>";
                    html += "<td align='right' width='100px'>" + zcl2[3] + "</td>";
                    html += "</tr>"
                    trId++;
                    $("#data_ddnr_list").append(html);
                }
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
                <span class="left3px">您的位置：<%=MODULE_NAME%></span>
            </td>
            <td class="bodyleft">
                <input type="button" class="button" value="保存" onclick="f_add_save()"/>
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

    <fieldset id="FIELDSET1" style="width:100%; margin-left: 3px; margin-top: 5px;">
        <legend><%=MODULE_NAME%> 信息</legend>
        <table id="table_add" align="center">
            <tr>
                <td align="right">仓库号</td>
                <td>
                    <s:textfield id="ckh" name="zcclddml.ckh" ondblclick="choose_ck();"
                                 title="双击选择仓库"/><%=REQUIRED_INPUT%>
                </td>
                <td align="right">仓库名称</td>
                <td>
                    <s:textfield id="ckjc" name="zcclddml.ckjc"/>
                </td>
                <td align="right">状态</td>
                <td>
                    <s:textfield id="zt" name="zcclddml.zt" value="0" cssStyle="width:100px"/><%--0：未确认--%>
                </td>
                <td align="right">类型</td>
                <td>
                    <s:select name="zcclddml.lx" list="#{'CL':'拆零','ZZ':'组装'}" headerKey="CL" headerValue="拆零"/>
                </td>
            </tr>
            <tr>
                <td align="right">总成件号</td>
                <td>
                    <s:textfield id="clh" name="zcclddml.clh" value="" ondblclick="choose_clh();"
                                 title="双击选择总成"/><%=REQUIRED_INPUT%>
                </td>

                <td align="right">总成件名称</td>
                <td>
                    <s:textfield id="clhMC" name="zcclddml.clmc"/>
                </td>
                <td align="right">操作人</td>
                <td>
                    <s:textfield id="czymc" name="zcclddml.czymc"
                                 value="%{@com.pub.user.SecurityUserHolder@getCurrentUser().getName()}"
                                 cssStyle="width:100px"/>
                </td>
                <td align="right">数量</td>
                <td>
                    <s:textfield id="cls" name="zcclddml.cls" value="1" cssStyle="width:55px"/>
                </td>
            </tr>
            <tr>
                <td align="right">备注</td>
                <td colspan="9">
                    <s:textarea name="zcclddml.memo" value="" onblur="checkAreaLength(this,1000)"
                                cssStyle="width:100%;height:80px;word-break:break-all;"/>
                </td>
            </tr>
        </table>
    </fieldset>
    <fieldset id="FIELDSET8" style="width:100%; margin-left: 3px; margin-top: 5px;">
        <legend>零件号信息</legend>
        <div style="width:100%;height:auto;border: 0px solid red;">
            <table id="data_ddnr_list" class="data" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr id="0">
                    <th align="center" style="width:40px;">行号</th>
                    <th align="center" style="width:100px;">零件号</th>
                    <th align="center" style="width:150px;">零件号名称</th>
                    <th align="center" style="width:100px;">零件号数量</th>
                    <th align="center" style="width:50px;">单位</th>
                    <th align="center" style="width:60px;">单价</th>
                </tr>
            </table>
        </div>
    </fieldset>

</s:form>
<%--要新增的数据 End--%>
</body>
</html>
