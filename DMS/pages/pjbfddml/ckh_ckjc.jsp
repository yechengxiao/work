<%--
  模块名称：仓库号、仓库简称选择页面
  User: ycx
  Date: 13-8-27
  Time: 下午2:00
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="../../util/header.jsp" %>
<!--在此导入你所需要的Java类-->
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>请选择仓库号</title>
    <!-- 在此加入样式文件 -->
    <link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/css/StyleSheet.css"/>

    <script type="text/javascript">
        //功能描述： 返回仓库号、仓库简称
        //参数说明：ckh ckjc
        //返回值：无
        function returnVal(ckh, ckjc) {
            var ts = new Array($.trim(ckh), $.trim(ckjc));
            window.returnValue = ts;
            window.close();
        }
    </script>
</head>
<body>
<%--如果有数据则自动载入--%>
<!-- 导航条 Start -->
<div id="navigate_container" style="width: 485px; height: 25px; margin-left: 3px; padding: 5px" class="MenuBar">
    <table style="width: 100%;" cellspacing="0">
        <tr style="height: 25px">
            <td>
                <img src="<%=path%>/images/navigation_left.gif" alt=""/>
                <span class="left3px">您的位置：仓库选择</span>
            </td>
            <td align="right">
                <input type="button" class="button" value="关闭" onclick="window.close();"/>
            </td>
        </tr>
    </table>
</div>
<!-- 导航条 END -->
<!-- 数据列表 Start -->
<div id="data_container" style="width: 485px; margin-left: 3px; margin-top: 3px;">
    <table id="data_list" class="data" border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr id="tb_field" class="field">
            <th style="width: 100px"> 仓库号</th>
            <th style="width: 100px"> 仓库名称</th>
        </tr>
        <s:if test="ckhckjcList != null">
            <s:set name="ckhckjcInfo" value="ckhckjcList"/>
        </s:if>
        <s:else>
            <tr>
                <td colspan="2">没有仓库可供选择</td>
            </tr>
        </s:else>
        <!--使用iterator迭代输出查询结果列表-->
        <s:iterator value="ckhckjcInfo" id="ckhckjc" status="st">
            <%--<s:if test="#st.index >= turnPage.needResultBeginNum && #st.index <= turnPage.needResultEndNum">--%>
            <tr ondblclick="returnVal('<s:property value="#ckhckjc.ckh"/>','<s:property value="#ckhckjc.ckjc"/>')">
                <td style="width: 100px; word-break: break-all" align="center" nowrap>
                    <s:property value="#ckhckjc.ckh"/>
                </td>
                <td style="width: 100px; word-break: break-all" align="left" nowrap>
                    <s:property value="@com.pub.util.tools@replaceNullStringWithNbsp(#ckhckjc.ckjc)"
                                escape="false"/>
                </td>
            </tr>
            <%--</s:if>--%>
        </s:iterator>
    </table>
</div>
<!-- 数据列表 END -->
</body>
</html>