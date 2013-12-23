<%--
	模块名称：ZB，拆零组装，设置，zccl列表页面
	描述：
	作者： 叶成潇
	创建日期：2013-08
--%>
<%@ include file="../../util/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!--在此导入你所需要的Java类-->
<!--在此声明你所需的TagLib-->
<%@ taglib uri="/struts-tags" prefix="s" %>
<%--放置你的Java代码--%>
<%
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
    <title><%=MODULE_NAME%>
    </title>
    <base target="_self">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>
    <!--在此包含你所需要的JS文件-->
    <script type="text/javascript" src="<%=baseWebPath%>/js/forminit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/util.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/operationSubmit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath %>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $(":checkbox").click(function (event) {
                event.stopPropagation();
            });
        });
        //功能描述：查询
        //参数说明：无
        //返回值：无
        function f_query() {
            disableAllSubmit();
            $("#form1 input[name='turnPage.needDispPage']").attr("value", "1");
            $("#form1 input[name='turnPage.totalLinesNum']").attr("value", "0");
            $("#form1 input[name='turnPage.queryFlag']").attr("value", "new");

            form1.action = "<%=baseWebPath%>/zcclddml/queryZcclListAction.action?otherCall=\"\"";
            form1.submit();
        }
        //功能描述：其他页面中查询
        //参数说明：无
        //返回值：无
        function f_queryForOtherCall() {
            disableAllSubmit();
            $("#form1 input[name='turnPage.needDispPage']").attr("value", "1");
            $("#form1 input[name='turnPage.totalLinesNum']").attr("value", "0");
            $("#form1 input[name='turnPage.queryFlag']").attr("value", "new");

            form1.action = "<%=baseWebPath%>/zcclddml/queryZcclListAction.action?otherCall=zcclddmlAdd";
            form1.submit();
        }
        //功能描述： 打开新增对话框
        //参数说明：无
        //返回值：无
        function f_add_dialog() {
            var ts = window.showModalDialog("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/zcclddml/turnZcclAddPageAction.action&time=" + new Date(), window, "dialogWidth:580px;dialogHeight:500px;center:yes;help:no;status:no;resizable:no;scroll:no;");
            return ts;
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
                    document.getElementsByName("rowflay_zccl")[0].value = inspectBox(document.all.rowflag);
                    form2.action = "<%=baseWebPath%>/zcclddml/deleteZcclAction.action";
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
        //功能描述：单击修改、查看
        //参数说明：mclh
        //返回值：无
        function turn_modify_view_dialog(mclh) {
            var needDispPage = document.getElementsByName("turnPage.needDispPage")[0].value
            var ts = window.showModalDialog(encodeURI("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/zcclddml/turnZcclModifyViewPageAction.action&zcclParam.mclh=" + (mclh) + "&turnPage.needDispPage=" + (needDispPage) + "&time=" + new Date()), window, "dialogWidth:580px;dialogHeight:500px;center:yes;help:no;status:no;resizable:no");

            if (ts != null) {
                form1.queryOrder.value = ts[0];
                $("input[name='turnPage.needDispPage']").attr("value", ts[1]);
                $("input[name='turnPage.queryFlag']").attr("value", "new");
                disableAllSubmit();
                form1.action = "<%=baseWebPath%>/zcclddml/queryZcclListAction.action";
                form1.submit();
            }
        }
        //功能描述：向父窗口返回数组
        //参数说明：需要返回的值
        //返回值：无
        function returnVal(clh, clmc, zclInfo) {
            var ts = new Array($.trim(clh), $.trim(clmc), $.trim(zclInfo));
            window.returnValue = ts;
            window.close();
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
                <s:if test='otherCall!="zcclddmlAdd"'>
                    <span class="left3px">您的位置：<%=MODULE_NAME%></span>
                </s:if>
                <s:elseif test='otherCall=="zcclddmlAdd"'><%--被其他页面调用--%>
                    <span class="left3px">您的位置：选择总成件号</span>
                </s:elseif>
            </td>
            <td class="bodyleft">
                <s:if test='otherCall!="zcclddmlAdd"'>
                    <input type="button" class="button" value="查询" onclick="f_query()"/>
                    <input type="button" class="button" value="新增" onclick="f_add_dialog()"/>
                    <input type="button" class="button" value="删除" onclick="f_delete()"/>
                </s:if>
                <s:elseif test='otherCall=="zcclddmlAdd"'>
                    <input type="button" class="button" value="查询" onclick="f_queryForOtherCall()"/>
                </s:elseif>
                <input type="button" class="button" value="返回" onclick="window.close()"/>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条 End -->

<s:form name="form1" id="form1" action=" " method="post">
    <s:token/>
    <s:hidden name="orderField" value="%{orderField}"/>
    <s:hidden name="orderTrend" value="%{orderTrend}"/>
    <s:hidden name="queryOrder" value="first"/>
    <!-- 设定列表显示的时候每页显示的条数 -->
    <input type="hidden" name="turnPage.linesPerPage"
           value='<%=(String) SysConstance.getParameterSettings().get("linesPerPage")%>'/>
    <!-- 缓冲页数，设定每次最多取多少条放到session里 -->
    <input type="hidden" name="turnPage.pagesPerQuery"
           value='<%=(String) SysConstance.getParameterSettings().get("pagesPerQuery")%>'/>
    <!-- 指定将要显示哪一页的内容 -->
    <s:hidden name="turnPage.needDispPage" value="%{turnPage.needDispPage}"/>
    <!-- 总的结果集记录数，在这里无意义，为了翻页整体查看方便 -->
    <s:hidden name="turnPage.totalLinesNum" value="%{turnPage.totalLinesNum}"/>
    <!-- new表示需要做一次全新的查询，包括重新count总结果集数(或者重新做汇总统计等);normal表示只重新查询下一块数据集;old表示不做任何查询直接返回当前动态数据 -->
    <s:hidden name="turnPage.queryFlag" value=""/>
    <!-- 以上翻页关键是要保证点击详细信息无结果或者在详细信息里删除记录OK后返回时要做一次全新的查询 -->

    <!-- 检索条件 Start -->
    <div id="serach_container" style="width:  100%; margin-left:3px; margin-top:5px;">
        <fieldset style="width:  100%" id="FIELDSET1">
            <legend>检索条件</legend>
            <table id="serach_table" align="center" style="width: 100%">
                <tr>
                    <td align="center">总成件号:
                        <s:textfield name="zcclParam.mclh" value="%{#session.zcclConParas.zcclParam.mclh}"
                                     maxlength="10" cssStyle="width:100px" cssClass="upper"/>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <!-- 检索条件 END -->

    <!-- 数据列表 Start -->
    <div id="data_container" style="width:  100%;margin-left:3px; margin-top:3px;overflow-x:scroll">
        <table id="data_list" class="data" align="center" border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr id="data_list_th" class="field">
                <th>
                    <input type="checkbox" name="all_flag" onclick="ck_select(this,'rowflag');"/>
                </th>
                <th>
                    <span>总成件号</span>
                </th>
                <th>
                    <span>总成件名称</span>
                </th>
                <th></th>
            </tr>

            <!-- 查询结果如果在当前堆栈中不存在，从Session中获取 set后默认放在ActionContext中-->
            <s:if test="zcclList != null">
                <s:set name="zcclInfo" value="zcclList"/>
            </s:if>
            <s:else>
                <s:set name="zcclInfo" value="#session.zcclList"/>
            </s:else>

            <!--使用iterator迭代输出查询结果列表-->
            <s:iterator value="zcclInfo" id="zccl" status="st">
                <s:if test="#st.index >= turnPage.needResultBeginNum && #st.index <= turnPage.needResultEndNum">
                    <s:if test='otherCall!="zcclddmlAdd"'>
                        <tr id="data_list_date"
                        onclick="turn_modify_view_dialog('<s:property value="#zccl.zcclPK.mclh"/>')">
                    </s:if>
                    <s:elseif test='otherCall=="zcclddmlAdd"'>
                        <tr id="data_list_date"
                        ondblclick="returnVal('<s:property value="%{#zccl.zcclPK.mclh}"/>',
                        '<s:property value="%{#zccl.mclmc}"/>','<s:property value="%{#zccl.zclmc}"/>')">
                    </s:elseif>
                    <td align="center">
                        <input type="checkbox" name="rowflag" value="<s:property value="%{#zccl.zcclPK.mclh}"/>"/>
                    </td>
                    <td align="left">
                        <s:property value="%{#zccl.zcclPK.mclh}"/>
                    </td>
                    <td>
                        <s:property value="%{#zccl.mclmc}" escape="false"/>
                    </td>
                    <td>
                        <s:hidden value="%{#zccl.zclmc}"/>
                    </td>
                    </tr>
                </s:if>
            </s:iterator>
        </table>
    </div>
    <!-- 数据列表 END -->
</s:form>

<!-- 删除功能调用 END -->
<s:form name="form2" action=" " method="post">
    <s:token/>
    <s:hidden name="rowflay_zccl" value=""/>
    <s:hidden name="turnPage.needDispPage" value="%{turnPage.needDispPage}"/>
</s:form>
<!-- 删除功能调用 END -->
</body>
</html>
