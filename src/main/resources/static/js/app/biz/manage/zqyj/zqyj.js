var _append_modify = '#append_modify';
var _append_modify_btn = '#append_modify_btn';
var _append_modify_ff = '#append_modify_ff';
var _search_ff = '#search_ff';

//投票选项数目
var tp_num = 0;
//定义flag_bc 0表示点击下一步  1表示第二步  2表示最后一步保存
var flag_bc = "0";
var twlx = "0"; //图文类型，0只有文字  1图文都有

//投票内容：
var append_zt = "";//主题
var append_ztsm = "";//主题说明
var append_gz = "";//主题规则
var append_tplx = "";//投票类型
var addArray_tpnr = new Array();//投票内容
var addArray_tptpdz = new Array();//投票内容对应的图片地址
var append_ksrq = "";//投票开始时间
var append_zzrq = "";//投票截止时间
var append_qybj = "";//启用标记

var xz_bj = "";//判断 新增状态 xz 或者  编辑状态 bj
//查询投票
var sel_url = _domain_path+'/mobile/request?tld=zqyj001TMbXtAppZqyjService_select';
var fields = [
    [
        {title:'zqzt_id',field:'zqzt_id',hidden : true,sortable:'true', width:'10%'},
        {title:'征求意见主题',field:'zq_zt',align : "left",sortable:'true', width:'38%',
            formatter : function(value) {
                return "<span title='" + value + "'>" + value
                    + "</span>";
            }},
        {title:'创建时间',field:'lr_sj',align : "center",sortable:'true', width:'8%'},
        {title:'开始日期',field:'ks_rq',align : "center",sortable:'true', width:'8%'},
        {title:'截止日期',field:'zz_rq',align : "center",sortable:'true', width:'8%'},
        {title:'发布日期',field:'fb_rq_rq',align : "center",sortable:'true', width:'8%'},
        {title:'创建人',field:'lr_ry',align : "center",sortable:'true', width:'8%'},
        {title:'状态',field:'yx_bj',align : "center",sortable:'true', width:'8%',
            formatter : function(value, row, index) {
                var a = '';
                if(value == "1"){
                    a="<span style='font-weight: bold;'>未 发 布</span>";
                }else if(value == "2"){
                    a="<span style='color: #0aa5df;font-weight: bold;'>已 发 布</span>";
                }else{
                    a="<span style='color: #CC0000;font-weight: bold;'>作 废</span>";
                }
                return a;
            }}
    ]
];

/**
 * 初始化加载
 */
$(function() {

    //查询投票信息
    initGSwjg();
    submit_search_ff("0","0");

});

//查询投票信息
function initGSwjg(){

    $("#tt").datagrid({
        collapsible : false,
        rownumbers : true,
        remoteSort : false,
        nowrap : true,
        striped : true,
        fitColumns:true,
        singleSelect : true,
        fit : true,
        striped : true,
        pagination : true,
        url : sel_url,
        frozenColumns : [ [ {
            field : 'ck',
            checkbox : true
        } ] ],
        columns : fields,
        toolbar: "#ttbar",
        onLoadSuccess : function(){
            dataLoadSuccess();
        }
    });
}

//加载成功执行方法
function dataLoadSuccess(){};

function submit_search_ff(a,b) {
    if(a == "1"){
        alertBottomRightAutoClose('提示',b);
    }
    $(_append_modify).dialog('close');
    var sel_zt = $("#sel_zt").textbox('getValue');
    var params = {sel_zt:sel_zt};
    $("#tt").datagrid({ queryParams : params });
}

//新增
function appendzqyj() {
    xz_bj = "xz";
    flag_bc = "0";
    $(_append_modify).dialog('open').dialog('setTitle','新增');
    $("#append_iframe").attr("src","/comm/html/templates/biz.manage.zqyj.add_zqyj?xz_bj="+xz_bj+"&flag_bc="+flag_bc);
}
//编辑
function edittp(obj) {
    xz_bj = "bj";
    flag_bc = "0";
    var zqyj_id = obj.getAttribute("data-id");
    var zyyj_zt = obj.getAttribute("data-zt");
    $(_append_modify).dialog('open').dialog('setTitle','编辑');
    $("#append_iframe").attr("src","/comm/html/templates/biz.manage.zqyj.add_zqyj?xz_bj="+xz_bj+"&flag_bc="+flag_bc+"&zqyj_id="+zqyj_id);
}
//最上勾选编辑
function ceditzyyj() {
    xz_bj = "bj";
    flag_bc = "0";
    var select = $("#tt").datagrid('getSelected');
    var totleRows = $("#tt").datagrid('getSelections');

    if (select) {
        if(totleRows.length>1){
            alertBottomRightAutoClose('提示','请选择一条记录进行修改!');
        }else{
            var zqzt_id = totleRows[0].zqzt_id;
            $(_append_modify).dialog('open').dialog('setTitle','编辑');
            $("#append_iframe").attr("src","/comm/html/templates/biz.manage.zqyj.add_zqyj?xz_bj="+xz_bj+"&flag_bc="+flag_bc+"&zqyj_id="+zqzt_id);
        }
    } else {
        alertBottomRightAutoClose('提示','请选择要修改的记录!');
    }
}
//启用或者作废 o: 0作廢 1啓用
function doQy(o) {
    var select = $("#tt").datagrid('getSelected');
    var totleRows = $("#tt").datagrid('getSelections');

    if (select) {
        if(totleRows.length>1){
            alertBottomRightAutoClose('提示','请选择一条记录!');
        }else{
            var qy_message = "";
            var zqzt_id = totleRows[0].zqzt_id;
            var zqzt_yx_bj = totleRows[0].yx_bj;
            var zqzt_zq_zt = totleRows[0].zq_zt;
            if(zqzt_yx_bj == "0" && o == "2"){
                alertBottomRightAutoClose('提示','该征求意见已经作废，无法再次发布！');
                return;
            }
            if(o == "2"){
                if(o == zqzt_yx_bj){
                    alertBottomRightAutoClose('提示','该征求意见已经启用，无需再次发布！');
                    return;
                }
                qy_message = "请确定是否将此征求意见发布！"
            }else{
                if(o == zqzt_yx_bj){
                    alertBottomRightAutoClose('提示','该征求意见已经作废，无需再次作废！');
                    return;
                }
                qy_message = "请确定是否将此征求意见作废！"
            }
            $.messager.confirm('提示', qy_message, function(r) {
                if (r) {

                    $.ajax({
                        url: _domain_path+"/mobile/request?tld=zqyj001TMbXtAppZqyjService_qytp",
                        data: {
                            zqzt_id:zqzt_id,
                            yx_bj:o,
                            zqzt_zq_zt:zqzt_zq_zt

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
                                submit_search_ff();
                                //$('#tt').datagrid('updateRow',{index:$('#tt').datagrid('getRowIndex',$('#tt').datagrid('getSelected')),row:{yx_bj:o}});
                            }else{
                                alertBottomRightAutoClose('提示',responseText.message);
                            }
                        }
                    });

                }
            });

        }
    } else {
        alertBottomRightAutoClose('提示','请选择一条记录!');
    }
}
//===============================================================================================================
//导出excel
function expExcel() {
    var select = $("#tt").datagrid('getSelected');
    var totleRows = $("#tt").datagrid('getSelections');

    if (select) {
        if(totleRows.length>1){
            alertBottomRightAutoClose('提示','请选择一条记录进行导出Excel操作!');
        }else{
            var zqzt_id = totleRows[0].zqzt_id;
            var zq_zt = totleRows[0].zq_zt;

            $.ajax({
                url: _domain_path+"/mobile/request?tld=zqyj001TMbXtAppZqyjService_ExcelFlag",
                data: {
                    zqzt_id:zqzt_id,
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
                        var excel_url ='/cssnj/exportExcle.action?tld=zqyj001TMbXtAppZqyjService_expExcel';
                        var paramString = "&zqzt_id="+zqzt_id+"&zq_zt="+zq_zt;
                        window.open(excel_url+paramString);

                    }else{
                        alertBottomRightAutoClose('提示',responseText.message);
                    }
                }
            });
        }
    } else {
        alertBottomRightAutoClose('提示','请选择一条记录进行导出Excel操作!');
    }
}

//判断是否是NULL 或者 undefined
function isnull(str){
    if(str==null||str=="undefined"||str==""||str=="null"){
        str="";
    }
    return str;
}