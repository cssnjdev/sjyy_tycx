var _append_modify = '#append_modify';
var _append_modify_btn = '#append_modify_btn';
var _append_modify_ff = '#append_modify_ff';
var _search_ff = '#search_ff';

//投票选项数目
var tp_num = 0;
//定义flag_bc 0表示点击下一步  1最后一步保存
//var flag_bc = "0";
var twlx = "0";

//投票内容：
var append_zt = "";//主题
var append_ztsm = "";//主题说明
var addArray_tpnr = new Array();//内容
var append_ksrq = "";//开始时间
var append_zzrq = "";//截止时间
var append_yxbj = "";//启用标记

//var xz_bj = "";//判断 新增状态 xz 或者  编辑状态 bj
/**
 * 初始化加载
 */
$(function() {

    $('#append_zt').textbox('textbox').attr('maxlength',45);
    $('#append_ztsm').textbox('textbox').attr('maxlength',100);

    if(xz_bj == "xz"){
        appendtp();
    }else{
        edittp();
    }

});

//新增
function appendtp() {
    xz_bj = "xz";
    flag_bc = "0";
    $("#append_tab1").css("display","block");
    $("#append_tab2").css("display","none");
    $(_append_modify_ff).form('clear');
    $('#append_yxbj').combobox("select", "1");
}
//编辑
function edittp() {
    xz_bj = "bj";
    flag_bc = "0";
    $("#append_tab1").css("display","block");
    $("#append_tab2").css("display","none");
    $(_append_modify_ff).form('clear');

    $.ajax({
        url: _domain_path+"/mobile/request?tld=zqyj001TMbXtAppZqyjService_getTpEdit",
        data: {
            zqyj_id: zqyj_id
        },
        type: "post",
        dataType: "json",
        async: false,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.alert(textStatus);
        },
        success: function (responseText, textStatus, XMLHttpRequest) {
            //console.log(responseText);

            var list001 = responseText.list001;
            var list002 = responseText.list002;
            $("#append_zt").textbox("setValue",list001[0].zq_zt);
            $("#append_ztsm").textbox("setValue",list001[0].zq_sm);

            $('#append_ksrq').datebox('setValue',list001[0].ks_rq);
            $('#append_zzrq').datebox('setValue',list001[0].zz_rq);

            if(list001[0].yx_bj == '2'){
                $('#append_yxbj').combobox('select','1');
            }else{
                $('#append_yxbj').combobox('select',list001[0].yx_bj);
            }

            var objInput = document.getElementById("append_tab2").getElementsByTagName("input");

            for(var i=0;i<list002.length;i++)
            {
                if(objInput[1].type=="text" && i==0){
                    objInput[1].value = list002[0].zq_nr;
                } else if(objInput[3].type=="text" && i==1){
                    objInput[3].value = list002[1].zq_nr;
                } else if(objInput[5].type=="text" && i==2){
                    objInput[5].value = list002[2].zq_nr;
                }else if(i > 2){
                    $("#append_tab2").append('<tr><td><input width="1%" type="checkbox" name="add_check"></td>' +
                        '<td bgcolor="#FFFFFF"><input name="inp_add" type="text" class="input_add" maxlength="100" style="width: 100%" value="'+list002[i].zq_nr+'"></td></tr>');

                }
            }
            $.parser.parse($('#tabProduct').parent());
        }
    });
}

//上一步
function append_syb() {

    if(flag_bc == "1"){
        flag_bc = "0";
        $('#append_syb').hide();
        $("#append_tab1").css("display","block");
        $("#append_tab2").css("display","none");
        $('#append_xyb').linkbutton({text:'下一步'});
    }
}

//下一步
function append_xyb() {
    tp_num = 0;

    if(flag_bc == "0"){
        //第一步内容
        append_zt = isnull($("#append_zt").val());
        append_ztsm = isnull($("#append_ztsm").val());
        append_ksrq = $('#append_ksrq').datebox('getValues');
        append_zzrq = $('#append_zzrq').datebox('getValues');
        append_yxbj = $('#append_yxbj').combobox('getValue');

        if(append_zt == ""){
            alertBottomRightAutoClose('提示','请填写征求意见主题!');
            return;
        }
        if(append_ztsm == ""){
            alertBottomRightAutoClose('提示','请填写征求意见详细说明!');
            return;
        }
        if(append_ksrq == ""){
            alertBottomRightAutoClose('提示','请选择开始日期!');
            return;
        }
        if(append_zzrq == ""){
            alertBottomRightAutoClose('提示','请选截止日期!');
            return;
        }

        flag_bc = "1";
        $('#append_syb').show();
        $("#append_tab1").css("display","none");
        $("#append_tab2").css("display","block");

        $('#append_xyb').linkbutton({text:'保存'});
    }else if(flag_bc == "1"){
        //第二步内容
        var objInput = document.getElementById("append_tab2").getElementsByTagName("input");

        addArray_tpnr = new Array();
        var str = "";
        for(var i=0;i<objInput.length;i++)
        {
            if(objInput[i].type=="text"){
                str = objInput[i].value;
                if(str != ""){
                    addArray_tpnr.push(objInput[i].value);
                    tp_num++;
                }
            }
        }

        if(tp_num < 1){
            alertBottomRightAutoClose('提示','请填写至少1项征求意见!');
            return;
        }
        $.messager.confirm('提示', "该征求意见确认保存提交吗？", function(r) {
            if (r) {
                if(xz_bj == "xz"){
                    doSaveTp();
                }else{
                    doEditTp();
                }

            }
        });

    }

}
//投票选项新增
function appendTabRow() {
    $("#append_tab2").append('<tr><td><input width="1%" type="checkbox" name="add_check"></td><td bgcolor="#FFFFFF"><input name="inp_add" type="text" class="input_add" maxlength="100" style="width: 100%"></td></tr>');
    $.parser.parse($('#tabProduct').parent());
}
//投票选择删除
function delTabRow() {
    var n = "";
    $("input[name='add_check']:checked").each(function() { // 遍历选中的checkbox
        n = $(this).parents("tr").index();  // 获取checkbox所在行的顺序
        $("table#append_tab2").find("tr:eq("+n+")").remove();

    });
}
//保存投票信息--新增
function doSaveTp() {

    addArray_tpnr = addArray_tpnr.toString();
    append_ksrq = append_ksrq.toString();
    append_zzrq = append_zzrq.toString();

    $.ajax({
        url: _domain_path+"/mobile/request?tld=zqyj001TMbXtAppZqyjService_saveTp",
        data: {
            append_zt: append_zt,
            append_ztsm: append_ztsm,
            addArray_tpnr: addArray_tpnr,
            append_ksrq: append_ksrq,
            append_zzrq: append_zzrq,
            append_yxbj: append_yxbj
        },
        type: "post",
        dataType: "json",
        async: false,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.alert(textStatus);
        },
        success: function (responseText, textStatus, XMLHttpRequest) {
            //console.log(responseText);
            var data_flag = responseText.state;
            if(data_flag == "1"){
                alertBottomRightAutoClose('提示',responseText.message);
                $('#append_modify').dialog('close');
                flag_bc = "0";
                $("#append_tab1").css("display","block");
                $("#append_tab2").css("display","none");
                $('#append_xyb').linkbutton({text:'下一步'});
                window.parent.submit_search_ff("1",responseText.message);
            }else{
                alertBottomRightAutoClose('提示',responseText.message);
            }
        }
    });

}
//保存投票信息--修改
function doEditTp() {
    addArray_tpnr = addArray_tpnr.toString();
    append_ksrq = append_ksrq.toString();
    append_zzrq = append_zzrq.toString();

    $.ajax({
        url: _domain_path+"/mobile/request?tld=zqyj001TMbXtAppZqyjService_editTp",
        data: {
            zqyj_id: zqyj_id,
            append_zt: append_zt,
            append_ztsm: append_ztsm,
            addArray_tpnr: addArray_tpnr,
            append_ksrq: append_ksrq,
            append_zzrq: append_zzrq,
            append_yxbj: append_yxbj
        },
        type: "post",
        dataType: "json",
        async: false,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.alert(textStatus);
        },
        success: function (responseText, textStatus, XMLHttpRequest) {
            //console.log(responseText);
            var data_flag = responseText.state;
            if(data_flag == "1"){
                alertBottomRightAutoClose('提示',responseText.message);
                $('#append_modify').dialog('close');
                flag_bc = "0";
                $("#append_tab1").css("display","block");
                $("#append_tab2").css("display","none");
                $('#append_xyb').linkbutton({text:'下一步'});
                window.parent.submit_search_ff("1",responseText.message);
            }else{
                alertBottomRightAutoClose('提示',responseText.message);
            }
        }
    });
}

//===============================================================================================================


//判断是否是NULL 或者 undefined
function isnull(str){
    if(str==null||str=="undefined"||str==""||str=="null"){
        str="";
    }
    return str;
}