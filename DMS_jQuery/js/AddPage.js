var moduleName = "";

//功能描述：新增_保存
//参数说明：无
//返回值：无
function f_add_save() {
    //对所有dojo日期处理
    //processDojoDate();
    //对所有三分位金额处理.处理所有三分位金额输入域，反格式化，去掉“,”分隔
    //processTierceAmount();
    if ($.trim($("input[name='gsk.gskPK.gsbz']").val()) == "") {
        alert("请输入序号!");
        $("input[name='gsk.gskPK.gsbz']").focus();
        return;
    }
    if ($.trim($("input[name='gsk.gskPK.gwh']").val()) == "") {
        alert("请输入项目名称!");
        $("input[name='gsk.gskPK.gwh']").focus();
        return;
    }
    //所有checkbox预处理
    //processCheckbox();
    disableAllSubmit();
    form1.action = "<%=baseWebPath%>/" + moduleName + "/addAction.action";
    form1.submit();
}

//功能描述：新增前检查必填项
//参数说明：无
//返回值：无
function checkAdd() {
    var bj = getTableCell2(document.getElementById("data_ddnr_list")).join("|");
    var ckh = $("#ckh").val();
    if (null == ckh || "" == ckh) {
        alert("请选择仓库号");
        return;
    }
    if (bj == "") {
        alert("请选择备件内容");
        return false;
    }
    $("#rowflag_str").val(bj);
    return true;
}

//功能描述：获取每行td中的数据,并且装订.从0开始计数。传至后台。
//参数说明：obj
//返回值：封装成String的数据，如xx#xx
function getTableCell2(obj) {
    var _arrCellOnes = [];
    var _oTBody = obj.TBodies ? obj.TBodies : obj;
    var _oTRows = _oTBody.rows;
    for (i = 2; i < _oTRows.length; i++) {
        if (_oTRows[i].cells[1]) {
            _arrCellOnes.push(_oTRows[i].cells[1].innerHTML + "#" + _oTRows[i].cells[2].innerHTML + "#" + _oTRows[i].cells[3].innerHTML + "#" + _oTRows[i].cells[4].innerHTML + "#" + _oTRows[i].cells[5].firstChild.value + "#" + _oTRows[i].cells[6].innerHTML);
        }
    }
    return _arrCellOnes;
}