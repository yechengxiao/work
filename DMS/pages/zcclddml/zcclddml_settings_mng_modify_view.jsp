<%--
    模块名称：ZB，拆零组装，设置，zccl修改&查看页面
    描述：
    作者： 叶成潇
    创建日期：2013-08
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
    final String MODULE_NAME = "拆零组装_设置 ";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title><%=MODULE_NAME%>详情/修改</title>
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
        $(function () {
            //设置只读属性，只读样式
            $("#mclh,#mclhMC").attr("readonly", "readonly").addClass("readonly");

            var td_modify = $("#data_ddnr_list tr:gt(0) input");
            td_modify.attr("readonly", "readonly").addClass("readonly");

            //设置按钮属性
            var save = $("#button_save");
            var modify = $("#button_modify");
            save.attr("disabled", true);
            modify.click(function () {
                modify.attr("disabled", true);
                save.attr("disabled", false);
                td_modify.removeAttr("readonly").removeClass("readonly");
            });
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
            var cls = obj.value;
            var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
            var regExec = reg.exec(cls);

            if (null != cls && cls != regExec) {
                alert("请输入大于零的整数！");
                //obj.focus();
                obj.value = 1;
                return;
            }
            return true;
        }
        //保存修改
        function f_save() {
            if (checkSave()) {
                if (confirm("您确定要修改该信息吗？")) {
                    disableAllSubmit();
                    form1.action = "<%=baseWebPath%>/zcclddml/updateZcclAction.action";
                    form1.submit();
                }
            }
        }
        //检查必填项
        function checkSave() {
            var zclh = getTableCell2(document.getElementById("data_ddnr_list")).join("|");

            if (zclh == "") {
                alert("零件号内容为空");
                return false;
            }
            $("#rowflag_modify").val(zclh);
            var mclh = $("#mclh").val();
            var mclmc = $("#mclhMC").val();
            $("#mclh_modify").val(mclh + "#" + mclmc);
            return true;
        }

        //获取每行数据,并且装订.从0开始计数
        function getTableCell2(obj) {
            var _arrCellOnes = [];
            var _oTBody = obj.TBodies ? obj.TBodies : obj;
            var _oTRows = _oTBody.rows;
            for (i = 1; i < _oTRows.length; i++) {
                if (_oTRows[i].cells[1]) {
                    _arrCellOnes.push(_oTRows[i].cells[0].innerHTML + "#" + _oTRows[i].cells[1].innerHTML + "#" + _oTRows[i].cells[2].firstChild.value + "#"
                            + _oTRows[i].cells[3].innerHTML + "#" + _oTRows[i].cells[4].innerHTML);
                }
            }
            return _arrCellOnes;
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
                <span class="left3px">您的位置：<%=MODULE_NAME%>详情/修改</span>
            </td>
            <td align="right">
                <input type="button" class="button" value="修改" id="button_modify"/>
                <input type="button" class="button" value="保存" id="button_save" onclick="f_save()"/>
                <input type="button" class="button" value="返回" onclick="window.close()"/>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条 End -->
<%--数据 Start--%>
<s:form name="form1" action="" method="post">
    <s:token/>
    <s:hidden name="turnPage.needDispPage" value="%{turnPage.needDispPage}"/>
    <s:hidden id="rowflag_modify" name="rowflag_modify" value=""/>
    <s:hidden id="mclh_modify" name="mclh_modify"/>

    <fieldset id="FIELDSET8" style="width:100%; margin-left: 3px; margin-top: 5px;">
        <legend>总成号信息</legend>
        <table align="center">
            <tr>
                <td align="right">总成号</td>
                <s:iterator value="zcclList" status="st">
                    <s:if test="#st.First">
                        <td>
                            <s:textfield id="mclh" value="%{zcclPK.mclh}"/>
                        </td>
                        <td align="right">总成号名称</td>
                        <td>
                            <s:textfield id="mclhMC" value="%{mclmc}"/>
                        </td>
                    </s:if>
                </s:iterator>
            </tr>
        </table>
    </fieldset>
    <fieldset id="FIELDSET8" style="width:100%; margin-left: 3px; margin-top: 5px;">
        <legend>零件号信息</legend>
        <div style="width:100%;height:auto;border: 0px solid red;">
            <table id="data_ddnr_list" class="data" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr id="0">
                    <th align="center" style="width:120px;">零件号</th>
                    <th align="center" style="width:200px;">零件号名称</th>
                    <th align="center" style="width:100px;">零件号数量</th>
                    <th align="center" style="width:100px;">单位</th>
                    <th align="center" style="width:100px;">单价</th>
                    <th align="center" style="width:60px;">操作</th>
                </tr>

                <s:iterator value="%{zcclList}">
                    <tr>
                        <td align="center"><s:property value="%{zcclPK.zclh}"/></td>
                        <td align="center"><s:property value="%{zclmc}"/></td>
                        <td align="right">
                            <input type="text" value="<s:property value='%{cls}'/>" max="10"
                                   style="width:98%;text-align:right;" onblur="f_inputCheck(this)"/>
                        </td>
                        <td align="center"><s:property value="%{dw}"/></td>
                        <td align="right"><s:property value="%{cldj}"/></td>
                        <td align='center' nowrap="nowrap"><a href="#" onclick="del(this,data_ddnr_list)">删除</a></td>
                    </tr>
                </s:iterator>
            </table>
        </div>
    </fieldset>
</s:form>
<%--数据 End--%>
</body>
</html>
