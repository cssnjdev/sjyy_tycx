var _url = _domain_path+'/mobile/request?tld=mobile007TMbXtUserService';
var _url_select = _url + '_select';
var _url_selectXb = _url + '_selectXb&nodeId=';
var _url_selectJg = _url + '_selectJg&nodeId=';
var _url_selectGh = _url + '_select&gh=';
var _url_selectPeople = _url + '_selectPeople';
var _url_insertSelective =  _url + '_insertSelective';
var _url_updateByPKeySelective =  _url + '_updateByPKeySelective';
var _url_selectByPKey =  _url + '_selectByPKey&gh=';
var _url_deleteByPKey =  _url + '_deleteByPKey';

var _tt = '#tt';
var _append_modify = '#append_modify';
var _append_modify_btn = '#append_modify_btn';
var _append_modify_ff = '#append_modify_ff';
var _append_modify_ff_info = '#append_modify_ff_info';
var _search_ff = '#search_ff';

var fields = [
    [
        {title:'登录帐号',field:'dl_account',sortable:'true', width:100},
        {title:'登录EMAIL',field:'dl_email',sortable:'true', width:100},
        {title:'登录身份证号',field:'dl_idcard',sortable:'true', width:100},
        {title:'登录手机号',field:'dl_tel',sortable:'true', width:100},
        {title:'录入时间',field:'lr_sj',sortable:'true', width:100},
        {title:'修改时间',field:'xg_sj',sortable:'true', width:100}
    ]
];

$(function() {
    systemInit();
});
function systemInit(){
    initDatagrid();
    initSearchCombo();
    search_jgdm();
}
function initFormCombo(){
}

function initSearchCombo(){
}
//初始化表格
function initDatagrid(){
    $(_tt).datagrid({
        collapsible : false,
        rownumbers : true,
        remoteSort : false,
        nowrap : true,
        striped : true,
        fitColumns:true,
        singleSelect : false,
        fit : true,
        striped : true,
        pagination : true,
        url : _url_select,
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

//表格查询员工名称
function initDatagrid2(gh,email,idcard,tel,xm){
    $(_tt).datagrid({
        collapsible : false,
        rownumbers : true,
        remoteSort : false,
        nowrap : true,
        striped : true,
        fitColumns:true,
        singleSelect : false,
        fit : true,
        striped : true,
        pagination : true,
        url : _url_selectByPKey+gh+"&email="+email+"&idcard="+idcard+"&tel="+tel+"&xm="+xm,
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
//新增
function append() {
	var newPasswd = "nodata";
	$("#xdlmb_head").hide();
	$("#xdlmb_body").hide();
	$("#dlmb_head").show();
	$("#dlmb_body").show();
    initFormCombo();
    $(_append_modify_ff).form('clear');
    $(_append_modify).dialog('open').dialog('setTitle','新增');
    $(_append_modify_btn).attr("onclick","submit_append_ff()");
    $("#f_newdl_password").textbox("setValue",newPasswd);
}
//修改
function modify() {
    initFormCombo();
	$("#dlmb_head").hide();
	$("#dlmb_body").hide();
	$("#xdlmb_head").show();
	$("#xdlmb_body").show();
    var select = $(_tt).datagrid('getSelected');
    var totleRows = $(_tt).datagrid('getSelections');
    if (select) {
        if(totleRows.length>1){
            alertBottomRightAutoClose('提示','请选择一条记录进行修改!');
        }else{
            console.log(totleRows[0].yh_dm);
            var yh_dm = totleRows[0].yh_dm;
            console.log(totleRows);
        	$(_append_modify_ff).form('clear');
        	getUserInfo(yh_dm);
            $(_append_modify_ff).form('load', select);
            $(_append_modify).dialog('open').dialog('setTitle','修改');
            $(_append_modify_btn).attr("onclick","submit_modify_ff()");
        }
    } else {
        alertBottomRightAutoClose('提示','请选择要修改的记录!');
    }
}

//获取user_info表数据
function getUserInfo(yh_dm){
	 $.ajax({
	        url: _domain_path+"/mobile/request?tld=mobile007TMbXtUserService_getUserInfo&yh_dm="+yh_dm,
	        type: "post",
	        dataType: "json",
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            $.messager.alert(textStatus);
	        },
	        success: function (responseText, textStatus, XMLHttpRequest) {
	        	var text =  JSON.parse(responseText);
	        	$('#f_xm').textbox("setValue",text[0].xm);
	        	$('#f_xb').combobox("setValue",text[0].xb);
	        	$('#f_lxdh').textbox("setValue",text[0].lxdh);
	        	$('#f_bz').textbox("setValue",text[0].bz);
	        	$('#f_jg_mc').textbox("setValue",text[0].jg_mc);
	        	$('#f_email').textbox("setValue",text[0].email);
	        	$('#f_bgdh').textbox("setValue",text[0].bgdh);
	        	$('#f_dh').textbox("setValue",text[0].dh);
	        	var jg_dm=text[0].jg_dm;
	        	//console.log(jg_dm);
	        	$('#f_jg_dm').combotree("setValue",jg_dm);
	        }
	    });
}


//删除
function remove(){
    var selected = $(_tt).datagrid('getSelected');
    if (selected) {
        var totleRows = $(_tt).datagrid('getSelections');
        $.messager.confirm('删除', '您确认要删除？', function(r) {
            if (r) {
                    var yh_dms = [];
                for ( var i = 0; i < totleRows.length; i++) {
                    yh_dms.push(totleRows[i].yh_dm);
                }
                $.post(_url_deleteByPKey,{'yh_dm':yh_dms}, function(data){
                    data = JSON.parse( data );
                        if (data.state == true) {
                            alertBottomRightAutoClose('提示',data.message);
                            $(_tt).datagrid('clearSelections');
                            $(_tt).datagrid('reload');
                        } else {
                            alertBottomRightAutoClose('提示',"删除失败！");
                        }
                }).error(function(jqXHR, textStatus, errorMsg) { alertCenter('提示',"操作失败!"+errorMsg);});
            }
        });
    } else {
        alertBottomRightAutoClose('提示','请选择要删除的记录!');
    }
}

//新增提交
function submit_append_ff() {
    if ($(_append_modify_ff).form("validate")) {
        $.post(_url_insertSelective, getFormJSON(_append_modify_ff), function(data,status) {
        	data = JSON.parse( data );
            if (data.state == true) {
                alertBottomRightAutoClose('提示',data.message);
                $(_append_modify).dialog('close');
            	$("#xdlmb_head").show();
            	$("#xdlmb_head").show();
                $(_tt).datagrid({});
            } else {
                alertBottomRightAutoClose('提示','增加失败!');
            }
        }).error(function(jqXHR, textStatus, errorMsg) { alertCenter('提示',"操作失败!"+errorMsg);});
    }else{
        alertBottomRightAutoClose('提示','表单验证不通过!');
    }
}
//修改提交
function submit_modify_ff() {
    if ($(_append_modify_ff).form("validate")) {
        $.post(_url_updateByPKeySelective, getFormJSON(_append_modify_ff), function(data) {
            data = JSON.parse( data );
            if (data.state == true) {
                alertBottomRightAutoClose('提示',data.message);
                $(_append_modify).dialog('close');
            	$("#dlmb_head").show();
            	$("#dlmb_body").show();
                $(_tt).datagrid({});
            } else {
                alertBottomRightAutoClose('提示','修改失败!');
            }
        }).error(function(jqXHR, textStatus, errorMsg) { alertCenter('提示',"操作失败!"+errorMsg);});
    }else{
        alertBottomRightAutoClose('提示','表单验证不通过!');
    }
}
//查询提交
function submit_search_ff() {
	var  gh = document.getElementById("f_dl_account_select").value;
	var  email = document.getElementById("f_dl_email_select").value;
	var  idcard = document.getElementById("f_dl_idcard_select").value;
	var  tel = document.getElementById("f_dl_tel_select").value;
	var  xm = document.getElementById("f_dl_xm_select").value;
	//alert(xm);
 	initDatagrid2(gh,email,idcard,tel,xm);
}

//查询新增机关代码
function search_jgdm(){
	$.ajax({
        url: _domain_path+"/mobile/request?tld=mobile007TMbXtUserService_search_jgdm&nodeId=0",
        type: "post",
        dataType: "json",
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.messager.alert(textStatus);
        },
        success: function (responseText, textStatus, XMLHttpRequest) {
        	$("#f_jg_dm").combotree('loadData',responseText);
        }
    });	 
}
