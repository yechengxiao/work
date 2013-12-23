<%--
    模块名称：服务站端，整车物料库_查询列表页面
    描述：
    作者： 叶成潇
    创建日期：2013-05
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ include file="../../util/header.jsp" %>
<!--在此导入你所需要的Java类-->
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>整车物料库</title>
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>

    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>

    <!--JS文件-->
    <script type="text/javascript" src="<%=baseWebPath%>/js/forminit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/open_dialog.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/jquery.ztree.all-3.5.min.js"></script>

    <script type="text/javascript">
        $(function () {
            //把重复的代码放一起
            $("#data_list_th a").attr("href", "javascript:void(0);");
            $("#data_list_date td").attr("nowrap", "nowrap");
        });
        //功能描述：查询
        //参数说明：无
        //返回值：无
        function f_query() {
            disableAllSubmit();

            $("#form1 input[name='turnPage.needDispPage']").attr("value", "1");
            $("#form1 input[name='turnPage.totalLinesNum']").attr("value", "0");
            $("#form1 input[name='turnPage.queryFlag']").attr("value", "new");

            form1.action = "<%=baseWebPath%>/pzjg/queryListAction.action";
            form1.submit();
        }

        //功能描述：双击打开详情页面
        //参数说明：PK
        //返回值：无
        function turn_view_page(vsn) {
            var ts = f_view_dialog(vsn);
            if (ts != null) {
                form1.queryOrder.value = ts[0];
                $("input[name='turnPage.needDispPage']").attr("value", ts[1]);
                $("input[name='turnPage.queryFlag']").attr("value", "new");

                disableAllSubmit();
                form1.action = "<%=baseWebPath%>/pzjg/queryListAction.action";
                form1.submit();
            }
        }
        //功能描述：打开详情页面对话框
        //参数说明：PK，needDispPage
        //返回值：无
        function f_view_dialog(vsn, needDispPage) {
            var ts = window.showModalDialog(encodeURI("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/pzjg/turnViewPageAction.action&pzjg.vsn=" + escape(vsn) + "&time=" + new Date()), window, "dialogWidth:610px;dialogHeight:550px;center:yes;help:no;status:no;resizable:no");
            return ts;
        }
        //功能描述：点标题排序
        //参数说明：orderField 排序的字段名
        //返回值：无
        function f_sort(orderField) {
            if (orderField == form1.orderField.value && form1.orderTrend.value == "asc")
                form1.orderTrend.value = "desc";
            else
                form1.orderTrend.value = "asc";

            form1.orderField.value = orderField;
            document.getElementsByName("turnPage.queryFlag")[0].value = "normal";
            form1.queryOrder.value = "back";
            document.getElementsByName("turnPage.needDispPage")[0].value = "1";

            disableAllSubmit();
            form1.action = "<%=baseWebPath%>/pzjg/queryListAction.action";
            form1.submit();
        }

        //功能描述：数据导出
        //参数说明：无
        //返回值：列表
        function f_export(obj) {
            form1.queryOrder.value = "back";
            $("#form1 input[name='turnPage.needDispPage']").attr("value", "1");
            $("#form1 input[name='turnPage.totalLinesNum']").attr("value", "0");
            $("#form1 input[name='turnPage.queryFlag']").attr("value", "new");
            form1.action = "<%=baseWebPath%>/excel/exportAction.action?model=pzjg";
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
            form1.action = "<%=baseWebPath%>/pzjg/queryListAction.action";
            disableAllSubmit();
            form1.submit();
        }
    </script>

</head>

<body>

<s:actionerror/>
<form name="form1" id="form1" action="" method="post">
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

<s:hidden name="module_str" value="fwz"/>

<!-- 导航条 Start -->
<div id="navigate_container"
     style="width: 800px;height: 25px; margin-left:3px; padding:5px"
     class="MenuBar">
    <table style="width: 100%;" cellspacing="0">
        <tr style="height:25px">
            <td>
                <img src="<%=path%>/images/navigation_left.gif" alt=""/>
                <span class="left3px">您的位置：整车物料库</span>
            </td>
            <td align="right">
                <input type="button" class="button" value="查询" onclick="f_query()"/>
                <input type="button" id="fexport" class="button" value="导出" onclick="f_export()"/>
                <input type="button" class="button" value="主页" onclick="gohome()"/>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条 END -->

<!-- 检索条件 Start -->
<div id="serach_container" style="width: 800px; margin-left:3px; margin-top:5px;">
    <fieldset style="width: 795px" id="FIELDSET1">
        <legend>检索条件</legend>
        <table style="width: 795px">
            <!-- cssClass="upper" 大写 -->
            <tr>
                <td align="right">销售物料编号&nbsp;</td>
                <td>
                    <s:textfield id="vsn" name="pzjgParam.vsn" value="%{#session.pzjgConParas.pzjgParam.vsn}"
                                 maxlength="10" cssStyle="width:100px" cssClass="upper"/>
                </td>
                <td align="right">车系分类&nbsp;</td>
                <td>
                    <s:textfield id="cxfl" name="pzjgParam.cxfl" value="%{#session.pzjgConParas.pzjgParam.cxfl}"
                                 size="20" cssStyle="width:100px" cssClass="upper"/></td>
                <td align="right">配置类型&nbsp;</td>
                <td>
                    <s:textfield id="pzlx" name="pzjgParam.pzlx" value="%{#session.pzjgConParas.pzjgParam.pzlx}"
                                 size="20" cssStyle="width:100px" cssClass="upper"/>
                </td>
                <td align="right">状态&nbsp;</td>
                <td>
                    <s:select name="pzjgParam.zt" list="#{'':'请选择状态','0':'停产','1':'正常' }" theme="simple"/>
                </td>

            </tr>
            <tr>
                <td align="right">车系&nbsp;</td>
                <td>
                    <s:select name="pzjgParam.cx" list="cxList" emptyOption="true"
                              value="%{#session.pzjgConParas.pzjgParam.cx}" cssStyle="width:100px"/>
                </td>
                <td align="right">车型描述&nbsp;</td>
                <td>
                    <s:select name="pzjgParam.xhms" list="xhmsList" emptyOption="true"
                              value="%{#session.pzjgConParas.pzjgParam.xhms}" cssStyle="width:100px"/>
                </td>
                <td align="right">车辆颜色&nbsp;</td>
                <td>
                    <s:select name="pzjgParam.clys" list="clysList" emptyOption="true"
                              value="%{#session.pzjgConParas.pzjgParam.clys}" cssStyle="width:100px"/>
                </td>
            </tr>
        </table>
    </fieldset>
</div>
<!-- 检索条件 END -->

<!-- 数据列表 Start -->
<div id="data_container" style="width: 800px;margin-left:3px; margin-top:3px;overflow-x:scroll">
<table id="data_list" class="data" border="0" cellspacing="0" cellpadding="0" width="2000px">
<tr id="data_list_th" class="field">
    <th>
        <input type="checkbox" name="all_flag" onclick="ck_select(this,'rowflag');"/>
    </th>
    <th>
        <a onclick="f_sort('vsn');return false;">
            <span>销售物料编号</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('pp');return false;">
            <span>品牌</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('cx');return false;">
            <span>车系</span>
        </a>
    <th>
        <a onclick="f_sort('cxfl');return false;">
            <span>车系分类</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('xhms');return false;">
            <span>车型描述</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('clys');return false;">
            <span>车辆颜色</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('yswd');return false;">
            <span>颜色未定</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('xh');return false;">
            <span>车型代码</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('cpms');return false;">
            <span>产品描述</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('zt');return false;">
            <span>状态</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('pzlx');return false;">
            <span>配置类型</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('fdjxh');return false;">
            <span>发动机型号</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('abs');return false;">
            <span>ABS</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('ns');return false;">
            <span>内饰</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('xzj');return false;">
            <span>选装件</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('tcrq');return false;">
            <span>停产日期</span>
        </a>
    </th>

    <th>
        <a onclick="f_sort('xsj');return false;">
            <span>销售价</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('dj');return false;">
            <span>单价</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('cbj');return false;">
            <span>成本价</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('wddbj');return false;">
            <span>网点调拨价</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('zdxsj');return false;">
            <span>最低销售限价</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('memo');return false;">
            <span>备&nbsp;注</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('czymc');return false;">
            <span>操作员名称</span>
        </a>
    </th>
    <th>
        <a onclick="f_sort('czrq');return false;">
            <span>操作日期</span>
        </a>
    </th>
</tr>

<!-- 查询结果如果在当前堆栈中不存在，从Session中获取 set后默认放在ActionContext中-->
<s:if test="pzjgList != null">
    <s:set name="pzjgInfo" value="pzjgList"/>
</s:if>
<s:else>
    <s:set name="pzjgInfo" value="#session.pzjgList"/>
</s:else>

<!--使用iterator迭代输出查询结果列表-->
<s:iterator value="pzjgInfo" id="pzjg" status="st">
    <s:if test="#st.index >= turnPage.needResultBeginNum && #st.index <= turnPage.needResultEndNum">
        <tr id="data_list_date">
            <td align="center">
                <input type="checkbox" name="rowflag" value="<s:property value="#pzjg.vsn"/>"/>
            </td>
            <!-- 物料编号 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="#pzjg.vsn" escape="false"/>
            </td>
            <!-- 品牌 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.pp)" escape="false"/>
            </td>
            <!-- 车系 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.cx)" escape="false"/>
            </td>
            <!--  车系分类-->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.cxfl)" escape="false"/>
            </td>
            <!-- 车型描述 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.xhms)" escape="false"/>
            </td>
            <!-- 车辆颜色 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.clys)" escape="false"/>
            </td>
            <!-- 颜色未定 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@formatPzjgYswd(#pzjg.yswd)" escape="false"/>
            </td>
            <!-- 车型代码 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.xh)" escape="false"/>
            </td>
            <!-- 产品描述 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.cpms)" escape="false"/>
            </td>
            <!-- 状态 -->
            <td align="center" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@formatPzjgZt(#pzjg.zt)" escape="false"/>
            </td>
            <!--配置类型  -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.pzlx)" escape="false"/>
            </td>
            <!-- 发动机型号 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.fdjxh)" escape="false"/>
            </td>
            <!-- ABS -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.abs)" escape="false"/>
            </td>
            <!-- 内饰 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.ns)" escape="false"/>
            </td>
            <!--  选装件-->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.xzj)" escape="false"/>
            </td>
            <!-- 停产日期 -->
            <td align="center" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property
                        value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatDateByBar(#pzjg.tcrq))"
                        escape="false"/>
            </td>
            <!--  销售价-->
            <td align="right" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>' )">
                <s:property
                        value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@fmat(@com.pub.util.tools@replaceNullStringToNum(#pzjg.xsj),2))"
                        escape="false"/>
            </td>
            <!--  单价-->
            <td align="right" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>' )">
                <s:property
                        value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@fmat(@com.pub.util.tools@replaceNullStringToNum(#pzjg.dj),2))"
                        escape="false"/>
            </td>
            <!--成本价  -->
            <td align="right" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>' )">
                <s:property
                        value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@fmat(@com.pub.util.tools@replaceNullStringToNum(#pzjg.cbj),2))"
                        escape="false"/>
            </td>
            <!-- 网点调拨价 -->
            <td align="right" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>' )">
                <s:property
                        value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@fmat(@com.pub.util.tools@replaceNullStringToNum(#pzjg.wddbj),2))"
                        escape="false"/>
            </td>
            <!-- 最低销售限价 -->
            <td align="right" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>' )">
                <s:property
                        value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@fmat(@com.pub.util.tools@replaceNullStringToNum(#pzjg.zdxsj),2))"
                        escape="false"/>
            </td>
            <!-- 备注 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>' )">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.memo)" escape="false"/>
            </td>
            <!-- 操作员名称 -->
            <td align="left" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#pzjg.czymc)" escape="false"/>
            </td>
            <!-- 操作日期 -->
            <td align="center" ondblclick="f_view_dialog('<s:property value="#pzjg.vsn"/>')">
                <s:property
                        value="@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatDateByBar(#pzjg.czrq))"
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

</body>
</html>