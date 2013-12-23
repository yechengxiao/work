<%--
	模块名称：Bjkc、Bzk选择页面
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
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>备件选择</title>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>

    <!-- 在此加入样式文件 -->
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>
    <!--在此包含你所需要的JS文件-->
    <script type="text/javascript" src="<%=baseWebPath%>/js/forminit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/open_dialog.js"></script>
    <script type="text/javascript">
        $(function () {
            //功能描述：获取从父窗口传递的值
            //参数说明：无
            //返回值：无
            if (window.dialogArguments != null) {
                var ckh = window.dialogArguments
                $("#ckh").val(ckh);
            }
        });
        //功能描述：查询
        //参数说明：无
        //返回值：无
        function f_query() {
            var ckh = $("#ckh").val();
            if (ckh == null || ckh === undefined || $.trim(ckh) == "") {
                alert("请输入仓库号");
                return;
            }
            disableAllSubmit();

            $("#formQueryBj input[name='turnPage.needDispPage']").attr("value", "1");
            $("#formQueryBj input[name='turnPage.totalLinesNum']").attr("value", "0");
            $("#formQueryBj input[name='turnPage.queryFlag']").attr("value", "new");

            formQueryBj.action = "<%=baseWebPath%>/pjbfddml/queryBjkcBzkAction.action";
            formQueryBj.submit();
        }
        //功能描述：点标题排序
        //参数说明：orderField 排序的字段名
        //返回值：无
        function f_sort(orderField) {

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
            formQueryBj.action = "<%=baseWebPath%>/pjbfddml/queryBjkcBzkAction.action";
            disableAllSubmit();
            formQueryBj.submit();
        }
        //功能描述：选择
        //参数说明：无
        //返回值：无
        function f_choose() {
            if (document.all.rowflag == null) {
                alert("没有记录供选择!");
                return;
            }
            if (inspectBox(document.all.rowflag) != "") {//inspectBox 选择多条记录,key拼装,用“|”分隔
                if (confirm("你确定选择打勾的备件吗？")) {
                    window.returnValue = inspectBox(document.all.rowflag);
                    window.close();
                } else {
                    return;
                }
            } else {
                alert("请选择记录!");
                return;
            }
        }

    </script>
</head>
<body>
<!-- 导航条 Start -->
<div id="navigate_container" style="width:800px;height: 25px; margin-left:3px; padding:5px" class="MenuBar">
    <table style="width: 100%;" cellspacing="0">
        <tr style="height:25px">
            <td>
                <img src="<%=baseWebPath%>/images/navigation_left.gif" alt=""/>
                <span class="left3px">您的位置：备件选择</span>
            </td>
            <td class="bodyleft">
                <input type="button" class="button" value="保存" onclick="f_choose()"/>
                <input type="button" class="button" value="返回" onclick="window.close()"/>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条 End -->

<s:form id="formQueryBj" name="formQueryBj" method="post">
<s:token/>
<!-- 以下翻页关键是要保证点击详细信息无结果或者在详细信息里删除记录OK后返回时要做一次全新的查询 -->
<s:hidden name="orderField" value="%{orderField}"/>
<s:hidden name="orderTrend" value="%{orderTrend}"/>
<s:hidden name="queryOrder" value="first"/>
<input type="hidden" name="turnPage.linesPerPage"
       value='<%=(String) SysConstance.getParameterSettings().get("linesPerPage")%>'/>
<!-- 设定列表显示的时候每页显示的条数 -->
<input type="hidden" name="turnPage.pagesPerQuery"
       value='<%=(String) SysConstance.getParameterSettings().get("pagesPerQuery")%>'/>
<!-- 缓冲页数，设定每次最多取多少条放到session里 -->
<s:hidden name="turnPage.needDispPage" value="%{turnPage.needDispPage}"/>
<!-- 指定将要显示哪一页的内容 -->
<s:hidden name="turnPage.totalLinesNum" value="%{turnPage.totalLinesNum}"/>
<!-- 总的结果集记录数，在这里无意义，为了翻页整体查看方便 -->
<s:hidden name="turnPage.queryFlag" value=""/>
<!-- new表示需要做一次全新的查询，包括重新count总结果集数(或者重新做汇总统计等);normal表示只重新查询下一块数据集;old表示不做任何查询直接返回当前动态数据 -->
<!-- 以上翻页关键是要保证点击详细信息无结果或者在详细信息里删除记录OK后返回时要做一次全新的查询 -->
<!-- 检索条件 Start -->
<div id="serach_container" style="margin-left: 3px; margin-top: 5px;">
    <fieldset style="width: 800px" id="FIELDSET1">
        <legend>备件选择_检索条件</legend>
        <table border="0" cellspacing="0" style="width: 100%; line-height: 25px;">
            <tr>
                <td align="right">仓库号</td>
                <td>
                    <s:textfield id="ckh" name="bjkcBzkParam.ckh" value=""  cssStyle="width:50px"/>
                </td>
                <td align="right">备件号</td>
                <td>
                    <s:textfield name="bjkcBzkParam.clh" value="%{bjkcBzkParam.clh}" size="15" maxlength="20"
                                 cssStyle="width:90px"/>
                </td>
                <td align="right">备件名称</td>
                <td>
                    <s:textfield name="bjkcBzkParam.clmc" value="%{bjkcBzkParam.clmc}" cssStyle="width:90px"/>
                </td>
                <td align="right">物料编号</td>
                <td>
                    <s:textfield name="bjkcBzkParam.scwlh" value="%{bjkcBzkParam.scwlh}"
                                 cssStyle="width:90px"/>
                </td>
                <td>
                    <input type="button" class="button" value="查询" onclick="f_query()"/>
                </td>
            </tr>
        </table>
    </fieldset>
</div>
<!-- 检索条件 End -->

<!-- 数据列表 Start -->
<div id="data_container"
     style="height:240px;width: 800px; margin-left: 3px; margin-top: 3px; overflow: auto; border: 0px solid green;">
    <table id="data_list" class="data" border="0" cellspacing="0" cellpadding="0" width="100%" align="right">
        <tr id="tb_field" class="field">
            <th>
                <input type="checkbox" name="all_flag" onclick="ck_select(this,'rowflag');"/>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>备件号</span>
                    <span id="clh" class="ordertip"></span> </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>备件名称</span>
                    <span id="clmc" class="ordertip"></span> </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>物料号</span>
                    <span class="ordertip"></span> </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;"
                   href="javascript:void(0);"><span>单位</span> <span id="dw"
                                                                    class="ordertip"></span> </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>库存量</span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>预订量</span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>预留量</span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>采购数</span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>材料单价</span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>状态</span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>适用车型</span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>每车用量</span>
                </a>
            </th>
            <th>
                <a onclick="f_sort('');return false;" href="javascript:void(0);"><span>备注</span>
                </a>
            </th>
        </tr>

        <!-- 查询结果如果在当前堆栈中不存在，从Session中获取 -->
        <s:if test="bjkcBzkList != null">
            <s:set name="bjkcBzkInfo" value="bjkcBzkList"/>
        </s:if>
        <s:else>
            <s:set name="bjkcBzkInfo" value="#session.bjkcBzkList"/>
        </s:else>

        <!--使用iterator迭代输出查询结果列表-->
        <s:iterator value="bjkcBzkInfo" id="bjkcBzk" status="st">
            <s:if
                    test="#st.index >= turnPage.needResultBeginNum && #st.index <= turnPage.needResultEndNum">
                <tr>
                    <td align="center" nowrap>
                        <input type="checkbox" name="rowflag"
                               value="<s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.clh)"/>#<s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.clmc)"/>#<s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.dw)"/>#<s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.cldj)"/>#1#<s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.kcl)"/>"/>
                    </td>
                        <%--上面的value 为传回去的值，分别是clh、clmc、dw、cldj、ckl。--%>
                    <td align="center" nowrap width="90px">
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.clh)"
                                escape="false"/></td>
                    <td align="left" nowrap width="150px">
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.clmc)"
                                escape="false"/></td>
                    <td align="left" nowrap width="90px">
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.scwlh)"
                                escape="false"/></td>
                    <td align="left" nowrap>
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.dw)"
                                escape="false"/></td>
                    <td align="right" nowrap>
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.kcl)"
                                escape="false"/></td>
                    <td align="right" nowrap>
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.ydl)"/></td>
                    <td align="right" nowrap>
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.yll)"
                                escape="false"/></td>
                    <td align="right" nowrap>
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.cgs)"
                                escape="false"/></td>
                    <td align="right" nowrap>
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@replaceNullStringToNum(#bjkcBzk.cldj))"
                                escape="false"/></td>
                    <td align="center" nowrap width="50px">
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatPjbfddmlZt(#bjkcBzk.zt))"
                                escape="false"/></td>
                    <td align="left" nowrap>
                        <textarea rows="1" cols="20" readonly="true"><s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.sycx)"
                                escape="false"/></textarea></td>
                    <td align="right" nowrap>
                        <s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.mcyl)"
                                escape="false"/></td>
                    <td align="left" nowrap>
                        <textarea rows="1" cols="20" readonly="true"><s:property
                                value="@com.pub.util.tools@replaceNullStringWithNbsp(#bjkcBzk.memo)"
                                escape="false"/></textarea></td>
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
                       onclick="javascript:turnPage('<s:property
                               value="%{turnPage.needDispPage}"/>', 'toFirstPage');">首页
                    </a>
                    <a id="gotoPrev"
                       onclick="javascript:turnPage('<s:property
                               value="%{turnPage.needDispPage}"/>', 'toPrePage');">上一页
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
                       onclick="javascript:turnPage('<s:property
                               value="%{turnPage.needDispPage}"/>', 'toNextPage');">下一页
                    </a>
                    <a id="gotoLast"
                       onclick="javascript:turnPage('<s:property
                               value="%{turnPage.needDispPage}"/>', 'toLastPage');">尾页
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
</s:form>
</body>
</html>