var _url = _cssnj_domain_path+'/tykf/request?tld=tycx002CxCxdyService';
var _url_select = _url + '_select';
var _url_insert =  _url + '_insertSelective';
var _url_updateByPKeySelective =  _url + '_updateByPKeySelective';
var _url_selectByPKey =  _url + '_selectByPKey';
var _url_deleteByPKey =  _url + '_deleteByPKey';

var _tt = '#tt';
var _tt_pager = '#tt_pager';
var _append_modify = '#append_modify';
var _append_modify_btn = '#append_modify_btn';
var _append_modify_title = '#append_modify_title';
var _append_modify_ff = '#append_modify_ff';
var _search_ff = '#search_ff';

var colNames = ['CCGCMC','CJJG_DM','CXFL','CXLX','CXMS','EXPORTMAX','FYBJ','GXFS','HEADSQL','HJXSBZ','JGSJ','LIMTTIME','LRRQ','LRR_DM','MBXH','MYJLS','PAGELAYOUT','QUERYPLUGIN','SJGSDQ','SJYLX','SM','SQLLX','SQLMC','SQLSTR','SQLXH','SSJG_DM','图形展示（可多选）','XGRQ','XGR_DM','XYBZ','YSSJSJ','YWY'];

var colModel =

    [
        {name:'ccgcmc',index:'ccgcmc', width:100, sorttype:"int" },
        {name:'cjjg_dm',index:'cjjg_dm', width:100, sorttype:"int" },
        {name:'cxfl',index:'cxfl', width:100, sorttype:"int" },
        {name:'cxlx',index:'cxlx', width:100, sorttype:"int" },
        {name:'cxms',index:'cxms', width:100, sorttype:"int" },
        {name:'exportmax',index:'exportmax', width:100, sorttype:"int" },
        {name:'fybj',index:'fybj', width:100, sorttype:"int" },
        {name:'gxfs',index:'gxfs', width:100, sorttype:"int" },
        {name:'headsql',index:'headsql', width:100, sorttype:"int" },
        {name:'hjxsbz',index:'hjxsbz', width:100, sorttype:"int" },
        {name:'jgsj',index:'jgsj', width:100, sorttype:"int" },
        {name:'limttime',index:'limttime', width:100, sorttype:"int" },
        {name:'lrrq',index:'lrrq', width:100, sorttype:"int" },
        {name:'lrr_dm',index:'lrr_dm', width:100, sorttype:"int" },
        {name:'mbxh',index:'mbxh', width:100, sorttype:"int" },
        {name:'myjls',index:'myjls', width:100, sorttype:"int" },
        {name:'pagelayout',index:'pagelayout', width:100, sorttype:"int" },
        {name:'queryplugin',index:'queryplugin', width:100, sorttype:"int" },
        {name:'sjgsdq',index:'sjgsdq', width:100, sorttype:"int" },
        {name:'sjylx',index:'sjylx', width:100, sorttype:"int" },
        {name:'sm',index:'sm', width:100, sorttype:"int" },
        {name:'sqllx',index:'sqllx', width:100, sorttype:"int" },
        {name:'sqlmc',index:'sqlmc', width:100, sorttype:"int" },
        {name:'sqlstr',index:'sqlstr', width:100, sorttype:"int" },
        {name:'sqlxh',index:'sqlxh', width:100, sorttype:"int" },
        {name:'ssjg_dm',index:'ssjg_dm', width:100, sorttype:"int" },
        {name:'txzs',index:'txzs', width:100, sorttype:"int" },
        {name:'xgrq',index:'xgrq', width:100, sorttype:"int" },
        {name:'xgr_dm',index:'xgr_dm', width:100, sorttype:"int" },
        {name:'xybz',index:'xybz', width:100, sorttype:"int" },
        {name:'yssjsj',index:'yssjsj', width:100, sorttype:"int" },
        {name:'ywy',index:'ywy', width:100, sorttype:"int" }
    ]

;

$(function() {
    systemInit();
});

function systemInit(){
    initDatagrid();
    initValidatorForm();
    initSearchCombo();
    initSearchComboChange();
    initFormComboChange();
    initDatetimepicker();
}
//初始化日期控件
function initDatetimepicker(){
}
//初始化表单校验
function initValidatorForm(){
    $(_append_modify_ff).bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            lrrq: {
                message: 'LRRQ验证失败',
                validators: {
                    notEmpty: {
                        message: 'LRRQ不能为空'
                    }
                }
            },
            lrr_dm: {
                message: 'LRR_DM验证失败',
                validators: {
                    notEmpty: {
                        message: 'LRR_DM不能为空'
                    }
                }
            },
            sjgsdq: {
                message: 'SJGSDQ验证失败',
                validators: {
                    notEmpty: {
                        message: 'SJGSDQ不能为空'
                    }
                }
            }
        }
    });
}
//初始化新增修改表单下拉框
function initFormCombo(){
}
//初始化新增修改表单下拉框事件
function initFormComboChange(){
}
//初始化搜索表单下拉框
function initSearchCombo(){
}
//初始化搜索表单下拉框事件
function initSearchComboChange(){
}

//初始化表格
function initDatagrid(){

    var parent_column = $(_tt).closest('[class*="col-"]');
    $(window).on('resize.jqGrid', function () {
        $(_tt).jqGrid( 'setGridWidth', parent_column.width() );
    })

    jQuery(_tt).jqGrid({
        url:_url_select,
        datatype: "json",
        height: 500,
        colNames:colNames,
        colModel:colModel,
        pagerpos: 'left',
        viewrecords : true,
        rowNum:10,
        rowList:[10,20,30],
        pager : _tt_pager,
        altRows: true,
        multiselect: true,
        multiboxonly: true,
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
            updatePagerIcons(table);
            enableTooltips(table);
        }, 0);
        }
    });

    $(window).triggerHandler('resize.jqGrid');

    function pickDate( cellvalue, options, cell ) {
        setTimeout(function(){
            $(cell) .find('input[type=text]').datepicker({format:'yyyy-mm-dd' , autoclose:true});
        }, 0);
    }


    function updatePagerIcons(table) {
        var replacement =
            {
                'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
                'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
                'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
                'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
            };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
            if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
        })
    }

    function enableTooltips(table) {
        $('.navtable .ui-pg-button').tooltip({container:'body'});
        $(table).find('.ui-pg-div').tooltip({container:'body'});
    }

    $(document).one('ajaxloadstart.page', function(e) {
        $.jgrid.gridDestroy(_tt);
        $('.ui-jqdialog').remove();
    });

}

//新增
function append() {
    $("input[name=freset]").trigger("click");
    initFormCombo();
    $(_append_modify_title).html("新增");
    $(_append_modify_btn).attr("onclick","submit_append_ff()");
    $(_append_modify).modal("show");
}
//修改
function modify() {
    $("input[name=freset]").trigger("click");
    initFormCombo();
    var rowIds=$(_tt).jqGrid("getGridParam","selarrrow");
    if (rowIds.length!=0) {
        if(rowIds.length>1){
            bootboxAlert('提示','请选择一条记录进行修改!');
        }else{
            var rowId=$(_tt).jqGrid('getGridParam','selrow');
            var rowData = $(_tt).jqGrid('getRowData',rowId);
            $(_append_modify_ff).loadFormData(rowData);
            $(_append_modify_title).html("修改");
            $(_append_modify_btn).attr("onclick","submit_modify_ff()");
            $(_append_modify).modal("show");
        }
    } else {
        bootboxAlert('提示','请选择要修改的记录!');
    }
}
//删除
function remove(){
    var rowIds=$(_tt).jqGrid("getGridParam","selarrrow");
    if (rowIds.length>0) {
        bootbox.confirm({
            title: "提示：",
            message: "您确认要删除？",
            buttons: {
                cancel: {label: '<i class="fa fa-times"></i> 取消'},
                confirm: {label: '<i class="fa fa-check"></i> 确认'}
            },
            callback: function (result) {
                if(result) {
                    var sqlxhs = [];
                    var rowData = "";
                    for ( var i = 0; i < rowIds.length; i++) {
                        rowData = $(_tt).jqGrid('getRowData',rowIds[i]);
                        sqlxhs.push(rowData.sqlxh);
                    }
                    $.post(_url_deleteByPKey,{'sqlxh':sqlxhs}, function(data){
                        data = JSON.parse( data );
                        if (data.state == true) {
                            bootboxAlert('提示',data.message);
                            $(_tt).jqGrid("setGridParam",{}).trigger('reloadGrid');
                        } else {
                            bootboxAlert('提示',"删除失败！");
                        }
                    }).error(function(jqXHR, textStatus, errorMsg) { bootboxAlert('提示',"操作失败!"+errorMsg);});
                }
            }
        });
    } else {
        bootboxAlert('提示','请选择要删除的记录!');
    }
}

//新增提交
function submit_append_ff() {
    $(_append_modify_ff).data("bootstrapValidator").validate();
    var flag = $(_append_modify_ff).data("bootstrapValidator").isValid();
    if (flag) {
        $.post(_url_insert, getFormJSON(_append_modify_ff), function(data,status) {
            data = JSON.parse( data );
            if (data.state == true) {
                $(_append_modify_ff).data('bootstrapValidator').resetForm();
                $(_append_modify).modal("hide");
                $(_tt).jqGrid("setGridParam",{}).trigger('reloadGrid');
                bootboxAlert('提示',data.message);
            } else {
                bootboxAlert('提示','增加失败!');
            }
        }).error(function(jqXHR, textStatus, errorMsg) { bootboxAlert('提示',"操作失败!"+errorMsg);});
    }else{
        bootboxAlert('提示','表单验证不通过!');
    }
}
//修改提交
function submit_modify_ff() {
    $(_append_modify_ff).data("bootstrapValidator").validate();
    var flag = $(_append_modify_ff).data("bootstrapValidator").isValid();
    if (flag) {
        $.post(_url_updateByPKeySelective, getFormJSON(_append_modify_ff), function(data) {
            data = JSON.parse( data );
            if (data.state == true) {
                $(_append_modify_ff).data('bootstrapValidator').resetForm();
                $(_append_modify).modal("hide");
                $(_tt).jqGrid("setGridParam",{}).trigger('reloadGrid');
                bootboxAlert('提示',data.message);
            } else {
                bootboxAlert('提示','修改失败!');
            }
        }).error(function(jqXHR, textStatus, errorMsg) { bootboxAlert('提示',"操作失败!"+errorMsg);});
    }else{
        bootboxAlert('提示','表单验证不通过!');
    }
}
//查询提交
function submit_search_ff() {
    var params = getFormJSON(_search_ff);
    $(_tt).jqGrid("setGridParam",{postData:params}).trigger('reloadGrid');
}


