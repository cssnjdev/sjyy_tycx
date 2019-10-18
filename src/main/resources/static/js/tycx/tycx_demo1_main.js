var d = null;
var cxdy = null;
var cxtj = null;
var sqlxh=null;
var boxsqlxh = null; //最原始的sqlxh
var swryDm=null;
var cxtjData=null;//查询条件，用于后续推送使用
var djxh=null;//djxh，主要用于一户式查询
var winlayout = null;
var fxdata = null ;
var singtonData = null;
var table = null;
var echartObj = null;
var tableObj =null;

var coldata = null;//展现列信息
var chartType = null; //图行类型

var queryParams = null; //查询参数 每次执行查询时重新初始化

var zqcs = []; // 钻取参数

var indexQueryParam = new Object(); //

var initQueryParams = null; //页面初始查询参数 包含查询列 queryParams 和钻取参数 zqcs 查询数组 和分析角度 

function do_search(){
	if(!validateQueryParams(cxtj)){
		alert("查询条件校验不通过");
		return;
	}
	
	queryParams = getCxtjParams(cxtj);
	
 	if(cxdy.cxlx==2||cxdy.cxlx==3){
 		
 		queryParams=  addParamsTjcxtj(queryParams);
 		
 		if(typeof tableObj=="object"){
 	 		delete dataTableObjects["dt_"+sqlxh];  		
 			$("#tb_departments").empty(); 		
 		}
 		
 	}
	
 	//alert(JSON.stringify(queryParams));

 	var index = $("#sqlList").find("a").last().attr("index"); 	 
 	
 	//保存当前查询的 查询条件数据
 	indexQueryParam[index] = queryParams;
 	

	if(singtonData){ //如果报表使用报表查询
	
	   var page= singtonData.PAGEURL;
	   
	   $.get(page, function(result){
 		 // $("#singTonBox").append(result,sqlxh);
 		   loadSington("#singTonBox",result,sqlxh,queryParams,cxdy,coldata);
	   });
	   
	}else{
		// table = $("#tb_departments").fyTable(ctx+"tykf/request?tld=tycx001CxCxdyService_selectCxdy&a=" + Math.random(),data);
 	 	tableObj = initTable("#tb_departments",sqlxh,queryParams,cxdy,fxdata); // 初始化表格数据
		//loadtable.js
	}
 	
 	
}

//加载图表数据
function do_loadChart(wdArr,dataArr,dlArr,wdzd,sqlxh){
	if(wdArr.length==0){//如果维度没勾选，不显示图像
		$("#chart_departments").hide();
		return false;
	}
	if(dlArr.length==0){//如果度量没勾选，不显示图像
		$("#chart_departments").hide();
		return false;
	}
 	if(wdArr.length<1){
 		$("#chart_departments").hide();
 		return false;
	}
 	
	var chartbox = document.getElementById('chart_departments');
  
	if(chartType=='list'){
		
		if(!$("#chart_departments").is(':hidden')){
			$("#chart_departments").hide();
			resetLayout();
		}
		
	}else{
		
		if($("#chart_departments").is(':hidden')){
			$("#chart_departments").show();
			resetLayout();
			if(echartObj){
				 echartObj.resize();
			}
		}
		
	}
	
	
 	if(chartType=="bar"){
 		echartObj=loadChartAsBar2(chartbox,wdArr,dataArr,dlArr,"");
	}else if(chartType=="line"){
 		echartObj=loadChartAsBar(chartbox,wdArr,dataArr,dlArr,"",fxdata);
	}else if(chartType=="pie"){
		echartObj=loadChartAsPie(chartbox,wdArr,dataArr,dlArr,"",true);
	}else if(chartType=="radar"){
		echartObj=loadChartAsRadar(chartbox,wdArr,dataArr,dlArr,"");
	}
	// if(!fxdata.messageCode==-1){
	// 	$("#chart_departments").hide();
	// }
	 
}
//初始化查询的配置数据
function initData(){
	
	//获取查询定义
	var  data = tycx_service.selectCxdyAndCxtj({"sqlxh":sqlxh});
	if(data){
		cxdy = data.cxdy;
		cxtj = data.cxtj;
		swryDm=data.swryDm;
		cxtjData=data;
	}
	
	coldata = tycx_service.getColdata({"sqlxh":sqlxh});

 	fxdata = tycx_service.getWdxx({"sqlxh":sqlxh});
	singtonData =  tycx_service.searchSington({"sqlxh":sqlxh});
	
}



function initBody(){
	// if(fxdata.messageCode==-1){
	// 	return;
	// }
	/*初始化图表查询工具栏*/
	$("#chartTool").empty();
	// if(!fxdata.messageCode==-1){
	if(fxdata){
 		if(fxdata.SHOWTYPE){
			showArr = fxdata.SHOWTYPE.split(",");
			for(var i=0;i<showArr.length;i++){
				$("#chartTool").append(' <label><input type="radio" name="chartType" value="'+showArr[i]+'"/><a  class="'+showArr[i]+'"  ></a></label>')
			}
		}
 		
 		var layout = fxdata.LAYOUT;
 		if(layout=="left"){
 			$("#chart_departments").addClass("f_left").removeClass("f_right");
 			$("#tb_departments").addClass("f_right").removeClass("f_left");
 		}else if(layout=="right"){
 			$("#chart_departments").addClass("f_right").removeClass("f_left");
 			$("#tb_departments").addClass("f_left").removeClass("f_right");
 		}else if(layout=="top"){
 			$("#chart_departments").after($("#tb_departments")).css("width","100%");
 			$("#tb_departments").css("width","100%");
 		}else if(layout=="bottom"){ 
 			$("#chart_departments").before($("#tb_departments")).css("width","100%");
 			$("#tb_departments").css("width","100%"); 
 		}else{//默认靠右
 			fxdata.LAYOUT = "right";
 			$("#chart_departments").addClass("f_right").removeClass("f_left");
 			$("#tb_departments").addClass("f_left").removeClass("f_right");
 		}
 		
	}
	
	$("#chartTool input").first().attr("checked",true);
	$("#chartTool").append(' <label><input type="radio" name="chartType" value="list"/><a  class="list"  ></a></label>')
 	 
	chartType =$("input[name='chartType']:checked").val();
	
	$("input[name='chartType']").change(function(){
	
		chartType =$("input[name='chartType']:checked").val();
		
		if(chartType=="pie"){
			dt_checkedFirstCol(sqlxh,fxdata);
		}else{
			dt_checkedAllCol(sqlxh);
		}
		
		dataTable2Chart(sqlxh);
		 
	});
	/*初始化图表查询工具栏结束*/

	/**
	 * 判断是报表查询
	 */
	if(singtonData){
		
 		$("#scrollBox").hide();
 		$("#singTonBox").empty();
		$("#singTonBox").show();
		 
	}else{
		
		$("#scrollBox").show();
		$("#singTonBox").hide();
		$("#singTonBox").empty();
		
	}
	
   
	//初始化查询条件 
	loadCxtj(cxtj,swryDm,cxdy);
	
	//初始化统计查询条件
 	if(cxdy.cxlx==2||cxdy.cxlx==3){ 
 		loadTjselect(coldata,cxdy.tjfzfs);
	}else{
		$("#tj_ul").html("");
		$("#tj_fzxm").val("");
	 	$("#tj_fzxmmc").val("");
	 	$("#fzTool").hide();
	}
 
 	//将钻取查询的，查询条件值 初始化到查询条件中
 	if(initQueryParams){
		loadQueryParams(initQueryParams); 
	} 
	
 	//优化页面布局
	// if(!fxdata.messageCode==-1){
		resetLayout();
	// }

	var queryStr = getQueryString("queryStr");
		 
	if(queryStr){
 
	  var str = queryStr.substring(1,queryStr.length-1);
	  var arr = str.split(",")
	  
	  var tjArr = new Array();
	  
	  for(var i=0;i<arr.length;i++){
		  
		  var b = arr[i];
		 
		  tjArr.push({"name":b.split(":")[0],"value":b.split(":")[1]})
		  
	  }
	 
	  loadQueryParams(tjArr)
 	  
	}
	
	var noTj = getQueryString("noTj");
 	if(noTj==1){
 		$("#cxtj_panel").hide();
		$("#moreBox").hide();
	}
	
 	if(typeof parent.setWinHeight == "function"){
 		
 		var h = window.screen.height;
 		parent.setWinHeight(h+1);
 	}
 	
 
	//开始查询数据(初始化不执行查询)
	// do_search();
 
}

$(function(){
	sqlxh=$("#sqlxh").val();
	boxsqlxh = $("#sqlxh").val();	
	djxh =$("#djxh").val(); 		
	
	initData();
	
	$("#sqlList").empty().append("<a href='javascript:void(0)' onclick='searchUrlSql(this)' index='0' sqlxh='"+sqlxh+"' >"+cxdy.sqlmc+"</a>");

	
	initBody();
	 
	
 	
 	
});


window.onresize=function(){
	// if(!fxdata.messageCode==-1) {
		resetLayout();
	// }
	 if(echartObj){
		 echartObj.resize();
	 }
	 
}

 
function resetLayout(){
	// if(fxdata==null){
	if(fxdata.messageCode==-1){
		$("#chart_departments").hide();
		$(".chartTool").hide();
		$("#tb_departments").css("width","100%");
		return ;
	}

	 $(".chartTool").show();
	
	 var layout = fxdata.LAYOUT;
		
	if(layout=="left"||layout=="right"){
	 
		 var winW = $(window).width();
	 	 
		 if(winW<800){
			 $("#chart_departments").css("width","100%");
			 $("#tb_departments").css("width","100%");
		 }else{
			 
			 if($("#chart_departments").is(':hidden')){
				 $("#tb_departments").css("width","100%");
			 }else{
				 $("#chart_departments").css("width","50%");
				 $("#tb_departments").css("width","50%");
			 }
			 
		 }
	 
	}
	
	if($.getDataTable("dt_"+sqlxh)){
		  $.getDataTable("dt_"+sqlxh).columns.adjust(); 
	}
	 
}


function getInitQeuryParam(moreParams){  
	
   var params = getCxtjParams(cxtj);	
   
   if(cxdy.cxlx==2||cxdy.cxlx==3){
	   params=  addParamsTjcxtj(params);
   }
   
   var new_params =[];					
   var flag =true; 						
   										
   for(var i=0;i<params.length;i++){	//
	   var obj = params[i];						
	   flag=true;								
       for(var j=0;j<moreParams.length;j++){	//是否存在
    	   var mobj = moreParams[j];			
    	   if(mobj["name"]==obj["name"]){		
    		   obj["value"] = mobj["value"];
    		   new_params.push(obj);			
    		   flag = false;						
     		   break;							
    	   }
       }
       
       if(flag){					
    	   new_params.push(obj);
       }
	    
   }
  
   for(var j=0;j<moreParams.length;j++){
	   
	   var mobj = moreParams[j];
	   flag=true;
	   
	   for(var k=0;k<new_params.length;k++){	
		   var nparams = new_params[k];			
		   if(mobj["name"]==nparams["name"]){	
			   flag = false;					
			   break;							
		   }
	   }										
	   												
	   if(flag){									
    	   new_params.push(mobj);				
       }
	   
   }
   
   return new_params;
 
}

/**
 * 路径中url查询方法 
 * @param obj
 */
function searchUrlSql(obj){
    
	var newSqlxh = $(obj).attr("sqlxh") ;
	
	var index = $(obj).attr("index");
  	
	if(typeof tableObj=="object"){ // 清空表格
		
		try{
			delete dataTableObjects["dt_"+sqlxh];  	
		}catch(e){
			
		}
 		$("#tb_departments").empty(); 		
		
	}
	 
	 
	if(typeof echartObj=="object"){ //清空图表
		try{
			echartObj.clear();
		}catch(e){
			
		}
 	}
	 
	sqlxh = newSqlxh;
 
	//取出当前查询的查询条件
 	initQueryParams = indexQueryParam[index]; //
	
	//获取当前查询的后天配置数据
	initData();
	
	initBody();
	
	$(obj).nextAll().remove();
	
}


function cssnj_tycx_colClick(aData,rowEl,col,colParam,dataTable,id){
	var isHj = $(rowEl).hasClass("hj_tr");
	if(isHj){
		return false;
	}
	
	var url=colParam.url;
	
	if(!url){
		return false;
	}
	
 	if(url.indexOf("SQLXH:")>=0){
		var sqlxhdm = url.split(":")[1];
		url = aData[sqlxhdm];
	}
	
	var urlmc=colParam.urlmc;
	var xzcs=colParam.xzcs;
	var xzfs = colParam.xzfs;
	
	var cs=[];
	if(xzcs!=""&&xzcs!=null){ // 下钻参数
		 
		var xzcsArr=xzcs.split(",");
	     
		for(var m=0;m<xzcsArr.length;m++){

			var csm=xzcsArr[m];

			if(colParam.lbm){
				csm=colParam.lbm;
			}
			var real_value;

			real_value=aData[xzcsArr[m]];
			cs[m]={
				name : csm,
				value : real_value
			};

		}
		
	}
	
 
	var urlArr=url.split(";");
 
	var urlmcArr=[];
	
	if(urlmc!=null&&urlmc!=''){
		 urlmcArr=urlmc.split(";");
	}
	 
	if(urlArr.length>1){
		
		$("#listgroup").empty();
		
		for(var i=0;i<urlArr.length;i++){
 			 $("#listgroup").append(" <li class='list-group-item' sqlxh='"+urlArr[i]+"'  >"+"<a  href='javascript:void(0)' class='do_click' onclick='zqcx($(this).parent())'  >"+urlmcArr[i]+"</a></li>");
		}	
		$("#listgroup").css("left",event.clientX+10);
	 	$("#listgroup").css("top",event.clientY); 	
	    $("#listgroup").show();
		
		zqcs = cs;

	}
	 
 	if(urlArr.length==1){
		
 		zqcs = cs;
		$(col).attr("sqlxh",urlArr[0]);
		zqcx(col);
	    
	}
	
	 
}


function zqcx(obj){
	var oldsqlxh = sqlxh;
	
	$("#listgroup").hide();
	
	newsqlxh = $(obj).attr("sqlxh"); 
	
	if(oldsqlxh ==newsqlxh){//测试是否存在下钻数据 ，不存在这不执行下钻
		
		var b = testZqData(zqcs);
		if(!b){
			$(obj).find(">a.do_click").replaceWith($(obj).find(">a.do_click").text()); 
			return false;
		}
 	}
	
	if(typeof tableObj=="object"){
		
 		delete dataTableObjects["dt_"+sqlxh];  		
		$("#tb_departments").empty(); 		
		
	}
	 
	if(typeof echartObj=="object"){
		try{
			echartObj.clear();
		}catch(e){
			
		}
 	}
	
	sqlxh=newsqlxh;
 	
	
 	//查询
	initData();

	var mc = cxdy.sqlmc
	
	if(oldsqlxh ==newsqlxh){
		mc=$(obj).text();
 	}
	
	var index = $("#sqlList").find("a").length; 	 
	$("#sqlList").append("<b>></b><a href='javascript:void(0)' onclick='searchUrlSql(this)' sqlxh='"+sqlxh+"' index ="+index+">"+mc+"</a>");
	
	initQueryParams = getInitQeuryParam(zqcs); 
	
	initBody();
 
}


function testZqData(zqcs){
	
  	var new_queryParam = getInitQeuryParam(zqcs); 
 
 	var datas = {
 			'sqlxh':sqlxh,
  			'queryParams':JSON.stringify(new_queryParam).replace(/\"/g,"'"),
   			'queryType':cxdy.cxlx
  	};
 	
 	var data = tycx_service.testZqData(datas);
 	
 	if(data.state==1){
 	 	return true;
 	}else{
 		return false;
 	}
 	
	
}




/**
 * 创建行时的回调方法
 * @param aData
 * @param rowEl
 * @param rowIndex
 * @param dataTable
 * @param columns
 * @param id
 * @param wdzd
 */
function tycx_createRow(aData, rowEl,rowIndex, dataTable,columns,id,wdzd) {
	for(var m=0;m<columns.length;m++){	
		 									
		var column=columns[m];
	 
		if(column.yjfw!=''&&column.yjfw!=null &&column.yjfw!='undefined'){
			var v=$("td:eq(" + m + ")", rowEl)[0].outerText;
 			if(eval('('+v+column.yjfw+')')){
				$("td:eq(" + m + ")", rowEl).css('color',column.color!=null?column.color:'red');
			}
		}
		
		var name = column.name;
		
		if(!$(rowEl).hasClass("usedWd")){ // 处理维度
	  		if(wdzd&&column.xsbj==true){
	  			var wdarr = wdzd.split(",");
	  			for(var i=0;i<wdarr.length;i++){
	 				wd = wdarr[i];
	 				if(wd==name){
	 	 					var html = $("td:eq(" + m + ")", rowEl).html();
	 	 					if(rowIndex<20){//选中前20条
	 	 						$("td:eq(" + m + ")", rowEl).prepend("<input type='checkbox' checked='checked'  onchange='dt_setCheckedRow(this);dataTable2Chart(\""+dataTableObjects[id].searchParam.sqlxh+"\");' class='dataTable_check float_r wdcheck' name='"+ name + "' value='"+aData[name]+"' />");
	 	 						$(rowEl).addClass("checked_true");
	 	 					}else{//不选中
	 	 	 				 	$("td:eq(" + m + ")", rowEl).prepend("<input type='checkbox' onchange='dt_setCheckedRow(this);dataTable2Chart(\""+dataTableObjects[id].searchParam.sqlxh+"\");' class='dataTable_check float_r wdcheck' name='"+ name + "' value='"+aData[name]+"' />");
	 	 					}
	 	 					$(rowEl).addClass("usedWd");
	 				}
	 			}
			}
		}
		
		
	}
	
}

