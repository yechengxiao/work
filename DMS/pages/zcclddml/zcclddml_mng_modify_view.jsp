<%--
    模块名称：ZB，拆零组装，修改/详情页面
    描述：
    作者： 叶成潇
    创建日期：2013-08
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!--在此导入你所需要的Java类-->
<%@ page import="com.pub.init.SysConstance" %>
<!--在此声明你所需的TagLib-->
<%@ taglib uri="/WEB-INF/taglib/excsTagLib.tld" prefix="excs" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%--放置你的Java代码--%>
<%
    String baseWebPath = (String) SysConstance.getParameterSettings().get("baseWebPath");
    String REQUIRED_INPUT = (String) SysConstance.getParameterSettings().get("requiredInput");

    request.setAttribute("decorator", "none");
    //强制浏览器不缓存本页面内容
    response.setHeader("Cache-Control", "no-cache");//HTTP 1.1
    response.setHeader("Pragma", "no-cache");//HTTP 1.0
    //阻止浏览器直接从代理服务器取得本页面的内容
    response.setDateHeader("Expires", 0);
    final String MODULE_NAME = "拆零组装_确认";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title><%=MODULE_NAME%>
    </title>
    <base target="_self">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>

    <!--在此包含你所需要的JS文件-->
    <script language="javascript" type="text/javascript" src="<%=baseWebPath%>/js/operationSubmit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/util.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/json_parse.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/forminit.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#table_add td:even").attr("nowrap", "nowrap");
            $("#table_modify input,textarea ").attr("readonly", "readonly").addClass("readonly");
        });

        //功能描述： 修改_保存
        //参数说明：无
        //返回值：无
        function f_modify_save() {

            disableAllSubmit();
            form1.action = "<%=baseWebPath%>/zcclddml/updateAction.action";
            form1.submit();
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
                <s:if test="zcclddml.zt==0">
                    <input type="button" class="button" value="确认" onclick="f_modify_save()"/>
                </s:if>
                <input type="button" class="button" value="返回" onclick="window.close()"/>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条 END -->

<!-- 修改信息区 Start -->
<div id="data_container" style="width: 100%; margin:5px auto;">
    <s:form name="form1" action="" method="post">
        <s:token/>
        <s:hidden name="turnPage.needDispPage" value="%{turnPage.needDispPage}"/>
        <fieldset style="width: 100%;">
            <legend><%=MODULE_NAME%> 信息</legend>
            <table id="table_modify" align="center">
                <tr>
                    <td align="right">仓库号</td>
                    <td>
                        <s:textfield id="ckh" name="zcclddml.ckh" value="%{zcclddml.ckh}"/>
                    </td>
                    <td align="right">仓库名称</td>
                    <td>
                        <s:textfield id="ckjc" value="%{zcclddml.ckjc}"/>
                    </td>
                    <td align="right">状态</td>
                    <td>
                        <s:textfield id="zt" value="%{zcclddml.zt}" cssStyle="width:100px"/>
                    </td>
                    <td align="right">数量</td>
                    <td align="right">
                        <s:textfield id="cls" value="%{zcclddml.cls}" cssStyle="width:55px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">总成件号</td>
                    <td>
                        <s:textfield id="clh" value="%{zcclddml.clh}"/>
                    </td>
                    <td align="right">总成件名称</td>
                    <td>
                        <s:textfield id="clhMC" value="%{zcclddml.clmc}"/>
                    </td>
                    <td align="right">操作人</td>
                    <td>
                        <s:textfield id="czymc" value="%{@com.pub.user.SecurityUserHolder@getCurrentUser().getName()}"
                                     cssStyle="width:100px"/>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">备注</td>
                    <td colspan="8">
                        <s:textarea value="%{zcclddml.memo}" onblur="checkAreaLength(this,1000)"
                                    cssStyle="width:100%;height:80px;word-break:break-all;"/>
                    </td>
                    <td><s:hidden name="zcclddml.zyh" value="%{zcclddml.zyh}"/></td>
                </tr>
            </table>
        </fieldset>
        <fieldset id="FIELDSET8" style="width:100%; margin-left: 3px; margin-top: 5px;">
            <legend>零件号信息</legend>
            <div style="width:100%;height:auto;border: 0px solid red;">
                <table id="data_ddnr_list" class="data" border="0" cellspacing="0" cellpadding="0" align="center">
                    <tr id="0">
                        <th align="center" style="width:100px;">零件号</th>
                        <th align="center" style="width:150px;">零件号名称</th>
                        <th align="center" style="width:100px;">零件号数量</th>
                        <th align="center" style="width:50px;">单位</th>
                        <th align="center" style="width:60px;">单价</th>
                    </tr>
                    <s:iterator value="zcclddnrList">
                        <tr>
                            <td><s:property value="%{zcclddnrPK.clh}"/></td>
                            <td><s:property value="%{clmc}"/></td>
                            <td align="rigth"><s:property value="%{cls}"/></td>
                            <td><s:property value="%{dw}"/></td>
                            <td align="rigth"><s:property value="%{cldj}"/></td>
                        </tr>
                    </s:iterator>
                </table>
            </div>
        </fieldset>
    </s:form>
</div>
<!-- 修改信息区 End -->
</body>
</html>
