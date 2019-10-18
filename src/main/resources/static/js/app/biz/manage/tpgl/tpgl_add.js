var _append_modify = '#append_modify';
var _append_modify_btn = '#append_modify_btn';
var _append_modify_ff = '#append_modify_ff';
var _search_ff = '#search_ff';


//投票选项数目u
var tp_num = 0;
var tup_num = 0;
//定义flag_bc 0表示点击下一步  1表示第二步  2表示最后一步保存
//var flag_bc = "0";
var twlx = "0"; //图文类型，0只有文字  1图文都有

//投票内容：
var append_zt = "";//主题
var append_ztsm = "";//主题说明
var append_gz = "";//主题规则
var append_tplx = "";//投票类型
var addArray_tpnr = new Array();//投票内容
var addArray_tptpdz = new Array();//投票内容对应的图片地址
var addArray_lx = new Array();//$('#lx0').val("0"); 修改lx : 0为默认值，1为新增的，2为修改后的
var addArray_tpid = new Array();//修改顯示的圖片id
var append_ksrq = "";//投票开始时间
var append_zzrq = "";//投票截止时间
var append_qybj = "";//启用标记
var twlx_zh ="";//判断图文类型在修改的时候是否改变
var twlx_zhq ="";//
//投票选项新增
var append_tw_num = 3;
//var xz_bj = "";//判断 新增状态 xz 或者  编辑状态 bj
/**
 * 初始化加载
 */
$(function() {
    $('#append_zt').textbox('textbox').attr('maxlength',45);
    $('#append_ztsm').textbox('textbox').attr('maxlength',100);
    $('#append_gz').textbox('textbox').attr('maxlength',100);

    //投票类型 onChange 事件
    $("#append_tplx").combobox({
        onChange: function (n,o) {
            if(n=="0"){
                $("#th_append_dxgs").css("display","none");
                $("#td_append_dxgs").css("display","none");

            }else{
                $("#th_append_dxgs").css("display","block");
                $("#td_append_dxgs").css("display","block");
            }
        }
    });

    //获取图文类型 onChange 事件
    $("#append_twlx").combobox({
        onChange: function (n,o) {
            if(n=="0"){
                twlx = "0";
                twlx_zh = "0";
            }else{
                twlx = "1";
                twlx_zh = "1";
            }
        }
    });

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
    append_tw_num = 3;
    $("#append_tab1").css("display","block");
    $("#append_tab2_0").css("display","none");
    $("#append_tab2_1").css("display","none");
    $("#append_tab3").css("display","none");
    $(_append_modify_ff).form('clear');
    $('#append_twlx').combobox("select", "0");
    $('#append_tplx').combobox("select", "0");
    $('#append_qybj').combobox("select", "1");
    $('#lx0').val("0");
    $('#lx1').val("0");
    $('#lx2').val("0");
    $('#append_dxgs').textbox("setValue", "1");
    $(_append_modify).dialog('open').dialog('setTitle','新增');
}
//编辑
function edittp() {
    xz_bj = "bj";
    flag_bc = "0";
    $("#append_tab1").css("display","block");
    $("#append_tab2_0").css("display","none");
    $("#append_tab2_1").css("display","none");
    $("#append_tab3").css("display","none");
    $(_append_modify_ff).form('clear');

    $.messager.progress({
        title : '提示',
        msg : '数据查询中...',
        text : ''
    });

    $.ajax({
        url: _domain_path+"/mobile/request?tld=tpgl001TMbXtAppTpglService_getTpEdit",
        data: {
            "contentType": "application/json;charset=utf-8",
            tp_id: tp_id
        },
        type: "post",
        dataType: "json",
        async: false,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.progress('close');
            $.messager.alert(textStatus);
        },
        success: function (responseText, textStatus, XMLHttpRequest) {
            //console.log(responseText);
            doedittp(responseText);
        }
    });


}
//执行将编辑信息展示
function doedittp(responseText) {
    $("#th_append_dxgs").css("display","block");
    $("#td_append_dxgs").css("display","block");

    var list001 = responseText.list001;
    var list002 = responseText.list002;
    $('#lx0').val("0");
    $('#lx1').val("0");
    $('#lx2').val("0");
    $("#append_zt").textbox("setValue",list001[0].zt);
    $("#append_ztsm").textbox("setValue",list001[0].zt_sm);
    $("#append_gz").textbox("setValue",list001[0].zt_gz);
    $("#append_tplx").combobox("select",list001[0].dx_bj);
    $("#append_twlx").combobox("select",list001[0].tp_bj);
    twlx_zhq = list001[0].tp_bj;
    twlx_zh = list001[0].tp_bj;
    $("#append_dxgs").textbox("setValue",list001[0].dx_gs);

    $('#append_ksrq').datebox('setValue',list001[0].ks_rq);
    $('#append_zzrq').datebox('setValue',list001[0].zz_rq);

    if(list001[0].qy_bj == "2"){
        $('#append_qybj').combobox('select','1');
    }else{
        $('#append_qybj').combobox('select',list001[0].qy_bj);
    }


    var objInput = "";
    //图文类型，0只有文字  1图文都有
    if(list001[0].tp_bj == "1"){
        var tw_url = responseText.tw_url;
        //var baseb4Image = responseText.baseb4Image;
        objInput = document.getElementById("append_tab2_1").getElementsByTagName("input");
        var edt_tw_num =3;

        for(var i=0;i<list002.length;i++)
        {
            if(i==0){
                //0-1-2-3-4-5
                $("#img0").attr("src",tw_url+list002[0].selection_tp);
                //$("#file_url0").val(baseb4Image[0].baseb4Image);
                $("#file_url0").val(list002[0].selection_tp);
                $("#range0").val(list002[0].uuid);
                objInput[5].value = list002[0].selection;
            } else if(i==1){
                //6-7-8-9-10-11
                $("#img1").attr("src",tw_url+list002[1].selection_tp);
                $("#file_url1").val(list002[1].selection_tp);
                $("#range1").val(list002[1].uuid);
                objInput[11].value = list002[1].selection;
            } else if(i==2){
                //12-13-14-15-16-17
                $("#img2").attr("src",tw_url+list002[2].selection_tp);
                $("#range3").val(list002[2].uuid);
                $("#file_url2").val(list002[2].selection_tp);
                objInput[17].value = list002[2].selection;
            }else if(i > 2){
                $("#append_tab2_1").append('<tr>' +
                    '                            <td><input width="1%" type="checkbox" name="add_check" id="add_check'+edt_tw_num+'"></td>' +
                    '                            <td bgcolor="#FFFFFF">' +
                    '                                <img src="" id="img'+edt_tw_num+'" width="80px" height="80px">' +
                    '                                <input type="file" class="" name="file'+edt_tw_num+'" id="file'+edt_tw_num+'" onchange="changeImg(this)" multiple="multiple" />' +
                    '                                <input type="hidden" name="file_url'+edt_tw_num+'" id="file_url'+edt_tw_num+'" multiple="multiple">' +
                    '                                <input type="password" name="range'+edt_tw_num+'" id="range'+edt_tw_num+'" value="'+list002[i].uuid+'" style="display: none;">'+
                    '                                <input type="number" name="lx'+edt_tw_num+'" id="lx'+edt_tw_num+'" value="0" style="display: none;">'+
                    '                            </td>' +
                    '                            <td bgcolor="#FFFFFF"><input type="text" class="input_add" maxlength="15" style="width:100%" value="'+list002[i].selection+'"></td>' +
                    '                        </tr>'
                );
                $("#img"+edt_tw_num).attr("src",tw_url+list002[i].selection_tp);
                $("#file_url"+edt_tw_num).val(list002[i].selection_tp);
                edt_tw_num++;
                append_tw_num = edt_tw_num;
            }
        }
    }else{
        objInput = document.getElementById("append_tab2_0").getElementsByTagName("input");
        for(var i=0;i<list002.length;i++)
        {
            if(objInput[1].type=="text" && i==0){
                objInput[1].value = list002[0].selection;
            } else if(objInput[3].type=="text" && i==1){
                objInput[3].value = list002[1].selection;
            } else if(objInput[5].type=="text" && i==2){
                objInput[5].value = list002[2].selection;
            }else if(i > 2){
                $("#append_tab2_0").append('<tr><td><input width="1%" type="checkbox" name="add_check"></td>' +
                    '<td bgcolor="#FFFFFF"><input name="inp_add" type="text" class="input_add" maxlength="15" style="width: 100%" value="'+list002[i].selection+'"></td></tr>');

            }
        }
    }

    $.parser.parse($('#tabProduct').parent());
    $(_append_modify).dialog('open').dialog('setTitle','编辑');
    $.messager.progress('close');
}
//上一步
function append_syb() {

    if(flag_bc == "1"){
        flag_bc = "0";
        $('#append_syb').hide();
        $("#append_tab1").css("display","block");
        $("#append_tab2_0").css("display","none");
        $("#append_tab2_1").css("display","none");
        $("#append_tab3").css("display","none");
        $('#append_xyb').linkbutton({text:'下一步'});
    }else if( flag_bc == "2"){
        flag_bc = "1";
        $("#append_tab1").css("display","none");
        if(twlx == "1"){
            $("#append_tab2_1").css("display","block");
        }else{
            $("#append_tab2_0").css("display","block");
        }
        $("#append_tab3").css("display","none");
        $('#append_xyb').linkbutton({text:'下一步'});
    }
}

//下一步
function append_xyb() {
    tp_num = 0;
    tup_num = 0;

    if(flag_bc == "0"){
        //第一步内容
        append_zt = isnull($("#append_zt").val());
        append_ztsm = isnull($("#append_ztsm").val());
        append_gz = isnull($("#append_gz").val());
        append_tplx = isnull($("#append_tplx").val());
        if(append_zt == ""){
            alertBottomRightAutoClose('提示','请填写投票主题!');
            return;
        }
        if(append_ztsm == ""){
            alertBottomRightAutoClose('提示','请填写主题详细说明!');
            return;
        }
        if(append_gz == ""){
            alertBottomRightAutoClose('提示','请填写主题规则!');
            return;
        }
        if(append_tplx == ""){
            alertBottomRightAutoClose('提示','请选择投票类型!');
            return;
        }

        flag_bc = "1";
        $('#append_syb').show();
        $("#append_tab1").css("display","none");
        if(twlx == "1"){
            $("#append_tab2_1").css("display","block");
        }else{
            $("#append_tab2_0").css("display","block");
        }
        $("#append_tab3").css("display","none");
        $('#append_xyb').linkbutton({text:'下一步'});
    }else if(flag_bc == "1"){
        //第二步内容
        var objInput = "";
        addArray_tpnr = new Array();
        addArray_tptpdz = new Array();
        addArray_lx = new Array();
        addArray_tpid = new Array();
        var str = "";
        if(twlx == "1"){
            objInput = document.getElementById("append_tab2_1").getElementsByTagName("input");
            for(var i=0;i<objInput.length;i++)
            {
                if(objInput[i].type=="hidden"){
                    str = objInput[i].value;
                    if(str != ""){
                        addArray_tptpdz.push(objInput[i].value);
                        tup_num++;
                    }
                }
                if(objInput[i].type=="text"){
                    str = objInput[i].value;
                    if(str != ""){
                        addArray_tpnr.push(objInput[i].value);
                        tp_num++;
                    }
                }
                if(objInput[i].type=="number"){
                    str = objInput[i].value;
                    if(str != ""){
                        addArray_lx.push(objInput[i].value);
                    }
                }
                if(objInput[i].type=="password"){
                    str = objInput[i].value;
                    if(str != ""){
                        addArray_tpid.push(objInput[i].value);
                    }
                }
            }
            if(tp_num < 2){
                alertBottomRightAutoClose('提示','请填写至少2项投票选项!');
                return;
            }
            if(tup_num != tp_num){
                alertBottomRightAutoClose('提示','您填写的图文内容有空缺!');
                return;
            }

        }else{
            objInput = document.getElementById("append_tab2_0").getElementsByTagName("input");
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
            if(tp_num < 2){
                alertBottomRightAutoClose('提示','请填写至少2项投票选项!');
                return;
            }

        }

        addArray_tpnr = addArray_tpnr.toString();
        addArray_tptpdz = addArray_tptpdz.toString();
        addArray_lx = addArray_lx.toString();
        addArray_tpid = addArray_tpid.toString();

        flag_bc = "2";
        $('#append_syb').show();
        $("#append_tab1").css("display","none");
        $("#append_tab2_0").css("display","none");
        $("#append_tab2_1").css("display","none");
        $("#append_tab3").css("display","block");
        $('#append_xyb').linkbutton({text:'保存'});

    }else if(flag_bc == "2"){
        //第三步保存内容
        append_ksrq = $('#append_ksrq').datebox('getValues');
        append_zzrq = $('#append_zzrq').datebox('getValues');
        append_qybj = $('#append_qybj').combobox('getValue');

        if(append_ksrq == ""){
            alertBottomRightAutoClose('提示','请选择投票开始日期!');
            return;
        }
        if(append_zzrq == ""){
            alertBottomRightAutoClose('提示','请选投票截止日期!');
            return;
        }

        $.messager.confirm('提示', "该投票确认保存提交吗？", function(r) {
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

function appendTabRow() {

    if(twlx == "0"){
        $("#append_tab2_0").append('<tr><td><input width="1%" type="checkbox" name="add_check"></td><td bgcolor="#FFFFFF"><input name="inp_add" type="text" class="input_add" maxlength="15" style="width: 100%"></td></tr>');
    }else{
        $("#append_tab2_1").append('<tr>' +
            '                            <td><input width="1%" type="checkbox" name="add_check" id="add_check'+append_tw_num+'"></td>' +
            '                            <td bgcolor="#FFFFFF">' +
            '                                <img src="" id="img'+append_tw_num+'" width="80px" height="80px">' +
            '                                <input type="file" class="" name="file'+append_tw_num+'" id="file'+append_tw_num+'" onchange="changeImg(this)" multiple="multiple" />' +
            '                                <input type="hidden" name="file_url'+append_tw_num+'" id="file_url'+append_tw_num+'" multiple="multiple">' +
            '                                <input type="password" name="range'+append_tw_num+'" id="range'+append_tw_num+'" style="display: none;">'+
            '                                <input type="number" name="lx'+append_tw_num+'" id="lx'+append_tw_num+'" value="1" style="display: none;">'+
            '                            </td>' +
            '                            <td bgcolor="#FFFFFF"><input type="text" class="input_add" maxlength="15" style="width:100%"></td>' +
            '                        </tr>');
        append_tw_num++;
    }
    $.parser.parse($('#tabProduct').parent());
}
//投票选择删除
function delTabRow() {
    var n = "";
    $("input[name='add_check']:checked").each(function() { // 遍历选中的checkbox
        n = $(this).parents("tr").index();  // 获取checkbox所在行的顺序
        if(twlx == "1"){
            if(xz_bj != "xz"){
                var check_id = $(this).attr("id");
                check_id = check_id.substring(9);
                var scflag = $("#file_url"+check_id).val();
                if(scflag != ""){
                    alertBottomRightAutoClose('提示','此行无法删除!');
                    return;
                }
            }
            $("table#append_tab2_1").find("tr:eq("+n+")").remove();
        }else{
            $("table#append_tab2_0").find("tr:eq("+n+")").remove();
        }

    });
}
//保存投票信息--新增
function doSaveTp() {
    // var append_zt = "";//主题
    // var append_ztsm = "";//主题说明
    // var append_gz = "";//主题规则

    // var append_tplx = "";//投票类型
    var append_dxgs = isnull($("#append_dxgs").val());//投票个数

    // twlx = "0"; //图文类型，0只有文字  1图文都有
    // var addArray_tpnr = [];//投票内容
    // var addArray_tptpdz = [];//投票内容对应的图片地址

    // var append_ksrq = "";//投票开始时间
    // var append_zzrq = "";//投票截止时间
    // var append_qybj = "";//启用标记
    addArray_lx = addArray_lx.toString();
    addArray_tpid = addArray_tpid.toString();
    append_ksrq = append_ksrq.toString();
    append_zzrq = append_zzrq.toString();

    $.ajax({
        url: _domain_path+"/mobile/request?tld=tpgl001TMbXtAppTpglService_saveTp",
        data: {
            append_zt: append_zt,
            append_ztsm: append_ztsm,
            append_gz: append_gz,
            append_tplx: append_tplx,
            append_dxgs: append_dxgs,
            twlx: twlx,
            addArray_tpnr: addArray_tpnr,
            addArray_tptpdz: addArray_tptpdz,
            append_ksrq: append_ksrq,
            append_zzrq: append_zzrq,
            append_qybj: append_qybj
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
                $('#append_syb').hide();
                $("#append_tab1").css("display","block");
                $("#append_tab2_0").css("display","none");
                $("#append_tab2_1").css("display","none");
                $("#append_tab3").css("display","none");
                $('#append_xyb').linkbutton({text:'下一步'});
                window.parent.submit_search_ff();
            }else{
                alertBottomRightAutoClose('提示',responseText.message);
            }
        }
    });

}
//保存投票信息--修改
function doEditTp() {

    var append_dxgs = isnull($("#append_dxgs").val());//投票个数

    append_ksrq = append_ksrq.toString();
    append_zzrq = append_zzrq.toString();

    if(twlx_zh != twlx_zhq){
        $.ajax({
            url: _domain_path+"/mobile/request?tld=tpgl001TMbXtAppTpglService_editTpToZh",
            data: {
                twlx_zh:twlx_zh,
                tp_id:tp_id,
                append_zt: append_zt,
                append_ztsm: append_ztsm,
                append_gz: append_gz,
                append_tplx: append_tplx,
                append_dxgs: append_dxgs,
                twlx: twlx,
                addArray_tpnr: addArray_tpnr,
                addArray_tptpdz: addArray_tptpdz,
                addArray_lx:addArray_lx,
                addArray_tpid:addArray_tpid,
                append_ksrq: append_ksrq,
                append_zzrq: append_zzrq,
                append_qybj: append_qybj
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
                    $('#append_syb').hide();
                    $("#append_tab1").css("display","block");
                    $("#append_tab2_0").css("display","none");
                    $("#append_tab2_1").css("display","none");
                    $("#append_tab3").css("display","none");
                    $('#append_xyb').linkbutton({text:'下一步'});
                    window.parent.submit_search_ff("1",responseText.message);
                }else{
                    alertBottomRightAutoClose('提示',responseText.message);
                }
            }
        });
    }else{
        $.ajax({
            url: _domain_path+"/mobile/request?tld=tpgl001TMbXtAppTpglService_editTp",
            data: {
                tp_id:tp_id,
                append_zt: append_zt,
                append_ztsm: append_ztsm,
                append_gz: append_gz,
                append_tplx: append_tplx,
                append_dxgs: append_dxgs,
                twlx: twlx,
                addArray_tpnr: addArray_tpnr,
                addArray_tptpdz: addArray_tptpdz,
                addArray_lx:addArray_lx,
                addArray_tpid:addArray_tpid,
                append_ksrq: append_ksrq,
                append_zzrq: append_zzrq,
                append_qybj: append_qybj
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
                    $('#append_syb').hide();
                    $("#append_tab1").css("display","block");
                    $("#append_tab2_0").css("display","none");
                    $("#append_tab2_1").css("display","none");
                    $("#append_tab3").css("display","none");
                    $('#append_xyb').linkbutton({text:'下一步'});
                    window.parent.submit_search_ff("1",responseText.message);
                }else{
                    alertBottomRightAutoClose('提示',responseText.message);
                }
            }
        });
    }

}

//===============================================================================================================
//投票包含图片的处理
function changeImg(obj){
    var objUrl = getObjectURL(obj.files[0]) ;
    var id = obj.getAttribute("id");
    var last = id.substring(id.length-1);
    //console.log("objUrl = "+objUrl) ;
    if (objUrl) {
        if(!isCanvasSupported){
            //console.log("生成base64Img失败");
            alertBottomRightAutoClose('提示','生成base64Img失败');
        }else{
            compress(event, function(base64Img){
                $("#img"+last).attr("src",base64Img);
                $("#file_url"+last).val(base64Img.replace("data:image/jpeg;base64,", ""));
                var lxlast = $("#lx"+last).val();
                if(lxlast != "1"){
                    $("#lx"+last).val("2");
                }
                //console.log(base64Img);
                // 如需保存  或 把base64转成图片  ajax提交后台处理
            });
        }

    }
}

//建立一個可存取到該file的url
function getObjectURL(file) {
    var url = null ;
    if (window.createObjectURL!=undefined) { // basic
        url = window.createObjectURL(file) ;
    } else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
    } else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url ;
}

//判断是否是NULL 或者 undefined
function isnull(str){
    if(str==null||str=="undefined"||str==""||str=="null"){
        str="";
    }
    return str;
}
