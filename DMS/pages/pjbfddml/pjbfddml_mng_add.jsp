<%--
	模块名称：ZB，备件报废，新增页面
	描述：
	作者： 叶成潇
	创建日期：2013-06
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
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base target="_self">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>
    <title>备件报废新增</title>
    <!--在此包含你所需要的JS文件-->
    <script type="text/javascript" src="<%=baseWebPath%>/js/forminit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/util.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/operationSubmit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=baseWebPath %>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#ckjc,#ckh").attr("readonly", "readonly").addClass("readonly");
        });
        //新增
        function f_addconfirm() {
            if (checkAdd()) {
                if (confirm("你确定要新建备件订单吗?")) {
                    processDojoDate();
                    disableAllSubmit();
                    abledAllText();
                    form1.action = "<%=baseWebPath%>/pjbfddml/addAction.action";
                    form1.submit();
                }
            }
        }

        //新增前检查必填项
        function checkAdd() {
            var bj = getTableCell2(document.getElementById("data_ddnr_list")).join("|");
            var ckh = $("#ckh").val();
            if (null == ckh || "" == ckh) {
                alert("请选择仓库号");
                return;
            }
            if (bj == "") {
                alert("请选择备件内容");
                return false;
            }
            $("#rowflag_str").val(bj);
            return true;
        }

        //获取每行数据,并且装订.从0开始计数
        function getTableCell2(obj) {
            var _arrCellOnes = [];
            var _oTBody = obj.TBodies ? obj.TBodies : obj;
            var _oTRows = _oTBody.rows;
            for (i = 2; i < _oTRows.length; i++) {
                if (_oTRows[i].cells[1]) {
                    _arrCellOnes.push(_oTRows[i].cells[1].innerHTML + "#" + _oTRows[i].cells[2].innerHTML + "#" + _oTRows[i].cells[3].innerHTML + "#" + _oTRows[i].cells[4].innerHTML + "#" + _oTRows[i].cells[5].firstChild.value + "#" + _oTRows[i].cells[6].innerHTML);
                }
            }
            return _arrCellOnes;
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
    </script>
</head>
<body>
<!-- 导航条 Start -->
<div id="navigate_container" style="width:100%;height: 25px;" class="MenuBar">
    <table style="width: 100%;" cellspacing="0">
        <tr style="height:25px">
            <td>
                <img src="<%=baseWebPath%>/images/navigation_left.gif" alt=""/>
                <span class="left3px">您的位置：备件报废新增</span>
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

    <fieldset id="FIELDSET1" style="width:100%; margin-left: 3px; margin-top: 5px;">
        <legend>备件报废信息</legend>
        <table align="center">
            <tr>
                <td align="right">仓库号</td>
                <td>
                        <%--默认显示第一个仓库号--%>
                    <s:textfield id="ckh" name="pjbfddml.ckh" value="%{#session.ckh}"
                                 ondblclick="choose_ck();" title="双击选择仓库"/><%=REQUIRED_INPUT%>
                </td>

                <td align="right">仓库名称</td>
                <td>
                    <s:textfield id="ckjc" name="pjbfddml.ckjc"/>
                </td>

                <td align="right">类型</td>
                <td>
                    <s:select name="pjbfddml.czlx" list="#{'BF':'报废','HY':'还原'}" headerKey="BF" headerValue="报废"/>
                </td>

                <td align="right">状态</td>
                <td>
                    <s:select name="pjbfddml.zt" list="#{'1':'已确认','0':'暂存'}" headerKey="0" headerValue="暂存"/>
                </td>

                <td align="right">操作人</td>
                <td>
                    <s:textfield name="pjbfddml.czr"
                                 value="%{@com.pub.user.SecurityUserHolder@getCurrentUser().getName()}"
                                 readonly="true"/>
                </td>
            </tr>
            <tr>
                <td align="right">备注</td>
                <td colspan="9">
                    <s:textarea name="pjbfddml.memo" value="" onblur="checkAreaLength(this,1000)"
                                cssStyle="width:100%;height:80px;word-break:break-all;"/>
                </td>
            </tr>
        </table>
    </fieldset>
    <%--引入的页面--%>
    <%@ include file="pjbfddnr_mng_bjdetail.jsp" %>
</s:form>
<%--要新增的数据 End--%>
</body>
</html>
