<%--
	模块名称：服务站端，整车物料库_查看页面
	描述：双击查看详情
	作者：叶成潇
	创建日期：2013-05
--%>
<%@ page language="java" import="com.pub.init.ParaBean" pageEncoding="UTF-8" %>

<!--在此导入你所需要的Java类-->
<%@ page import="com.pub.init.SysConstance" %>

<!--在此声明你所需的TagLib-->
<%@ taglib uri="/struts-tags" prefix="s" %>

<%--放置你的Java代码--%>
<%
    String baseWebPath = (String) SysConstance.getParameterSettings().get("baseWebPath");
    java.util.Hashtable h_table = SysConstance.getDictParam();
    ParaBean bean = (ParaBean) h_table.get("DMS");

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
    <title>整车物料库详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>

    <!--在此包含你所需要的JS文件-->
    <script language="javascript" type="text/javascript" src="<%=baseWebPath%>/js/textcolor.js"></script>

</head>
<body>
<!-- 导航条 Start -->
<div id="navigate_container" style="width: 580px;height: 25px; margin-left:3px; padding:5px"
     class="MenuBar">
    <table style="width: 100%;" cellspacing="0">
        <tr style="height:25px">
            <td>
                <img src="<%=baseWebPath%>/images/navigation_left.gif" alt=""/>
                <span class="left3px">您的位置：整车物料库</span>
            </td>
            <td align="right">
                <input type="button" class="button" value="关闭" onclick="window.close();"/>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条 END -->

<!-- 数据列表 Start -->
<div id="data_container" style="width: 580px; margin-left:3px; margin-top:5px;">
    <form name="form1" action="" method="post">
        <s:token/>
        <s:hidden name="turnPage.needDispPage" value="%{turnPage.needDispPage}"/>
        <fieldset style="width: 580px" id="FIELDSET1">
            <legend>整车物料库信息</legend>
            <table align="center" width="100%">
                <tr>
                    <td align="right">物料编号</td>
                    <td><s:textfield value="%{pzjg.vsn}"
                                     size="20" cssStyle="width:150px" cssClass="readonly"/></td>
                    <td align="right">车系</td>
                    <td><s:textfield value="%{pzjg.cx}" size="10"
                                     maxlength="50" cssStyle="width:150px" cssClass="upper readonly"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">车型描述</td>
                    <td colspan="3"><s:textfield
                            value="%{pzjg.xhms}" size="10" maxlength="50"
                            cssStyle="width:438px" cssClass="readonly"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">车辆颜色</td>
                    <td><s:textfield value="%{pzjg.clys}"
                                     size="10" maxlength="50" cssStyle="width:150px"
                                     cssClass="readonly"/></td>
                    <td align="right">颜色未定</td>
                    <td><s:select
                            list="#{'0':'颜色已定','1':'颜色未定'}"
                            headerValue="@com.pub.util.tools@formatPzjgYswd(#pzjg.yswd)"
                            cssStyle="width:150px" cssClass="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">车系分类</td>
                    <td><s:textfield value="%{pzjg.cxfl}"
                                     size="10" maxlength="50" cssStyle="width:150px"
                                     cssClass="readonly"/></td>
                    <td align="right">状态</td>
                    <td><s:select list="#{'0':'停产','1':'正常'}"
                                  headerValue="@com.pub.util.tools@formatPzjgZt(#pzjg.zt)"
                                  theme="simple" cssStyle="width:150px" cssClass="readonly"/>
                    </td>
                </tr>

                <tr>
                    <td align="right">车型代码</td>
                    <td><s:textfield value="%{pzjg.xh}" size="10"
                                     maxlength="50" cssStyle="width:150px" cssClass="readonly"/>
                    </td>
                    <td align="right">品牌</td>
                    <td><s:textfield value="%{pzjg.pp}" size="10"
                                     maxlength="50" cssStyle="width:150px" cssClass="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">配置类型</td>
                    <td><s:textfield value="%{pzjg.pzlx}"
                                     size="10" maxlength="50" cssStyle="width:150px"
                                     cssClass="readonly"/></td>
                    <td align="right">发动机型号</td>
                    <td><s:textfield value="%{pzjg.fdjxh}"
                                     size="10" maxlength="50" cssStyle="width:150px"
                                     cssClass="readonly"/></td>
                </tr>
                <tr>
                    <td align="right">ABS</td>
                    <td><s:textfield value="%{pzjg.abs}"
                                     size="10" maxlength="50" cssStyle="width:150px"
                                     cssClass="readonly"/></td>
                    <td align="right">内饰</td>
                    <td><s:textfield value="%{pzjg.ns}" size="10"
                                     maxlength="50" cssStyle="width:150px" cssClass="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">选装件</td>
                    <td colspan="3"><s:textfield value="%{pzjg.xzj}"
                                                 size="10" maxlength="50" cssStyle="width:438px"
                                                 cssClass="readonly"/></td>
                </tr>
                <tr>
                    <td align="right">产品描述</td>
                    <td colspan="3"><s:textfield
                            value="%{pzjg.cpms}" size="10" maxlength="100"
                            cssStyle="width:438px" cssClass="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">销售价</td>
                    <td><s:textfield value="%{pzjg.xsj}"
                                     size="10" maxlength="50" cssStyle="width:150px"
                                     cssClass="readonly"/></td>
                    <td align="right">停产日期</td>
                    <td><s:textfield id="pzjgtcrp"
                                     value="%{@com.pub.util.tools@formatDateByBar(pzjg.tcrq)}"
                                     size="10" maxlength="20" cssStyle="width:150px"
                                     cssClass="dateCSS readonly"/></td>
                </tr>
                <tr>
                    <td align="right">单价</td>
                    <td><s:textfield value="%{pzjg.dj}" size="10"
                                     maxlength="50" cssStyle="width:150px" cssClass="readonly"/></td>
                    <td align="right">成本价</td>
                    <td><s:textfield value="%{pzjg.cbj}"
                                     size="10" maxlength="50" cssStyle="width:150px"
                                     cssClass="readonly"/></td>
                </tr>
                <tr>
                    <td align="right">网点调拨价</td>
                    <td><s:textfield value="%{pzjg.wddbj}"
                                     size="10" maxlength="50" cssStyle="width:150px"
                                     cssClass="readonly"/></td>
                    <td align="right">最低销售限价</td>
                    <td><s:textfield value="%{pzjg.zdxsj}"
                                     size="10" maxlength="50" cssStyle="width:150px"
                                     cssClass="readonly"/></td>
                </tr>
                <tr>
                    <td align="right">操作员名称</td>
                    <td><s:textfield
                            value="%{@com.pub.user.SecurityUserHolder@getCurrentUser().getName()}"
                            size="10" maxlength="50" cssStyle="width:150px"
                            cssClass="readonly"/></td>
                    <td align="right">操作日期</td>
                    <td><s:textfield
                            value="%{@com.pub.util.tools@getCurrentSimpleDate('yyyy-MM-dd', null)}"
                            size="10" maxlength="8" cssStyle="width:150px"
                            cssClass="readonly"/></td>
                </tr>
                <tr>
                    <td align="right">备注</td>
                    <td colspan="3">
                        <s:textarea rows="3" value="%{pzjg.memo}" cssStyle="width:438px"
                                    cssClass="readonly"/>
                    </td>
                </tr>
            </table>
        </fieldset>
    </form>
</div>
<!-- 数据列表 END -->

</body>
</html>
