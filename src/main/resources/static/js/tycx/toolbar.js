
//查询按钮
function queryMx() {
	do_search();
}


//导出excel
function exportExcel(queryType){
alert(queryType);
	if(cxdy.cxlx==4){
		document.getElementById("bbcxFrame").contentWindow.exportExcel('report1');
	}else{
	    var queryParams = getCxtjParams(cxtj);
	    if(cxdy.cxlx==2){
	    	addParamsTjcxtj(queryParams);
	    }
	    var queryParam=JSON.stringify(queryParams).replace(/\"/g,"'");
		 var downUrl = ctx+"tykf/request?tld=Tycx002DzcxService_exportFile&sqlxh="+sqlxh+"&queryParams="+queryParam+"&queryType="+queryType;

	    if(queryType==4){
	    	var tjcxtj=getTjcxtj();
		    var wrapParams=JSON.stringify(tjcxtj).replace(/\"/g,"'");
		    downUrl=downUrl+"&wrapParams="+wrapParams;
	    }
	   window.open(downUrl);
	}

}

//统计按钮事件
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

//导出excel按钮
function exportExcel(queryType){
	var SUMMARY_TYPE_MAPPING = {
		"2" : "sum",
		"3" : "average",
		"0" : "合计"
	};
	var summaryparams = [];
	var data = tycx_service.getCxjgl({
		'sqlxh':cxdy.sqlxh
	}) ;
	if(data){
		var headParams = data.cxjgList;
		for(var i=0;i<headParams.length;i++){
			var tdParam = headParams[i];
			if (tdParam.tjlx == "2" || tdParam.tjlx == "3") { //统计类型
				summaryparams.push({
					name : tdParam.lmc,
					summaryType : SUMMARY_TYPE_MAPPING[tdParam.tjlx]
				});
			}
		}
	}
	if(cxdy.cxlx==4){
		document.getElementById("bbcxFrame").contentWindow.exportExcel('report1');
	}else{
	    var queryParams = getCxtjParams(cxtj);
		// if (cxdy. == "2" || tdParam.tjlx == "3") { //统计类型
		// 	summaryparams.push({
		// 		name : tdParam.lmc,
		// 		summaryType : SUMMARY_TYPE_MAPPING[tdParam.tjlx]
		// 	});
		// }
	    if(cxdy.cxlx==2){
	    	addParamsTjcxtj(queryParams);
	    }
	    var queryParam=JSON.stringify(queryParams).replace(/\"/g,"'");
		var queryParam1 = encodeURI(encodeURI(queryParam));
		var downUrl = ctx+"tykf/exportExcle?tld=Tycx002DzcxService_exportFile&sqlxh="+sqlxh+"&queryParams="+queryParam1+"&queryType="+queryType+"&summaryparams="+encodeURI(encodeURI(summaryparams));

	    if(queryType==4){
	    	var tjcxtj=getTjcxtj();
		    var wrapParams=JSON.stringify(tjcxtj).replace(/\"/g,"'");
			var queryParam2 = encodeURI(encodeURI(wrapParams));
		    downUrl=downUrl+"&wrapParams="+queryParam2;
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

//删除已保存查询条件
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

//详情弹出框
function details(){
	var fxyyid=$("#fxyyid").val();
	cssnj_iframe("应用详情说明",ctx+'tykf/request_http?tld=YyfwService_getXiangqing&fxyyid='+fxyyid,{
		
	}); 
}

//评价
function pj(){
	var fxyyid=$("#fxyyid").val();
	cssnj_iframe("评价",ctx+'tykf/request_http?tld=YyfwService_initPj&fxyyid='+fxyyid,{
		isMax:true
	}); 
 
}

//反馈
function fk(){
	
	var sqlxh=$("#sqlxh").val();
	var fxyyid=$("#fxyyid").val();
	cssnj_iframe("反馈",ctx+"tykf/request_http?tld=Tycx002DzcxService_openFK&sqlxh="+sqlxh+"&fxyyid="+fxyyid,{
		
	}); 
	
}

//推送
function tuisong(){
	var sqlxh=$("#sqlxh").val();
	
	var queryParams = getCxtjParams(cxtj);
	//alert(JSON.stringify(queryParams))
	
	var queryArr = new Array();
	for(var i=0;i<queryParams.length;i++){
		var obj =queryParams[i];
		queryArr.push(obj["name"]+":"+obj["value"]);
	}
	
	var queryStr = queryArr.join(",");
	// queryStr = "{"+encodeURIComponent(queryStr)+"}";
	queryStr = encodeURI(encodeURI(queryStr));
  	var tzurl = ctx+"tykf/request_http?tld=Tycx002DzcxService_tuisong&sqlxh="+sqlxh+"&queryStr="+queryStr;
 	window.open(tzurl);
	
}

//分享
function fenxiang(){
 
	var fxyyid=$("#fxyyid").val();
    cssnj_iframe("分享",ctx+'tykf/request_http?tld=YyfwService_openFenxiang&fxyyid='+fxyyid,{
		
	}); 
    
    
}

//收藏
function shoucang(){
	
	var fxyyid=$("#fxyyid").val();
	
    cssnj_iframe("收藏",ctx+'tykf/request_http?tld=YyfwService_openSc&fxyyid='+fxyyid,{
    	width:"400px",
    	height:"600px",
    	resizable:false
	}); 
  
}
