<%--
	模块名称：总部端，装备标准库_新增页面
	描述：
	作者： ycx
	创建日期：2013-05
--%>

<%@ page language="java" import="com.pub.init.ParaBean" pageEncoding="UTF-8" %>
<%--在此导入你所需要的Java类--%>
<%@ page import="com.pub.init.SysConstance" %>

<%--在此声明你所需的TagLib--%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%--放置你的Java代码--%>
<%
    String baseWebPath = (String) SysConstance.getParameterSettings()
            .get("baseWebPath");
    java.util.Hashtable h_table = SysConstance.getDictParam();
    ParaBean bean = (ParaBean) h_table.get("DMS");
    String REQUIRED_INPUT = (String) SysConstance
            .getParameterSettings().get("requiredInput");

    request.setAttribute("decorator", "none");
    //强制浏览器不缓存本页面内容
    response.setHeader("Cache-Control", "no-cache");//HTTP 1.1
    response.setHeader("Pragma", "no-cache");//HTTP 1.0

    //阻止浏览器直接从代理服务器取得本页面的内容
    response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>整车物料库新建</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>

    <!--在此包含你所需要的JS文件-->
    <script language="javascript" type="text/javascript" src="<%=baseWebPath%>/js/operationSubmit.js"></script>
    <script language="javascript" type="text/javascript" src="<%=baseWebPath%>/js/json_parse.js"></script>
    <script language="javascript" type="text/javascript" src="<%=baseWebPath%>/js/textcolor.js"></script>
    <script language="javascript" type="text/javascript"
            src="<%=baseWebPath%>/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {
            //光标自动定位到物料编号
            $("input[name='pzjg.vsn']").focus();
        });

        //功能描述：新增_保存
        //参数说明：无
        //返回值：无
        function f_add_save() {

            //对所有dojo日期处理
            processDojoDate();
            //对所有三分位金额处理
            processTierceAmount();
            if ($.trim($("input[name='pzjg.vsn']").val()) == "") {
                alert("请输入销售物料编号!");
                $("input[name='pzjg.vsn']").focus();
                return;
            }
            if ($.trim($("input[name='pzjg.cx']").val()) == "") {
                alert("请输入车系!");
                $("input[name='pzjg.cx']").focus();
                return;
            }
            if ($.trim($("input[name='pzjg.xhms']").val()) == "") {
                alert("请输入车型描述!");
                $("input[name='pzjg.xhms']").focus();
                return;
            }
            if ($.trim($("input[name='pzjg.clys']").val()) == "") {
                alert("请输入车辆颜色!");
                $("input[name='pzjg.clys']").focus();
                return;
            }

            disableAllSubmit();
            form1.action = "<%=baseWebPath%>/pzjg/addAction.action";
            form1.submit();
        }

    </script>
</head>
<body>
<!-- 导航条 Start -->
<div id="navigate_container" style="width:580px;height: 25px; margin-left:3px; padding:5px" class="MenuBar">
    <table style="width: 100%;" cellspacing="0">
        <tr style="height:25px">
            <td>
                <img src="<%=baseWebPath%>/images/navigation_left.gif" alt=""/>
                <span class="left3px">您的位置：整车物料库新建</span>
            </td>
            <td class="bodyleft">
                <input type="button" class="button" value="确定" onclick="f_add_save()"/>
                <input type="button" class="button" value="返回" onclick="window.close()"/>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条 END -->


<!-- 新增信息区 Start -->
<div id="data_container" style="width: 580px; margin-left:3px; margin-top:3px;">
    <s:form name="form1" action=" " method="post">
        <s:token/>
        <!-- 获得停产日期 -->
        <s:hidden name="pzjg.tcrq"/>
        <s:hidden name="pzjg.czrq"/>
        <fieldset style="width: 580px" style="margin-bottom:5px">
            <legend>整车物料库信息</legend>
            <table align="center" width="100%">
                <tr>
                    <td align="right">物料编号</td>
                    <td>
                        <s:textfield id="pzjgvsn" name="pzjg.vsn" size="10" maxlength="50" cssStyle="width:150px"
                                     cssClass="upper" onblur="checkChar(this)"/><%=REQUIRED_INPUT%>
                    </td>
                    <td align="right">车系</td>
                    <td>
                        <s:textfield name="pzjg.cx" size="10" maxlength="50"
                                     cssStyle="width:150px" cssClass="upper"/><%=REQUIRED_INPUT%>
                    </td>
                </tr>
                <tr>
                    <td align="right">车型描述</td>
                    <td colspan="3">
                        <s:textfield name="pzjg.xhms" size="10"
                                     maxlength="50" cssStyle="width:440px"/><%=REQUIRED_INPUT%>
                    </td>
                    </td>
                </tr>
                <tr>
                    <td align="right">车辆颜色</td>
                    <td>
                        <s:textfield name="pzjg.clys" size="10" maxlength="50"
                                     cssStyle="width:150px"/><%=REQUIRED_INPUT%>
                    </td>
                    </td>
                    <td align="right">颜色未定</td>
                    <td>
                        <s:select name="pzjg.yswd"
                                  list="#{'0':'颜色已定','1':'颜色未定'}" theme="simple"
                                  cssStyle="width:150px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">车系分类</td>
                    <td>
                        <s:textfield name="pzjg.cxfl" size="10" maxlength="50"
                                     cssStyle="width:150px" cssClass="upper"/>
                    </td>
                    <td align="right">状态</td>
                    <td>
                        <s:select name="pzjg.zt" list="#{'0':'停产','1':'正常'}"
                                  theme="simple" cssStyle="width:150px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">车型代码</td>
                    <td>
                        <s:textfield name="pzjg.xh" size="10" maxlength="50"
                                     cssStyle="width:150px" cssClass="upper"
                                     onblur="checkChar(this)"/>
                    </td>
                    <td align="right">品牌</td>
                    <td>
                        <s:textfield name="pzjg.pp" size="10" maxlength="50"
                                     cssStyle="width:150px" cssClass="upper"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">配置类型</td>
                    <td>
                        <s:textfield name="pzjg.pzlx" size="10" maxlength="50"
                                     cssStyle="width:150px" cssClass="upper"/>
                    </td>
                    <td align="right">发动机型号</td>
                    <td>
                        <s:textfield name="pzjg.fdjxh" size="10" maxlength="50"
                                     cssStyle="width:150px" cssClass="upper"
                                     onblur="checkChar(this)"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">ABS</td>
                    <td><s:textfield name="pzjg.abs" size="10" maxlength="50"
                                     cssStyle="width:150px" cssClass="upper"
                                     onblur="checkChar(this)"/>
                    </td>
                    <td align="right">内饰</td>
                    <td><s:textfield name="pzjg.ns" size="10" maxlength="50"
                                     cssStyle="width:150px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">选装件</td>
                    <td colspan="3"><s:textfield name="pzjg.xzj" size="10"
                                                 maxlength="50" cssStyle="width:440px"/>
                    </td>

                </tr>
                <tr>
                    <td align="right">产品描述</td>
                    <td colspan="3"><s:textfield name="pzjg.cpms" size="10"
                                                 maxlength="100" cssStyle="width:440px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">销售价</td>
                    <td>
                        <s:textfield name="pzjg.xsj" size="10" maxlength="10"
                                     cssStyle="width:150px" onblur="checkAmount(this,12,2,'+0')"/>
                    </td>
                    <td align="right">停产日期</td>
                    <td>
                        <s:textfield id="pzjgtcrp" name="dojo.pzjg.tcrq"
                                     value="" onblur="checkrq(this);" size="10" maxlength="20"
                                     cssStyle="width:150px" cssClass="dateCSS"/>
                        <img onclick="WdatePicker({el:'pzjgtcrp'})"
                             src="<%=baseWebPath%>/js/My97DatePicker/skin/datePicker.gif"
                             style="cursor:pointer; width: 16px; height: 22px;"
                             align="absmiddle"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">单价</td>
                    <td>
                        <s:textfield name="pzjg.dj" size="10" maxlength="10"
                                     cssStyle="width:150px" onblur="checkAmount(this,12,2,'+0')"/>
                    </td>
                    <td align="right">成本价</td>
                    <td>
                        <s:textfield name="pzjg.cbj" size="10" maxlength="10"
                                     cssStyle="width:150px" onblur="checkAmount(this,12,2,'+0')"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">网点调拨价</td>
                    <td>
                        <s:textfield name="pzjg.wddbj" size="10" maxlength="10"
                                     cssStyle="width:150px" onblur="checkAmount(this,12,2,'+0')"/>
                    </td>
                    <td align="right">最低销售限价</td>
                    <td>
                        <s:textfield name="pzjg.zdxsj" size="10" maxlength="10"
                                     cssStyle="width:150px" onblur="checkAmount(this,12,2,'+0')"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">操作员名称</td>
                    <td>
                        <s:textfield name="pzjg.czymc"
                                     value="%{@com.pub.user.SecurityUserHolder@getCurrentUser().getName()}"
                                     size="10" maxlength="50" cssStyle="width:150px" readonly="true"/>
                    </td>
                    <td align="right">操作日期</td>
                    <td>
                        <s:textfield name="dojo.pzjg.czrq"
                                     value="%{@com.pub.util.tools@getCurrentSimpleDate('yyyy-MM-dd', null)}"
                                     cssClass="dateCSS" size="10" maxlength="10"
                                     cssStyle="width:150px" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">备注</td>
                    <td colspan="3">
                        <s:textarea name="pzjg.memo" rows="5" onblur="checkAreaLength(this,2000)"
                                    cssStyle="width:440px"/>
                    </td>
                </tr>
            </table>
        </fieldset>
    </s:form>
</div>
<!-- 新增信息区 End -->

</body>
</html>
