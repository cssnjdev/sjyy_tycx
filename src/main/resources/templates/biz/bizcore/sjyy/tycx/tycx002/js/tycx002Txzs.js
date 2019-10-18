var chartOption= null ;
$(function(){
	
	$("#barAndLine").height($(window).height()-20);
	
	var txuuid=getQueryString('txuuid');
	var sqlxh=getQueryString(sqlxh); 
	
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		url:ctx+"tykf/request?tld=Tycx002DzcxService_executeQueryCharts",//路径
		data:{'txuuid':txuuid,
		'sqlxh':sqlxh},
		success : function(result) {//返回数据根据结果进行相应的处理
			
			if(result){
				
				if(result[0].charData){
					//initEchart(obj,chartsParams[0].charData);
					chartOption=echarts.init(document.getElementById('barAndLine'));
					chartOption.setOption(result[0].charData);
					
				}else{
					alert("未查询到数据");
				}
				
			}
			
			//$('#table').bootstrapTable('load', result);
			
			var chartOption2=echarts.init(document.getElementById('bar'));
 			chartOption2.setOption(result[1]);
		}  
	});
	
	
});

window.onresize=function(){
	 
	if(chartOption!=null){
		$("#barAndLine").height($(window).height()-20);
		chartOption.resize();
		chartOption.resize();
	}
}

function getQueryString(name){
	var reg = new RegExp("(^|&)"+name+"=([^&*])(&|$)","ig");
	 
    var url = window.location.href;
   
    if(url.indexOf("?")>-1){
    	
    	url = url.substring(url.indexOf("?")+1, url.length);
    	var arr = url.split("&");
    	for(var i =0;i<arr.length;i++){
    		if(name==arr[i].split("=")[0]){ 
    			return arr[i].split("=")[1] 
    		} 								 
    	}			
    				
    } 				
    				
    return null;	
      
}