var _url = _domain_path+'/mobile/request?tld=xwtzService';
var _url_select = _url + '_select';
var _url_insertSelective =  _url + '_insertSelective';
var _url_updateByPKeySelective =  _url + '_updateByPKeySelective';
var _url_selectByPKey =  _url + '_selectByPKey';
var _url_deleteByPKey =  _url + '_deleteByPKey';
var _url_xwtzfb = _url + '_xwtzfb';

var _tt = '#tt';
var _append_modify = '#append_modify';
var _append_modify_btn = '#append_modify_btn';
var _append_modify_ff = '#append_modify_ff';
var _search_ff = '#search_ff';

var fields = [
    [
        {title:'标题',field:'title',sortable:'true', width:100},
        {title:'标题内容',field:'title_content',sortable:'true', width:100},
        //{title:'描述',field:'ms',sortable:'true', width:100},
        {title:'url 路径',field:'url',sortable:'true', width:100},
        {title:'文档类型',field:'lxmc',sortable:'true', width:100},
        {title:'文档类型2',field:'lx',sortable:'true', width:100,hidden:'true'},
        {title:'优先等级',field:'lv',sortable:'true', width:100,hidden:'true'},
        {title:'优先等级',field:'lvmc',sortable:'true', width:100},
        /*{title:'录入人',field:'lrr_dm',sortable:'true', width:100},
        {title:'录入机关',field:'lrjg_dm',sortable:'true', width:100},
        {title:'录入时间',field:'lrsj',sortable:'true', width:100},*/
        /*{title:'修改人',field:'xgr_dm',sortable:'true', width:100},
        {title:'修改机关',field:'xgjg_dm',sortable:'true', width:100},
        {title:'修改时间',field:'xgsj',sortable:'true', width:100},*/
        {title:'发布时间',field:'fbsj',sortable:'true', width:100},
        {title:'选用标记',field:'xybjmc',sortable:'true', width:100},
        {title:'选用标记2',field:'xybj',sortable:'true', width:100,hidden:'true'},
        {title:'状态',field:'ztmc',sortable:'true', width:100},
        {title:'状态2',field:'zt',sortable:'true', width:100,hidden:'true'},
        //{title:'具体来源',field:'xxly',sortable:'true', width:100,hidden:'true'},
        {title:'信息来源',field:'lylxmc',sortable:'true', width:100},
        {title:'信息来源2',field:'lylx',sortable:'true', width:100,hidden:'true'},
        {title:'图片路径',field:'title_img',sortable:'true', width:100}
    ]
];

$(function() {
    systemInit();
    init();
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
	$('#xg_button').css("display","none");
	$('#xz_button').css("display","block");
	$(_tt).datagrid('clearSelections');
    initFormCombo();
    $(_append_modify_ff).form('clear');
    $(_append_modify).dialog('open').dialog('setTitle','新增');
   // $(_append_modify_btn).attr("onclick","submit_append_ff()");
}



//修改
function modify() {
	$('#xz_button').css("display","none");
	$("#xg_button").css("display","block");
    initFormCombo();
    var select = $(_tt).datagrid('getSelected');
    var totleRows = $(_tt).datagrid('getSelections');
    var title_content_length  = document.getElementById('f_title_content').value.length;
    //alert(xmjjlen);
    if (select) {
        if(totleRows.length>1){
            alertBottomRightAutoClose('提示','请选择一条记录进行修改!');
        }else{
            $(_append_modify_ff).form('clear');
            $(_append_modify_ff).form('load', select);
            $(_append_modify).dialog('open').dialog('setTitle','修改');
            $("#modify_Btn").hide();
            var url =  totleRows[0].url;
            //var datas = getHtml(url);
            //$("#htmlContent").attr("src",totleRows[0].url);
            if(title_content_length==0){ //防止多次写入
            	getHtml(url);
            //$.get(url,function(data){
         	  // CKEDITOR.instances.fpageNum_title_content.insertHtml(data)
         		//}) ;
            }
            $('#xg_button').attr("onclick","submit_modify_ff()");
        }
    } else {
        alertBottomRightAutoClose('提示','请选择要修改的记录!');
    }
}

//获取html页面
function getHtml(url){
	 $.ajax({
	        url: _domain_path+"/mobile/request?tld=xwtzService_getHtml&url="+url,
	        type: "post",
	        dataType: "json",
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            $.messager.alert(textStatus);
	        },
	        success: function (responseText, textStatus, XMLHttpRequest) {
	        	//console.log(responseText);
	        	CKEDITOR.instances.f_title_content.insertHtml(responseText);
	        }
	    });
}

//删除
function remove(){
    var selected = $(_tt).datagrid('getSelected');
    if (selected) {
        var totleRows = $(_tt).datagrid('getSelections');
        console.log(totleRows);
        $.messager.confirm('删除', '您确认要删除？', function(r) {
            if (r) {
                    var xhs = [];
                    var urls = [];
                    var imgs = [];
                for ( var i = 0; i < totleRows.length; i++) {
                    xhs.push(totleRows[i].xh);
                    urls.push(totleRows[i].url);
                    imgs.push(totleRows[i].title_img);
                    console.log(xhs);
                    console.log(urls);
                    console.log(imgs);
                }
                $.post(_url_deleteByPKey,{'xh':xhs,'url':urls,'title_img':imgs}, function(data){
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
var  text_ms;
function submit_append_ff() {
	alert(1);
	text_ms = editor.getData();
	alert(text_ms);
	text_ms = text_ms.replace(/</g,'221#');
	text_ms = text_ms.replace(/>/g,'222#');
	text_ms = text_ms.replace('/','4u3');
	alert(text_ms);
	$('#f_title_content').val(text_ms);
    if ($(_append_modify_ff).form("validate")) {
        $.post(_url_insertSelective, getFormJSON(_append_modify_ff), function(data,status) {
            //data = JSON.parse( data );
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
/*function submit_modify_ff() {
	alert(0);
    if ($(_append_modify_ff).form("validate")) {
        $.post(_url_updateByPKeySelective, getFormJSON(_append_modify_ff), function(data) {
            data = JSON.parse( data );
            if (data.state == true) {
                alertBottomRightAutoClose('提示',data.message);
                $(_tt).datagrid('clearSelections');
                $(_append_modify).dialog('close');
                $(_tt).datagrid({});
            } else {
            	$(_tt).datagrid('clearSelections');
                alertBottomRightAutoClose('提示','修改失败!');
            }
        }).error(function(jqXHR, textStatus, errorMsg) { alertCenter('提示',"操作失败!"+errorMsg);});
    }else{
    	$(_tt).datagrid('clearSelections');
        alertBottomRightAutoClose('提示','表单验证不通过!');
    }
}*/

//查询提交
function submit_search_ff() {
    $(_tt).datagrid('clearSelections');
    var params = getFormJSON(_search_ff);
    $(_tt).datagrid({ queryParams : params });
}

//新闻通知发布
function xwtz_fb(){
	var selected = $(_tt).datagrid('getSelected');
	console.log(selected);
    if (selected) {
        var totleRows = $(_tt).datagrid('getSelections');
        $.messager.confirm('发布', '您确认要发布吗？', function(r) {
            if (r) {
                    var xhs = selected.xh;
                    var lxs = selected.lx;
                    var titles= selected.title;
                    var urls = selected.url;
                    var title_contents = selected.title_content;
                    var title_imgs = selected.title_img;
                    
                $.post(_url_xwtzfb,{'xh':xhs,'title':titles,'url':urls,'lx':lxs,'title_content':title_contents,'title_img':title_imgs}, function(data){
                    data = JSON.parse( data );
                        if (data.state == true) {
                            alertBottomRightAutoClose('提示',data.message);
                            $(_tt).datagrid('clearSelections');
                            $(_tt).datagrid('reload');
                        } else {
                            alertBottomRightAutoClose('提示',"发布失败！");
                        }
                }).error(function(jqXHR, textStatus, errorMsg) { alertCenter('提示',"发布失败!"+errorMsg);});
            }
        });
    } else {
        alertBottomRightAutoClose('提示','请选择要发布的新闻通知!');
    }
}

//日期
$('#f_lrsj').datebox({
    required: true
});  

//CKeditor获取值
var editor;

function init() {
	editor = CKEDITOR.replace('title_content');
}

//修改提交
function submit_modify_ff() {
	var totleRows = $(_tt).datagrid('getSelections');
	/*alert(1);
	console.log(totleRows);*/
	var url = totleRows[0].url;
	var title_img = totleRows[0].title_img;
	var xh = totleRows[0].xh;
	var newsContent = editor.document.getBody().getText().replace(/\r\n/g,"").replace(/\n/g,"").replace(/\s/g,"");
	//alert(2);
	//alert(newsContent.replace(/\r\n/g,"").replace(/\n/g,"").replace(/\s/g,""));
	/*console.log(newsContent);
	alert(newsContent);*/
	document.myform.action= _domain_path+'/mobile/request_http?tld=xwtzService_updateByPKeySelective&url='+url +'&newsContent=' +newsContent +'&title_img=' +title_img +'&xh='+xh;
    document.myform.submit();     
    $('#xg_button').css("display","none");
    initDatagrid();
}

//js提交   
function saveTp(){
	//alert(1);
	//var cText=CKEDITOR.instances.document.getBody().getText(); 
	 //var CText=CKEDITOR.instances.title_content.document.getBody().getText(); //取得纯文本             
	var newsContent = editor.document.getBody().getText().replace(/\r\n/g,"").replace(/\n/g,"").replace(/\s/g,"");
	//alert(2);
	//alert(newsContent.replace(/\r\n/g,"").replace(/\n/g,"").replace(/\s/g,""));
//return;	
    document.myform.action= _domain_path+'/mobile/request_http?tld=xwtzService_insertSelective&newsContent='+newsContent;
    document.myform.submit();     
    $('#xz_button').css("display","none");
    initDatagrid();
}