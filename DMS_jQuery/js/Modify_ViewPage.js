var moduleName = "";

//功能描述： 修改_保存
//参数说明：无
//返回值：无
function f_modify_save() {
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
    form1.action = "<%=baseWebPath%>/" + moduleName + "/updateAction.action";
    form1.submit();
}