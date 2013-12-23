<%--
	模块名称：备件报废_修改页面
	描述：修改
	叶成潇
	201307
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!--在此导入你所需要的Java类-->
<%@ page import="com.opensymphony.xwork2.util.ValueStack" %>
<%@ page import="com.pub.init.SysConstance" %>
<!--在此声明你所需的TagLib-->
<%@ taglib uri="/WEB-INF/taglib/excsTagLib.tld" prefix="excs" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%--放置你的Java代码--%>
<%
    String baseWebPath = (String) SysConstance.getParameterSettings().get("baseWebPath");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String REQUIRED_INPUT = (String) SysConstance.getParameterSettings().get("requiredInput");
    ValueStack vs = (ValueStack) request.getAttribute("struts.valueStack");

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
    <title>备件报废详情/修改</title>
    <base target="_self">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>

    <!--在此包含你所需要的JS文件-->
    <script type="text/javascript" src="<%=baseWebPath%>/js/operationSubmit.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/util.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/json_parse.js"></script>
    <script type="text/javascript" src="<%=baseWebPath%>/js/forminit.js"></script>
    <script type="text/javascript">
        <%--全局变量声明区域--%>
        <%--var baseWebPath = "<%=baseWebPath%>";--%>
        $(function () {
            var zt = $("#ddmlZt").val();
            if (zt == 1) {//zt为1 表示已确认，需要设置只读属性，只读样式
                $("#form1 input,textarea,select").attr("readonly", "readonly").addClass("readonly");
            }
            //设置nowrap="nowrap"
            $("#tableInfo tr:eq(0) td:even").attr("nowrap", "nowrap");
            $("#ckjc,#ckh").attr("readonly", "readonly").addClass("readonly");
        });

        //删除
        function del(obj, dataId) {//存放数据隐藏域数组名，colNames “hiddenName”中每行列名
            if ($(obj).parent().parent().siblings("tr")) {
                $(obj).parent().parent().remove();
            } else {
                alert("至少保留一条数据。");
            }

            //删除行后，表格每行id和序号需要重新计算
            $("#" + dataId + ">tbody>tr").each(
                    function (n) {
                        if (n != 0) {
                            $(this).attr("id", n);
                            $(this).children().filter(":first").text(n);
                            if (n % 2 == 0)
                                $(this).attr("class", 'odd');
                            else
                                $(this).attr("class", 'enen');
                        }
                    });
        }

        //数据校验。输入报废数量需要校验。
        function f_inputCheck(obj) {
            var kcl = parseInt($(obj).parent().next().text());
            var cls = obj.value;
            if ((null == kcl) || ("" == kcl)) {
                kcl = 0;
            }

            var re = new RegExp("^[0-9]*[1-9][0-9]*$");
            if ((cls).match(re) == null) {
                alert("请输入大于零的整数!");
                obj.focus();
                return false;
            } else if (cls > kcl) {
                alert("数量不能大于库存量");
                obj.focus();
                return false;
            }

            return true;
        }

        //把null 值转换成 空值
        function formatNullToString(str) {
            if (str == null) {
                return "";
            } else {
                return str;
            }
        }

        //保存修改
        function f_save() {
            if (checkSave()) {
                if (confirm("您确定要修改该信息吗？")) {
                    disableAllSubmit();
                    form1.action = "<%=baseWebPath%>/pjbfddml/updateAction.action";
                    form1.submit();
                }
            }
        }

        //检查必填项
        function checkSave() {
            var bj = getTableCell2(document.getElementById("data_ddnr_list")).join("|");
            var ckh = $("#ckh").val();
            if (null == ckh || "" == ckh) {
                alert("请选择仓库号");
                return;
            }
            if (bj == "") {
                alert("备件内容为空");
                return false;
            }
            $("#rowflag_modify").val(bj);
            return true;
        }

        //获取每行数据,并且装订.从0开始计数
        function getTableCell2(obj) {
            var _arrCellOnes = [];
            var _oTBody = obj.TBodies ? obj.TBodies : obj;
            var _oTRows = _oTBody.rows;
            for (i = 1; i < _oTRows.length; i++) {
                if (_oTRows[i].cells[1]) {
                    _arrCellOnes.push(_oTRows[i].cells[0].innerHTML + "#" + _oTRows[i].cells[1].innerHTML + "#" + _oTRows[i].cells[2].innerHTML + "#" + _oTRows[i].cells[3].innerHTML + "#" + _oTRows[i].cells[4].firstChild.value + "#" + _oTRows[i].cells[5].innerHTML);
                }
            }
            return _arrCellOnes;
        }

        //功能描述： 选择仓库号、仓库简称
        //参数说明：无
        //返回值：无
        function choose_ck() {
            var ts = window.showModalDialog("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/pjbfddml/dispatchCkhCkjcAction.action&time=" + new Date(), window, "dialogWidth:525px;dialogHeight:495px;center:yes;help:no;status:no;resizable:no");
            $("#ckh").val(ts[0]);
            $("#ckjc").val(ts[1]);
        }

    </script>
</head>

<body>
<s:hidden id="ddmlZt" value="%{pjbfddml.zt}"/><%--取出状态，根据状态修改可读/只读属性--%>
<!-- 导航条 Start -->
<div id="navigate_container" style="width: 100%;height: 25px; margin-left:3px; padding:5px" class="MenuBar">
    <table style="width: 100%;" cellspacing="0">
        <tr>
            <td>
                <img src="<%=baseWebPath%>/images/navigation_left.gif" alt=""/>
                <s:if test="pjbfddml.zt==1">
                    <span class="left3px">您的位置：备件报废详情</span>
                </s:if>
                <s:else>
                    <span class="left3px">您的位置：备件报废修改</span>
                </s:else>
            </td>
            <td align="right">
                <s:if test="pjbfddml.zt==1"><%--1为已确认--%>
                    <input type="button" class="button" value="返回" onclick="window.close()"/>
                </s:if>
                <s:else>
                    <input type="button" class="button" value="保存" onclick="f_save()"/>
                    <input type="button" class="button" value="返回" onclick="window.close()"/>
                </s:else>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条 End -->
<%--数据 Start--%>
<s:form id="form1" name="form1" action="" method="post">
    <s:token/>
    <s:set name="ckhInfo" value="#session.ckh"/>
    <s:hidden name="turnPage.needDispPage" value="%{turnPage.needDispPage}"/>
    <s:hidden id="rowflag_modify" name="rowflag_modify" value=""/>
    <s:hidden name="pjbfddml.zyh" value="%{pjbfddml.zyh}"/>

    <fieldset id="FIELDSET1" style="width:100%; margin-left: 3px; margin-top: 5px;">
        <legend>备件报废信息</legend>
        <table id="tableInfo" align="center" width="700px">
            <tr>
                <td align="right">仓库号</td>
                <td>
                    <s:if test="pjbfddml.zt==1">
                        <s:textfield value="%{pjbfddml.ckh}"/>
                    </s:if>
                    <s:else>
                        <s:textfield id="ckh" name="pjbfddml.ckh" value="%{pjbfddml.ckh}"
                                     ondblclick="choose_ck();" title="双击选择仓库"/><%=REQUIRED_INPUT%>
                    </s:else>
                </td>
                <td align="right">仓库名称</td>
                <td>
                    <s:textfield id="ckjc" name="pjbfddml.ckjc" value="%{pjbfddml.ckjc}"/>
                </td>
                <td align="right">类型</td>
                <td>
                    <s:if test="pjbfddml.zt==1">
                        <s:textfield
                                value="%{@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatPjbfddmlLx(pjbfddml.czlx))}"
                                cssStyle="width:80px;"/>
                    </s:if>
                    <s:else>
                        <s:select name="pjbfddml.czlx" list="#{'BF':'报废','HY':'还原'}" headerKey="%{pjbfddml.czlx}"
                                  headerValue="%{@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatPjbfddmlLx(pjbfddml.czlx))}"/>
                    </s:else>
                </td>
                <td align="right">状态</td>
                <td>
                    <s:if test="pjbfddml.zt==1">
                        <s:textfield
                                value="%{@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatPjbfddmlZt(pjbfddml.zt))}"
                                cssStyle="width:80px;"/>
                    </s:if>
                    <s:else>
                        <s:select name="pjbfddml.zt" list="#{'1':'已确认','0':'暂存'}" headerKey="%{pjbfddml.zt}"
                                  headerValue="%{@com.pub.util.tools@replaceNullStringWithNbsp(@com.pub.util.tools@formatPjbfddmlZt(pjbfddml.zt))}"/>
                    </s:else>
                </td>
                <td align="right">操作人</td>
                <td>
                    <s:textfield name="pjbfddml.czr" readonly="true"
                                 value="%{@com.pub.user.SecurityUserHolder@getCurrentUser().getName()}"
                                 cssStyle="width:80px;"/>
                </td>
            </tr>
            <tr>
                <td align="right">备注</td>
                <td colspan="9">
                    <s:textarea name="pjbfddml.memo" value="%{pjbfddml.memo}" onblur="checkAreaLength(this,1000)"
                                cssStyle="width:100%;height:80px;word-break:break-all;"/>
                </td>
            </tr>
        </table>
    </fieldset>
    <fieldset id="FIELDSET8" style="width:100%; margin-left: 3px; margin-top: 5px;">
        <legend>备件内容</legend>
        <table id="data_ddnr_list" class="data" border="0" cellspacing="0" cellpadding="0" align="center">
            <tr id="0">
                <th align="center" style="width:180px;">备件号</th>
                <th align="center" style="width:180px;">备件名称</th>
                <th align="center" style="width:60px;">单位</th>
                <th align="center" style="width:80px;">单价</th>
                <th align="center" style="width:60px;">数量</th>
                <th align="center" style="width:60px;">库存量</th>
                <th align="center" style="width:60px;">操作</th>
            </tr>
            <s:iterator value="%{bjkcBzkList}">
                <tr>
                    <td><s:property value="%{clh}"/></td>
                    <td><s:property value="%{clmc}"/></td>
                    <td align="center"><s:property value="%{dw}"/></td>
                    <td align="right"><s:property value="%{cldj}"/></td>
                    <td align="right"><input type="text" value="<s:property value='%{cls}'/>" max="10"
                                             style="width:98%;text-align:right;" onblur="f_inputCheck(this)"/></td>
                    <td align="right"><s:property value="kcl"/></td>
                    <td align='center' nowrap="nowrap"><a href="#" onclick="del(this,data_ddnr_list)">删除</a></td>
                </tr>
            </s:iterator>
            <tr>
        </table>
    </fieldset>
</s:form>
<%--数据 End--%>
</body>
</html>
