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
var sel_url = _domain_path+'/mobile/request?tld=tpgl001TMbXtAppTpglService_select';
var fields = [
    [
        {title:'uuid',field:'uuid',hidden : true,sortable:'true', width:'10%'},
        {title:'tp_bj',field:'tp_bj',hidden : true,sortable:'true', width:'10%'},
        {title:'投票主题',field:'zt',align : "left",sortable:'true', width:'26%',
            formatter : function(value) {
                return "<span title='" + value + "'>" + value
                    + "</span>";
            }},
        {title:'创建时间',field:'lr_sj',align : "center",sortable:'true', width:'8%'},
        {title:'开始日期',field:'ks_rq',align : "center",sortable:'true', width:'8%'},
        {title:'截止日期',field:'zz_rq',align : "center",sortable:'true', width:'8%'},
        {title:'发布日期',field:'fb_rq',align : "center",sortable:'true', width:'8%'},
        {title:'创建人',field:'lr_ry',align : "center",sortable:'true', width:'6%'},
        {title:'投票状态',field:'qy_bj',align : "center",sortable:'true', width:'6%',
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
            }},
        {title:'投票类型',field:'dx_bj',align : "center",sortable:'true', width:'6%',
            formatter : function(value, row, index) {
                var a = '';
                if(value == "1"){
                    a="多选";
                }else{
                    a="单选";
                }
                return a;
            }},
        {title:'操作',field:'xgrq',align : "center",sortable:'true', width:'6%',
            formatter : function(value, row, index) {
                // var a = '<a href="javascript:void(0)" style="color: #0c80d7;text-decoration:underline" data-id="'
                //     + row.uuid + '"  data-zt="'
                //     + row.qy_bj + '" onclick="edittp(this)">编辑</a>';
                var a = '<a href="javascript:void(0)" style="color: #0c80d7;text-decoration:underline" data-id="'
                     + row.uuid + '"  data-qybj="'
                     + row.qy_bj + '" data-zt="'
                     + row.zt + '" onclick="getEcharts(this)">生成柱状图</a>';
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
function appendtp() {
    xz_bj = "xz";
    flag_bc = "0";
    $(_append_modify).dialog('open').dialog('setTitle','新增');
    $("#append_iframe").attr("src","/comm/html/biz.manage.tpgl.tpgl_add?xz_bj="+xz_bj+"&flag_bc="+flag_bc+"&v="+Math.random());
}
//编辑
function edittp(obj) {
    xz_bj = "bj";
    flag_bc = "0";
    var tp_id = obj.getAttribute("data-id");
    var tp_zt = obj.getAttribute("data-zt");
    $(_append_modify).dialog('open').dialog('setTitle','编辑');
    $("#append_iframe").attr("src","/comm/html/biz.manage.tpgl.tpgl_add?xz_bj="+xz_bj+"&flag_bc="+flag_bc+"&tp_id="+tp_id+"&v="+Math.random());
}
//最上勾选编辑
function cedittp() {
    xz_bj = "bj";
    flag_bc = "0";
    var select = $("#tt").datagrid('getSelected');
    var totleRows = $("#tt").datagrid('getSelections');

    if (select) {
        if(totleRows.length>1){
            alertBottomRightAutoClose('提示','请选择一条记录进行修改!');
        }else{
            var tp_id = totleRows[0].uuid;
            $(_append_modify).dialog('open').dialog('setTitle','编辑');
            $("#append_iframe").attr("src","/comm/html/biz.manage.tpgl.tpgl_add?xz_bj="+xz_bj+"&flag_bc="+flag_bc+"&tp_id="+tp_id);
        }
    } else {
        alertBottomRightAutoClose('提示','请选择要修改的记录!');
    }
}
//启用或者作废 o: 0作廢 1啓用  2 发布
function doQy(o) {
    var select = $("#tt").datagrid('getSelected');
    var totleRows = $("#tt").datagrid('getSelections');

    if (select) {
        if(totleRows.length>1){
            alertBottomRightAutoClose('提示','请选择一条记录!');
        }else{
            var qy_message = "";
            var tp_id = totleRows[0].uuid;
            var tp_qybj = totleRows[0].qy_bj;
            var tp_title = totleRows[0].zt;
            var tp_bj = totleRows[0].tp_bj;

            if(tp_qybj == "0" && o == "2"){
                alertBottomRightAutoClose('提示','该投票已经作废，无法再次发布！');
                return;
            }
            if(o == "2"){
                if(o == tp_qybj){
                    alertBottomRightAutoClose('提示','该投票已经启用，无需再次发布！');
                    return;
                }
                qy_message = "请确定是否将此投票信息发布！"
            }else{
                if(o == tp_qybj){
                    alertBottomRightAutoClose('提示','该投票已经作废，无需再次作废！');
                    return;
                }

                qy_message = "请确定是否将此投票信息作废！"
            }
            $.messager.confirm('提示', qy_message, function(r) {
                if (r) {

                    $.ajax({
                        url: _domain_path+"/mobile/request?tld=tpgl001TMbXtAppTpglService_qytp",
                        data: {
                            tp_id:tp_id,
                            qy_bj:o,
                            tp_title:tp_title,
                            tp_bj:tp_bj
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
                                submit_search_ff("0","0");
                                //$('#tt').datagrid('updateRow',{index:$('#tt').datagrid('getRowIndex',$('#tt').datagrid('getSelected')),row:{qy_bj:o}})
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

//echarts
function getEcharts(obj) {
    var tp_id = obj.getAttribute("data-id");
    var tp_zt = obj.getAttribute("data-zt");
    $(_append_modify).dialog('open').dialog('setTitle','柱状图 -- '+tp_zt);
    $("#append_iframe").attr("src","/comm/html/biz.manage.tpgl.tpgl_echarts?tp_id="+tp_id+"&tp_zt="+tp_zt+"&v="+Math.random());
}

//判断是否是NULL 或者 undefined
function isnull(str){
    if(str==null||str=="undefined"||str==""||str=="null"){
        str="";
    }
    return str;
}