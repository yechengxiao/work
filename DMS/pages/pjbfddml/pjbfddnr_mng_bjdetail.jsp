<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%--显示备件报废子表的数据--%>
<script type="text/javascript">
    //功能描述：获取表格内容
    //参数说明：obj
    //返回值：无
    function getTableCell(obj) {
        var _arrCellOnes = [];
        var _oTBody = obj.TBodies ? obj.TBodies : obj;
        var _oTRows = _oTBody.rows;
        for (i = 1; i < _oTRows.length; i++) {
            if (_oTRows[i].cells[1]) {
                _arrCellOnes.push(_oTRows[i].cells[1].innerHTML);
            }
        }
        return _arrCellOnes;
    }

    //功能描述：打开Bjkc、Bzk新增对话框
    //参数说明：无
    //返回值：无
    function f_add_bjkc_dialog() {
        var winParam = $("#ckh").val();//向bjkc_bzk_choose_mng_list.jsp传递参数

        //需要判断新增的内容是否已经增加过。增加过了就需要去掉。
        var tmp_gn = [];
        var tmp_gn2 = [];
        var tmp_gn_array = getTableCell(document.getElementById("data_ddnr_list"));

        if (tmp_gn_array.length > 10) {
            alert("订单内容的数量超载,请控制在10条以内!");
            return;
        }
        if (null != tmp_gn_array && tmp_gn_array.length > 0) {
            for (var i = 0; i < tmp_gn_array.length; i++) {
                tmp_gn[tmp_gn_array[i]] = true;
            }
        }
        var ts = window.showModalDialog(encodeURI("<%=baseWebPath%>/dialogue/dialog_panel.jsp?action=<%=baseWebPath%>/pjbfddml/turnChooseBjkcPageAction.action&time=" + new Date()), winParam, "dialogWidth:830px;dialogHeight:410px;center:yes;help:no;status:no;resizable:no");
        //多行数据被返回，每行数据用|，分隔。
        if (ts == undefined || ts == "") {
            return;
        }
        var ts_tmp = ts.split("|");//addTr中，还要继续split()，以便取出用#分隔的数据。用#分隔的数据为每行中<td></td>间需要显示的数据

        for (var i = 0; i < ts_tmp.length; i++) {
            var tmpRecord = ts_tmp[i].split("#")
            if (!tmp_gn[ tmpRecord[0] ]) {
                tmp_gn2.push(ts_tmp[i]);
            }
        }
        if (tmp_gn_array.length + tmp_gn2.length > 10) {
            alert("添加完成后订单内容的数量超载,请控制在10条以内!");
            return;
        }

        addTr(tmp_gn2, "data_ddnr_list");
    }
    //功能描述：新增行。model为需要添加行的table的id
    //参数说明：无
    //返回值：无
    function addTr(recordArray, model) {
        var tr_id = $("#" + model + ">tbody>tr:last").attr("id");
        tr_id++;
        var html;
        for (var i = 0; i < recordArray.length; i++) {
            html = "";
            if (tr_id % 2 == 0)
                html += "<tr id='" + tr_id + "' class='odd'>";
            else
                html += "<tr id='" + tr_id + "' class='enen'>";

            arr = String(recordArray[i]).split("#");

            html += "<td align='center' nowrap  style='width:30px;word-break:break-all' >" + tr_id + "</td>";
            html += "<td align='left' nowrap width='100px'>" + formatNullToString(arr[0]) + "</td>";
            html += "<td align='left' nowrap width='100px'>" + formatNullToString(arr[1]) + "</td>";
            html += "<td align='left' nowrap >" + formatNullToString(arr[2]) + "</td>";
            html += "<td align='right' nowrap >" + formatNullToString(arr[3]) + "</td>";
            html += "<td align='right' nowrap ><input type='text' name='' value='" + formatNullToString(arr[4]) + "' size='10' max='10' style='width:98%;text-align:right;' onblur='f_inputCheck(this)'  />" + "</td>";
            html += "<td align='right' nowrap> " + formatNullToString(arr[5]) + "</td>";
            html += "<td style='word-break:break-all' align='center' nowrap> <a href='#' onclick='del(this, \"xsddnrList_arr\", \"data_ddnr_list\",\"" + formatNullToString(arr[0]) + "\")'>删除</a></td>";
            html += "</tr>";

            $("#" + model).append(html);
            tr_id++;
        }
    }

    //功能描述：删除行
    //参数说明：obj, hiddenName, dataId, data
    //返回值：无
    function del(obj, hiddenName, dataId, data) {//存放数据隐藏域数组名，colNames “hiddenName”中每行列名
        $(obj).parent().parent().remove();
        //删除行后，表格每行id和序号需要重新计算
        $("#" + dataId + ">tbody>tr").each(
                function (n) {
                    if (n != 0) {
                        $(this).attr("id", n);
                        $(this).next().children().filter(":first").text(n);
                        if (n % 2 == 0)
                            $(this).attr("class", 'odd');
                        else
                            $(this).attr("class", 'enen');
                    }
                });
    }

    //功能描述：数据校验，输入必须为正整数。
    //参数说明：obj
    //返回值：无
    function f_inputCheck(obj) {
        var kcl = $(obj).parent().next().text();//获取当前对象的父节点的下一个节点中的值。库存量
        if ((null == kcl) || ("" == kcl)) kcl = 0;
        var cls = obj.value;//材料数。
        var reg = new RegExp("^[1-9][0-9]*$");//正则表达式，正整数
        var regExec = reg.exec(cls);

        if (null != cls && cls != regExec) {
            alert("请输入大于零的整数！");
            obj.focus();
            return;
        } else if (cls > kcl) {
            //alert(cls + " " + kcl);
            alert("数量不能大于库存量");
            //obj.focus();
            return;
        }
        return true;
    }

    //功能描述：把null 值转换成 空值
    //参数说明：str
    //返回值：无
    function formatNullToString(str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }
</script>

<fieldset id="FIELDSET8" style="width:100%; margin-left: 3px; margin-top: 5px;">
    <legend>备件内容</legend>
    <%--<div id="ydfwzhdiv2" style="height:100%; margin-top: 5px;border: 0px solid red; display:inline;">--%>

    <div style="width:100%;height:auto;border: 0px solid red;">

        <table id="data_ddnr_list" class="data" border="0" cellspacing="0" cellpadding="0" align="center">
            <tr>
                <td align="right" colspan="8">
                    <input id="button" type="button" size="100px" value="打开备件选择" onclick="f_add_bjkc_dialog()"/>
                </td>
            </tr>
            <tr id="0">
                <th align="center" style="width:60px;">行号</th>
                <th align="center" style="width:120px;">备件号</th>
                <th align="center" style="width:200px;">备件名称</th>
                <th align="center" style="width:60px;">单位</th>
                <th align="center" style="width:60px;">单价</th>
                <th align="center" style="width:60px;">数量</th>
                <th align="center" style="width:60px;">库存量</th>
                <th align="center" style="width:60px;">操作</th>
            </tr>
        </table>
    </div>
    <%--</div>--%>
</fieldset>