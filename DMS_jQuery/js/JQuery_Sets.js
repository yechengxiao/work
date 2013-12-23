//功能：出库数小于材料数总数，则自动在列表中选中对应的材料数
//出处： KarryShouhou ckcl_mng_list.jsp
//模块名称：出库处理查询列表页面
$("#cks").blur(function () {
    var $cksVal = $("#cks").val();//出库数
    var reg = new RegExp("^[1-9][0-9]*$");//正则表达式，正整数
    var regExec = reg.exec($cksVal);
    if (null != $cksVal && $cksVal != regExec) {
        alert("请输入大于0的整数！");
    } else if (null != $cksVal && $cksVal == regExec && $("#data_list tr").length > 1) {//输入的出库数为正数,判断列表中是否有数据
        var clsAll = 0;//材料数总数
        $("#data_list tr:gt(0)").each(function () {//:gt(index) 返回值:Array<Element(s)> 意义：匹配所有大于给定索引值的元素 index：从 0 开始计数
            clsAll = clsAll + parseInt($(this).children("td:eq(3)").text());
            //alert("clsAll is " + clsAll)
        });
        if ($cksVal == clsAll) {
            $("#data_list :checkbox").attr("checked", true);
        } else if ($cksVal > clsAll) {
            alert("出库数不能大于材料数！");
        } else if ($cksVal < clsAll) {//出库数小于材料数总数的情况
            var valTd4 = 0;//材料数
            //alert("$cksVal is " + $cksVal);
            var $thisTr = $("#data_list tr:gt(0)");//除去第一行的其他行
            //alert("$thisTr.length is " + $thisTr.length);

            if (0 != $("#data_list :checkbox:checked").length) {//当有多选框被选中，则先取消
                $("#data_list :checkbox").attr("checked", false);
            }

            $thisTr.each(function () {//开始遍历
                valTd4 = parseInt($(this).children("td:eq(3)").text());//cls中的值
                //alert("valTd4 " + valTd4);
                if (valTd4 <= $cksVal) {//当材料数小于出库数，则选中
                    $(this).find("[name='rowflag']").attr("checked", true);//多选框勾选当前
                    //alert("checked ?");
                    $cksVal = $cksVal - valTd4;//出库数减，与下一个材料数对比
                }
            });
        }
    }
});

//功能：往Action传包含对象的List
//出处： KarryShouhou fwz_fwzzb_mng_add.jsp
//模块名称：服务站装备(fwzzb)新增页面

//功能描述：向父窗口返回数组
//参数说明：需要返回的值
//返回值：无
function returnVal(clh, clmc) {
    var ts = new Array($.trim(clh), $.trim(clmc));
    window.returnValue = ts;
    window.close();
}

//功能描述：获取父窗口的值
//参数说明：无
//返回值：无
$(function () {
    var ckh = window.dialogArguments
    $("#ckh").val(ckh);
});

//功能：数据校验，输入必须为正整数。
//出处： DMS  pjbfddnr_mng_bjdetail.jsp
//模块名称：备件报废 子表内容
function f_inputCheck(obj) {
    var kcl = $(obj).parent().next().text();//获取当前对象的父节点的下一个节点中的值。库存量
    if ((null == kcl) || ("" == kcl)) kcl = 0;

    var cls = obj.value;//材料数。
    var reg = new RegExp("^[1-9][0-9]*$");//正则表达式，正整数
    var regExec = reg.exec(cls);

    if (null != cls && cls != regExec) {
        alert("请输入大于零的整数！");
        //obj.focus();
        obj.value = 1;
        return;
    } else if (cls > kcl) {
        alert("数量不能大于库存量");
        //obj.focus();
        obj.value = 1;
        return;
    }
    return true;
}



$(function () {
    //设置属性、样式为只读
    $("#mclh,#mclhMC").attr("readonly", "readonly").addClass("readonly");

    //在行上触发ondblclick事件，取消checkbox的click事件冒泡
    //http://www.oschina.net/question/919301_83721
    $(":checkbox").click(function (event) {
        event.stopPropagation();
    });

    //设置宽度
    $("#add_table td:even").width("60");
});


//使用正则表达式
//还没用过，可以试试
function valid_email(email) {
    var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
    return patten.test(email);
}
if(valid_email(email)) {
}
