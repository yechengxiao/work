<%--
	模块名称：ZB，拆零组装，查询列表页面
	描述：
	作者： 叶成潇
	创建日期：2013-08
--%>
<%@ page contentType="text/html;" pageEncoding="UTF-8" %>
<%@ include file="../../util/header.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%
    final String MODULE_NAME = "拆零组装";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><%=MODULE_NAME%>_管理</title>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>

    <!-- 在此加入样式文件 -->
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>
    <!--在此包含你所需要的JS文件-->
    <script type="text/javascript" src="<%=baseWebPath%>/js/forminit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/open_dialog.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/My97DatePicker/WdatePicker.js"></script>
    <%--<script type="text/javascript" src="<%=baseWebPath%>/js/jquery-1.7.2.min.js"></script> 会取不到linesPerPage pagesPerQuery--%>

    <script type="text/javascript">
        $(function () {
            $("#data_list tr:gt(0)").attr("nowrap", "nowrap");
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

        //功能描述：查询
        //参数说明：无
        //返回值：无
        function f_query() {
            //对所有dojo日期处理
            processDojoDate();
            disableAllSubmit();

            $("#form1 input[name='turnPage.needDispPage']").attr("value", "1");
            $("#form1 input[name='turnPage.totalLinesNum']").attr("value", "0");
            $("#form1 input[name='turnPage.queryFlag']").attr("value", "new");
            form1.action = "<%=baseWebPath%>/zcclddml/queryListAction.action";
            form1.submit();
        }
        //功能描述： 打开新增对话框
        //参数说明：无
        //返回值：无
        function f_add() {
            var ts = window.showModalDialog("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/zcclddml/turnAddPageAction.action&time=" + new Date(), window, "dialogWidth:880px;dialogHeight:500px;center:yes;help:no;status:no;resizable:yes");
            return ts;
        }

        //功能描述：双击打开修改页面/详情页面
        //参数说明：PK
        //返回值：无
        function turn_modify_view_dialog(zyh) {
            var needDispPage = document.getElementsByName("turnPage.needDispPage")[0].value
            var ts = window.showModalDialog(encodeURI("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/zcclddml/turnModifyViewPageAction.action&zcclddml.zyh=" + escape(zyh) + "&turnPage.needDispPage=" + (needDispPage) + "&time=" + new Date()), window, "dialogWidth:880px;dialogHeight:500px;center:yes;help:no;status:no;resizable:no");

            if (ts != null) {
                form1.queryOrder.value = ts[0];
                $("input[name='turnPage.needDispPage']").attr("value", ts[1]);
                $("input[name='turnPage.queryFlag']").attr("value", "new");

                disableAllSubmit();
                form1.action = "<%=baseWebPath%>/zcclddml/queryListAction.action";
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
                    form2.action = "<%=baseWebPath%>/zcclddml/deleteAction.action";
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
        //功能描述： 设置功能
        //参数说明：无
        //返回值：无
        function f_settings() {
            var ts = window.showModalDialog("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/zcclddml/turnSettingsPageAction.action&time=" + new Date(), window, "dialogWidth:500px;dialogHeight:500px;center:yes;help:no;status:no;resizable:yes");
            return ts;
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
                form1.action = "<%=baseWebPath%>/zcclddml/queryListAction.action";
                form1.submit();
            }
            document.getElementsByName("turnPage.needDispPage")[0].value = "1";
            disableAllSubmit();
            form1.action = "<%=baseWebPath%>/zcclddml/queryListAction.action";
            form1.submit();
        }
        //功能描述：实现翻页
        //参数说明：currentDispPage 当前翻转页，mode 翻转动作
        //返回值：无
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
            form1.action = "<%=baseWebPath%>/zcclddml/queryListAction.action";
            disableAllSubmit();
            form1.submit();
        }
        //功能描述：数据导出
        //参数说明：无
        //返回值：无
        function f_export(obj) {
            form1.queryOrder.value = "back";
            $("#form1 input[name='turnPage.needDispPage']").attr("value", "1");
            $("#form1 input[name='turnPage.totalLinesNum']").attr("value", "0");
            $("#form1 input[name='turnPage.queryFlag']").attr("value", "new");
            form1.action = "<%=baseWebPath%>/excel/exportAction.action?model=zcclddml";
            form1.submit();
        }
    </script>
</head>
<body>
<s:actionerror/>
<form id="form1" name="form1" action="" method="post">
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

<%--日期时间--%>
<s:hidden name="zcclddmlParam.rqStart"/>
<s:hidden name="zcclddmlParam.rqEnd"/>

<!-- 导航条 Start -->
<div id="navigate_container"
     style="width: 800px; height: 25px; margin-left: 3px; padding: 5px" class="MenuBar">
    <table style="width: 100%;" cellspacing="0">
        <tr style="height: 25px">
            <td>
                <img src="<%=baseWebPath%>/images/navigation_left.gif" alt=""/>
                <span class="left3px">您的位置：<%=MODULE_NAME%>管理</span>
            </td>
            <td align="right">
                <input type="button" class="button" value="查询" onclick="f_query()"/>
                <input type="button" class="button" value="新增" onclick="f_add()"/>
                <input type="button" class="button" value="删除" onclick="f_delete()"/>
                <input type="button" class="button" value="导出" onclick="f_export()"/>
                <input type="button" class="button" value="设置" onclick="f_settings()"/>
                <input type="button" class="button" value="主页" onclick="gohome()"/>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条 END -->

<!-- 检索条件 S -->
<div id="serach_container" style="width: 800px; margin-left: 3px; margin-top: 5px;">
    <fieldset style="width: 795px" id="FIELDSET1">
        <legend>检索条件</legend>
        <table align="center" border="0" cellspacing="0" style="width: 100%;">
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
                <td><s:textfield name="zcclddmlParam.zyh" value="%{#session.zcclddmlConParas.zcclddmlParam.zyh}"
                                 maxlength="20" cssStyle="width:100%" cssClass="upper"/></td>
                <td></td>
                <td align="right">仓库号</td>
                <td><s:textfield name="zcclddmlParam.ckh" value="%{#session.zcclddmlConParas.zcclddmlParam.ckh}"
                                 maxlength="20" cssStyle="width:100%" cssClass="upper"/></td>
                <td></td>
                <td align="right">状态</td>
                <td align="left"><s:select name="zcclddmlParam.zt" value="%{#session.zcclddmlConParas.zcclddmlParam.zt}"
                                           list="#{'':'请选择','0':'未确认','1':'已确认'}" cssStyle="width:100%"/></td>
            </tr>
            <tr>
                <td align="right">操作日期</td>
                <td align="left"><s:textfield id="rqStart" name="dojo.zcclddmlParam.rqStart" onblur="checkrq(this);"
                                              value="%{@com.pub.util.tools@formatDateByBar(#session.zcclddmlConParas.zcclddmlParam.rqStart)}"
                                              cssClass="dateCSS" cssStyle="width: 100%"/></td>
                <td align="center"><img onclick="WdatePicker({el:'rqStart'})"
                                        src="<%=baseWebPath%>/js/My97DatePicker/skin/datePicker.gif"
                                        style="cursor: pointer; width: 16px; height: 22px;" align="absmiddle"/></td>

                <td align="right">至</td>
                <td align="left"><s:textfield id="rqEnd" name="dojo.zcclddmlParam.rqEnd" onblur="checkrq(this);"
                                              value="%{@com.pub.util.tools@formatDateByBar(#session.zcclddmlConParas.zcclddmlParam.rqEnd)}"
                                              cssClass="dateCSS" cssStyle="width: 100%"/></td>
                <td align="center"><img onclick="WdatePicker({el:'rqEnd'})"
                                        src="<%=baseWebPath%>/js/My97DatePicker/skin/datePicker.gif"
                                        style="cursor: pointer; width: 16px; height: 22px;" align="absmiddle"/>
                </td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </fieldset>
</div>
<!-- 检索条件 E -->

<!-- 数据列表 Start -->
<div id="data_container"
     style="height: 355px;width: 800px; margin-left: 3px; margin-top: 3px; overflow: auto; border: 0px solid black;">
    <table id="data_list" class="data" border="0" cellspacing="0" cellpadding="0" width="1240px;">
        <%--标题--%>
        <tr id="tb_field" class="field">
            <th><input type="checkbox" name="all_flag" onclick="ck_select(this,'rowflag');"/></th>
            <th>
                <a onclick="f_sort('zyh');return false;" href="javascript:void(0);">
                    <span>流水号</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('zt');return false;" href="javascript:void(0);">
                    <span>状态</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('clh');return false;" href="javascript:void(0);">
                    <span>总成件号</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('clmc');return false;" href="javascript:void(0);">
                    <span>总成件名称</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('cls');return false;" href="javascript:void(0);">
                    <span>数量</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('dw');return false;" href="javascript:void(0);">
                    <span>单位</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('cldj');return false;" href="javascript:void(0);">
                    <span>单价</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('cfhjz');return false;" href="javascript:void(0);">
                    <span>总成价值</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('clzjz');return false;" href="javascript:void(0);">
                    <span>拆零价值</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('shr');return false;" href="javascript:void(0);">
                    <span>确认人</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('shsj');return false;" href="javascript:void(0);">
                    <span>确认时间</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('czymc');return false;" href="javascript:void(0);">
                    <span>操作人</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('czsj');return false;" href="javascript:void(0);">
                    <span>操作时间</span>
                    <span class="arr"></span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('memo');return false;" href="javascript:void(0);">
                    <span>备注</span>
                    <span class="arr"></span>
                </a>
            </th>
        </tr>

        <!-- 查询结果如果在当前堆栈中不存在，从Session中获取 set后默认放在ActionContext中-->
        <s:if test="zcclddmlList != null">
            <s:set name="zcclddmlInfo" value="zcclddmlList"/>
        </s:if>
        <s:else>
            <s:set name="zcclddmlInfo" value="#session.zcclddmlList"/>
        </s:else>

        <!--使用iterator迭代输出查询结果列表 -->
        <s:iterator value="zcclddmlInfo" id="zcclddml" status="st">
            <s:if test="#st.index >= turnPage.needResultBeginNum && #st.index <= turnPage.needResultEndNum">
                <%--数据--%>
                <tr ondblclick="turn_modify_view_dialog('<s:property value="#zcclddml.zyh"/>')">
                    <td style="width: 40px;" align="center">
                        <input type="checkbox" name="rowflag"
                               value="<s:property value="#zcclddml.zyh" />"/>
                    </td>
                        <%--上为多选框，下为数据--%>
                    <td align="left">
                        <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#zcclddml.zyh)"
                                    escape="false"/>
                    </td>
                    <td name="formatZt" align="center">
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#zcclddml.zt)"
                                escape="false"/>
                    </td>
                    <td align="left">
                        <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#zcclddml.clh)"
                                    escape="false"/>
                    </td>
                    <td align="left">
                        <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#zcclddml.clmc)"
                                    escape="false"/>
                    </td>
                    <td align="right">
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#zcclddml.cls)"
                                escape="false"/>

                    </td>
                    <td align="right">
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#zcclddml.dw)"
                                escape="false"/>

                    </td>
                    <td align="right">
                        <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#zcclddml.cldj)"
                                    escape="false"/>
                    </td>
                    <td align="right">
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#zcclddml.cfhjz)"
                                escape="false"/>
                    </td>
                    <td align="right">
                        <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#zcclddml.clzjz)"
                                    escape="false"/>
                    </td>
                    <td align="center">
                        <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#zcclddml.shr)"
                                    escape="false"/>
                    </td>
                    <td align="center">
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatLongDate(#zcclddml.shsj))"
                                escape="false"/>
                    </td>

                    <td align="center">
                        <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#zcclddml.czymc)"
                                    escape="false"/>
                    </td>
                    <td align="center">
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatLongDate(#zcclddml.czsj))"
                                escape="false"/>
                    </td>

                    <td align="left">
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@SubStr(#zcclddml.memo,8))"
                                escape="false"/>
                    </td>
                </tr>
            </s:if>
        </s:iterator>
    </table>
</div>
<!-- 数据列表 END -->

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