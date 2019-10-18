var d = null;
var cxdy = null;
var cxtj = null;
var sqlxh=null;
var table=null;
var swryDm=null;
var cxtjData=null;//查询条件，用于后续推送使用
var djxh=null;//djxh，主要用于一户式查询

var winlayout = null;

$(function(){
	 
	sqlxh=$("#sqlxh").val();
	djxh=$("#djxh").val();	
	var extraParams=$("#extraParams").val();
	 
	//获取查询定义
	$.ajax({
		url:ctx+"tykf/request?tld=Tycx002DzcxService_selectCxdyAndCxtj",
		async : false,
		data : {
			"sqlxh":sqlxh
		},
		dataType : 'json',
		success : function(data, textStatus, jqXHR) {
			cxdy = data.cxdy[0];
			cxtj = data.cxtj;
			swryDm=data.swryDm;
			cxtjData=data;
		},
		error : function(xhr, textStatus) {

		}
	});
	
     if(djxh!=null&&djxh!=""){
		 cxtj.djxh=djxh;
	 }
     
	//初始化查询条件
	loadCxtj(cxtj,swryDm,cxdy,extraParams);
	//$('select').comboSelect();
	
	//加载统计的查询条件
	if(cxdy.cxlx==2||cxdy.cxlx==3){
		
		loadTjCxtj(cxdy.sqlxh,cxdy.cxlx);
		
		$('.multiselect').multiselect({
			nonSelectedText:'--请选择--',
			buttonWidth:'200px',
			includeSelectAllOption:true,
			dropRight:true,			
			numberDisplayed:30,
			maxHeight:200,
			selectedList:30,			
			selectAll:true,
			selectAllText:'全选'
		});
		
		//$('.multiselect').multiselect("checkAll");
		
	}
	 
	var queryParams = getCxtjParams(cxtj);
	 if(cxdy.cxlx==4){ // 报表查询 
		 goBbcx(queryParams);
		 return true;
	 }else if(cxdy.cxlx==2||cxdy.cxlx==3){
		 addParamsTjcxtj(queryParams);
	 }
	  
	 if(cxdy.cxlx==3){
		table= initJcbTable("#tb_departments",sqlxh,queryParams,cxdy.cxlx);
	 }else{
	    table= initTable("#tb_departments",sqlxh,queryParams,cxdy.cxlx,cxdy.hjxsbz);
	 }
	 
	 setTimeout(function(){
		 resetLayout();
	 },500)
	 
});

window.onresize=function(){
	 resetLayout()
}
 

function resetLayout(){
	 
	 
	var wrapper = $("#tb_departments>.dataTables_wrapper").eq(0);
	
	$(wrapper).find(".dataTables_scrollBody").css("height", "auto");
	 
	$("#box").scrollTop(1);
	
	if($("#box").scrollTop() > 0) {// 父节点有滚动条
 
		var h1 =  $(window).height()-$("#top").height()-$("#cxtj_panel").height()-$("#tool_head").height() - 45;
		
		$(wrapper).find(".dataTables_scrollBody").height(h1);
 		
		/*
		h2 = $(wrapper).find(".dataTables_scrollBody table.dataTable").height();
		
		if (h2 < minHeight) {
			$(wrapper).find(".dataTables_scrollBody").css("height",minHeight);
		}
         */
	}
   
	$(wrapper).width($("body").width()-1);
			
	return false;
	
}


function addParamsTjcxtj(queryParams){
	
	    var fzxm_obj = {
			"name" :"fzxm",
			"type" :$("#tj_fzxm").attr("data-type"),
			"value":$("#tj_fzxm").val()!=null? ($("#tj_fzxm").val()).toString():""
		};
	  
		var zbxm_obj = {
			"name" :"zbxm",
			"type" :$("#tj_zbxm").attr("data-type"),
			"value":$("#tj_zbxm").val()!=null? ($("#tj_zbxm").val()).toString():""
		};
		
		queryParams.push(fzxm_obj);
		queryParams.push(zbxm_obj);
		
		return queryParams;
		
}

$('#apkBtn').bind('click',//自定义搜索框在按下回车键时搜索数据
function(e) {
	var pos = $("#apkInput").val();
	table.search(pos).draw();//搜索
});
function showCellValue(obj) {

	var value = $(obj).text();
	$("#tootip span").html(value);
	$('#tootip').css("top", event.clientY);
	$('#tootip').css("left", event.clientX);
	$("#tootip").show();

}

//查询按钮
function queryMx() {

	//_validateQueryParams() 先校验查询条件
	if(!validateQueryParams(cxtj)){
		alert("查询条件校验不通过");
		return;
	}
	var queryParams = getCxtjParams(cxtj);
	if(cxdy.cxlx==2||cxdy.cxlx==3){
		addParamsTjcxtj(queryParams);
     }
	//$("#tb_departments").dataTable().fnDestroy();
	$("#tb_departments").empty();
	if(cxdy.cxlx==3){
		table= initJcbTable("#tb_departments",sqlxh,queryParams,cxdy.cxlx);
	 }else{
	    table= initTable("#tb_departments",sqlxh,queryParams,cxdy.cxlx,cxdy.hjxsbz);
	 }
}
//报表查询
function goBbcx(queryParams){ 
	
	  var cxtjParams =queryParams;
		
	  var queryParamsStr = "" ;
	
	  for(var i=0;i<cxtjParams.length;i++){
		  
		queryParamsStr = queryParamsStr+"&"+cxtjParams[i].name+"="+cxtjParams[i].value;
		  
	  }
	  var bbmc=cxdy.sqlstr;
	 
		  $("#dataShow").html("<iframe  id='bbcxFrame' name='bbcxFrame' src='/reportJsp/" + "showReport.jsp?rpx="+  cxdy.sqlstr + "&queryParams="+queryParamsStr+ "&sjylx=" + cxdy.sjylx+"' width='100%' height='100%' frameborder='0' style='margin:0;padding:0;'></iframe>");
	  

}
//结果列设置
function SetResultColumns(){
	$.ajax({
		url:ctx+"tykf/request?tld=Tycx002DzcxService_setResultColumns",
		async : false,
		data : {
			"sqlxh" : sqlxh
		},
		dataType : 'json',
		success : function(data, textStatus, jqXHR) {
			$("#jgl").empty();
			for(var i=0;i<data.length;i++){
			 var cxjgl=data[i];
			 var tables='';
			  if(i%5==0){			  
			   $("#jgl").append('<tr>');
			   tr_len=0;
			  }
			   tables = tables+'<td style="width:150px"><input type="checkbox"  name="'+cxjgl.lmc+'" value="'+cxjgl.lmc+'" class="toggle-vis" data-column='+i+'>'+cxjgl.lms +'</td>';
			    if(tr_len==4){
			     $("#jgl").append('</tr>');
			    }
	           $("#jgl").append(tables) ;
	           tr_len++;
			}
		},
		error : function(xhr, textStatus) {

		}
	});
	 $("#cxjglForm").modal({backdrop:'static'});
}
function queryCxjgl(){
	  $.each($("input:checkbox"),function(){
		  var ischecked=$(this).context.checked;
		  var column=table.column($(this).attr('data-column')); 
	     column.visible(ischecked);
	  });
	 }
function allCheck(){
		  $("input[type=checkbox]").prop("checked","true");
}
function qxAllCheck(){
	 $.each($("input:checkbox"),function(){
		  $(this).removeAttr("checked");
	  });
}
function viewTjModal(){
	$("#fzlleft option[value!='1']").remove();
	$("#fzlright option[value!='1']").remove();
	$("#tjlleft option[value!='1']").remove();
	$("#tjlright option[value!='1']").remove();
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=Tycx002DzcxService_getCxjgl",//路径
		data:{
			'sqlxh':sqlxh
		},
		success : function(result) {//返回数据根据结果进行相应的处理
			for(var i=0;i<result.length;i++){
				var jgl=result[i];
				var tjlx=jgl.tjlx;
				if(tjlx=="1"){
					$("#fzlleft").append("<option value='"+jgl.lmc+"' >"+jgl.lms+"</option>");
				}else if(tjlx=="2"){
					$("#tjlleft").append("<option value='"+jgl.lmc+"'>"+jgl.lms+"(合计)</option>");
				}else if(tjlx=="3"){
					$("#tjlleft").append("<option value='"+jgl.lmc+"'>"+jgl.lms+"(平均)</option>");
				}
			}
		}  
	});
	$("#tjModal").modal({backdrop:'static'});
}
/** 
 * 移动select的部分内容,必须存在value，此函数以value为标准进行移动     
 * oSourceSel: 源列表框对象  
 * oTargetSel: 目的列表框对象 
 */  
function moveSelected(oSourceSel, oTargetSel) {  
  //建立存储value和text的缓存数组  
  var arrSelValue = new Array();  
  var arrSelText = new Array();  
  //此数组存贮选中的options，以value来对应  
  var arrValueTextRelation = new Array();  
  var index = 0;//用来辅助建立缓存数组  
    
  //存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系  
  for ( var i = 0; i < oSourceSel.options.length; i++) {  
      if (oSourceSel.options[i].selected) {  
          arrSelValue[index] = oSourceSel.options[i].value;  
          arrSelText[index] = oSourceSel.options[i].text;  
          //建立value和选中option的对应关系  
          arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i];  
          index++;  
      }  
  }  

  //增加缓存的数据到目的列表框中，并删除源列表框中的对应项  
  for ( var i = 0; i < arrSelText.length; i++) {
      var oOption = document.createElement("option");  
      oOption.text = arrSelText[i];  
      oOption.value = arrSelValue[i];  
      oTargetSel.add(oOption);  
      //删除源列表框中的对应项  
      oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);  
  }  
}

/** 
 * 移动select的全部内容 
 */  
function moveAll(oSourceSel, oTargetSel) {  
  //建立存储value和text的缓存数组  
  var arrSelValue = new Array();  
  var arrSelText = new Array();  
  //此数组存贮选中的options，以value来对应  
  var arrValueTextRelation = new Array();  
  //存储所有源列表框数据到缓存数组  
  var index = 0;//用来辅助建立缓存数组  
  for ( var i = 0; i < oSourceSel.options.length; i++) {  
	  if(oSourceSel.options[i].disabled==false){
      arrSelValue[index] = oSourceSel.options[i].value;  
      arrSelText[index] = oSourceSel.options[i].text; 
      //建立value和选中option的对应关系  
      arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i];  
      index++;
	  }
  }  

  //将缓存数组的数据增加到目的select中  
  for ( var i = 0; i < arrSelText.length; i++) {  
      var oOption = document.createElement("option");  
      oOption.text = arrSelText[i];  
      oOption.value = arrSelValue[i];  
      oTargetSel.add(oOption); 
      //oTargetSel.appendChild(oOption);
     // $("#"+oTargetSel).append("<option value='"+oOption.value+"'>"+oOption.text+"</option>");
      //删除源列表框中的对应项  
      oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);  
  }  

  //清空源列表框数据，完成移动  
  //oSourceSel.innerHTML = "";  
}  
/**
*执行统计
**/
var tjtables=null;
function executeStatistic(){
	var params=getTjcxtj();
	var queryParams = getCxtjParams(cxtj);
	
	if(tjtables!=null){
		$("#tb_tjResult").dataTable().fnDestroy();
	}
	 tjtables= initTjTable("#tb_tjResult",sqlxh,queryParams,params,'4');
	$("#tjResultModal").modal("show");
}
//得到统计的条件
function getTjcxtj(){
	//获取分组列和统计列
	var fzl=document.getElementById("fzlright").options;
	var tjl=document.getElementById("tjlright").options;
	
	var params = [];
	if(fzl.length==1){
		$.cssnj.alert("请选择分组列!");		
	}
	if(tjl.length==1){
		$.cssnj.alert("请选择统计列!");
	}
	for(var i=1;i<fzl.length;i++){
		var nfzl=fzl[i];
		var data={
		'name':nfzl.value,
		'type':'group',
		'text':nfzl.text
		};
		params.push(data);
	}
	for(var i=1;i<tjl.length;i++){
		var ntjl=tjl[i];
		var type='sum';
		if(ntjl.text.indexOf("(合计)")>-1){
			type='sum';
		}else if(ntjl.text.indexOf("(平均)")>-1){
			type='avg';
		}
		
		var data={
		'name':ntjl.value,
		'type':type,
		'text':ntjl.text
		}
		params.push(data);
	}
	return params;
}
//导出excel
function exportExcel(queryType){
	if(cxdy.cxlx==4){
		document.getElementById("bbcxFrame").contentWindow.exportExcel('report1');
	}else{
	    var queryParams = getCxtjParams(cxtj);
	    if(cxdy.cxlx==2){
	    	addParamsTjcxtj(queryParams);
	    }
	    var queryParam=JSON.stringify(queryParams).replace(/\"/g,"'");
		var downUrl = "/cssnj/exportExcle.action?tld=Tycx002DzcxService_exportFile&sqlxh="+sqlxh+"&queryParams="+queryParam+"&queryType="+queryType;

	    if(queryType==4){
	    	var tjcxtj=getTjcxtj();
		    var wrapParams=JSON.stringify(tjcxtj).replace(/\"/g,"'");
		    downUrl=downUrl+"&wrapParams="+wrapParams;
	    }

	   window.open(downUrl);
	}
}
//保存查询条件
function saveQueryParams(){
	var cookieId = document.location.host + swryDm + cxdy.sqlxh;
	var queryParams = getCxtjParams(cxtj);
	setCookieData(cookieId,queryParams);
	 alert("保存查询条件成功!");
}
//保存到cookie
function setCookieData(name,data){
	var exp = new Date();

	exp.setTime(exp.getTime() +  (1000 * 60 * 60 * 24 * 365 * 10));//cookie保存10年
	document.cookie = name + "=" + escape(JSON.stringify(data).replace(/\"/g,"'")) + ";expires=" + exp.toGMTString();
}
function addColumns(){//该方法还未找到有效解决方法，暂停
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=Tycx002DzcxService_getCxjgl",//路径
		data:{
			'sqlxh':sqlxh
		},
		success : function(result) {//返回数据根据结果进行相应的处理
			for(var i=0;i<result.length;i++){
				var jgl=result[i];
				var llx=jgl.llx;
				if(llx=="NUMBER"){
					$("#jslleft").append("<option value='"+jgl.lmc+"' >"+jgl.lms+"</option>");
					$("#jslright").append("<option value='"+jgl.lmc+"' >"+jgl.lms+"</option>");
					$("#jslleft").comboSelect();
					$("#jslright").comboSelect();
					$("#bds").comboSelect();
				}
			}
		}  
	});
	$("#jsModal").modal({backdrop:'static'});
	
}
function deleteQueryParams(){
	var cookieId = document.location.host + swryDm + cxdy.sqlxh;
	var exp=new Date();
	exp.setTime(exp.getTime()-1);
	var cval=getCookieData(cookieId);
	if(cval!=null){
		document.cookie=cookieId+"="+cval+";expires="+exp.toGMTString();
	}
	alert("删除已保存查询条件成功!");
}

/**
*执行计算
**/
var jstables=null;
function executeJs(){
	var params=getJstj();
	var queryParams = getCxtjParams(cxtj);
	
	if(tjtables!=null){
		$("#tb_jsResult").dataTable().fnDestroy();
	}
	 tjtables= initTjTable("#tb_jsResult",sqlxh,queryParams,params,'10');
	$("#jsResultModal").modal("show");
}
//得到计算的条件
function getJstj(){
	//获取分组列和统计列
	var jslleft=document.getElementById("jslleft").options;
	var jslright=document.getElementById("jslright").options;
	
	var params = [];
	if(jslleft.length==0||jslright.length==0){
		$.cssnj.alert("请选择计算列!");		
	}
	var jslleftVal=$("#jslleft").val();
	var jslleftText=$("#jslleft").find("option:selected").text();
	var bds=$("#bds").val();
	var jslrightVal=$("#jslright").val();
	var jslrightText=$("#jslright").find("option:selected").text();
	var data={
			'name':jslleftVal+bds+jslrightVal,
			'text':jslleftText+bds+jslrightText,
			'type':'sum'
	};
	params.push(data);
	return params;
} 

//详情弹出框
function details(){
	var fxyyid=$("#fxyyid").val();
	$("#xiangqingDialog").removeData("bs.modal").find(".modal-content").empty();
	$("#xiangqingDialog").modal({
		remote:'/tykf/request_http?tld=Tycx002XiangqingService_getXiangqing&fxyyid='+fxyyid
	});
	$("#xiangqingDialog").modal('show');
}

//评价
function pj(){
	$("#pingjiaDialog").removeData("bs.modal").find(".modal-content").empty();
	var fxyyid=$("#fxyyid").val();
	$("#pingjiaDialog").modal({
		remote:'/tykf/request_http?tld=Tycx002DzcxService_initPj&fxyyid='+fxyyid
	});
	$("#pingjiaDialog").modal('show');
}

//反馈
function fk(){
	var sqlxh=$("#sqlxh").val();
	var fxyyid=$("#fxyyid").val();
	
	$("#fankuiDialog").removeData("bs.modal").find(".modal-content").empty();
	$("#fankuiDialog").modal({
		remote:'/tykf/request_http?tld=Tycx002DzcxService_openFK&sqlxh='+sqlxh+"&fxyyid="+fxyyid
	});
	$("#fankuiDialog").modal('show');
	
}

//推送
function tuisong(){
	var sqlxh=$("#sqlxh").val();
	//获取查询条件，拼接样式：条件名称：条件值;……
	var cxtjObj=cxtjData;
	var len=cxtjObj.cxtj.length;
	var cxtjmc=null;	//查询条件名称
	var cxtjVal=null;	//查询条件值
	var cxtjStr="";		//查询条件名称拼接字符串，用于保存
	var tagType=null;	//dom元素类型
	var fxyyid=$("#fxyyid").val();
	for(var i=0;i<len;i++){
		//条件名称
		cxtjmc=cxtjObj.cxtj[i].lmc;
		cxtjStr=cxtjStr+cxtjmc+":";
		cxtjmc="tj_"+cxtjmc;	//查询条件id
		var tagType=document.getElementById(cxtjmc).tagName;
		if(tagType == "INPUT"){
			cxtjVal=$('#'+cxtjmc).val();
		}else if(tagType == "SELECT"){
			cxtjVal=$('#'+cxtjmc+' option:selected').text();
		}
		cxtjStr=cxtjStr+cxtjVal+";";
	}
	
	/*$.cssnj.iframe("问题推送","/tykf/request_http?tld=Tycx002DzcxService_openTS&sqlxh="+sqlxh+"&cxtjStr="+cxtjStr+"&a="+Math.random());*/
	
	$("#tuisongDialog").removeData("bs.modal").find(".modal-content").empty();
	$("#tuisongDialog").modal({
		remote:'/tykf/request_http?tld=Tycx002DzcxService_openTS&sqlxh='+sqlxh+'&cxtjStr='+cxtjStr+'&fxyyid='+fxyyid
	});
	$("#tuisongDialog").modal('show');
}

//分享
function fenxiang(){
	var url=window.location.href;
	
	url=url.replace(/\//g,"%2F");
	url=url.replace(/\&/g,"%26");
	$("#fenxiangDialog").removeData("bs.modal").find(".modal-content").empty();
	$("#fenxiangDialog").modal({
		remote:'/tykf/request_http?tld=Tycx002DzcxService_openFenxiang&url='+url
	});
	$("#fenxiangDialog").modal('show');
	
}







