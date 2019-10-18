var _url = _cssnj_domain_path+'/tykf/request?tld=tycx003CxTxpzxxService';
var _url_select = _url + '_select';
var _url_insertSelective =  _url + '_insertSelective';
var _url_updateByPKeySelective =  _url + '_updateByPKeySelective';
var _url_selectByPKey =  _url + '_selectByPKey';
var _url_deleteByPKey =  _url + '_deleteByPKey';

var _tt = '#tt';
var _append_modify = '#append_modify';
var _append_modify_btn = '#append_modify_btn';
var _append_modify_ff = '#append_modify_ff';
var _search_ff = '#search_ff';

var fields = [
    [
        {title:'字体大小',field:'fontsize',sortable:'true', width:100},
        {title:'横坐标',field:'hzb',sortable:'true', width:100},
        {title:'横坐标名称',field:'hzbmc',sortable:'true', width:100},
        {title:'数据源类型',field:'sjylx',sortable:'true', width:100},
        {title:'SQL，可以自定义SQL，如果不定义，默',field:'sql',sortable:'true', width:100},
        {title:'SQLXH',field:'sqlxh',sortable:'true', width:100},
        {title:'标题',field:'title',sortable:'true', width:100},
        {title:'图形类型,txlx=barAndLine',field:'txlx',sortable:'true', width:100},
        {title:'UUID',field:'uuid',sortable:'true', width:100},
        {title:'纵坐标',field:'zzb',sortable:'true', width:100},
        {title:'纵坐标单位',field:'zzbdw',sortable:'true', width:100},
        {title:'纵坐标名称',field:'zzbmc',sortable:'true', width:100}
    ]
];

$(function() {
    systemInit();
});
function systemInit(){
    initDatagrid();
    initSearchCombo();
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
//加载成功执行方法
function dataLoadSuccess(){};
//新增
function append() {
    initFormCombo();
    $(_append_modify_ff).form('clear');
    $(_append_modify).dialog('open').dialog('setTitle','新增');
    $(_append_modify_btn).attr("onclick","submit_append_ff()");
}
//修改
function modify() {
    initFormCombo();
    var select = $(_tt).datagrid('getSelected');
    var totleRows = $(_tt).datagrid('getSelections');
    if (select) {
        if(totleRows.length>1){
            alertBottomRightAutoClose('提示','请选择一条记录进行修改!');
        }else{
            $(_append_modify_ff).form('clear');
            $(_append_modify_ff).form('load', select);
            $(_append_modify).dialog('open').dialog('setTitle','修改');
            $(_append_modify_btn).attr("onclick","submit_modify_ff()");
            //$(_modify_ff).form('load', _url_selectByPKey + rowId);
        }
    } else {
        alertBottomRightAutoClose('提示','请选择要修改的记录!');
    }
}
//删除
function remove(){
    var selected = $(_tt).datagrid('getSelected');
    if (selected) {
        var totleRows = $(_tt).datagrid('getSelections');
        $.messager.confirm('删除', '您确认要删除？', function(r) {
            if (r) {
                    var uuids = [];
                for ( var i = 0; i < totleRows.length; i++) {
                    uuids.push(totleRows[i].uuid);
                }
                $.post(_url_deleteByPKey,{'uuid':uuids}, function(data){
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
    $(_tt).datagrid('clearSelections');
    var params = getFormJSON(_search_ff);
    $(_tt).datagrid({ queryParams : params });
}


