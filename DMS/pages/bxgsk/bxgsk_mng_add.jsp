<%--
	作者： 叶成潇
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
    final String MODULE_NAME = "保险公司_新增";
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
            $("#czymc,#czrq").attr("readonly", "readonly").addClass("readonly");
            var czDate = new Date();
            $("#czrq").val(czDate.toLocaleDateString());// 操作日期
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
                    form1.action = "<%=baseWebPath%>/bxgsk/addAction.action";
                    form1.submit();
                }
            }
        }

        //功能描述：新增前检查必填项
        //参数说明：无
        //返回值：无
        function checkAdd() {
            var dm = $.trim($("#bxgsdm").val());
            if ("" == dm) {
                alert("请输入保险公司代码");
                return;
            }
            var mc = $.trim($("#bxgsmc").val());
            if ("" == mc) {
                alert("请输入保险公司名称");
                return false;
            }

            return true;
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
                <td align="right">保险公司代码</td>
                <td>
                    <s:textfield id="bxgsdm" name="bxgsk.bxgsdm" cssClass="upper"/><%=REQUIRED_INPUT%>
                </td>

                <td align="right">保险公司名称</td>
                <td>
                    <s:textfield id="bxgsmc" name="bxgsk.bxgsmc"/><%=REQUIRED_INPUT%>
                </td>
            </tr>
            <tr>
                <td align="right">备注</td>
                <td colspan="9">
                    <s:textarea name="bxgsk.memo" onblur="checkAreaLength(this,1000)"
                                cssStyle="width:388px;height:80px;word-break:break-all;"/>
                </td>
            </tr>
            <tr>
                <td align="right">操作员名称</td>
                <td>
                    <s:textfield id="czymc" name="bxgsk.czymc"
                                 value="%{@com.pub.user.SecurityUserHolder@getCurrentUser().getName()}"
                            />
                </td>
                <td align="right">操作日期</td>
                <td>
                    <s:textfield id="czrq" name="bxgsk.czrq"/>
                </td>
            </tr>
        </table>
    </fieldset>

</s:form>
<%--要新增的数据 End--%>
</body>
</html>
