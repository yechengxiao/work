<%--
	模块名称：总部端，备件报废，查询列表页面
	描述：
	作者： 叶成潇
	创建日期：2013-06
--%>
<%@ page language="java" import="com.pub.init.SysConstance" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!--在此导入你所需要的Java类-->

<%
    String baseWebPath = (String) SysConstance.getParameterSettings()
            .get("baseWebPath");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>备件报废管理</title>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>

    <!-- 在此加入样式文件 -->
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>
    <!--在此包含你所需要的JS文件-->
    <script type="text/javascript" src="<%=baseWebPath%>/js/forminit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/open_dialog.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        <%--查询--%>
        function f_query() {
            //对所有dojo日期处理
            processDojoDate();

            disableAllSubmit();
            $("#form1 input[name='turnPage.needDispPage']").attr("value", "1");
            $("#form1 input[name='turnPage.totalLinesNum']").attr("value", "0");
            $("#form1 input[name='turnPage.queryFlag']").attr("value", "new");
            form1.action = "<%=basePath%>/pjbfddml/queryListAction.action";
            form1.submit();
        }
        <%--新增--%>
        function f_add(baseWebPath) {
            var ts = window.showModalDialog(baseWebPath + "/dialogue/dialog_panel.jsp?action=" + baseWebPath +
                    "/pjbfddml/turnAddPageAction.action&time=" + new Date(), window, "dialogWidth:880px;dialogHeight:500px;center:yes;help:no;status:no;resizable:yes");
            return ts;
        }

        <%--修改--%>
        function turn_modify_page(zyh) {
            var ts = f_modify_pjbfddml_dialog(zyh, document.getElementsByName("turnPage.needDispPage")[0].value);
            if (ts != null) {
                form1.queryOrder.value = ts[0];
                $("input[name='turnPage.needDispPage']").attr("value", ts[1]);
                $("input[name='turnPage.queryFlag']").attr("value", "new");

                disableAllSubmit();
                form1.action = "<%=basePath%>/pjbfddml/queryListAction.action";
                form1.submit();
            }
        }
        function f_modify_pjbfddml_dialog(zyh, needDispPage) {
            var ts = window.showModalDialog(encodeURI("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/pjbfddml/turnModifyPageAction.action&pjbfddml.zyh=" + escape(zyh) + "&turnPage.needDispPage=" + (needDispPage) + "&time=" + new Date()), window, "dialogWidth:800px;dialogHeight:520px;center:yes;help:no;status:no;resizable:no");
            return ts;
        }
        <%--删除--%>
        function f_del() {
            if (document.all.rowflag == null) {
                alert("没有记录供选择!");
                return;
            }
            if (inspectBox(document.all.rowflag) != "") {
                if (confirm("只有状态为暂存的信息能删除。")) {
                    document.getElementsByName("rowflag_str")[0].value = inspectBox(document.all.rowflag);

                    form2.action = "<%=baseWebPath%>/pjbfddml/deleteAction.action";
                    disableAllSubmit();
                    form2.submit();
                }
                else
                    return;
            }
            else {
                alert("请选择记录!");
                return;
            }
        }
        <%--排序--%>
        function Sort(orderField) {
            if (orderField == form1.orderField.value && form1.orderTrend.value == "asc")
                form1.orderTrend.value = "desc";
            else
                form1.orderTrend.value = "asc";
            form1.orderField.value = orderField;
            document.getElementsByName("turnPage.queryFlag")[0].value = "normal";
            form1.queryOrder.value = "back";
            document.getElementsByName("turnPage.needDispPage")[0].value = "1";
            disableAllSubmit();
            form1.action = "pjbfddml/queryListAction.action";
            form1.submit();
        }
        <%--翻页--%>
        function turnPage(currentDispPage, mode) {
            var currentPageNum = parseInt(currentDispPage);
            var totalPages = <s:property default="0" value="%{turnPage.totalPages}" />;
            var nextPageNum;

            if (mode == "choosePage") {
                //输入整数判断
                var ret = trim($("#pageindex").val());
                if (ret == "") return false;
                if (isNaN(ret)) {
                    alert("输入数据不合法,只能输入数字!");
                    return false;
                }

                if (ret >= totalPages) {
                    ret = totalPages;
                }
                else if (ret <= 0) {
                    ret = 1;
                }
                nextPageNum = ret;
            }
            else {
                nextPageNum = getNextPageNum(currentDispPage, mode, totalPages);
            }

            if (nextPageNum == null)
                return;

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
            form1.action = "<%=baseWebPath%>/pjbfddml/queryListAction.action";
            disableAllSubmit();
            form1.submit();
        }
        <%--导出--%>
        function f_export() {
            form1.queryOrder.value = "back";
            $("#form1 input[name='turnPage.needDispPage']").attr("value", "1");
            $("#form1 input[name='turnPage.totalLinesNum']").attr("value", "0");
            $("#form1 input[name='turnPage.queryFlag']").attr("value", "new");
            form1.action = "<%=baseWebPath%>/excel/exportAction.action?model=pjbfddml";
            form1.submit();
        }
    </script>
</head>
<body>
<s:actionerror/>
<form id="form1" name="form1" action="" method="post">
<s:token/>
<%--日期时间--%>
<s:hidden name="pjbfddmlParam.rqStart"/>
<s:hidden name="pjbfddmlParam.rqEnd"/>

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

<!-- 导航条         Start -->
<div id="navigate_container"
     style="width: 800px; height: 25px; margin-left: 3px; padding: 5px"
     class="MenuBar">
    <table style="width: 100%;" cellspacing="0">
        <tr style="height: 25px">
            <td>
                <img src="<%=path%>/images/navigation_left.gif" alt=""/>
                <span class="left3px">您的位置：备件报废管理</span>
            </td>
            <td align="right">
                <input type="button" class="button" value="查询" onclick="f_query()"/>
                <input type="button" class="button" value="新增" onclick="f_add('<%=baseWebPath%>')"/>
                <input type="button" class="button" value="删除" onclick="f_del()"/>
                <input type="button" class="button" value="导出" onclick="f_export()"/>
                <input type="button" class="button" value="主页" onclick="gohome()"/>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条         END -->

<!-- 检索条件    S -->
<div id="serach_container" style="width: 800px; margin-left: 3px; margin-top: 5px;">
    <fieldset style="width: 795px" id="FIELDSET1">
        <legend> 检索条件</legend>
        <table border="0" cellspacing="0" style="width: 795px; line-height: 25px;">
            <tr style="line-height: 0px;">
                <td style="width: 60px">
                    &nbsp;
                </td>
                <td style="width: 100px">
                    &nbsp;
                </td>
                <td style="width: 20px">
                    &nbsp;
                </td>
                <td style="width: 65px">
                    &nbsp;
                </td>
                <td style="width: 100px">
                    &nbsp;
                </td>
                <td style="width: 20px">
                    &nbsp;
                </td>
                <td style="width: 65px">
                    &nbsp;
                </td>
                <td style="width: 100px">
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td align="right">流水号</td>
                <td><s:textfield name="pjbfddmlParam.zyh" value="%{#session.pjbfddmlConParas.pjbfddmlParam.zyh}"
                                 maxlength="20" cssStyle="width:100%" cssClass="upper"/></td>
                <td></td>
                <td align="right">仓库号</td>
                <td><s:textfield name="pjbfddmlParam.ckh" value="%{#session.pjbfddmlConParas.pjbfddmlParam.ckh}"
                                 maxlength="20" cssStyle="width:100%" cssClass="upper"/></td>
                <td></td>
                <td align="right">类型</td>
                <td><s:select name="pjbfddmlParam.czlx" value="%{#session.pjbfddmlConParas.pjbfddmlParam.czlx}"
                              list="#{'':'请选择','BF':'报废','HY':'还原'}" cssStyle="width:100%"/></td>
            </tr>
            <tr>
                <td align="right">操作日期</td>
                <td align="left"><s:textfield id="rqStart" name="dojo.pjbfddmlParam.rqStart" onblur="checkrq(this);"
                                              value="%{@com.pub.util.tools@formatDateByBar(#session.pjbfddmlConParas.pjbfddmlParam.rqStart)}"
                                              cssClass="dateCSS" cssStyle="width: 100%"/></td>
                <td align="center"><img onclick="WdatePicker({el:'rqStart'})"
                                        src="<%=baseWebPath%>/js/My97DatePicker/skin/datePicker.gif"
                                        style="cursor: pointer; width: 16px; height: 22px;" align="absmiddle"/></td>

                <td align="right">至</td>
                <td align="left"><s:textfield id="rqEnd" name="dojo.pjbfddmlParam.rqEnd" onblur="checkrq(this);"
                                              value="%{@com.pub.util.tools@formatDateByBar(#session.pjbfddmlConParas.pjbfddmlParam.rqEnd)}"
                                              cssClass="dateCSS" cssStyle="width: 100%"/></td>
                <td align="center"><img onclick="WdatePicker({el:'rqEnd'})"
                                        src="<%=baseWebPath%>/js/My97DatePicker/skin/datePicker.gif"
                                        style="cursor: pointer; width: 16px; height: 22px;" align="absmiddle"/>
                </td>

                <td align="right">状态</td>
                <td align="left"><s:select name="pjbfddmlParam.zt" value="%{#session.pjbfddmlConParas.pjbfddmlParam.zt}"
                                           list="#{'':'请选择','0':'暂存','1':'已确认'}" cssStyle="width:100%"/></td>
            </tr>
        </table>
    </fieldset>
</div>
<!-- 检索条件    E -->

<!-- 数据列表       Start -->
<div id="data_container"
     style="height: 355px;width: 800px; margin-left: 3px; margin-top: 3px; overflow: auto; border: 0px solid black;">
    <table id="data_list" class="data" border="0" cellspacing="0" cellpadding="0" width="1240px;">
        <%--标题--%>
        <tr id="tb_field" class="field">
            <th><input type="checkbox" name="all_flag" onclick="ck_select(this,'rowflag');"/></th>
            <th><a onclick="Sort('zyh');return false;" href="javascript:void(0);"><span>流水号</span> <span
                    class="arr"></span></a></th>
            <th><a onclick="Sort('zt');return false;" href="javascript:void(0);"><span>状态</span> <span
                    class="arr"></span></a></th>
            <th><a onclick="Sort('ckh');return false;" href="javascript:void(0);"><span>仓库号</span> <span
                    class="arr"></span></a></th>
            <th><a onclick="Sort('ckjc');return false;" href="javascript:void(0);"><span>仓库简称</span> <span
                    class="arr"></span></a></th>
            <th><a onclick="Sort('czlx');return false;" href="javascript:void(0);"><span>类型</span> <span
                    class="arr"></span></a></th>
            <th><a onclick="Sort('bfpz');return false;" href="javascript:void(0);"><span>品种</span> <span
                    class="arr"></span></a></th>
            <th><a onclick="Sort('bfje');return false;" href="javascript:void(0);"><span>金额</span> <span
                    class="arr"></span></a></th>
            <th><a onclick="Sort('czr');return false;" href="javascript:void(0);"><span>操作人</span> <span
                    class="arr"></span></a></th>
            <th><a onclick="Sort('rq');return false;" href="javascript:void(0);"><span>日期</span> <span
                    class="arr"></span></a></th>
            <th><a onclick="Sort('sj');return false;" href="javascript:void(0);"><span>时间</span> <span
                    class="arr"></span></a></th>
            <th><a onclick="Sort('memo');return false;" href="javascript:void(0);"><span>备注</span> <span
                    class="arr"></span></a></th>
        </tr>

        <!-- 查询结果如果在当前堆栈中不存在，从Session中获取 set后默认放在ActionContext中-->
        <s:if test="pjbfddmlList != null">
            <s:set name="pjbfddmlInfo" value="pjbfddmlList"/>
        </s:if>
        <s:else>
            <s:set name="pjbfddmlInfo" value="#session.pjbfddmlList"/>
        </s:else>

        <!--使用iterator迭代输出查询结果列表 -->
        <s:iterator value="pjbfddmlInfo" id="pjbfddml" status="st">
            <s:if test="#st.index >= turnPage.needResultBeginNum && #st.index <= turnPage.needResultEndNum">
                <%--数据--%>
                <tr>
                    <td style="width: 10px;" align="center" nowrap><input type="checkbox" name="rowflag"
                                                                          value="<s:property value="#pjbfddml.zyh" />#<s:property value="#pjbfddml.zt" />"/>
                    </td>
                        <%--上为多选框，下为数据--%>
                    <td align="left" width="120px" ondblclick="turn_modify_page('<s:property value="#pjbfddml.zyh"/>')"
                        nowrap><s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pjbfddml.zyh)"
                                           escape="false"/></td>
                    <td align="center" width="60px" ondblclick="turn_modify_page('<s:property value="#pjbfddml.zyh"/>')"
                        nowrap><s:property
                            value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatPjbfddmlZt(#pjbfddml.zt))"
                            escape="false"/></td>
                    <td align="center" width="50px" ondblclick="turn_modify_page('<s:property value="#pjbfddml.zyh"/>')"
                        nowrap><s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pjbfddml.ckh)"
                                           escape="false"/></td>
                    <td align="center" width="80px" ondblclick="turn_modify_page('<s:property value="#pjbfddml.zyh"/>')"
                        nowrap><s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pjbfddml.ckjc)"
                                           escape="false"/></td>
                    <td align="center" width="50px" ondblclick="turn_modify_page('<s:property value="#pjbfddml.zyh"/>')"
                        nowrap><s:property
                            value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatPjbfddmlLx(#pjbfddml.czlx))"
                            escape="false"/></td>
                    <td align="right" width="50px" ondblclick="turn_modify_page('<s:property value="#pjbfddml.zyh"/>')"
                        nowrap><s:property
                            value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@replaceNullStringToNum(#pjbfddml.bfpz))"
                            escape="false"/></td>
                    <td align="right" width="70px" ondblclick="turn_modify_page('<s:property value="#pjbfddml.zyh"/>')"
                        nowrap><s:property
                            value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@fmat(@com.pub.util.tools@replaceNullStringToNum(#pjbfddml.bfje),2))"
                            escape="false"/></td>
                    <td align="center" width="70px" ondblclick="turn_modify_page('<s:property value="#pjbfddml.zyh"/>')"
                        nowrap><s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pjbfddml.czr)"
                                           escape="false"/></td>
                    <td align="center" width="120px"
                        ondblclick="turn_modify_page('<s:property value="#pjbfddml.zyh"/>')" nowrap><s:property
                            value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatDateByBar(#pjbfddml.rq))"
                            escape="false"/></td>
                    <td align="center" width="120px"
                        ondblclick="turn_modify_page('<s:property value="#pjbfddml.zyh"/>')" nowrap><s:property
                            value="@com.pub.util.tools@formatTimeByBar(#pjbfddml.sj)" escape="false"/></td>
                    <td align="left" ondblclick="turn_modify_page('<s:property value="#pjbfddml.zyh"/>')" nowrap>
                        <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pjbfddml.memo)"
                                    escape="false"/></td>
                </tr>
            </s:if>
        </s:iterator>
    </table>
</div>
<!-- 数据列表       END -->

<!-- 分页 Start -->
<div id="pagination_container" class="pagination">
    <table width="100%">
        <tr>
            <td align="right">
                <span class="page_skip">共<s:property value="turnPage.totalLinesNum" default="0"/>条
                    第<s:property value="turnPage.needDispPage" default="0"/>
                    /<s:property value="turnPage.totalPages" default="0"/>页
                </span>
                <s:if test="turnPage.needDispPage == 1">
                    <a id="gotoFirst" class="firstbtn">首页</a>
                    <a id="gotoPrev" class="prebtn">上一页</a>
                </s:if>
                <s:elseif test="turnPage.needDispPage > 1">
                    <a id="gotoFirst"
                       onclick="javascript:turnPage('<s:property value="%{turnPage.needDispPage}"/>', 'toFirstPage');">首页
                    </a>
                    <a id="gotoPrev"
                       onclick="javascript:turnPage('<s:property value="%{turnPage.needDispPage}"/>', 'toPrePage');">上一页
                    </a>
                </s:elseif>
                <s:else>
                </s:else>

                <s:if test="turnPage.needDispPage == turnPage.totalPages">
                    <a id="gotoNext" class="nextbtn">下一页</a>
                    <a id="gotoLast" class="lastbtn">尾页</a>
                </s:if>
                <s:elseif test="turnPage.needDispPage < turnPage.totalPages">
                    <a id="gotoNext"
                       onclick="javascript:turnPage('<s:property value="%{turnPage.needDispPage}"/>', 'toNextPage');">下一页
                    </a>
                    <a id="gotoLast"
                       onclick="javascript:turnPage('<s:property value="%{turnPage.needDispPage}"/>', 'toLastPage');">尾页
                    </a>
                </s:elseif>
                <s:else>
                </s:else>

                <input type="text" id="pageindex" class="txt_pageindex"
                       value='<s:property value="%{turnPage.needDispPage}"/>'/>
                <a id="gotoindex"
                   onclick="javascript:turnPage('<s:property value="%{turnPage.needDispPage}"/>', 'choosePage');">Go
                </a>
            </td>
        </tr>
    </table>
</div>
<!-- 分页 END -->
</form>

<form name="form2" action="" method="post">
    <s:token/>
    <s:hidden name="rowflag_str" value=""/>
    <s:hidden name="turnPage.needDispPage" value="%{turnPage.needDispPage}"/>
</form>
</body>
</html>